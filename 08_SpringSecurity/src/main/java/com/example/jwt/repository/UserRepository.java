package com.example.jwt.repository;

import com.example.jwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {    // JPA Repo 사용<entity객체, pk 자료형>

    boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
}
