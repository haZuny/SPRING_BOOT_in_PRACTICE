package com.example.hello.service;

import com.example.hello.domain.Member;
import com.example.hello.repository.MemberRepository;
import com.example.hello.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemoryMemberRepository repository) {
        this.memberRepository = repository;
    }

    // 회원가입
    public Long join(Member member) {
        // 중복 회원 탐색
        // Optional 객체가 널이면 실행, 값을 가지면 그냥 넘어감
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });

        // 회원 저장 후 아이디 반환
        memberRepository.save(member);
        return member.getId();
    }

    // 전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // 아이디로 탐색
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
