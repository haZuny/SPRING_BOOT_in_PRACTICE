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
    WatchRepository watchRepository = new MemoryWatchRepository();


    // 시계 추가
    public Watch addWatch(Watch watch){
    }

    // 시계 아이디로 찾기
    public Optional<Watch> findById(int id){}

    // 한 사용자의 시계 목록 조회
    public List<Watch> watchesByUser(User user){}

    // 전체 시계 목록 조회
    public List<Watch> getAllWatches(){}

    // 시계 정보 변경하기
    public Optional<Watch> updateWatch(Watch watch){}

    // 시계 삭제하기
    public boolean deleteWatch(Watch watch){}

    // 한 사용자의 시계 삭제하기
    public boolean deleteUserWatch(User user){}

    // 전체 시계 삭제하기
    public boolean deleteAllWatches(){}

}
