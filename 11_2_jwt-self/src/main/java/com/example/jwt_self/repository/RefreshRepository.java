package com.example.jwt_self.repository;

import com.example.jwt_self.entity.RefreshEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface RefreshRepository extends JpaRepository<RefreshEntity, Integer> {

    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);
}
