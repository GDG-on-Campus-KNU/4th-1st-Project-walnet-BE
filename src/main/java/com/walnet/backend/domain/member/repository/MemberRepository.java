package com.walnet.backend.domain.member.repository;

import com.walnet.backend.domain.member.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    public Optional<Member> findByEmail(String email) {
        String jpql = "select m from Member m where m.email = :email";
        List<Member> result = em.createQuery(jpql, Member.class)
                .setParameter("email", email)
                .getResultList();
        return result.stream().findFirst();
    }

    public Boolean existsByEmail(String email) {
        String jpql = "select count(m) from Member m where m.email = :email";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }
}
