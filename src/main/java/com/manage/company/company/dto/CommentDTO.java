package com.manage.company.company.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manage.company.company.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Long id;
    private String text;
    private LocalDateTime date;
    private Topic topic;
    private Long userId;
}
