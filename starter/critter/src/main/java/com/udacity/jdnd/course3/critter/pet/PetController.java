package com.udacity.jdnd.course3.critter.pet;


import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private PetService petService;
    private CustomerService customerService;

    @Autowired
    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);

        pet.setOwner(customerService.getCustomerById(petDTO.getOwnerId()));

        PetDTO petDTO2 = new PetDTO();
        BeanUtils.copyProperties(petService.savePet(pet), petDTO2);

        return petDTO2;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        PetDTO petDTO = new PetDTO();
        Pet pet = petService.getPetById(petId);

        if(pet != null)
            BeanUtils.copyProperties(pet, petDTO);
        else
            return new PetDTO();

        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        throw new UnsupportedOperationException();
    }
}
