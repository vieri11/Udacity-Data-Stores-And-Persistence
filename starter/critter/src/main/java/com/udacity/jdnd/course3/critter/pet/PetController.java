package com.udacity.jdnd.course3.critter.pet;


import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

        Pet pet = petService.savePet(convertPetDTOToPet(petDTO));
        return convertPetToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);

        if(pet != null)
            return convertPetToPetDTO(pet);
        else
            return new PetDTO();
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> petList = petService.getAllPets();

        return convertPetstoPetDTOs(petList);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return convertPetstoPetDTOs(petService.getPetsByOwner(ownerId));
    }

    public Pet convertPetDTOToPet (PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);

        // Get Owner object from ownerId
        pet.setOwner(customerService.getCustomerById(petDTO.getOwnerId()));

        return pet;
    }

    public PetDTO convertPetToPetDTO (Pet pet) {
        PetDTO petDTO = new PetDTO();

        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());

        return petDTO;
    }

    public List<PetDTO> convertPetstoPetDTOs (List<Pet> petList) {
        return petList.stream()
                .map(p -> convertPetToPetDTO(p))
                .collect(Collectors.toList());
    }
}
