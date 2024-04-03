package com.example.hello.repository;

import com.example.hello.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository // 스프링 컨테이너가 레포지토리 객체로 인식
public class MemoryMemberRepository implements MemberRepository {

    // 찾은 멤버를 저장
    private static Map<Long, Member> store = new HashMap<>();
    // 키값 관리 변수
    private static long sequence = 0L;


    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream().filter(member -> member.getName().equals(name)).findAny();  // findAny는 Optional로 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
        sequence = 0L;
    }
}
