package com.example.jwt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntiry {
    @Id // 기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 DB에 위임
    private int id;

    @Column(unique = true)
    private String username;
    private String password;

    private String role;    // security에서의 role(ex. admin)

}
