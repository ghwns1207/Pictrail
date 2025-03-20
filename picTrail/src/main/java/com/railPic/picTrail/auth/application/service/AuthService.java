package com.railPic.picTrail.auth.application.service;


import com.railPic.picTrail.auth.application.port.in.LoginRequest;
import com.railPic.picTrail.auth.application.port.in.LoginUseCase;
import com.railPic.picTrail.auth.application.port.in.RegisterRequest;
import com.railPic.picTrail.auth.application.port.in.RegisterUseCase;
import com.railPic.picTrail.auth.application.port.out.UserRepository;
import com.railPic.picTrail.auth.domain.User;
import com.railPic.picTrail.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService implements LoginUseCase, RegisterUseCase {

    private final List<User> users = new ArrayList<>(); // 메모리 저장소
    //private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public String register(RegisterRequest registerRequest) {
        if(users.stream().anyMatch(u -> u.getUsername().equals(registerRequest.username()))){
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }
        String hashedPassword = passwordEncoder.encode(registerRequest.password());
        users.add(new User(registerRequest.username(),hashedPassword,registerRequest.email()));
        log.info("User : {}",users);
            return "회원가입 성공!";
    }

    @Override
    public String login(LoginRequest loginRequest) {
        Optional<User> userOptional = users.stream()
                .filter(u -> u.getUsername().equals(loginRequest.getUsername()))
                .findFirst();
        if(userOptional.isEmpty() || !passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword())){
            throw new RuntimeException("잘못된 로그인 정보입니다.");
        }
        return jwtProvider.generateToken(userOptional.get().getUsername());
    }

}
