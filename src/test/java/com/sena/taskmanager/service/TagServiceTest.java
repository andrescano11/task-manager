package com.sena.taskmanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sena.taskmanager.dto.TagDto;
import com.sena.taskmanager.entity.Tag;
import com.sena.taskmanager.exceptions.NotFoundException;
import com.sena.taskmanager.repository.TagRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTag_createsAndReturnsTag() {
        TagDto tagDto = new TagDto();
        tagDto.setName("New Tag");

        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("New Tag");

        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        TagDto result = tagService.createTag(tagDto);

        assertNotNull(result);
        assertEquals("New Tag", result.getName());
        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    void getAllTags_returnsAllTags() {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Tag1");

        when(tagRepository.findAll()).thenReturn(List.of(tag));

        List<TagDto> result = tagService.getAllTags();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Tag1", result.get(0).getName());
        verify(tagRepository).findAll();
    }

    @Test
    void getTagByName_returnsTag() {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Tag1");

        when(tagRepository.findByName("Tag1")).thenReturn(Optional.of(tag));

        TagDto result = tagService.getTagByName("Tag1");

        assertNotNull(result);
        assertEquals("Tag1", result.getName());
        verify(tagRepository).findByName("Tag1");
    }

    @Test
    void getTagByName_tagNotFound_throwsNotFoundException() {
        when(tagRepository.findByName("Tag1")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> tagService.getTagByName("Tag1"));
    }

    @Test
    void updateTag_updatesAndReturnsTag() {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Tag1");

        TagDto tagDto = new TagDto();
        tagDto.setName("Updated Tag");

        when(tagRepository.findByName("Tag1")).thenReturn(Optional.of(tag));
        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        TagDto result = tagService.updateTag("Tag1", tagDto);

        assertNotNull(result);
        assertEquals("Updated Tag", result.getName());
        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    void updateTag_tagNotFound_throwsNotFoundException() {
        TagDto tagDto = new TagDto();
        tagDto.setName("Updated Tag");

        when(tagRepository.findByName("Tag1")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> tagService.updateTag("Tag1", tagDto));
    }

    @Test
    void deleteTag_deletesTag() {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setName("Tag1");

        when(tagRepository.findByName("Tag1")).thenReturn(Optional.of(tag));

        tagService.deleteTag("Tag1");

        verify(tagRepository).delete(tag);
    }

    @Test
    void deleteTag_tagNotFound_throwsNotFoundException() {
        when(tagRepository.findByName("Tag1")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> tagService.deleteTag("Tag1"));
    }
}
