package com.example.hello.service;

import com.example.hello.domain.Member;
import com.example.hello.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemoryMemberRepository memoryMemberRepository;
    MemberService memberService;

    // 메소드 시작 전 실행되는 콜백 메소드
    @BeforeEach
    public void beforeEach(){
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

    // 메소드가 끝나고 실행되는 콜백 메소드
    @AfterEach
    public void afterEach(){
        memoryMemberRepository.clearStore();
    }

    @Test
    void join() {
        // given
        Member memver = new Member();
        memver.setName("hello");

        // when
        Long saveId = memberService.join(memver);

        // then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertEquals(memver.getName(), findMember.getName());
    }

    @Test
    public void 중복회원예외(){
        // given
        Member member1 = new Member();
        member1.setName("중복");

        Member member2 = new Member();
        member2.setName("중복");

        // when
        memberService.join(member1);
        // 뒤의 람다를 실행했을때 발생하는 에러를 비교
        assertThrows(IllegalStateException.class, ()->memberService.join(member2));
        /** try-catch 활용
        try{
            memberService.join(member2);
            fail("예외 잡기 실패");
        } catch (IllegalStateException e){
            Assertions.assertEquals(e.getMessage(), "이미 존재하는 회원입니다.");
        }
        */


        // then
    }

    @Test
    void findMembers() {
        // given

        // when

        // then
    }

    @Test
    void findOne() {
    }
}