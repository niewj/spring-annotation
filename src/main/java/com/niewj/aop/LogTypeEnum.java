package com.niewj.aop;

public enum LogTypeEnum {
    BEFORE("@Before"),
    AFTER("@After"),
    AFTER_RETURNING("@AfterReturning"),
    AFTER_THROWING("@AfterThrowing"),
    AROUND("@Around");

    private String value;

    LogTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
