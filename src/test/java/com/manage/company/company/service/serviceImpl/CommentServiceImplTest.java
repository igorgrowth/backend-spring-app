package com.manage.company.company.service.serviceImpl;

import com.manage.company.company.dto.CommentDTO;
import com.manage.company.company.dto.TopicDTO;
import com.manage.company.company.exeption.ResourceNotFoundException;
import com.manage.company.company.model.Comment;
import com.manage.company.company.model.Topic;
import com.manage.company.company.repository.CommentRepo;
import com.manage.company.company.repository.TopicRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepo commentRepo;

    @Mock
    private TopicRepo topicRepo;

    private Comment comment;
    private CommentDTO commentDTO;

    @BeforeEach
    public void setUp() {
        TopicDTO topicDTO = new TopicDTO(1L, "title", "description", Collections.emptyList());
        Topic topic = new Topic(1L, "title", "description", Collections.emptyList());

        comment =
                comment.builder()
                .id(1L)
                .text("test text")
                .date(LocalDateTime.now())
                .topic(topic)
                .userId(1L)
                .build();
        commentDTO =
                commentDTO.builder()
                        .id(1L)
                        .text("test text")
                        .date(LocalDateTime.now())
                        .topicDTO(topicDTO)
                        .userId(1L)
                        .build();
    }

    @Test
    void save_ShouldReturnSavedCommentDTO() {
        Topic topic = new Topic(1L, "title", "description", Collections.emptyList());

        //When
        when(topicRepo.findById(any(Long.TYPE))).thenReturn(Optional.of(topic));
        when(commentRepo.save(any(Comment.class))).thenReturn(comment);
        CommentDTO result = commentService.save(commentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(commentDTO.getId(), result.getId());
        assertEquals(commentDTO.getText(), result.getText());
        assertEquals(commentDTO.getDate().getMinute(), result.getDate().getMinute());
        assertEquals(commentDTO.getUserId(), result.getUserId());
        assertEquals(commentDTO.getTopicDTO().getId(), result.getTopicDTO().getId());
    }

    @Test
    public void testSave_InvalidTopicId_ShouldThrowResourceNotFoundException() {
        //When
        when(topicRepo.findById(999L)).thenReturn(Optional.empty());

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> commentService.save(commentDTO));
    }

    @Test
    void getAll_ShouldReturnCommentDTOPage() {
        int totalComments = 5;
        int pageSize = 3;
        int pageNumber = 1;

        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Comment> commentsPage = new PageImpl<>(comments, pageable, totalComments);

        //When
        when(commentRepo.findAll(pageable)).thenReturn(commentsPage);

        Page<CommentDTO> result = commentService.getAll(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(commentDTO.getId(), result.getContent().get(0).getId());
        assertEquals(commentDTO.getText(), result.getContent().get(0).getText());
        assertEquals(commentDTO.getDate().getMinute(), result.getContent().get(0).getDate().getMinute());
        assertEquals(commentDTO.getUserId(), result.getContent().get(0).getUserId());
        assertEquals(commentDTO.getTopicDTO().getId(), result.getContent().get(0).getTopicDTO().getId());
    }

    @Test
    void getById_ShouldReturnCommentDTO() {
        //When
        when(commentRepo.findById(any(Long.TYPE))).thenReturn(Optional.of(comment));

        CommentDTO result = commentService.getById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(commentDTO.getId(), result.getId());
        assertEquals(commentDTO.getText(), result.getText());
        assertEquals(commentDTO.getDate().getMinute(), result.getDate().getMinute());
        assertEquals(commentDTO.getUserId(), result.getUserId());
        assertEquals(commentDTO.getTopicDTO().getId(), result.getTopicDTO().getId());
    }

    @Test
    void delete_ShouldDeleteCommentDTO() {
        //When
        when(commentRepo.findById(any(Long.TYPE))).thenReturn(Optional.of(comment));

        CommentDTO result = commentService.delete(1L);
        // Assert
        assertNotNull(result);
        assertEquals(commentDTO.getId(), result.getId());
        assertEquals(commentDTO.getText(), result.getText());
        assertEquals(commentDTO.getDate().getMinute(), result.getDate().getMinute());
        assertEquals(commentDTO.getUserId(), result.getUserId());
        assertEquals(commentDTO.getTopicDTO().getId(), result.getTopicDTO().getId());
        verify(commentRepo, times(1)).delete(comment);

    }

    @Test
    public void testDelete_ShouldThrowResourceNotFoundException() {
        //When
        when(commentRepo.findById(any(Long.TYPE))).thenReturn(Optional.empty());

        //Assert
        assertThrows(ResourceNotFoundException.class, () -> commentService.delete(1L));

    }

    @Test
    void update_ShouldUpdateCommentDTO() {
        CommentDTO updatedCommentDTO = new CommentDTO(1L,"updated text", LocalDateTime.now(), new TopicDTO(), 1L );

        //When
        when(commentRepo.findById(any(Long.TYPE))).thenReturn(Optional.of(comment));
        when(commentRepo.save(any(Comment.class))).thenReturn(comment);

        CommentDTO result = commentService.update(updatedCommentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(updatedCommentDTO.getId(), result.getId());
        assertEquals(updatedCommentDTO.getText(), result.getText());
        assertEquals(updatedCommentDTO.getDate().getMinute(), result.getDate().getMinute());

    }

    @Test
    void findByTopicTitl_ShouldReturnCommentDTO() {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        Page<Comment> commentsPage = new PageImpl<>(comments, pageable, 1);

        //When
        when(commentRepo.findByTopicTitle(anyString(), any(Pageable.class)))
                .thenReturn(commentsPage);
        Page<CommentDTO> result = commentService.findByTopicTitle("test title", pageable);

        // Assert
        assertNotNull(result);
        assertNotNull(result);
        assertEquals(commentsPage.getTotalElements(), result.getTotalElements());
        assertEquals(commentsPage.getContent().size(), result.getContent().size());
        assertEquals(commentDTO.getText(), result.getContent().get(0).getText());
        assertEquals(commentDTO.getDate().getMinute(), result.getContent().get(0).getDate().getMinute());
        assertEquals(commentDTO.getUserId(), result.getContent().get(0).getUserId());

    }

    @Test
    void findByUserId() {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        Page<Comment> commentsPage = new PageImpl<>(comments, pageable, 1);

        //When
        when(commentRepo.findByUserId(any(Long.TYPE), any(Pageable.class)))
                .thenReturn(commentsPage);
        Page<CommentDTO> result = commentService.findByUserId(1L, pageable);

        // Assert
        assertNotNull(result);
        assertNotNull(result);
        assertEquals(commentsPage.getTotalElements(), result.getTotalElements());
        assertEquals(commentsPage.getContent().size(), result.getContent().size());
        assertEquals(commentDTO.getText(), result.getContent().get(0).getText());
        assertEquals(commentDTO.getDate().getMinute(), result.getContent().get(0).getDate().getMinute());
        assertEquals(commentDTO.getUserId(), result.getContent().get(0).getUserId());
    }
}