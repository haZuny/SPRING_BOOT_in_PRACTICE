package com.example.hello.controller;

import com.example.hello.domain.Member;
import com.example.hello.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller     // 아노테이션을 붙이면, 스프링 컨테이너가 빌드할 때 컨트롤러로 빌드
public class MemberController {
    private  final MemberService memberService;

    @Autowired  // 컨테이너가 등록될 때, 서비스 스프링빈을 찾아서 의존관계를 주입시켜 줌
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String create(MemberForm form){  // form에 동일한 변수명이 없으면, 객체에 저장X -> null값 반환
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member); // 멤버 저장
        return "redirect:/";    // 홈 화면으로 이동
    }

    @GetMapping("members")
    public String list(Model model){    // MVC 패턴의 Model 파트에 접근
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
