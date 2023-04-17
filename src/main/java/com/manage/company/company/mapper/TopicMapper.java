package com.manage.company.company.mapper;


import com.manage.company.company.dto.TopicDTO;
import com.manage.company.company.entity.Topic;

public class TopicMapper {

    public static Topic toEntity(TopicDTO topicDTO) {

        return Topic.builder()
                .id(topicDTO.getId())
                .title(topicDTO.getTitle())
                .description(topicDTO.getDescription())
                .build();
    }

    public static TopicDTO toDTO(Topic topic) {
        return TopicDTO.builder()
                .id(topic.getId())
                .title(topic.getTitle())
                .description(topic.getDescription())
                .build();
    }
}
