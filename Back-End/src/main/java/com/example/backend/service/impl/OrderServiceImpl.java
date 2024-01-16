package com.example.backend.service.impl;

import com.example.backend.dto.OrderDTO;
import com.example.backend.dto.OrderItemsDTO;
import com.example.backend.dto.UserDTO;
import com.example.backend.entity.Order;
import com.example.backend.entity.OrderItems;
import com.example.backend.entity.User;
import com.example.backend.service.interfaces.LoginService;
import com.example.backend.service.interfaces.OrderService;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService<OrderDTO> {

    @Override
    public boolean save(OrderDTO dto) throws SQLException, NamingException {
        if(dto.getId() != null && dto.getCustomer_id() != null && dto.getOrder_total() != 0.0){
            List<OrderItemsDTO> orderItemsDTOS = dto.getItems();
            List<OrderItems>  orderItems = new ArrayList<>();
            for (OrderItemsDTO orderItemsDTO : orderItemsDTOS) {
                orderItems.add(new OrderItems(orderItemsDTO.getItem_id(), orderItemsDTO.getItem_name(), orderItemsDTO.getItem_price(), orderItemsDTO.getItem_qty(), orderItemsDTO.getItem_total()));
            }
            Order order = new Order(dto.getId(), dto.getCustomer_id(), orderItems ,dto.getOrder_total(), dto.getOrder_total(),LocalDate.now());
            boolean save = orderDAO.save(order);
            return save;
        }else {
            return false;
        }
    }

    @Override
    public boolean update(OrderDTO dto) throws SQLException, NamingException {
        return false;
    }

    @Override
    public boolean delete(OrderDTO dto) throws SQLException, NamingException {
        return false;
    }

    @Override
    public OrderDTO findById(OrderDTO dto) throws SQLException, NamingException {
        return null;
    }

    @Override
    public ArrayList<OrderDTO> findAll() {
        return null;
    }
}
