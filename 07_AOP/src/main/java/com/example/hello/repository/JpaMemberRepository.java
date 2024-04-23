package com.example.hello.repository;

import com.example.hello.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    // jpa 는 EntityManager을 통해서 동작
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // 테이블에 멤버 저장, member에 id까지 세팅해줌
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // jap 쿼리 문법, 객체(엔티티)를 대상으로 쿼리를 날림, Member객체 테이블에서 객체를 조회
        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
        return result;
    }
}
