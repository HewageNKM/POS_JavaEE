package com.example.backend.service.impl;

import com.example.backend.dto.UserDTO;
import com.example.backend.entity.User;
import com.example.backend.service.interfaces.LoginService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginServiceImpl implements LoginService<UserDTO> {

    @Override
    public boolean save(UserDTO dto) throws SQLException, NamingException {
        return loginDAO.save(new User(dto.getUserName(),dto.getPassword()));
    }

    @Override
    public boolean update(UserDTO dto) throws SQLException, NamingException {
        return loginDAO.update(new User(dto.getUserName(),dto.getPassword()));
    }

    @Override
    public boolean delete(UserDTO dto) throws SQLException, NamingException {
        return loginDAO.delete(new User(dto.getUserName(),dto.getPassword()));
    }

    @Override
    public UserDTO findById(UserDTO dto) throws SQLException, NamingException {
        User byId = loginDAO.findById(new User(dto.getUserName(), dto.getPassword()));
        return byId == null ? null : new UserDTO(byId.getUserName(), byId.getPassword());
    }

    @Override
    public ArrayList<UserDTO> findAll() {
        return null;
    }
}
