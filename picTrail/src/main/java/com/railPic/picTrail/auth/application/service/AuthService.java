package com.railPic.picTrail.auth.application.service;


import com.railPic.picTrail.auth.application.port.in.LoginRequest;
import com.railPic.picTrail.auth.application.port.in.LoginUseCase;
import com.railPic.picTrail.auth.application.port.in.RegisterRequest;
import com.railPic.picTrail.auth.application.port.in.RegisterUseCase;
import com.railPic.picTrail.auth.domain.User;
import com.railPic.picTrail.config.security.JwtProvider;
import com.railPic.picTrail.util.InMemoryAuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService implements LoginUseCase, RegisterUseCase {

    private final InMemoryAuthUserRepository userRepository;

    //private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public String register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.username()).isPresent()) {
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }
        String hashedPassword = passwordEncoder.encode(registerRequest.password());
        userRepository.addUser(new User(registerRequest.username(), hashedPassword, registerRequest.email()));
        log.info("User : {}", userRepository.findAll());
        return "회원가입 성공!";
    }

    @Override
    public String login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        if (userOptional.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword())) {
            throw new RuntimeException("잘못된 로그인 정보입니다.");
        }
        return jwtProvider.generateToken(userOptional.get().getUsername());
    }


}
