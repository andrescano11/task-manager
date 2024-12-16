package com.sena.taskmanager.service;

import com.sena.taskmanager.dto.CheckDto;
import com.sena.taskmanager.entity.Check;
import com.sena.taskmanager.entity.Task;
import com.sena.taskmanager.exceptions.NotFoundException;
import com.sena.taskmanager.repository.CheckRepository;
import com.sena.taskmanager.repository.TaskRepository;
import com.sena.taskmanager.service.interfaces.ICheckService;
import com.sena.taskmanager.service.interfaces.ITaskService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CheckService implements ICheckService {
    TaskRepository taskRepository;
    CheckRepository checkRepository;

    public CheckService(TaskRepository taskRepository, CheckRepository checkRepository) {
        this.taskRepository = taskRepository;
        this.checkRepository = checkRepository;
    }

    @Override
    public CheckDto createCheck(Long taskId, CheckDto checkDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada"));
        Check check = Check.builder()
                .description(checkDto.getDescription())
                .isChecked(checkDto.getIsChecked())
                .task(task)
                .build();

        Check response = checkRepository.save(check);
        return CheckDto.builder()
                .id(response.getId())
                .description(response.getDescription())
                .isChecked(response.getIsChecked())
                .taskId(response.getTask().getId())
                .build();
    }

    @Override
    public List<CheckDto> getAllChecks(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada"));
        List<Check> checks = checkRepository.findAllByTask(task);
        return checks.stream()
                .map(check -> CheckDto.builder()
                        .id(check.getId())
                        .description(check.getDescription())
                        .isChecked(check.getIsChecked())
                        .taskId(check.getTask().getId())
                        .build())
                .toList();
    }

    @Override
    public CheckDto getCheckById(Long taskId, Long checkId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada"));
        Check check = checkRepository.findByIdAndTask(checkId, task)
                .orElseThrow(() -> new NotFoundException("Check no encontrado"));
        return CheckDto.builder()
                .id(check.getId())
                .description(check.getDescription())
                .isChecked(check.getIsChecked())
                .taskId(check.getTask().getId())
                .build();
    }

    @Override
    public CheckDto updateCheck(Long taskId, Long checkId, CheckDto checkDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada"));
        Check check = checkRepository.findByIdAndTask(checkId, task)
                .orElseThrow(() -> new NotFoundException("Check no encontrado"));
        check.setDescription(checkDto.getDescription());
        check.setIsChecked(checkDto.getIsChecked());
        Check response = checkRepository.save(check);
        return CheckDto.builder()
                .id(response.getId())
                .description(response.getDescription())
                .isChecked(response.getIsChecked())
                .taskId(response.getTask().getId())
                .build();
    }

    @Override
    public void deleteCheck(Long taskId, Long checkId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada"));
        Check check = checkRepository.findByIdAndTask(checkId, task)
                .orElseThrow(() -> new NotFoundException("Check no encontrado"));
        checkRepository.delete(check);
    }
}
