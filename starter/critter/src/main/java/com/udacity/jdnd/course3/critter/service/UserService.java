package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Long saveEmployee(Employee employee){
        Employee employee1 = employeeRepository.save(employee);

        return employee1.getUser_id();
    }

    public Employee getEmployee(Long id){
         return employeeRepository.getById(id);
    }

    public Long saveCustomer(Customer customer){
        Customer customer1 = customerRepository.save(customer);
        return customer1.getUser_id();
    }

    public Customer getCustomer(Long id){ return customerRepository.getById(id); }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getOwnerByPet(Pet pet){
        return customerRepository.getCustomerByPets(pet);
    }

    public List<Employee> getBySkillsAndDays(Set<EmployeeSkill> skills, DayOfWeek day){
         List<Employee> employees = employeeRepository.getEmployeeByDaysAvailableContaining(day);
         return employees.stream().filter(e -> e.getSkills().containsAll(skills)).collect(Collectors.toList());
    }

}
