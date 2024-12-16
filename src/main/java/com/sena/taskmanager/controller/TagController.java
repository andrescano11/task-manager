package com.sena.taskmanager.controller;

import com.sena.taskmanager.dto.TagDto;
import com.sena.taskmanager.service.interfaces.ITagService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/tag")
@Tag(name = "Tag", description = "Operaciones sobre las etiquetas")
public class TagController {
    ITagService service;

    public TagController(ITagService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasPermission('permission', 'create_tag')")
    @Tag(name = "Tag", description = "Crear una etiqueta")
    public ResponseEntity<?> createTag(@RequestBody TagDto tagDto) {
        return new ResponseEntity<>(service.createTag(tagDto), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasPermission('permission', 'read_tag')")
    @Tag(name = "Tag", description = "Obtener todas las etiquetas")
    public ResponseEntity<List<TagDto>> getTags(HttpServletRequest servletRequest) {
        return new ResponseEntity<>(service.getAllTags(), HttpStatus.OK);
    }

    @GetMapping("/{tagName}")
    @PreAuthorize("hasPermission('permission', 'read_tag')")
    @Tag(name = "Tag", description = "Obtener una etiqueta por su nombre")
    public ResponseEntity<TagDto> getTagById(@PathVariable String tagName) {
        return new ResponseEntity<>(service.getTagByName(tagName), HttpStatus.OK);
    }

    @PutMapping("/{tagName}")
    @PreAuthorize("hasPermission('permission', 'update_tag')")
    @Tag(name = "Tag", description = "Actualizar una etiqueta")
    public ResponseEntity<TagDto> updateTag(@PathVariable String tagName, @RequestBody TagDto tagDto) {
        return new ResponseEntity<>(service.updateTag(tagName, tagDto), HttpStatus.OK);
    }

    @DeleteMapping("/{tagName}")
    @PreAuthorize("hasPermission('permission', 'delete_tag')")
    @Tag(name = "Tag", description = "Eliminar una etiqueta")
    public ResponseEntity<Void> deleteTag(@PathVariable String tagName) {
        service.deleteTag(tagName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
