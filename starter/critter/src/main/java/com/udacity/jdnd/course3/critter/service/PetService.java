package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;


    public Pet getPetById(long id){
        Optional<Pet> pet =  petRepository.findById(id);

        return pet.isPresent() ? pet.get() : null;
    }

    public Pet savePet(Pet pet) {
        Pet returnedPet = petRepository.save(pet);
        Customer customer = returnedPet.getOwner();

        customer.addPet(returnedPet);
        customerRepository.save(customer);

        return returnedPet;
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(Long owner) {
        return petRepository.findAllByOwnerId(owner);
    }
}
