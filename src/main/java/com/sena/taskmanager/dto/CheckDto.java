package com.sena.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CheckDto(
        String description,
        @JsonProperty("is_checked")
        Boolean isChecked,
        @JsonProperty("task_id")
        String taskId
) {}
