package com.sena.taskmanager.service;

import com.sena.taskmanager.dto.TaskDto;
import com.sena.taskmanager.service.interfaces.ITaskService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TaskService implements ITaskService {

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        return null;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return null;
    }

    @Override
    public TaskDto getTaskById(Long taskId) {
        return null;
    }

    @Override
    public TaskDto updateTask(Long taskId, TaskDto taskDto) {
        return null;
    }

    @Override
    public void deleteTask(Long taskId) {

    }
}
