package com.sena.taskmanager.repository;

import com.sena.taskmanager.entity.Task;
import com.sena.taskmanager.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByTitleIn(List<String> names);
    List<Task> findAllByStatusIn(List<String> status);
    List<Task> findAllByAssignedToIn(List<User> assignedTo);
}
