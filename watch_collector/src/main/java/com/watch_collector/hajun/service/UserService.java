package com.watch_collector.hajun.service;

import com.watch_collector.hajun.domain.User;
import com.watch_collector.hajun.repository.MemoryUserRepository;
import com.watch_collector.hajun.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository repository = new MemoryUserRepository();

    // 회원가입
        // 동일한 아이디가 있으면 회원가입 불가
    public boolean join(User user){
        return true;
    }

    // 회원 탈퇴
    public boolean withdraw(User user){
        return true;
    }

    // 아이디로 회원 조회
    public Optional<User> findUser(String id){
        return repository.findById(id);
    }

    // 회원 목록 조회
    public List<User> findAllUser(){
        return repository.findAll();
    }


}
