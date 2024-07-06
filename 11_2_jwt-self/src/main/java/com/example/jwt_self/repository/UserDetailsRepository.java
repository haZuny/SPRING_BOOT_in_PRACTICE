package com.example.jwt_self.repository;

import com.example.jwt_self.entity.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<CustomUserDetails, Integer> {
    CustomUserDetails findByUsername(String username);
    Boolean existsByUsername(String username);
}
