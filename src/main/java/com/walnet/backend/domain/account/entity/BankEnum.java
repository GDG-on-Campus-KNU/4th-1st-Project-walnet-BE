package com.walnet.backend.domain.account.entity;

public enum BankEnum {
    KDB("산업은행"),
    IBK("기업은행"),
    KB("국민은행"),
    SHINHAN("신한은행"),
    WOORI("우리은행"),
    HANA("하나은행"),
    NH("농협은행"),
    KAKAOBANK("카카오뱅크"),
    TOSSBANK("토스뱅크"),
    SC("SC제일은행"),
    CITY("씨티은행"),
    Deagu("대구은행");

    private final String bankName;

    BankEnum(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }
}
