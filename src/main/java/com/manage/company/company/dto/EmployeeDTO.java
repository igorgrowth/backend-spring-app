package com.manage.company.company.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manage.company.company.model.Project;
import com.manage.company.company.model.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Position position;

}
