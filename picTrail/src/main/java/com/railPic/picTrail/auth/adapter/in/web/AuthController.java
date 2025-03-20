package com.railPic.picTrail.auth.adapter.in.web;


import com.railPic.picTrail.auth.application.port.in.LoginRequest;
import com.railPic.picTrail.auth.application.port.in.LoginUseCase;
import com.railPic.picTrail.auth.application.port.in.RegisterRequest;
import com.railPic.picTrail.auth.application.port.in.RegisterUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        log.info("Register request received: {}", registerRequest); // 요청 정보 로그
        String st = registerUseCase.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(st);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = loginUseCase.login(loginRequest);
            return ResponseEntity.ok().body(token);
        }catch (Exception exception){
            return ResponseEntity.status(401).body(exception.getMessage());
        }
    }

}
