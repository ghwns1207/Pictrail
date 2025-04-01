package com.railPic.picTrail.auth.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private String username;
    private String password;
    private String email;

    // 비밀번호 변경 로직을 엔티티에 위임
    public void changePassword(String rawPassword) {
        this.password = rawPassword;
    }

}
