package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Long saveSchedule(Schedule schedule){

        Schedule s = scheduleRepository.save(schedule);
        return s.getSchedule_id();

    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesByPet(Pet pet){
        return scheduleRepository.getSchedulesByPets(pet);
    }

    public List<Schedule> getSchedulesByEmployee(Employee employee){
        return scheduleRepository.getSchedulesByEmployees(employee);
    }

    public List<Schedule> getSchedulesByCustomer(Customer customer){
        List<Pet> pets = petRepository.getPetsByOwner(customer);
        List<Schedule> schedules = new ArrayList<>();

        if (pets != null) {
            for (Pet p : pets){
                List<Schedule> tmp = getSchedulesByPet(p);
                if ( tmp != null) {
                    for (Schedule s : tmp){
                        schedules.add(s);
                    }
                }
            }
        }

        return schedules;


    }


}
