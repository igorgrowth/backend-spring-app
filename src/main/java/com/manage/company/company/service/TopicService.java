package com.manage.company.company.service;

import com.manage.company.company.dto.TopicDTO;
import com.manage.company.company.model.Topic;

import java.util.List;

public interface TopicService {
    TopicDTO save(TopicDTO topicDTO);
    List<TopicDTO> findAll();
    TopicDTO getById(Long id);
    TopicDTO getByTitle(String title);
    TopicDTO delete(Long id);
    TopicDTO update(TopicDTO topicDTO);
}
