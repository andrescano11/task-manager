package com.sena.taskmanager.service;

import com.sena.taskmanager.dto.CheckDto;
import com.sena.taskmanager.service.interfaces.ICheckService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CheckService implements ICheckService {

    @Override
    public CheckDto createCheck(Long taskId, CheckDto checkDto) {
        return null;
    }

    @Override
    public List<CheckDto> getAllChecks(Long taskId) {
        return null;
    }

    @Override
    public CheckDto getCheckById(Long taskId, Long checkId) {
        return null;
    }

    @Override
    public CheckDto updateCheck(Long taskId, Long checkId, CheckDto checkDto) {
        return null;
    }

    @Override
    public void deleteCheck(Long taskId, Long checkId) {

    }
}
