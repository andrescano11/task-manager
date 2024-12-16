package com.sena.taskmanager.service;

import static java.util.Objects.nonNull;

import com.sena.taskmanager.dto.CheckDto;
import com.sena.taskmanager.dto.TaskDto;
import com.sena.taskmanager.entity.Check;
import com.sena.taskmanager.entity.Status;
import com.sena.taskmanager.entity.Tag;
import com.sena.taskmanager.entity.Task;
import com.sena.taskmanager.entity.User;
import com.sena.taskmanager.exceptions.NotFoundException;
import com.sena.taskmanager.repository.CheckRepository;
import com.sena.taskmanager.repository.TagRepository;
import com.sena.taskmanager.repository.TaskRepository;
import com.sena.taskmanager.repository.UserRepository;
import com.sena.taskmanager.service.interfaces.ITaskService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TaskService implements ITaskService {
    TaskRepository taskRepository;
    CheckRepository checkRepository;
    UserRepository userRepository;
    TagRepository tagRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, CheckRepository checkRepository, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.checkRepository = checkRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        User createdBy = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User assignedTo = userRepository.findById(Math.toIntExact(taskDto.getAssignedTo()))
                    .orElse(null);
        Task task = buildTag(taskDto, createdBy, assignedTo);
        List tags = taskDto.getTags().stream().map(tag -> Tag.builder().name(tag).build()).map(tag -> tagRepository.save(tag)).toList();
        task.setTags(tags);


        Task response = taskRepository.save(task);
        return buildTagDto(response);
    }

    @Override
    public List<TaskDto> getAllTasks(String title, Long assignedTo, Status status) {
        List<Task> tasks = taskRepository.findAll();

        if (nonNull(title)) {
            tasks = taskRepository.findAllByTitleIn(List.of(title));
        }

        if (nonNull(assignedTo)) {
            User assignedUser = userRepository.findById(Integer.parseInt(assignedTo.toString()))
                    .orElseThrow(() -> new NotFoundException("Usuario asignado no encontrado"));
            tasks = taskRepository.findAllByAssignedToIn(List.of(assignedUser));
        }

        if (nonNull(status)) {
            tasks = taskRepository.findAllByStatusIn(List.of(status.fromValue()));
        }



        return tasks.stream().map(TaskService::buildTagDto).toList();

    }

    @Override
    public TaskDto getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada"));
        return buildTagDto(task);
    }

    @Override
    public TaskDto updateTask(Long taskId, TaskDto taskDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada"));
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(Status.fromValue(taskDto.getStatus()));
        task.setTags(taskDto.getTags().stream().map(tag -> Tag.builder().name(tag).build()).toList());
        List<Check> notSavedChecks = taskDto.getChecks().stream()
                .filter(checkDto -> checkDto.getId() == null)
                .map(checkDto -> Check.builder()
                                                .description(checkDto.getDescription())
                                                .isChecked(checkDto.getIsChecked())
                                                .task(task).build()).toList();
        List<Check> knewChecks = taskDto.getChecks().stream()
                .filter(checkDto -> checkDto.getId() != null)
                .map(checkDto -> Check.builder()
                        .id(checkDto.getId())
                        .description(checkDto.getDescription())
                        .isChecked(checkDto.getIsChecked())
                        .task(task).build()).toList();

        List<Check> oldChecks = checkRepository.findAllByIdInAndTask(knewChecks.stream()
                        .map(Check::getId).toList(), task);

        oldChecks = oldChecks.stream().map(check -> {
            Check newCheck = knewChecks.stream()
                    .filter(checkDto -> checkDto.getId().equals(check.getId()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Check no encontrado"));
            check.setDescription(newCheck.getDescription());
            check.setIsChecked(newCheck.getIsChecked());
            checkRepository.save(check);
            return check;
        }).toList();

        List<Check> savedChecks = checkRepository.saveAll(notSavedChecks);

        savedChecks.addAll(oldChecks);

        task.setChecks(savedChecks);
        task.setAssignedTo(userRepository.findById(Math.toIntExact(taskDto.getAssignedTo()))
                .orElseThrow(() -> new NotFoundException("Usuario asignado no encontrado")));
        task.setDueDate(nonNull(taskDto.getDueDate()) ? taskDto.getDueDate() : LocalDateTime.now().plusDays(8));
        task.setUpdatedAt(LocalDateTime.now());
        Task response;
        try {
            response = taskRepository.save(task);
        } catch (UnsupportedOperationException e) {
            response = taskRepository.save(task);
        }
        return buildTagDto(response);
    }

    @Override
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada"));
        taskRepository.delete(task);
    }

    private static TaskDto buildTagDto(Task task) {
        List<CheckDto> checks = nonNull(task.getChecks())
                ? task.getChecks().stream().map(check -> CheckDto.builder()
                    .id(check.getId())
                    .description(check.getDescription())
                    .isChecked(check.getIsChecked())
                    .taskId(nonNull(check.getTask()) ? check.getTask().getId() : null)
                    .build()).toList()
                : null;
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(nonNull(task.getStatus()) ? task.getStatus().fromValue() : null)
                .tags(task.getTags().stream().map(Tag::getName).toList())
                .checks(checks)
                .createdBy(Long.valueOf(task.getCreatedBy().getId()))
                .assignedTo(Long.valueOf(task.getAssignedTo().getId()))
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    private static Task buildTag(TaskDto taskDto, User createdBy, User assignedTo) {
        return Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .createdBy(createdBy)
                .assignedTo(assignedTo)
                .dueDate(nonNull(taskDto.getDueDate()) ? taskDto.getDueDate() : LocalDateTime.now().plusDays(8))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
