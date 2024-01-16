package com.example.backend.dao.impl;

import com.example.backend.dao.interfaces.CustomerDAO;
import com.example.backend.dao.interfaces.LoginDAO;
import com.example.backend.db.DBSource;
import com.example.backend.dto.CustomerDTO;
import com.example.backend.entity.Customer;
import com.example.backend.entity.User;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO<Customer> {
    @Override
    public boolean save(Customer entity) throws SQLException, NamingException {
        Connection connection = DBSource.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM customer WHERE ID=?");
        pst.setObject(1, entity.getId());
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return false;
        } else {
            pst = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?,?)");
            pst.setObject(1, entity.getId());
            pst.setObject(2, entity.getName());
            pst.setObject(3, entity.getAddress());
            pst.setObject(4, entity.getSalary());
            return pst.executeUpdate() > 0;
        }

    }

    @Override
    public boolean update(Customer entity) throws SQLException, NamingException {
        Connection connection = DBSource.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM customer WHERE ID=?");
        pst.setObject(1, entity.getId());
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pst = connection.prepareStatement("UPDATE customer SET Name=?, Address=?, Salary=? WHERE ID=?");
            pst.setObject(1, entity.getName());
            pst.setObject(2, entity.getAddress());
            pst.setObject(3, entity.getSalary());
            pst.setObject(4, entity.getId());
            return pst.executeUpdate() > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Customer entity) throws SQLException, NamingException {
       Connection connection = DBSource.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM customer WHERE ID=?");
            pst.setObject(1, entity.getId());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                pst = connection.prepareStatement("DELETE FROM customer WHERE ID=?");
                pst.setObject(1, entity.getId());
                return pst.executeUpdate() > 0;
            } else {
                return false;
            }
    }

    @Override
    public Customer findById(Customer entity) throws SQLException, NamingException {
        Connection connection = DBSource.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM customer WHERE ID=?");
        pst.setObject(1, entity.getId());
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
        } else {
            return null;
        }
    }


    @Override
    public ArrayList<Customer> findAll() throws SQLException, NamingException {
            Connection connection = DBSource.getInstance().getConnection();
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM customer");
            ResultSet rs = pst.executeQuery();
            ArrayList<Customer> all = new ArrayList<>();
            while (rs.next()) {
                all.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDouble(4)));
            }
            return all;

    }
}
