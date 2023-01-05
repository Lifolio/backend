package com.example.lifolio.service;


import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponseStatus;
import com.example.lifolio.dto.home.GetGoalRes;
import com.example.lifolio.dto.home.PostGoalReq;
import com.example.lifolio.dto.home.PostGoalRes;
import com.example.lifolio.dto.user.KakaoLoginRes;
import com.example.lifolio.dto.user.*;
import com.example.lifolio.entity.*;
import com.example.lifolio.jwt.JwtFilter;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.repository.*;
import com.example.lifolio.util.SecurityUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
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
import java.util.Collections;
import java.time.LocalDate;
import java.util.*;

import static com.example.lifolio.base.BaseResponseStatus.NOT_CORRECT_USER;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final GoalOfYearRepository goalOfYearRepository;
    private final CustomLifolioColorRepository customLifolioColorRepository;
    private final CustomLifolioRepository customLifolioRepository;
    private final PasswordEncoder passwordEncoder;
    private final MyFolioRepository myFolioRepository;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Value("${coolsms.key}")
    private String apiKey;
    @Value("${coolsms.secret}")
    private String apiSecret;


    //현재 로그인한(jwt 인증된) 사용자 반환
    public User findNowLoginUser(){
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null);
    }





    //회원가입, 로그인 로직
    public TokenRes login(LoginUserReq loginUserReq) throws BaseException {

        if(!checkUserId(loginUserReq.getUsername())){
            throw new BaseException(BaseResponseStatus.NOT_EXIST_USER);
        }

        User user=userRepository.findByUsername(loginUserReq.getUsername());
        Long userId = user.getId();

        if(!passwordEncoder.matches(loginUserReq.getPassword(),user.getPassword())){
            throw new BaseException(BaseResponseStatus.NOT_CORRECT_PASSWORD);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserReq.getUsername(), loginUserReq.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        String jwt = tokenProvider.createToken(userId); //user인덱스로 토큰 생성

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);


        //반환 값 아이디 추가
        return new TokenRes(userId,jwt);
    }



    @Transactional
    @SneakyThrows
    public TokenRes signup(SignupUserReq signupUserReq) throws BaseException {

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(signupUserReq.getUsername())
                .password(passwordEncoder.encode(signupUserReq.getPassword()))
                .name(signupUserReq.getName())
                .nickname(signupUserReq.getNickname())
                .phone(signupUserReq.getPhone())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        Long userId=userRepository.save(user).getId();
        String jwt=tokenProvider.createToken(userId);

        return new TokenRes(userId,jwt);

    }

    @Transactional(readOnly = true)
    public SignupUserReq getUserWithAuthorities(String username) {
        return SignupUserReq.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public SignupUserReq getMyUserWithAuthorities() {
        return SignupUserReq.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null));
    }


    public boolean checkNickName(String nickName) {
        return userRepository.existsByNickname(nickName);
    }

    public boolean checkUserId(String userId) {
        return userRepository.existsByUsername(userId);
    }




    //비밀번호 로직
    @SneakyThrows
    public PasswordRes setNewPassword(PasswordReq passwordReq){ //새 비밀번호로 바꾸기
        User user = userRepository.findByUsernameEquals(passwordReq.getUsername());
        if(user != null){
            user.setPassword(passwordEncoder.encode(passwordReq.getNewPassword()));
            userRepository.save(user);
            return new PasswordRes(passwordReq.getNewPassword());
        } else {
            return null;
        }

    }




    public int phoneNumberCheck(String to) throws CoolsmsException {

        Message coolsms = new Message(apiKey, apiSecret);

        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<6; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", to);    // 수신전화번호
        params.put("from", "01049177671");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "sms");
        params.put("text", "[Lifolio] 인증번호는 [" + numStr + "] 입니다.");


        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
        return Integer.parseInt(numStr);
    }

    public String findUserId(FindUserIdReq findUserIdReq) throws BaseException {
        User user =userRepository.findByNameAndPhone(findUserIdReq.getName(),findUserIdReq.getPhone());
        if(user==null){
            throw new BaseException(NOT_CORRECT_USER);
        }
        return user.getUsername();
    }


    public KakaoLoginRes createKakaoUser(String token) throws BaseException {

        String reqURL = "https://kapi.kakao.com/v2/user/me";
        String profileImgUrl="";
        String nickname="";
        String email = "";

        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송


            int responseCode = conn.getResponseCode();

            if (responseCode==401){
                throw new BaseException(BaseResponseStatus.INVALID_ACCESS_TOKEN);
            }

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON 파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);


            //kakao로부터 정보 받아옴
            profileImgUrl = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("profile").getAsString();
            nickname = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("name").getAsString();
            email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();

            br.close();

//            //유저 로그인 & 회원가입으로 유저 ID값 받아오기
//            if (!checkNickName(nickname)){
//
//            }
//
//            else {
//
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //임시 아이디 반환
        return new KakaoLoginRes(100, nickname, profileImgUrl, "kakao");


    }

}