package com.sena.taskmanager.service;

import com.sena.taskmanager.dto.TagDto;
import com.sena.taskmanager.service.interfaces.ITagService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TagService implements ITagService {

    @Override
    public TagDto createTag(TagDto taskDto) {
        return null;
    }

    @Override
    public List<TagDto> getAllTags() {
        return null;
    }

    @Override
    public TagDto getTagById(Long taskId) {
        return null;
    }

    @Override
    public TagDto updateTag(Long taskId, TagDto taskDto) {
        return null;
    }

    @Override
    public void deleteTag(Long taskId) {

    }
}
