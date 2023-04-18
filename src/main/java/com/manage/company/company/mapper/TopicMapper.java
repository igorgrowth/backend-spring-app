package com.manage.company.company.mapper;


import com.manage.company.company.dto.TopicDTO;
import com.manage.company.company.entity.Topic;

import java.util.stream.Collectors;

public class TopicMapper {

    public static Topic toEntity(TopicDTO topicDTO) {

        return Topic.builder()
                .id(topicDTO.getId())
                .title(topicDTO.getTitle())
                .description(topicDTO.getDescription())
                .commentList(topicDTO.getCommentDTOList().stream()
                        .map(CommentMapper::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    public static TopicDTO toDTO(Topic topic) {
        return TopicDTO.builder()
                .id(topic.getId())
                .title(topic.getTitle())
                .description(topic.getDescription())
                .commentDTOList(topic.getCommentList().stream()
                        .map(CommentMapper::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}