package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertCustomerDTOToCustomer(customerDTO);
        Long id = userService.saveCustomer(customer);
        customerDTO.setId(id);
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){

        List<Customer> customers = userService.getAllCustomers();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer c : customers) {
            customerDTOS.add(convertCustomerToCustomerDTO(c));
        }
        return customerDTOS;

    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = userService.getOwnerByPet(petService.getPet(petId));
        return convertCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertEmployeeDTOToEmployee(employeeDTO);
        Long id = userService.saveEmployee(employee);
        employeeDTO.setId(id);
        return employeeDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = userService.getEmployee(employeeId);
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = userService.getEmployee(employeeId);
        employee.setDaysAvailable(daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {

        List<Employee> employees = userService.getBySkillsAndDays(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek());
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();

        if (employees != null) {
            for (Employee e : employees){
                employeeDTOS.add(convertEmployeeToEmployeeDTO(e));
            }
        }

        return employeeDTOS;
    }

    public CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO, "user_id, pets");
        customerDTO.setId(customer.getUser_id());
        List<Long> petIds = new ArrayList<>();
        List<Pet> pets = customer.getPets();


        if (pets != null){
            for (Pet pet : pets){
                petIds.add(pet.getPet_id());
            }
            customerDTO.setPetIds(petIds);
        }

        return customerDTO;
    }

    public Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer, "id, petIds");
        List<Long> petIds = customerDTO.getPetIds();
        List<Pet> pets = new ArrayList<>();

        if (petIds != null){
            for (Long id : petIds){
               pets.add(petService.getPet(id));
            }
            customer.setPets(pets);
        }
        return customer;
    }

    public EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO, "user_id, schedules");
        employeeDTO.setId(employee.getUser_id());
        return employeeDTO;
    }

    public Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee, "id");
        employee.setUser_id(employeeDTO.getId());
        return employee;
    }



}
