package com.railPic.picTrail.profiles.application.service;

import com.railPic.picTrail.profiles.application.port.in.UpdatePwUseCase;
import com.railPic.picTrail.util.InMemoryAuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class profileService implements UpdatePwUseCase {

    private final InMemoryAuthUserRepository userRepository;

    //private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public String updatePw(String username, String password) {
        String hashedPw = passwordEncoder.encode(password);
        log.info(hashedPw);
        return userRepository.updatePw(username, hashedPw);
    }
}
