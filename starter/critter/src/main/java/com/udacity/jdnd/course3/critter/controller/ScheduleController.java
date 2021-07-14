package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    ScheduleService scheduleService;

    PetService petService;

    UserService userService;

    ScheduleController(ScheduleService scheduleService, PetService petService, UserService userService){
        this.scheduleService = scheduleService;
        this.petService = petService;
        this.userService = userService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        List<Long> petIds = scheduleDTO.getPetIds();
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Pet> pets = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();

        if (petIds != null){
            for (Long petId : petIds){
                pets.add(petService.getPet(petId));
            }
        }

        schedule.setPets(pets);

        if (employeeIds != null){
            for (Long employeeId : employeeIds){
                employees.add(userService.getEmployee(employeeId));
            }
        }

        schedule.setEmployees(employees);

        Long id = scheduleService.saveSchedule(schedule);
        scheduleDTO.setId(id);
        return scheduleDTO;

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        if (schedules != null){
            for (Schedule s : schedules){
                scheduleDTOS.add(convertScheduleToScheduleDTO(s));
            }
        }

        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        List<Schedule> schedules = scheduleService.getSchedulesByPet(petService.getPet(petId));
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        if (schedules != null){
            for (Schedule s : schedules){
                scheduleDTOS.add(convertScheduleToScheduleDTO(s));
            }
        }

        return scheduleDTOS;



    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getSchedulesByEmployee(userService.getEmployee(employeeId));
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        if (schedules != null){
            for (Schedule s : schedules){
                scheduleDTOS.add(convertScheduleToScheduleDTO(s));
            }
        }

        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {


        List<Schedule> schedules = scheduleService.getSchedulesByCustomer(userService.getCustomer(customerId));
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        if (schedules != null){
            for (Schedule s : schedules){
                scheduleDTOS.add(convertScheduleToScheduleDTO(s));
            }
        }

        return scheduleDTOS;
    }

    public ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO, "schedule_id, employees, pets");
        scheduleDTO.setId(schedule.getSchedule_id());

        List<Pet> pets = schedule.getPets();
        List<Employee> employees = schedule.getEmployees();
        List<Long> petIds = new ArrayList<>();
        List<Long> empoyeeIds = new ArrayList<>();
        if (pets != null){
            for (Pet p : pets){
                petIds.add(p.getPet_id());
            }
        }

        scheduleDTO.setPetIds(petIds);

        if (employees != null){
            for ( Employee e : employees){
                empoyeeIds.add(e.getUser_id());
            }
        }

        scheduleDTO.setEmployeeIds(empoyeeIds);

        return scheduleDTO;

    }


}
