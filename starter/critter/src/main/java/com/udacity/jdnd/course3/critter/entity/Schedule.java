package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name="schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(targetEntity = Employee.class)
    private List<Long> employeeIds = new ArrayList<>();

    @ManyToMany(targetEntity = Pet.class)
    private List<Long> petIds = new ArrayList<>();

    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> activities;
}
