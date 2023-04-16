package com.manage.company.company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicDTO {
    private Long id;

    private String title;

    private String description;

    private List<CommentDTO> commentDTOList = new ArrayList<>();

}
