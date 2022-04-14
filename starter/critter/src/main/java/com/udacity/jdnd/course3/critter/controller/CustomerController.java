package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customer/{id}")
    public Optional<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
}
