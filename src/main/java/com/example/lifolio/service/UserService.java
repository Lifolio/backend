package com.example.lifolio.service;


import com.example.lifolio.dto.SignupUserReq;
import com.example.lifolio.entity.Authority;
import com.example.lifolio.entity.User;
import com.example.lifolio.repository.UserRepository;
import com.example.lifolio.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public SignupUserReq signup(SignupUserReq signupUserReq) {
        if (userRepository.findOneWithAuthoritiesByUsername(signupUserReq.getUsername()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

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

        return SignupUserReq.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public SignupUserReq getUserWithAuthorities(String username) {
        return SignupUserReq.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @Transactional(readOnly = true)
    public SignupUserReq getMyUserWithAuthorities() {
        return SignupUserReq.from(SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername).orElse(null));
    }
}