package com.railPic.picTrail.profiles.application.service;

import com.railPic.picTrail.profiles.application.port.in.UpdateProfile;
import com.railPic.picTrail.profiles.application.port.in.UpdateProfileUseCase;
import com.railPic.picTrail.profiles.application.port.in.UpdatePwUseCase;
import com.railPic.picTrail.util.InMemoryAuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class profileService implements UpdatePwUseCase, UpdateProfileUseCase {

    private final InMemoryAuthUserRepository userRepository;

    //private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String updatePw(String username, String password) {
        String hashedPw = passwordEncoder.encode(password);
        log.info(hashedPw);
        return userRepository.findByUsername(username)
                .map(exUser -> {
                    exUser.changePassword(hashedPw);
                    return "비밀번호가 성공적으로 변경되었습니다.";
                }).orElse("사용자를 찾을 수 없습니다.");
    }

    @Override
    public String updateProfile(UpdateProfile updateProfile) {
        return userRepository.findByUsername(updateProfile.username())
                .map(exUser-> {
                    exUser.setEmail(updateProfile.email());
                    exUser.setUsername(updateProfile.username());
                    return "회원 정보 수정을 성공적으로 변경되었습니다.";
                }).orElse("사용자를 찾을 수 없습니다.");
    }
}
