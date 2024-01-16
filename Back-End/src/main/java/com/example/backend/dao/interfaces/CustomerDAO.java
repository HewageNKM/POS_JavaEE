package com.example.backend.dao.interfaces;

import com.example.backend.dao.CRUD_DAO;
import com.example.backend.entity.User;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO<T> extends CRUD_DAO{
    boolean save(T entity) throws SQLException, NamingException;
    boolean update(T entity) throws SQLException, NamingException;
    boolean delete(T entity) throws SQLException, NamingException;
    T findById(T entity) throws SQLException, NamingException;
    ArrayList<T> findAll() throws SQLException, NamingException;
}
