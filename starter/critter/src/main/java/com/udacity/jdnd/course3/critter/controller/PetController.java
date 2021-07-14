package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    UserService userService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setType(petDTO.getType());
        if (petDTO.getOwnerId() > 0) {
            pet.setOwner(userService.getCustomer(petDTO.getOwnerId()));
            userService.getCustomer(petDTO.getOwnerId()).getPets().add(pet);
        }
        Long id = petService.savePet(pet);
        petDTO.setId(id);
        return convertPetToPetDTO(pet);

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId);
        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> allPets = petService.getAllPets();
        List<PetDTO> allPetsDTO = new ArrayList<>();

        if (!allPets.isEmpty()){
            for (Pet p : allPets){
                allPetsDTO.add(convertPetToPetDTO(p));
            }
        }
        return allPetsDTO;

    }

    @GetMapping("/customer/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> petDTOS = new ArrayList<>();
        List<Pet> pets = petService.getPetsByOwner(userService.getCustomer(ownerId));

        if (pets != null) {
            for (Pet p : pets) {
                petDTOS.add(convertPetToPetDTO(p));
            }
        }
        return petDTOS;
    }


    public PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO, "pet_id, owner");
        petDTO.setId(pet.getPet_id());
        if (pet.getOwner()!= null){
            petDTO.setOwnerId(pet.getOwner().getUser_id());
        }
        return petDTO;
    }

    public Pet convertPetDTOToPet(PetDTO petDTO){

        return null;

    }


}
