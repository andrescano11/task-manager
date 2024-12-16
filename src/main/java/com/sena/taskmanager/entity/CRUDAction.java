package com.sena.taskmanager.entity;

public enum CRUDAction {
    CREATE("create"),
    READ("read"),
    UPDATE("update"),
    DELETE("delete");

    private final String value;

    CRUDAction(String value) {
        this.value = value;
    }

    public String fromValue() {
        return value;
    }
}
