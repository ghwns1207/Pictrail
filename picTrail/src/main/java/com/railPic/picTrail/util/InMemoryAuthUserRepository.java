package com.railPic.picTrail.util;

<<<<<<< HEAD
import com.railPic.picTrail.auth.application.port.out.UserRepository;
import com.railPic.picTrail.auth.domain.User;
=======

import com.railPic.picTrail.auth.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
>>>>>>> origin/master
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
<<<<<<< HEAD
public class InMemoryAuthUserRepository implements UserRepository {
    private final List<User> authUsers = new ArrayList<>(); // 메모리 저장소

    // ✔ 공통 조회 메서드
    private User findUserOrThrow(String username) {
        return authUsers.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    }

    @Override
    public void addUser(User user) {
        if (authUsers.stream().noneMatch(exUser -> exUser.getUsername().equals(user.getUsername()))) {
            authUsers.add(user);
        } else {
            // 사용자 이미 존재 시 처리 (예: 예외 던지기)
            throw new IllegalArgumentException("사용자가 이미 존재합니다.");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(findUserOrThrow(username));
    }

    @Override
    public void save(User user) {
        User exUser = findUserOrThrow(user.getUsername());
        exUser.setUsername(user.getUsername());
        exUser.setEmail(user.getEmail());
    }

    public void save(String userName, String userPw) {
        User exUser = findUserOrThrow(userName);
        exUser.changePassword(userPw);
    }

    public List<User> findAll() {
        return authUsers;
    }

//    public String updatePw(String username, String password){
//        Optional<User> userOptional = findByUsername(username);
//        if(userOptional.isPresent()){
//            userOptional.get().setPassword(password);
//            return "비밀번호가 성공적으로 변경되었습니다.";
//        }else {
//            return "사용자를 찾을 수 없습니다.";
//        }
//    }
=======
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
>>>>>>> origin/master


}
