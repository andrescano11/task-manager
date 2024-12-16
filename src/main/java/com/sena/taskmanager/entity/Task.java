package com.sena.taskmanager.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Builder
@Data
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String description;
    private Status status;
    @ManyToOne
    private User assignedTo;
    @ManyToOne
    private User createdBy;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "task", cascade = CascadeType.ALL)
    private List<Check> checks;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tasks_tags",
            joinColumns = @JoinColumn(
                    name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tags;
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Task() {
        this.status = Status.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
