package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

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
        return scheduleRepository.getSchedulesByCustomers(customer);
    }


}
