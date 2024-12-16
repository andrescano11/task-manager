package com.sena.taskmanager.repository;

import com.sena.taskmanager.entity.Role;
import com.sena.taskmanager.entity.Task;
import com.sena.taskmanager.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTitle(String name);
    List<Task> findAllByTitleIn(List<String> names);
    List<Task> findAllByStatusIn(List<String> status);
    List<Task> findAllByAssignedToIn(List<User> assignedTo);
    List<Task> findAllByCreatedByIn(List<User> createdBy);
    List<Task> findAllByTagsIn(List<String> tags);
    List<Task> findAllByDueDateBefore(LocalDateTime dueDate);
}
