package com.example.hello.controller;

import com.example.hello.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller     // 아노테이션을 붙이면, 스프링 컨테이너가 빌드할 때 컨트롤러로 빌드
public class MemberController {
    private  final MemberService memberService;

    @Autowired  // 컨테이너가 등록될 때, 서비스 스프링빈을 찾아서 의존관계를 주입시켜 줌
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
