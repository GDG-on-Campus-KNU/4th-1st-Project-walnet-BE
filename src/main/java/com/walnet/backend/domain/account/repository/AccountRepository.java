package com.walnet.backend.domain.account.repository;

import com.walnet.backend.domain.member.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepository {
    private final EntityManager em;

    public Long save(final Member member) {
        em.persist(member);
        return member.getId();
    }

    public Optional<Member> findById(final Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

}
