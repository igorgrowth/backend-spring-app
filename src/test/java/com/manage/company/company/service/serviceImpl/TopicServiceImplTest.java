package com.manage.company.company.service.serviceImpl;

import com.manage.company.company.dto.TopicDTO;
import com.manage.company.company.entity.Topic;
import com.manage.company.company.repository.TopicRepo;
import com.manage.company.company.exeption.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class TopicServiceImplTest {

    @InjectMocks
    private TopicServiceImpl topicService;

    @Mock
    private TopicRepo topicRepo;

    private TopicDTO topicDTO;
    private Topic topic;

    @BeforeEach
    public void setUp() {
        topicDTO =
                TopicDTO.builder()
                        .id(1L)
                        .title("test title")
                        .description("test description")
                        .commentDTOList(Collections.emptyList())
                        .build();
        topic =
                Topic.builder()
                        .id(1L)
                        .title("test title")
                        .description("test description")
                        .commentList(Collections.emptyList())
                        .build();
    }
    @Test
    void save_ShouldSaveTopicDTO() {
        //When
        when(topicRepo.save(any(Topic.class))).thenReturn(topic);

        TopicDTO result = topicService.save(topicDTO);

        // Assert
        assertNotNull(result);
        assertEquals(topicDTO.getTitle(), result.getTitle());
        assertEquals(topicDTO.getDescription(), result.getDescription());
        verify(topicRepo, times(1)).save(any(Topic.class));
    }

    @Test
    void findAll_ShouldReturnListOfTopicDTOs() {
        //Setup
        List<Topic> topics = new ArrayList<>();
        topics.add(topic);

        //When
        when(topicRepo.findAll()).thenReturn(topics);

        List<TopicDTO> result = topicService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(topics.size(), result.size());
        assertEquals(result.get(0).getTitle(), topicDTO.getTitle());
        assertEquals(result.get(0).getDescription(), topicDTO.getDescription());

    }

    @Test
    void getById_ShouldReturnTopicDTO() {
        //When
        when(topicRepo.findById(any(Long.TYPE))).thenReturn(Optional.of(topic));

        TopicDTO result = topicService.getById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(topic.getTitle(), result.getTitle());
        assertEquals(topic.getDescription(), result.getDescription());
    }

    @Test
    public void testGetById_ShouldThrowResourceNotFoundException() {
        //Setup
        Long topicId = 1L;

        //When
        when(topicRepo.findById(any(Long.TYPE))).thenReturn(Optional.empty());

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> topicService.getById(topicId));
    }

    @Test
    void getByTitle_ShouldReturnTopicDTO() {
        //Setup
        String testTitle = "test title";
        //When
        when(topicRepo.findByTitle(testTitle)).thenReturn(topic);

        TopicDTO result = topicService.getByTitle(testTitle);

        // Assert
        assertNotNull(result);
        assertEquals(topic.getId(), result.getId());
        assertEquals(testTitle, result.getTitle());
        assertEquals(topic.getDescription(), result.getDescription());
    }

    @Test
    public void testGetByTitle_ShouldThrowResourceNotFoundException() {
        //Setup
        String topicTitle = "Non existing Topic";

        when(topicRepo.findByTitle(topicTitle)).thenReturn(null);

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> topicService.getByTitle(topicTitle));

    }

    @Test
    void delete_ShouldDeleteTopicDTO() {
        //Setup
        Long id = 1L;
        //When
        when(topicRepo.findById(any(Long.TYPE))).thenReturn(Optional.of(topic));

        TopicDTO result = topicService.delete(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(topic.getTitle(), result.getTitle());
        assertEquals(topic.getDescription(), result.getDescription());
        verify(topicRepo, times(1)).delete(topic);
    }

    @Test
    void update_ShouldUpdateTopicDTO() {
        //Setup
        TopicDTO updatedTopicDTO = new TopicDTO(1L, "Updated title", "Updated description", Collections.emptyList());

        //When
        when(topicRepo.findById(any(Long.TYPE))).thenReturn(Optional.of(topic));
        when(topicRepo.save(any(Topic.class))).thenReturn(topic);

        TopicDTO result = topicService.update(updatedTopicDTO);
        // Assert
        assertNotNull(result);
        assertEquals(updatedTopicDTO.getId(), result.getId());
        assertEquals(updatedTopicDTO.getTitle(), result.getTitle());
        assertEquals(updatedTopicDTO.getDescription(), result.getDescription());
    }
}