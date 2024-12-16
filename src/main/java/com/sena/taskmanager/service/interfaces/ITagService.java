package com.sena.taskmanager.service.interfaces;

import com.sena.taskmanager.dto.TagDto;
import java.util.List;

public interface ITagService {
    TagDto createTag(TagDto tagDto);

    List<TagDto> getAllTags();

    TagDto getTagByName(String tagName);

    TagDto updateTag(String tagName, TagDto tagDto);

    void deleteTag(String tagName);
}
