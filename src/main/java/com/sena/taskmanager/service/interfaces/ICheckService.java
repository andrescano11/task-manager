package com.sena.taskmanager.service.interfaces;

import com.sena.taskmanager.dto.CheckDto;
import java.util.List;

public interface ICheckService {

    CheckDto createCheck(Long taskId, CheckDto checkDto);

    List<CheckDto> getAllChecks(Long taskId);

    CheckDto getCheckById(Long taskId, Long checkId);

    CheckDto updateCheck(Long taskId, Long checkId, CheckDto checkDto);

    void deleteCheck(Long taskId, Long checkId);
}
