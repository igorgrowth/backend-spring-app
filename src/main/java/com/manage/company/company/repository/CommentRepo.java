package com.manage.company.company.repository;

import com.manage.company.company.model.Comment;
import com.manage.company.company.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    Page<Comment> findByTopicTitle(String title, Pageable pageable);
    Page<Comment> findByUserId(Long id, Pageable pageable);
}
