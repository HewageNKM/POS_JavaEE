package com.example.backend.service.impl;

import com.example.backend.dto.CustomerDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.entity.Customer;
import com.example.backend.entity.User;
import com.example.backend.service.interfaces.CustomerService;
import com.example.backend.service.interfaces.LoginService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerServiceImpl implements CustomerService<CustomerDTO> {

    @Override
    public boolean save(CustomerDTO dto) throws SQLException, NamingException {
        if (dto.getId() != null && dto.getName() != null && dto.getAddress() != null && dto.getSalary() != 0.0) {
            return customerDAO.save(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
        }else {
            return false;
        }
    }

    @Override
    public boolean update(CustomerDTO dto) throws SQLException, NamingException {
        if (dto.getId() != null && dto.getName() != null && dto.getAddress() != null && dto.getSalary() != 0.0) {
            return customerDAO.update(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
        }else {
            return false;
        }
    }

    @Override
    public boolean delete(CustomerDTO dto) throws SQLException, NamingException {
        if (dto.getId() != null) {
            return customerDAO.delete(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
        }else {
            return false;
        }
    }

    @Override
    public CustomerDTO findById(CustomerDTO dto) throws SQLException, NamingException {
        if (dto.getId() != null) {
            Customer customer = customerDAO.findById(new Customer(dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary()));
            if (customer == null) {
                return null;
            }
            return new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getSalary());
        }else {
            return null;
        }
    }

    @Override
    public ArrayList<CustomerDTO> findAll() throws SQLException, NamingException {
        ArrayList<Customer> all = customerDAO.findAll();
        ArrayList<CustomerDTO> allDTO = new ArrayList<>();
        for (Customer customer : all) {
            allDTO.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(), customer.getSalary()));
        }
        return allDTO;
    }
}
