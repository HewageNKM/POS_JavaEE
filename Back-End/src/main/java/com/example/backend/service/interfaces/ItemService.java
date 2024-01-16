package com.example.backend.service.interfaces;

import com.example.backend.dao.DAOFactory;
import com.example.backend.dao.impl.CustomerDAOImpl;
import com.example.backend.dao.impl.ItemDAOImpl;
import com.example.backend.dao.interfaces.ItemDAO;
import com.example.backend.service.SuperService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.backend.dao.DAOFactory.DAOType.CUSTOMER;

public interface ItemService<T> extends SuperService {
    ItemDAOImpl itemDAO = (ItemDAOImpl) DAOFactory.getInstance().getDAO(CUSTOMER);
    boolean save(T dto) throws SQLException, NamingException;
    boolean update(T dto) throws SQLException, NamingException;
    boolean delete(T dto) throws SQLException, NamingException;
    T findById(T dto) throws SQLException, NamingException;
    ArrayList<T> findAll() throws SQLException, NamingException;
}
