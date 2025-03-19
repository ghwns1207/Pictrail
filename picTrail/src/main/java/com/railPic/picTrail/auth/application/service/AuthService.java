package com.railPic.picTrail.auth.application.service;


import com.railPic.picTrail.auth.application.port.in.LoginRequest;
import com.railPic.picTrail.auth.application.port.in.LoginUseCase;
import com.railPic.picTrail.auth.application.port.out.UserRepository;
import com.railPic.picTrail.config.security.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements LoginUseCase {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;

    @Override
    public String login(LoginRequest loginRequest) {
        return null;
    }
}
