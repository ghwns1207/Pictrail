package com.railPic.picTrail.auth.application.port.in;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;


}
