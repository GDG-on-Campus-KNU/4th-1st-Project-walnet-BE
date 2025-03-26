package com.walnet.backend.domain.transaction.entity;

public enum TransactionStatusEnum {
    PENDING,     // 거래 요청됨 (대기 중)
    PROCESSING,  // 거래 처리 중 (승인 대기 등)
    COMPLETED,   // 거래 완료됨
    FAILED,      // 거래 실패 (잔액 부족, 오류 등)
    CANCELED     // 거래 취소됨
}
