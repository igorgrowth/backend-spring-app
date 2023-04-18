package com.manage.company.company.service.serviceImpl;

import com.manage.company.company.dto.CommentDTO;
import com.manage.company.company.exeption.ResourceNotFoundException;
import com.manage.company.company.mapper.CommentMapper;
import com.manage.company.company.entity.Comment;
import com.manage.company.company.entity.Topic;
import com.manage.company.company.repository.CommentRepo;
import com.manage.company.company.repository.TopicRepo;
import com.manage.company.company.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final TopicRepo topicRepo;

    public CommentServiceImpl(CommentRepo commentRepo, TopicRepo topicRepo) {
        this.commentRepo = commentRepo;
        this.topicRepo = topicRepo;
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO, long topicId) {
        Topic topic = topicRepo.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id: " + topicId));

        Comment comment = Comment.builder()
                .text(commentDTO.getText())
                .date(LocalDateTime.now())
                .userId(commentDTO.getUserId())
                .topic(topic)
                .build();

        topic.getCommentList().add(comment);

        Topic savedTopic = topicRepo.save(topic);
        CommentDTO savedCommentDTO = CommentMapper.toDTO(comment);
        savedCommentDTO.setTopicId(savedTopic.getId());
        savedCommentDTO.setUserId(savedTopic.getId());

        log.info("Comment saved: {}", savedCommentDTO);
        return savedCommentDTO;
    }

    @Override
    public Page<CommentDTO> getAll(Pageable pageable) {
        Page<Comment> commentsPage = commentRepo.findAll(pageable);
        List<CommentDTO> commentDTOs = commentsPage.getContent().stream()
                .map(CommentMapper::toDTO)
                .collect(Collectors.toList());
        log.info("Getting all comments with pagination: {}", pageable);
        return new PageImpl<>(commentDTOs, pageable, commentsPage.getTotalElements());
    }

    @Override
    public CommentDTO getById(Long id) {
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id: " + id));
        log.info("Getting comment by id: {}", id);
        return CommentMapper.toDTO(comment);
    }

    @Override
    public CommentDTO delete(Long id) {
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id: " + id));
        commentRepo.delete(comment);
        log.info("Comment deleted: {}", id);
        return CommentMapper.toDTO(comment);
    }

    @Override
    public CommentDTO update(CommentDTO commentDTO, Long topicId) {
        Comment existingComment = commentRepo.findById(commentDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id: " + commentDTO.getId()));
        existingComment.setText(commentDTO.getText());

        Topic topic = topicRepo.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id: " + topicId));
        existingComment.setTopic(topic);

        Comment updatedComment = commentRepo.save(existingComment);
        log.info("Updated comment: {}", updatedComment);
        return CommentMapper.toDTO(updatedComment);
    }

    @Override
    public Page<CommentDTO> findByTopicTitle(String title, Pageable pageable) {
        Page<Comment> comments = commentRepo.findByTopicTitle(title, pageable);
        log.info("Total comments found for topic title '{}': {}", title, comments.getTotalElements());
        return comments.map(CommentMapper::toDTO);
    }

    @Override
    public Page<CommentDTO> findByUserId(Long userId, Pageable pageable) {
        Page<Comment> comments = commentRepo.findByUserId(userId, pageable);
        log.info("Total comments found for employee name '{}': {}", userId, comments.getTotalElements());
        return comments.map(CommentMapper::toDTO);
    }
}