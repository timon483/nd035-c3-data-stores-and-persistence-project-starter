package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Customer;
import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
