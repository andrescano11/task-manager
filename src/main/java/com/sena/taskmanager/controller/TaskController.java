package com.sena.taskmanager.controller;

import com.sena.taskmanager.dto.TaskDto;
import com.sena.taskmanager.entity.Task;
import com.sena.taskmanager.service.interfaces.ITaskService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/task")
public class TaskController {
    ITaskService service;

    public TaskController(ITaskService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasPermission('permission', 'create_task')")
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(service.createTask(taskDto), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasPermission('permission', 'read_task')")
    public ResponseEntity<List<TaskDto>> getTasks(HttpServletRequest servletRequest) {
        return new ResponseEntity<>(service.getAllTasks(), HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    @PostAuthorize("hasPermission('permission', 'read_task')")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long taskId) {
        return new ResponseEntity<>(service.getTaskById(taskId), HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasPermission('permission', 'update_task')")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId, @RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(service.updateTask(taskId, taskDto), HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasPermission('permission', 'delete_task')")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        service.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
