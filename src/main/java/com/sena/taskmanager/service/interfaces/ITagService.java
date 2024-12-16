package com.sena.taskmanager.service.interfaces;

import com.sena.taskmanager.dto.TagDto;
import java.util.List;

public interface ITagService {
    TagDto createTag(TagDto tagDto);

    List<TagDto> getAllTags();

    TagDto getTagById(Long tagId);

    TagDto updateTag(Long tagId, TagDto tagDto);

    void deleteTag(Long tagId);
}
