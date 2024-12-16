package com.sena.taskmanager.service.interfaces;

import com.sena.taskmanager.dto.TaskDto;
import java.util.List;

public interface ITaskService {
    TaskDto createTask(TaskDto taskDto);

    List<TaskDto> getAllTasks();

    TaskDto getTaskById(Long taskId);

    TaskDto updateTask(Long taskId, TaskDto taskDto);

    void deleteTask(Long taskId);
}
