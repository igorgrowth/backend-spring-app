package com.manage.company.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TopicDTO {
    private Long id;

    private String title;

    private String description;

}
