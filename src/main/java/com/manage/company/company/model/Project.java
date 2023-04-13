package com.manage.company.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@ToString(exclude = {"employeeList"})
@EqualsAndHashCode(exclude = {"employeeList"})
@NoArgsConstructor
public class Project extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    public Project(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    List<Employee> employeeList = new ArrayList<>();
}
