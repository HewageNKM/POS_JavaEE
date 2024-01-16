package com.example.backend.service.interfaces;

import com.example.backend.dao.DAOFactory;
import com.example.backend.dao.impl.LoginDAOImpl;
import com.example.backend.dao.impl.OrderDAOImpl;
import com.example.backend.service.SuperService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.backend.dao.DAOFactory.DAOType.LOGIN;
import static com.example.backend.dao.DAOFactory.DAOType.ORDER;

public interface OrderService<T> extends SuperService {

    OrderDAOImpl orderDAO = (OrderDAOImpl) DAOFactory.getInstance().getDAO(ORDER);
    boolean save(T dto) throws SQLException, NamingException;
    boolean update(T dto) throws SQLException, NamingException;
    boolean delete(T dto) throws SQLException, NamingException;
    T findById(T dto) throws SQLException, NamingException;
    ArrayList<T> findAll();
}
