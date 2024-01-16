package com.example.backend.service;

import com.example.backend.service.impl.CustomerServiceImpl;
import com.example.backend.service.impl.ItemServiceImpl;
import com.example.backend.service.impl.LoginServiceImpl;
import com.example.backend.service.impl.OrderServiceImpl;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    public enum ServiceType {
        LOGIN, ITEM, ORDER, CUSTOMER
    }

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return (serviceFactory == null) ? (serviceFactory = new ServiceFactory()) : serviceFactory;
    }

    public SuperService getService(ServiceType serviceType) {
        switch (serviceType) {
            case LOGIN:
                return  new LoginServiceImpl();
            case CUSTOMER:
                return new CustomerServiceImpl();
            case ITEM:
                return new ItemServiceImpl();
            case ORDER:
                return new OrderServiceImpl();
            default:
                return null;
        }
    }

}
