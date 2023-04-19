package com.manage.company.company.service;

import com.manage.company.company.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentDTO save(CommentDTO commentDTO, long topicId);
    Page<CommentDTO> getAll(Pageable pageable);
    CommentDTO getById(Long Id);
    CommentDTO delete(Long commentId);
    CommentDTO update(CommentDTO commentDTO, Long topicId);
    Page<CommentDTO> findByTopicTitle(String title, Pageable pageable);
    Page<CommentDTO> findByUserId(Long userId, Pageable pageable);
}
