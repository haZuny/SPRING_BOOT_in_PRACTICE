package com.watch_collector.hajun.service;

import com.watch_collector.hajun.domain.User;
import com.watch_collector.hajun.domain.Watch;
import com.watch_collector.hajun.repository.MemoryWatchRepository;
import com.watch_collector.hajun.repository.WatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WatchService {
    WatchRepository repository = new MemoryWatchRepository();


    // 시계 추가
    public Watch addWatch(Watch watch, User user){
        watch.setUserId(user.getId());
        return repository.addWatch(watch);
    }

    // 시계 아이디로 찾기
    public Optional<Watch> findById(int id){
        return repository.findById(id);
    }

    // 한 사용자의 시계 목록 조회
    public List<Watch> watchesByUser(User user){
        return repository.findByUser(user.getId());
    }

    // 전체 시계 목록 조회
    public List<Watch> getAllWatches(){
        return repository.getAllWatches();
    }

    // 시계 정보 변경하기
    public Optional<Watch> updateWatch(Watch watch){
        return repository.updateWatch(watch);
    }

    // 시계 삭제하기
    public boolean deleteWatch(Watch watch){
        if (repository.findById(watch.getId()).isPresent()){
            return repository.deleteWatch(watch);
        }
        else return false;
    }

    // 한 사용자의 시계 삭제하기
    public boolean deleteUserWatch(User user){
        return repository.deleteByUser(user.getId());
    }

    // 전체 시계 삭제하기
    public boolean deleteAllWatches(){
        return repository.removeAllWatches();
    }

}
