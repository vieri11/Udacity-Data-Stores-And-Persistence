package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private ScheduleService scheduleService;
    private CustomerService customerService;
    private PetService petService;
    private EmployeeService employeeService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, CustomerService customerService, PetService petService, EmployeeService employeeService) {
        this.scheduleService = scheduleService;
        this.customerService = customerService;
        this.petService = petService;
        this.employeeService = employeeService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.saveSchedule(convertScheduleDTOToSchedule(scheduleDTO));

        if(schedule == null)
            return new ScheduleDTO();
        else
            return convertScheduleToScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return convertSchedulesToScheduleDTOs(scheduleService.getAllSchedules());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return convertSchedulesToScheduleDTOs(scheduleService.getAllSchedulesByPet(petService.getPetById(petId)));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return convertSchedulesToScheduleDTOs(scheduleService.getAllSchedulesByEmployee(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        // Get customer Pet List
        List<Pet> petList = customerService.getCustomerById(customerId).getPets();
        List<Schedule> scheduleList = new ArrayList<>();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        for(Pet pet:petList){
            scheduleService.getAllSchedulesByPet(pet).forEach(schedule -> scheduleList.add(schedule));
        }

        return convertSchedulesToScheduleDTOs(scheduleList);
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        if(!scheduleDTO.getPetIds().isEmpty()) {
            // translate List of petIds to List of Pets
            scheduleDTO.getPetIds().stream()
                    .map(id -> petService.getPetById(id))
                    .forEach(pet -> schedule.addPet(pet));
        }

        if(!scheduleDTO.getEmployeeIds().isEmpty()) {
            // translate List of petIds to List of Pets
            scheduleDTO.getEmployeeIds().stream()
                    .map(id -> employeeService.getEmployeeById(id))
                    .forEach(employee -> schedule.addEmployee(employee));
        }

        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        // translate List<Pets> to List<Long> of petIds
        scheduleDTO.setPetIds(schedule.getPets().stream()
                .map(Pet::getId)
                .collect(Collectors.toList()));

        // translate List<Employee> to List<Long> of employeeIds;
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream()
                .map(Employee::getId)
                .collect(Collectors.toList()));

        return scheduleDTO;
    }

    private List<ScheduleDTO> convertSchedulesToScheduleDTOs(List<Schedule> scheduleList) {
        return scheduleList.stream()
                .map(s -> convertScheduleToScheduleDTO(s))
                .collect(Collectors.toList());
    }
}
