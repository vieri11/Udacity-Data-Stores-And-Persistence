package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private PetService petService;
    private EmployeeService employeeService;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, PetService petService, EmployeeService employeeService) {
        this.scheduleRepository = scheduleRepository;
        this.petService = petService;
        this.employeeService = employeeService;
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getAllSchedulesByPet(Pet pet) {
        return scheduleRepository.findAllByPetsContaining(pet);
    }

    public List<Schedule> getAllSchedulesByEmployee(Long employeeId) {
        return scheduleRepository.findAllByEmployeesContaining(employeeService.getEmployeeById(employeeId));
    }
}
