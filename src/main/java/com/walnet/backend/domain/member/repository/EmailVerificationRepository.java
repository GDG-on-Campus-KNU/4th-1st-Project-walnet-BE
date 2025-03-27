package com.walnet.backend.domain.member.repository;

import com.walnet.backend.domain.member.entity.EmailVerification;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmailVerificationRepository {
    private final EntityManager em;

    public void save(EmailVerification emailVerification) {
        if (findByEmail(emailVerification.getEmail()).isEmpty()) {
            em.persist(emailVerification);
        } else {
            em.merge(emailVerification);
        }
    }

    public Optional<EmailVerification> findByEmail(String email) {
        return Optional.ofNullable(em.find(EmailVerification.class, email));
    }
}
