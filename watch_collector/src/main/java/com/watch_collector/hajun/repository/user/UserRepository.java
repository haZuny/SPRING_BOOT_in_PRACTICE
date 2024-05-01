package com.watch_collector.hajun.repository.user;

import com.watch_collector.hajun.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    // 사용자 추가
    User addUser(User user);

    // 사용자 삭제
    Optional<User> deleteById(String id);

    // 사용자 id로 찾기
    Optional<User> findById(String id);

    // 모든 사용자 리스트 조회
    List<User> findAll();

    // 모든 사용자 삭제
    void removeAllUser();


}
