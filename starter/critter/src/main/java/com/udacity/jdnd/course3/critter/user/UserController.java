package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private CustomerService customerService;
    private PetService petService;

    @Autowired
    public UserController(CustomerService customerService) {
        this.customerService = customerService;
        this.petService = petService;
    }

    @GetMapping("/customer/{id}")
    public CustomerDTO getCustomer(@PathVariable long id) {
        Customer customer = customerService.getCustomerById(id);

        if(customer == null)
            return new CustomerDTO();
        else
            return convertCustomerToCustomerDTO(customer);
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){

        Customer costumer = customerService.saveCustomer(convertCustomerDTOToCustomer(customerDTO));

        return convertCustomerToCustomerDTO(costumer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        if(!customerDTO.getPetIds().isEmpty()) {
            // translate List of petIds to List of Pets
            customerDTO.getPetIds().stream()
                    .map(id -> petService.getPetById(id))
                    .forEach(pet -> customer.addPet(pet));
        }

        return customer;
    }

    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {

        CustomerDTO customerDTO2 = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO2);

        // translate List of Pets to List of petIds
        customerDTO2.setPetIds(customer.getPets().stream()
                                .map(Pet::getId)
                                .collect(Collectors.toList()));

        return customerDTO2;
    }


    private Optional<List<CustomerDTO>> convertCustomerToCustomerDTO(List<Customer> customers){
        List<CustomerDTO> newCustomerDTO = new ArrayList<>();
        CustomerDTO customerDTO = new CustomerDTO();

        for(Customer c : customers) {
            BeanUtils.copyProperties(c, customerDTO);
            newCustomerDTO.add(customerDTO);
        }

        return Optional.of(newCustomerDTO);
    }

}
