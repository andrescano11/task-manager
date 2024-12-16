package com.sena.taskmanager.controller;

import com.sena.taskmanager.dto.CheckDto;
import com.sena.taskmanager.service.interfaces.ICheckService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("api/v1/task/{taskId}/check")
public class CheckController {
    ICheckService service;

    @PostMapping
    @PreAuthorize("hasPermission('permission', 'create_check')")
    public ResponseEntity<CheckDto> createCheck(@PathVariable Long taskId, @RequestBody CheckDto checkDto) {
        return new ResponseEntity<>(service.createCheck(taskId, checkDto), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasPermission('permission', 'read_check')")
    public ResponseEntity<List<CheckDto>> getChecks(@PathVariable Long taskId) {
        return new ResponseEntity<>(service.getAllChecks(taskId), HttpStatus.OK);
    }

    @GetMapping("/{checkId}")
    @PreAuthorize("hasPermission('permission', 'read_check')")
    public ResponseEntity<CheckDto> getCheckById(@PathVariable Long taskId, @PathVariable Long checkId) {
        return new ResponseEntity<>(service.getCheckById(taskId, checkId), HttpStatus.OK);
    }

    @PutMapping("/{checkId}")
    @PreAuthorize("hasPermission('permission', 'update_check')")
    public ResponseEntity<CheckDto> updateCheck(@PathVariable Long taskId, @PathVariable Long checkId, @RequestBody CheckDto checkDto) {
        return new ResponseEntity<>(service.updateCheck(taskId, checkId, checkDto), HttpStatus.OK);
    }

    @DeleteMapping("/{checkId}")
    @PreAuthorize("hasPermission('permission', 'delete_check')")
    public ResponseEntity<Void> deleteCheck(@PathVariable Long taskId, @PathVariable Long checkId) {
        service.deleteCheck(taskId, checkId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
