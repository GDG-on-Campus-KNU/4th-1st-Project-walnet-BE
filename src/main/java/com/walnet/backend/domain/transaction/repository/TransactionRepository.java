package com.walnet.backend.domain.transaction.repository;

import com.walnet.backend.domain.transaction.entity.Transaction;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {
    private final EntityManager em;

    public Long save(Transaction transaction) {
        em.persist(transaction);
        return transaction.getId();
    }

    public Optional<Transaction> findById(Long id) {
        return Optional.ofNullable(em.find(Transaction.class, id));
    }
}
