package com.example.jwt.service;

import com.example.jwt.dto.JoinDto;
import com.example.jwt.entity.UserEntity;
import com.example.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDto joinDto){

        UserEntity data = new UserEntity();

        data.setUsername(joinDto.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }
}
