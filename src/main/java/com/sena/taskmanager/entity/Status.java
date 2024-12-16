package com.sena.taskmanager.entity;

public enum Status {
    TODO("Por hacer"),
    IN_PROGRESS("En proceso"),
    REVIEW("En revisión"),
    DONE("Hecho"),
    CANCELED("Cancelado"),
    BLOCKED("Bloqueado");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String fromValue() {
        return value;
    }
}
