package com.railPic.picTrail.util;


import com.railPic.picTrail.auth.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryAuthUserRepository {
    private final List<User> authUsers = new ArrayList<>(); // 메모리 저장소

    public boolean addUser(User user) {
        return authUsers.add(user);
    }

    public Optional<User> findByUsername(String username) {
        return authUsers.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public List<User> findAll(){
        return authUsers;
    }

    public String updatePw(String username, String password){
        Optional<User> userOptional = findByUsername(username);
        if(userOptional.isPresent()){
            userOptional.get().setPassword(password);
            return "비밀번호가 성공적으로 변경되었습니다.";
        }else {
            return "사용자를 찾을 수 없습니다.";
        }
    }


}
