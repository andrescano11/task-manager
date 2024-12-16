package com.sena.taskmanager.entity;

public enum EntityType {
    TASK("task"),
    CHECK("check"),
    USER("user"),
    ROLE("role"),
    TAG("tag");

    private final String value;

    EntityType(String value) {
        this.value = value;
    }

    public String fromValue() {
        return value;
    }
}
