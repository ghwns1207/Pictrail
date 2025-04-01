package com.railPic.picTrail.auth.application.port.out;

import com.railPic.picTrail.auth.domain.User;

import java.util.Optional;

public interface UserRepository {
    void addUser(User user);
    Optional<User> findByUsername(String username);
    void save(User user); // 저장

}
