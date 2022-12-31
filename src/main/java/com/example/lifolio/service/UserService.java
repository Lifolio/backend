package com.example.lifolio.service;


import com.example.lifolio.base.BaseException;
import com.example.lifolio.base.BaseResponseStatus;
import com.example.lifolio.dto.*;
import com.example.lifolio.entity.Authority;
import com.example.lifolio.entity.User;
import com.example.lifolio.jwt.JwtFilter;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.repository.UserRepository;
import com.example.lifolio.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    //현재 로그인한(jwt 인증된) 사용자 반환
    public User findNowLoginUser(){
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null);
    }



    public TokenRes login(LoginUserReq loginUserReq) throws BaseException {
        if(!checkUserId(loginUserReq.getUsername())){
            throw new BaseException(BaseResponseStatus.NOT_EXIST_USER_ID);
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


            //반환 값 아이디 추가
        return new TokenRes(userId, jwt);
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


}