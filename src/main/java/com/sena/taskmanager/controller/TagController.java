package com.sena.taskmanager.controller;

import com.sena.taskmanager.dto.TagDto;
import com.sena.taskmanager.service.interfaces.ITagService;
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
@RequestMapping("api/v1/tag")
public class TagController {
    ITagService service;

    public TagController(ITagService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasPermission('permission', 'create_tag')")
    public ResponseEntity<?> createTag(@RequestBody TagDto tagDto) {
        return new ResponseEntity<>(service.createTag(tagDto), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasPermission('permission', 'read_tag')")
    public ResponseEntity<List<TagDto>> getTags(HttpServletRequest servletRequest) {
        return new ResponseEntity<>(service.getAllTags(), HttpStatus.OK);
    }

    @GetMapping("/{tagId}")
    @PostAuthorize("hasPermission('permission', 'read_tag')")
    public ResponseEntity<TagDto> getTagById(@PathVariable Long tagId) {
        return new ResponseEntity<>(service.getTagById(tagId), HttpStatus.OK);
    }

    @PutMapping("/{tagId}")
    @PreAuthorize("hasPermission('permission', 'update_tag')")
    public ResponseEntity<TagDto> updateTag(@PathVariable Long tagId, @RequestBody TagDto tagDto) {
        return new ResponseEntity<>(service.updateTag(tagId, tagDto), HttpStatus.OK);
    }

    @DeleteMapping("/{tagId}")
    @PreAuthorize("hasPermission('permission', 'delete_tag')")
    public ResponseEntity<Void> deleteTag(@PathVariable Long tagId) {
        service.deleteTag(tagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
