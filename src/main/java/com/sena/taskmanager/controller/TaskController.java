package com.sena.taskmanager.controller;

import com.sena.taskmanager.dto.TaskDto;
import com.sena.taskmanager.entity.Status;
import com.sena.taskmanager.service.interfaces.ITaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/task")
@Tag(name = "Task", description = "Operaciones sobre las tareas")
public class TaskController {
    ITaskService service;

    public TaskController(ITaskService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasPermission('permission', 'create_task')")
    @Tag(name = "Task", description = "Crear una tarea")
    public ResponseEntity<?> createTask(@RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(service.createTask(taskDto), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasPermission('permission', 'read_task')")
    @Tag(name = "Task", description = "Obtener todas las tareas")
    public ResponseEntity<List<TaskDto>> getTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long assignedTo,
            @RequestParam(required = false) Status status) {
        return new ResponseEntity<>(service.getAllTasks(title, assignedTo, status), HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    @PostAuthorize("hasPermission('permission', 'read_task')")
    @Tag(name = "Task", description = "Obtener una tarea por su ID")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long taskId) {
        return new ResponseEntity<>(service.getTaskById(taskId), HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("hasPermission('permission', 'update_task')")
    @Tag(name = "Task", description = "Actualizar una tarea")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId, @RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(service.updateTask(taskId, taskDto), HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasPermission('permission', 'delete_task')")
    @Tag(name = "Task", description = "Eliminar una tarea")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        service.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
