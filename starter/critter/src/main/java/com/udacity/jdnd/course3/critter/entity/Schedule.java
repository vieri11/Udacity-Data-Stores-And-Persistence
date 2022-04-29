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
    @JoinTable(
            name="schedule_employee",
            joinColumns = @JoinColumn(name="schedule_id"),
            inverseJoinColumns = @JoinColumn(name="employee_id")
    )
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany(targetEntity = Pet.class)
    @JoinTable(
            name="schedule_pet",
            joinColumns = @JoinColumn(name="schedule_id"),
            inverseJoinColumns = @JoinColumn(name="pet_id")
    )
    private List<Pet> pets = new ArrayList<>();

    private LocalDate date;

    @ElementCollection
    private Set<EmployeeSkill> activities;

    public void addPet(Pet pet) {
        if(this.pets == null){
            this.pets = new ArrayList<>();
        }
        this.pets.add(pet);
    }

    public void addEmployee(Employee employee) {
        if(this.employees == null){
            this.employees = new ArrayList<>();
        }
        this.employees.add(employee);
    }
}
