package com.walnet.backend.domain.wallet.repository;

import com.walnet.backend.domain.member.entity.Member;
import com.walnet.backend.domain.transaction.entity.Transaction;
import com.walnet.backend.domain.wallet.entity.Wallet;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WalletRepository {
    private final EntityManager em;

    public Long save(Wallet wallet) {
        em.persist(wallet);
        return wallet.getId();
    }

    public Optional<Wallet> findById(Long id) {
        return Optional.ofNullable(em.find(Wallet.class, id));
    }

    public List<Wallet> findByMember(Member member) {
        return em.createQuery("select w from Wallet w where w.member = :member", Wallet.class)
                .setParameter("member", member)
                .getResultList();
    }
}