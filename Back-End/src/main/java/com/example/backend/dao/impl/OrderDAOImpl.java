package com.example.backend.dao.impl;

import com.example.backend.dao.interfaces.OrderDAO;
import com.example.backend.db.DBSource;
import com.example.backend.entity.Order;
import com.example.backend.entity.OrderItems;

import javax.naming.NamingException;
import java.sql.*;
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
            pst.setDate(5, Date.valueOf(String.valueOf(entity.getDate())));
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
        Connection connection = DBSource.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT orders.ID, customer.Name, orders.order_total, orders.discount, orders.date  FROM orders LEFT JOIN customer on orders.CID = customer.id WHERE orders.ID=?");
        pst.setString(1, entity.getId());
        ResultSet resultSet = pst.executeQuery();
        Order order = new Order();
        if (resultSet.next()) {
            order.setId(resultSet.getString(1));
            order.setCustomer_id(resultSet.getString(2));
            order.setOrder_total(resultSet.getDouble(3));
            order.setDiscount(resultSet.getDouble(4));
            order.setDate(resultSet.getDate(5).toLocalDate());
        }
        return order;
    }

    @Override
    public ArrayList<Order> findAll() throws SQLException, NamingException {
        Connection connection = DBSource.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT orders.ID, customer.Name, orders.order_total, orders.discount, orders.date  FROM orders LEFT JOIN customer on orders.CID = customer.id");
        ResultSet resultSet = pst.executeQuery();
        ArrayList<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getString(1));
            order.setCustomer_id(resultSet.getString(2));
            order.setOrder_total(resultSet.getDouble(3));
            order.setDiscount(resultSet.getDouble(4));
            order.setDate(resultSet.getDate(5).toLocalDate());
            orders.add(order);
        }
        return orders;
    }
}
