package com.example.backend.dao;

import com.example.backend.dao.impl.CustomerDAOImpl;
import com.example.backend.dao.impl.ItemDAOImpl;
import com.example.backend.dao.impl.LoginDAOImpl;
import com.example.backend.dao.interfaces.CustomerDAO;


public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public enum DAOType {
        LOGIN, ITEM, ORDER, CUSTOMER
    }

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public SuperDAO getDAO(DAOType daoType) {
        switch (daoType) {
            case LOGIN:
                return new LoginDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            default:
                return null;
        }
    }
}
