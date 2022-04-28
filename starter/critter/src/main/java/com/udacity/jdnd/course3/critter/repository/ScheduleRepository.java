package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    public List<Schedule> findAllByPetIds(Long petId);
    public List<Schedule> findAllByEmployeeIds(Long employeeId);
}