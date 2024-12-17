package com.sena.taskmanager.repository;

import com.sena.taskmanager.entity.Check;
import com.sena.taskmanager.entity.Task;
import com.sena.taskmanager.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckRepository extends JpaRepository<Check, Long> {
    List<Check> findAllByTask(Task task);

    Optional<Check> findByIdAndTask(Long checkId, Task task);

    List<Check> findAllByIdInAndTask(List<Integer> checkIds, Task task);
}
