package com.example.hello;

import com.example.hello.controller.MemberController;
import com.example.hello.repository.JdbcMemberRepository;
import com.example.hello.repository.MemberRepository;
import com.example.hello.repository.MemoryMemberRepository;
import com.example.hello.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration  // @Bean을 사용하는 클래스는, Configuration 클래스로 등록 해주어야함
public class SpringConfig {
    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean   // 싱글톤 스타일로 스프링 빈 생성
    public MemberRepository memberRepository() {
        // return new MemoryMemberRepository();
        return new JdbcMemberRepository(dataSource);
    }
}