package com.example.jwt_self.service;

import com.example.jwt_self.dto.JoinDto;
import com.example.jwt_self.entity.CustomUserDetails;
import com.example.jwt_self.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserDetailsRepository userDetailsRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean joinProcess(JoinDto joinDto){
        // null 검증
        if (joinDto == null || joinDto.getUsername() == null || joinDto.getPassword() == null){
            System.out.println("[JOIN] empty join form");
            return false;
        }

        // username 중복 검사
        if (userDetailsRepository.existsByUsername(joinDto.getUsername())){
            System.out.println("[JOIN] username is already exist");
            return false;
        }

        // join process
        CustomUserDetails newUser = new CustomUserDetails();
        newUser.setUsername(joinDto.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
        newUser.setRole("ROLE_USER");

        userDetailsRepository.save(newUser);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsRepository.findByUsername(username);
    }
}
