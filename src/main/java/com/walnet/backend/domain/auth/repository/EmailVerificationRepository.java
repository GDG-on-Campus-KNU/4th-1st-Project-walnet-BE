package com.walnet.backend.domain.auth.repository;

import com.walnet.backend.domain.auth.Entity.EmailVerification;
import com.walnet.backend.global.exception.BusinessException;
import com.walnet.backend.global.exception.ErrorCode;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
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
        Optional<EmailVerification> emailVerification = Optional.ofNullable(em.find(EmailVerification.class, email));
        log.info("emailVerification : {}", emailVerification);
        return emailVerification;
    }

    public boolean isEmailVerified(String email) {
        Optional<EmailVerification> byEmail = findByEmail(email);
        if (byEmail.isPresent()) {
            return byEmail.get().isVerified();
        } else {
            throw new BusinessException(ErrorCode.EMAIL_NOT_FOUND);
        }
    }
}
