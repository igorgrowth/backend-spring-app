package com.manage.company.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manage.company.company.model.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@ToString(exclude = {"project"})
@EqualsAndHashCode(exclude = {"project"})
@NoArgsConstructor
public class Employee extends BaseEntity {
    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Position position;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "project_id")
    private Project project;
}
