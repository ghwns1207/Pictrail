package com.railPic.picTrail.auth.application.port.out;

import com.railPic.picTrail.auth.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);

}
