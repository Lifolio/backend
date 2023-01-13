package com.example.lifolio.service;


import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponseStatus;
import com.example.lifolio.converter.UserConverter;
import com.example.lifolio.dto.user.*;
import com.example.lifolio.entity.*;
import com.example.lifolio.jwt.JwtFilter;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.repository.*;
import com.example.lifolio.util.SecurityUtil;
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


import java.sql.SQLException;
import java.util.Collections;
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
    private final ArchiveRepository archiveRepository;
    private final AlarmRepository alarmRepository;
    private final RedisService redisService;

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
    public UserRes.TokenRes login(UserReq.LoginUserReq loginUserReq) throws BaseException {

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

        UserRes.GenerateToken generateToken=createToken(userId);


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + generateToken.getAccessToken());

        //refresh token 저장
        redisService.saveToken(String.valueOf(userId),generateToken.getRefreshToken(), (System.currentTimeMillis()+ 365*(1000 * 60 * 60 * 24 * 365)));




        //반환 값 아이디 추가
        return new UserRes.TokenRes(userId,generateToken.getAccessToken(),generateToken.getRefreshToken(),user.getName());

    }

    @Transactional(rollbackFor=SQLException.class)
    @SneakyThrows
    public UserRes.TokenRes signup(UserReq.SignupUserReq signupUserReq) throws BaseException {

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
        String refreshToken =tokenProvider.createRefreshToken(userId);


        postAlarmUser(userId);

        return new UserRes.TokenRes(userId,jwt,refreshToken, user.getName());

    }

    @Transactional(readOnly = true)
    public UserReq.SignupUserReq getUserWithAuthorities(String username) {
        return UserReq.SignupUserReq.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public UserReq.SignupUserReq getMyUserWithAuthorities() {
        return UserReq.SignupUserReq.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null));
    }


    public boolean checkNickName(String nickName) {
        return userRepository.existsByNickname(nickName);
    }

    public boolean checkUserId(String userId) {
        return userRepository.existsByUsername(userId);
    }


    //비밀번호 로직
    @SneakyThrows
    public UserRes.PasswordRes setNewPassword(UserReq.PasswordReq passwordReq){ //새 비밀번호로 바꾸기
        User user = userRepository.findByUsernameEquals(passwordReq.getUsername());
        if(user != null){
            user.setPassword(passwordEncoder.encode(passwordReq.getNewPassword()));
            userRepository.save(user);
            return new UserRes.PasswordRes(passwordReq.getNewPassword());
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

    public String findUserId(UserReq.FindUserIdReq findUserIdReq) throws BaseException {
        User user =userRepository.findByNameAndPhone(findUserIdReq.getName(),findUserIdReq.getPhone());
        if(user==null){
            throw new BaseException(NOT_CORRECT_USER);
        }
        return user.getUsername();
    }

    public UserRes.TokenRes reIssueToken(Long userId) {
        User user=userRepository.getOne(userId);
        UserRes.GenerateToken generateToken=createToken(userId);

        //Redis 에 RefreshToken 저장
        redisService.saveToken(String.valueOf(userId),generateToken.getRefreshToken(),(System.currentTimeMillis()+ 365*(1000 * 60 * 60 * 24 * 365)));

        return new UserRes.TokenRes(userId,generateToken.getAccessToken(),generateToken.getRefreshToken(),user.getName());
    }

    public UserRes.GenerateToken createToken(Long userId){
        String accessToken=tokenProvider.createToken(userId);
        String refreshToken=tokenProvider.createRefreshToken(userId);

        redisService.saveToken(String.valueOf(userId),refreshToken, (System.currentTimeMillis()+ 365*(1000 * 60 * 60 * 24 * 365)));

        return new UserRes.GenerateToken(accessToken,refreshToken);
    }

    @Transactional(rollbackFor=SQLException.class)
    public void postAlarmUser(Long userId){
        Alarm alarm=UserConverter.postAlarm(userId);
        alarmRepository.save(alarm);
    }
}