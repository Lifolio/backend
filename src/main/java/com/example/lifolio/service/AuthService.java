package com.example.lifolio.service;

import com.example.lifolio.base.BaseException;
import com.example.lifolio.converter.UserConverter;
import com.example.lifolio.dto.user.UserReq;
import com.example.lifolio.dto.user.UserRes;
import com.example.lifolio.entity.User;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.repository.UserRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Value("${jwt.refresh-token-seconds}")
    private long refreshTime;
    private final RedisService redisService;

    public String getKakaoAccessToken(String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=98dde62928c7a676ea17b1241492fe0b"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:9000/auth/kakao"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    @Transactional(rollbackFor = SQLException.class)
    public UserRes.TokenRes logInKakaoUser(UserReq.SocialReq socialReq) throws BaseException {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + socialReq.getAccessToken()); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            Long id = element.getAsJsonObject().get("id").getAsLong();


            String name = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

            if (!userRepository.existsByUsernameAndSocial(String.valueOf(id), socialReq.getSocial())) {
                User user = UserConverter.postUser(String.valueOf(id), socialReq.getSocial(), name, passwordEncoder.encode("kakao"));

                Long userId = userRepository.save(user).getId();

                UserRes.GenerateToken generateToken = userService.createToken(userId);
                userService.postAlarmUser(userId);
                redisService.saveToken(String.valueOf(userId), generateToken.getRefreshToken(), (System.currentTimeMillis() + refreshTime * 1000));


                return new UserRes.TokenRes(userId, generateToken.getAccessToken(), generateToken.getRefreshToken(), user.getName());
            }

            br.close();

            User user = userRepository.findByUsernameAndSocial(String.valueOf(id), socialReq.getSocial());
            Long userId = user.getId();


            UserRes.GenerateToken generateToken = userService.createToken(userId);


            redisService.saveToken(String.valueOf(userId), generateToken.getRefreshToken(), (System.currentTimeMillis() + refreshTime * 1000));
            return new UserRes.TokenRes(userId, generateToken.getAccessToken(), generateToken.getRefreshToken(), user.getName());


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getNaverAccessToken(String code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://nid.naver.com/oauth2.0/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=V0uhhk8tOC5K67Yw5wqi");
            sb.append("&client_secret=aejWSUmwLv");
            sb.append("&redirect_uri=http://localhost:9000/auth/naver");
            sb.append("&code=" + code);
            sb.append("&state=STATE_STRING");
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    @Transactional(rollbackFor = SQLException.class)
    public UserRes.TokenRes logInNaverUser(UserReq.SocialReq socialReq) throws BaseException {

        String reqURL = "https://openapi.naver.com/v1/nid/me";

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + socialReq.getAccessToken()); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String id = element.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsString();
            String name = element.getAsJsonObject().get("response").getAsJsonObject().get("name").getAsString();
//            boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
//            String email = "";
//
//            if (hasEmail) {
//                email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
//            }


            if (!userRepository.existsByUsernameAndSocial(String.valueOf(id), socialReq.getSocial())) {
                User user = UserConverter.postUser(String.valueOf(id), socialReq.getSocial(), name, passwordEncoder.encode("naver"));
                Long userId = userRepository.save(user).getId();
                UserRes.GenerateToken generateToken = userService.createToken(userId);
                userService.postAlarmUser(userId);
                redisService.saveToken(String.valueOf(userId), generateToken.getRefreshToken(), (System.currentTimeMillis() + refreshTime * 1000));

                return new UserRes.TokenRes(userId, generateToken.getAccessToken(), generateToken.getRefreshToken(), user.getName());
            }

            br.close();
            User user = userRepository.findByUsernameAndSocial(String.valueOf(id), socialReq.getSocial());
            Long userId = user.getId();

            UserRes.GenerateToken generateToken = userService.createToken(userId);

            redisService.saveToken(String.valueOf(userId), generateToken.getRefreshToken(), (System.currentTimeMillis() + refreshTime * 1000));

            return new UserRes.TokenRes(userId, generateToken.getAccessToken(), generateToken.getRefreshToken(), user.getName());


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
