package com.manage.company.company.mapper;

import com.manage.company.company.dto.CommentDTO;
import com.manage.company.company.dto.EmployeeDTO;
import com.manage.company.company.model.Comment;
import com.manage.company.company.model.Employee;

public class CommentMapper {
    public static CommentDTO toDTO(Comment comment){
            return CommentDTO.builder()
                    .id(comment.getId())
                    .text(comment.getText())
                    .topic(comment.getTopic())
                    .date(comment.getDate())
                    .build();
        }
    public static Comment toEntiti(CommentDTO commentDTO){
        return Comment.builder()
                .id(commentDTO.getId())
                .text(commentDTO.getText())
                .topic(commentDTO.getTopic())
                .date(commentDTO.getDate())
                .build();
    }

}
