package com.watch_collector.hajun.repository;

import com.watch_collector.hajun.domain.User;
import com.watch_collector.hajun.domain.Watch;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchRepository {


    // 시계 추가
    Watch addWatch(Watch watch);

    // id로 시계 찾기
    Optional<Watch> findById(int id);

    // 특정 User의 시계 목록 조회
    List<Watch> findByUser(String userId);

    // 모든 시계 목록 반환
    List<Watch> getAllWatches();

    // 시계 정보 변경
    Optional<Watch> updateWatch(Watch watch);

    // 시계 삭제
    boolean deleteWatch(Watch watch);

    // 특정 User의 시계들 제거
    boolean deleteByUser(String userId);

    // 모든 시계 초기화
    boolean removeAllWatches();

}
