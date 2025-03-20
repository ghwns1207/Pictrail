package com.railPic.picTrail.profiles.adapter.in.web;


import com.railPic.picTrail.profiles.application.port.in.UpdatePwUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class ProfilesController {

    private final UpdatePwUseCase updatePwUseCase;

    @PutMapping("/updatePw/{username}")
    public ResponseEntity<String> updatePw(@PathVariable(name = "username") String username, @RequestBody String password){
        String updateStatus = updatePwUseCase.updatePw(username,password);
        log.info("일단 여기 까지는 들어옴");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(updateStatus);
    }





}
