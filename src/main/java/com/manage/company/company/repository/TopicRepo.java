package com.manage.company.company.repository;

import com.manage.company.company.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepo extends JpaRepository<Topic, Long> {
    Topic findByTitle(String title);
}
