package com.example.backend.dao.impl;

import com.example.backend.db.DBSource;
import com.example.backend.dto.OrderItemsDTO;
import com.example.backend.entity.Order;
import com.example.backend.dao.interfaces.OrderDAO;
import com.example.backend.entity.OrderItems;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO<Order> {
    @Override
    public boolean save(Order entity) throws SQLException, NamingException {
        Connection connection = DBSource.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            PreparedStatement pst = connection.prepareStatement("INSERT INTO orders VALUES (?,?,?,?,?)");
            pst.setString(1, entity.getId());
            pst.setString(2, entity.getCustomer_id());
            pst.setDouble(3, entity.getOrder_total());
            pst.setDouble(4, entity.getDiscount());
            pst.setDate(5, Date.valueOf(entity.getDate()));
            boolean save = pst.executeUpdate() > 0;
            if (save) {
                List<OrderItems> orders = entity.getItems();
                for (OrderItems order : orders) {
                    pst = connection.prepareStatement("INSERT INTO order_details VALUES (?,?,?,?,?,?)");
                    pst.setString(1, entity.getId());
                    pst.setString(2, order.getItem_id());
                    pst.setString(3, order.getItem_name());
                    pst.setDouble(4, order.getItem_price());
                    pst.setDouble(5, order.getItem_qty());
                    pst.setDouble(6, order.getItem_total());
                    save = pst.executeUpdate() > 0;
                    if (!save) {
                        connection.rollback();
                        return false;
                    }
                }
                for (OrderItems order : orders) {
                    pst = connection.prepareStatement("UPDATE item SET QTY=QTY-? WHERE ID=?");
                    pst.setDouble(1, order.getItem_qty());
                    pst.setString(2, order.getItem_id());
                    save = pst.executeUpdate() > 0;
                    if (!save) {
                        connection.rollback();
                        return false;
                    }
                }
            } else {
                connection.rollback();
                return false;
            }
        } finally {
            connection.setAutoCommit(true);
        }
        return true;
    }

    @Override
    public boolean update(Order entity) throws SQLException, NamingException {
        return false;
    }

    @Override
    public boolean delete(Order entity) throws SQLException, NamingException {
        return false;
    }

    @Override
    public Order findById(Order entity) throws SQLException, NamingException {
        return null;
    }

    @Override
    public ArrayList<Order> findAll() throws SQLException, NamingException {
        return null;
    }
}
