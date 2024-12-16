package com.sena.taskmanager.service;

import com.sena.taskmanager.dto.TagDto;
import com.sena.taskmanager.entity.Tag;
import com.sena.taskmanager.exceptions.NotFoundException;
import com.sena.taskmanager.repository.TagRepository;
import com.sena.taskmanager.service.interfaces.ITagService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TagService implements ITagService {

    TagRepository repository;

    public TagService(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    public TagDto createTag(TagDto tagDto) {
        Tag tag = Tag.builder()
                .name(tagDto.getName()).build();
        Tag response = repository.save(tag);
        return TagDto.builder()
                .id(response.getId())
                .name(response.getName()).build();
    }

    @Override
    public List<TagDto> getAllTags() {
        List<Tag> tags = repository.findAll();
        return tags.stream().map(tag -> TagDto.builder()
                .id(tag.getId())
                .name(tag.getName()).build()).toList();
    }

    @Override
    public TagDto getTagByName(String tagName) {
        Tag tag = findTagByName(tagName);
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName()).build();
    }



    @Override
    public TagDto updateTag(String tagName, TagDto tagDto) {
        Tag tag = findTagByName(tagName);
        tag.setName(tagDto.getName());
        Tag response = repository.save(tag);
        return TagDto.builder()
                .id(response.getId())
                .name(response.getName()).build();
    }

    @Override
    public void deleteTag(String tagName) {
        Tag tag = findTagByName(tagName);
        repository.delete(tag);
    }

    private Tag findTagByName(String tagName) {
        Optional<Tag> tag = repository.findByName(tagName);
        if (tag.isEmpty()){
            throw new NotFoundException("Etiqueta no encontrada");
        }
        return tag.get();
    }
}
