package com.example.hello.service;

import com.example.hello.domain.Member;
import com.example.hello.repository.MemberRepository;
import com.example.hello.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional  // 테스트 끝나면 DB 롤백(커밋X, 테스트만)
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
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
    }
}