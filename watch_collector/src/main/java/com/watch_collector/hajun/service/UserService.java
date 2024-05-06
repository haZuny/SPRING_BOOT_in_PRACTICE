package com.watch_collector.hajun.service;

import com.watch_collector.hajun.domain.User;
import com.watch_collector.hajun.domain.Watch;
import com.watch_collector.hajun.repository.MemoryUserRepository;
import com.watch_collector.hajun.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository repository = new MemoryUserRepository();
    private WatchService watchService = new WatchService();

    // 회원가입
        // 동일한 아이디가 있으면 회원가입 불가
    public boolean join(User user){
        if (repository.findById(user.getId()).isPresent())  return false;
        repository.addUser(user);
        return true;
    }

    // 회원 탈퇴
        // 존재하지 않는 아이디 처리
        // 해당 User의 시계 모두 제거
    public boolean withdraw(User user){
        if (repository.findById(user.getId()).isPresent()){
            watchService.deleteUserWatch(user);
            repository.deleteById(user.getId());
            return true;
        }
        return false;
    }

    // 유저의 시계 반환
        // 해당 유저가 없는 경우
    public List<Watch> watchesByUser(User user){
        return watchService.watchesByUser(user);
    }

    // 아이디로 회원 조회
    public Optional<User> findUser(String id){
        return repository.findById(id);
    }

    // 회원 목록 조회
    public List<User> findAllUser(){
        return repository.findAll();
    }

    // 초기화
    public void resetAllUser(){
        repository.removeAllUser();
    }
}
