package com.example.hello.domain;

import jakarta.persistence.*;

// 자바 객체와 db 테이블 매핑
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identity: 키값 자동 생성
    private Long id;
    @Column(name="username")    // db 필드랑 매핑
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
