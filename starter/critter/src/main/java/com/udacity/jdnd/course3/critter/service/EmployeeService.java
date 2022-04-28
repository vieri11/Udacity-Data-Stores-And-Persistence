package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long employeeId) {
        Optional<Employee> employeeOption = employeeRepository.findById(employeeId);

        return (employeeOption.isPresent()) ? employeeOption.get() : null;
    }

    public List<Employee> findEmployeesForService (LocalDate date, Set<EmployeeSkill> employeeSkillSet) {

        return employeeRepository.findAllByDaysAvailable(date.getDayOfWeek())
                .stream()
                .filter(employee -> employee.getSkills().containsAll(employeeSkillSet))
                .collect(Collectors.toList());
    }
}
