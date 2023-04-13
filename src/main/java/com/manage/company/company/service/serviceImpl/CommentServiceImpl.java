package com.manage.company.company.service.serviceImpl;

import com.manage.company.company.dto.CommentDTO;
import com.manage.company.company.exeption.ResourceNotFoundException;
import com.manage.company.company.mapper.CommentMapper;
import com.manage.company.company.model.Comment;
import com.manage.company.company.model.Topic;
import com.manage.company.company.repository.CommentRepo;
import com.manage.company.company.repository.TopicRepo;
import com.manage.company.company.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public CommentDTO save(CommentDTO commentDTO) {

        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setDate(LocalDateTime.now());

        Topic topic = topicRepo.findById(commentDTO.getTopic().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id: " + commentDTO.getTopic().getId()));
        comment.setTopic(topic);

        Comment savedComment = commentRepo.save(comment);
        log.info("Comment saved: {}", savedComment.getId());
        return CommentMapper.toDTO(savedComment);
    }

    @Override
    public Page<CommentDTO> getAll(Pageable pageable) {

        Page<Comment> comments = commentRepo.findAll(pageable);
        log.info("Total comments found: {}", comments.getTotalElements());
        return comments.map(CommentMapper::toDTO);
    }

    @Override
    public CommentDTO getById(Long id) {
        log.info("Getting comment by id: {}", id);

        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id: " + id));
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
    public CommentDTO update(CommentDTO commentDTO) {

        Comment existingComment = commentRepo.findById(commentDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id: " + commentDTO.getId()));
        existingComment.setText(commentDTO.getText());

        Topic topic = topicRepo.findById(commentDTO.getTopic().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id: " + commentDTO.getTopic().getId()));
        existingComment.setTopic(topic);

        Comment updatedComment = commentRepo.save(existingComment);
        log.info("Comment updated: {}", updatedComment.getId());
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