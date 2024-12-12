package com.sena.taskmanager.entity;

public enum Status {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    REVIEW("Review"),
    DONE("Done"),
    CANCELED("Canceled"),
    BLOCKED("Blocked");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String fromValue() {
        return value;
    }
}
