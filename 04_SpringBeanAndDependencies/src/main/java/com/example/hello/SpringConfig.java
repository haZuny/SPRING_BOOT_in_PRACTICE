package com.example.hello;

import com.example.hello.controller.MemberController;
import com.example.hello.repository.MemberRepository;
import com.example.hello.repository.MemoryMemberRepository;
import com.example.hello.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // @Bean을 사용하는 클래스는, 스프링부트 설정 클래스로 등록 해주어야함
public class SpringConfig {

    @Bean   // 싱글톤 스타일로 스프링 빈 생성
    public MemoryMemberRepository memoryMemberRepository(){
        return new MemoryMemberRepository();
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memoryMemberRepository());
    }

//    @Bean
//    public MemberController memberController(){
//        return new MemberController(memberService());
//    }
}
