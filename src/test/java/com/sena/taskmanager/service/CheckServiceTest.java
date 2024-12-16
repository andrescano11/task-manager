package com.sena.taskmanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sena.taskmanager.dto.CheckDto;
import com.sena.taskmanager.entity.Check;
import com.sena.taskmanager.entity.Task;
import com.sena.taskmanager.exceptions.NotFoundException;
import com.sena.taskmanager.repository.CheckRepository;
import com.sena.taskmanager.repository.TaskRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CheckServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CheckRepository checkRepository;

    @InjectMocks
    private CheckService checkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCheck_createsAndReturnsCheck() {
        CheckDto checkDto = CheckDto.builder()
                .description("New Check")
                .isChecked(false)
                .build();

        Task task = Task.builder()
                .id(1)
                .build();

        Check expectedCheck = Check.builder()
                .description("New Check")
                .isChecked(false)
                .task(task)
                .build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(checkRepository.save(any(Check.class))).thenReturn(expectedCheck);

        CheckDto result = checkService.createCheck(1L, checkDto);

        assertNotNull(result);
        verify(checkRepository).save(any(Check.class));
        assertEquals(checkDto.getDescription(), result.getDescription());
        assertEquals(checkDto.getIsChecked(), result.getIsChecked());
        assertEquals(task.getId(), result.getTaskId());
    }

    @Test
    void createCheck_taskNotFound_throwsNotFoundException() {
        CheckDto checkDto = new CheckDto();
        checkDto.setDescription("New Check");
        checkDto.setIsChecked(false);

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> checkService.createCheck(1L, checkDto));
    }

    @Test
    void getAllChecks_returnsAllChecks() {
        CheckDto checkDto = CheckDto.builder()
                .description("New Check")
                .isChecked(false)
                .build();

        Task task = Task.builder()
                .id(1)
                .build();

        List<Check> expectedChecks = List.of(Check.builder()
                .description("New Check")
                .isChecked(false)
                .task(task)
                .build());

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(checkRepository.findAllByTask(task)).thenReturn(expectedChecks);

        List<CheckDto> result = checkService.getAllChecks(1L);

        assertFalse(result.isEmpty());
        verify(checkRepository).findAllByTask(task);
    }

    @Test
    void getAllChecks_taskNotFound_throwsNotFoundException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> checkService.getAllChecks(1L));
    }

    @Test
    void getCheckById_returnsCheck() {
        Task task = Task.builder()
                .id(1)
                .build();

        Check expectedCheck = Check.builder()
                .id(1)
                .description("New Check")
                .isChecked(false)
                .task(task)
                .build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(checkRepository.findByIdAndTask(1L, task)).thenReturn(Optional.of(expectedCheck));

        CheckDto result = checkService.getCheckById(1L, 1L);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(checkRepository).findByIdAndTask(1L, task);
    }

    @Test
    void getCheckById_checkNotFound_throwsNotFoundException() {
        Task task = new Task();
        task.setId(1);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(checkRepository.findByIdAndTask(1L, task)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> checkService.getCheckById(1L, 1L));
    }

    @Test
    void updateCheck_updatesAndReturnsCheck() {
        CheckDto checkDto = CheckDto.builder()
                .description("Updated Check")
                .isChecked(false)
                .build();

        Task task = Task.builder()
                .id(1)
                .build();

        Check expectedCheck = Check.builder()
                .description("Updated Check")
                .isChecked(true)
                .task(task)
                .build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(checkRepository.findByIdAndTask(1L, task)).thenReturn(Optional.of(expectedCheck));
        when(checkRepository.save(any(Check.class))).thenReturn(expectedCheck);

        CheckDto result = checkService.updateCheck(1L, 1L, checkDto);

        assertNotNull(result);
        assertEquals("Updated Check", result.getDescription());
        verify(checkRepository).save(any(Check.class));
    }

    @Test
    void updateCheck_checkNotFound_throwsNotFoundException() {
        Task task = new Task();
        task.setId(1);

        CheckDto checkDto = new CheckDto();
        checkDto.setDescription("Updated Check");
        checkDto.setIsChecked(true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(checkRepository.findByIdAndTask(1L, task)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> checkService.updateCheck(1L, 1L, checkDto));
    }

    @Test
    void deleteCheck_deletesCheck() {
        Task task = new Task();
        task.setId(1);

        Check check = new Check();
        check.setId(1);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(checkRepository.findByIdAndTask(1L, task)).thenReturn(Optional.of(check));

        checkService.deleteCheck(1L, 1L);

        verify(checkRepository).delete(check);
    }

    @Test
    void deleteCheck_checkNotFound_throwsNotFoundException() {
        Task task = new Task();
        task.setId(1);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(checkRepository.findByIdAndTask(1L, task)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> checkService.deleteCheck(1L, 1L));
    }
}
