package com.sena.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_check")
public class Check {
    @Id
    @GeneratedValue
    private Integer id;
    private String description;
    @JsonProperty("is_checked")
    private Boolean isChecked;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
}
