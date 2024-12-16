package com.sena.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sena.taskmanager.entity.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
        private String title;
        private String description;
        private List<String> tags;
}
