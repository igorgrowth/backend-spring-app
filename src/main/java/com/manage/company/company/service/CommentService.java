package com.manage.company.company.service;

import com.manage.company.company.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentDTO save(CommentDTO commentDTO);
    Page<CommentDTO> getAll(Pageable pageable);
    CommentDTO getById(Long id);
    CommentDTO delete(Long id);
    CommentDTO update(CommentDTO commentDTO);
    Page<CommentDTO> findByTopicTitle(String title, Pageable pageable);
    Page<CommentDTO> findByUserId(Long userId, Pageable pageable);
}
