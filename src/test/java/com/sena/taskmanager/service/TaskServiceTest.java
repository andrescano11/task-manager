package com.sena.taskmanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CheckRepository checkRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_createsAndReturnsTask() {
        User assignedTo = User.builder()
                .id(1)
                .firstname("John")
                .lastname("Doe")
                .build();
        User createdBy = User.builder()
                .id(2)
                .firstname("Jane")
                .lastname("Doe")
                .build();
        Task task = Task.builder()
                .title("Test Task")
                .description("Test Description")
                .status(Status.TODO)
                .assignedTo(assignedTo)
                .createdBy(createdBy)
                .dueDate(LocalDateTime.now().plusDays(8))
                .build();
        TaskDto taskDto = TaskDto.builder()
                .title("Test Task")
                .description("Test Description")
                .status(Status.TODO.fromValue())
                .assignedTo(Long.valueOf(assignedTo.getId()))
                .createdBy(Long.valueOf(createdBy.getId()))
                .tags(List.of("Test Tag"))
                .dueDate(LocalDateTime.now().plusDays(8))
                .build();

        Check check = Check.builder()
                .description("Test Check")
                .isChecked(false)
                .build();

        Tag tag = Tag.builder()
                .name("Test Tag")
                .build();

        task.setChecks(List.of(check));
        task.setTags(List.of(tag));

        when(userRepository.findById(2)).thenReturn(Optional.of(assignedTo));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(tagRepository.findByName("Test Tag")).thenReturn(Optional.of(tag));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(createdBy);
        SecurityContextHolder.setContext(securityContext);
        SecurityContextHolder.setContext(securityContext);

        TaskDto result = taskService.createTask(taskDto);

        assertNotNull(result);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void getAllTasks_returnsAllTasks() {
        User assignedTo = User.builder()
                .id(1)
                .firstname("John")
                .lastname("Doe")
                .build();
        User createdBy = User.builder()
                .id(2)
                .firstname("Jane")
                .lastname("Doe")
                .build();
        Task task = Task.builder()
                .title("Test Task")
                .description("Test Description")
                .status(Status.TODO)
                .assignedTo(assignedTo)
                .createdBy(createdBy)
                .dueDate(LocalDateTime.now().plusDays(8))
                .build();
        TaskDto taskDto = TaskDto.builder()
                .title("Test Task")
                .description("Test Description")
                .status(Status.TODO.fromValue())
                .assignedTo(Long.valueOf(assignedTo.getId()))
                .createdBy(Long.valueOf(createdBy.getId()))
                .tags(List.of("Test Tag"))
                .dueDate(LocalDateTime.now().plusDays(8))
                .build();

        Check check = Check.builder()
                .description("Test Check")
                .isChecked(false)
                .build();

        Tag tag = Tag.builder()
                .name("Test Tag")
                .build();

        task.setChecks(List.of(check));
        task.setTags(List.of(tag));

        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<TaskDto> result = taskService.getAllTasks(null, null, null);

        assertFalse(result.isEmpty());
        verify(taskRepository).findAll();
    }

    @Test
    void getTaskById_returnsTask() {
        User assignedTo = User.builder()
                .id(1)
                .firstname("John")
                .lastname("Doe")
                .build();
        User createdBy = User.builder()
                .id(2)
                .firstname("Jane")
                .lastname("Doe")
                .build();
        Task task = Task.builder()
                .id(1)
                .title("Test Task")
                .description("Test Description")
                .status(Status.TODO)
                .assignedTo(assignedTo)
                .createdBy(createdBy)
                .dueDate(LocalDateTime.now().plusDays(8))
                .build();
        TaskDto taskDto = TaskDto.builder()
                .title("Test Task")
                .description("Test Description")
                .status(Status.TODO.fromValue())
                .assignedTo(Long.valueOf(assignedTo.getId()))
                .createdBy(Long.valueOf(createdBy.getId()))
                .tags(List.of("Test Tag"))
                .dueDate(LocalDateTime.now().plusDays(8))
                .build();

        Check check = Check.builder()
                .description("Test Check")
                .isChecked(false)
                .task(task)
                .build();

        Tag tag = Tag.builder()
                .name("Test Tag")
                .build();

        task.setChecks(List.of(check));
        task.setTags(List.of(tag));

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskDto result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(taskRepository).findById(1L);
    }

    @Test
    void getTaskById_throwsNotFoundException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    void updateTask_updatesAndReturnsTask() {
        User assignedTo = User.builder()
                .id(1)
                .firstname("John")
                .lastname("Doe")
                .build();
        User createdBy = User.builder()
                .id(2)
                .firstname("Jane")
                .lastname("Doe")
                .build();
        Task task = Task.builder()
                .id(1)
                .title("Updated Task")
                .description("Test Description")
                .status(Status.TODO)
                .assignedTo(assignedTo)
                .createdBy(createdBy)
                .dueDate(LocalDateTime.now().plusDays(8))
                .build();
        TaskDto taskDto = TaskDto.builder()
                .id(1)
                .title("Updated Task")
                .description("Test Description")
                .status(Status.TODO.fromValue())
                .assignedTo(Long.valueOf(assignedTo.getId()))
                .createdBy(Long.valueOf(createdBy.getId()))
                .tags(List.of("Test Tag"))
                .dueDate(LocalDateTime.now().plusDays(8))
                .build();

        Check check = Check.builder()
                .description("Test Check")
                .isChecked(false)
                .build();

        Tag tag = Tag.builder()
                .name("Test Tag")
                .build();

        task.setChecks(List.of(check));
        taskDto.setChecks(List.of(CheckDto.builder()
                .description("Test Check")
                .isChecked(false)
                .build()));
        task.setTags(List.of(tag));

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(userRepository.findById(1)).thenReturn(Optional.of(assignedTo));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDto result = taskService.updateTask(1L, taskDto);

        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void deleteTask_deletesTask() {
        Task task = new Task();
        task.setId(1);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepository).delete(task);
    }

    @Test
    void deleteTask_throwsNotFoundException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.deleteTask(1L));
    }
}
