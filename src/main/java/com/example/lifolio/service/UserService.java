package com.example.lifolio.service;


import com.example.lifolio.base.BaseException;
import com.example.lifolio.dto.LoginUserReq;
import com.example.lifolio.dto.SignupUserReq;
import com.example.lifolio.dto.TokenRes;
import com.example.lifolio.entity.Authority;
import com.example.lifolio.entity.User;
import com.example.lifolio.jwt.JwtFilter;
import com.example.lifolio.jwt.TokenProvider;
import com.example.lifolio.repository.UserRepository;
import com.example.lifolio.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
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



    public TokenRes login(LoginUserReq loginUserReq){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserReq.getUsername(), loginUserReq.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user=userRepository.findByusername(loginUserReq.getUsername());
        Long userId = user.getId();

        String jwt = tokenProvider.createToken(userId); //user인덱스로 토큰 생성

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);


        //반환 값 아이디 추가
        return new TokenRes(userId,jwt);
    }


    @Transactional
    public TokenRes signup(SignupUserReq signupUserReq) throws BaseException {

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .username(signupUserReq.getUsername())
                .password(passwordEncoder.encode(signupUserReq.getPassword()))
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
        return userRepository.existsBynickname(nickName);
    }

    public boolean checkUserId(String userId) {
        return userRepository.existsByusername(userId);
    }


}