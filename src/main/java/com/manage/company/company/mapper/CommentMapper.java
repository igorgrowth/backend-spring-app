package com.manage.company.company.mapper;

import com.manage.company.company.dto.CommentDTO;
import com.manage.company.company.entity.Comment;

public class CommentMapper {
        public static CommentDTO toDTO(Comment comment){
            return CommentDTO.builder()
                    .id(comment.getId())
                    .text(comment.getText())
                    //.topicDTO(TopicMapper.toDTO(comment.getTopic()))
                    .date(comment.getDate())
                    .userId(comment.getUserId())
                    .build();
        }
        public static Comment toEntity(CommentDTO commentDTO){
            return Comment.builder()
                    .id(commentDTO.getId())
                    .text(commentDTO.getText())
                    //.topic(TopicMapper.toEntity(commentDTO.getTopicDTO()))
                    .date(commentDTO.getDate())
                    .userId(commentDTO.getUserId())
                    .build();
        }

}
