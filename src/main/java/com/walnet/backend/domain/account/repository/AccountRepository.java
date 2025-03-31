package com.walnet.backend.domain.account.repository;

import com.walnet.backend.domain.account.entity.Account;
import com.walnet.backend.domain.member.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountRepository {
    private final EntityManager em;

    public Long save(Account account) {
        em.persist(account);
        return account.getId();
    }

    public Optional<Account> findById(final Long id) {
        return Optional.ofNullable(em.find(Account.class, id));
    }

}
