package com.example.hello.repository;

import com.example.hello.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 메소드가 끝나고 실행되는 콜백 메소드
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("권하준 테스트");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();  // .get: Optional에서 꺼냄
        Assertions.assertEquals(result, null);    // 두 객체가 같은 객체인지 테스트
    }

    @Test
    public void findByName(){
        Member member = new Member();
        member.setName("findByName Test");

        repository.save(member);

        Member result = repository.findByName("findByName Test").get();

        Assertions.assertEquals(member, null);
    }
    
    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("11");
        repository.save(member1);
        
        Member member2 = new Member();
        member2.setName("22");
        repository.save(member2);

        List<Member> result = repository.findAll();

        Assertions.assertEquals(3, result.size());
    }
}
