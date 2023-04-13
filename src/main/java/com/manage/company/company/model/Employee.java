package com.manage.company.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manage.company.company.model.enums.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@Builder
@ToString(exclude = {"project"})
@EqualsAndHashCode(exclude = {"project"})
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    public Employee(Long id, String firstName, String lastName, String email, Position position, Project project) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.position = position;
        this.project = project;
    }
}
