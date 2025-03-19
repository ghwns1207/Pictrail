package com.railPic.picTrail.auth.adapter.in.web;


import com.railPic.picTrail.auth.application.port.in.LoginRequest;
import com.railPic.picTrail.auth.application.port.in.LoginUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private LoginUseCase loginUseCase;

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
