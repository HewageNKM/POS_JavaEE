package com.example.backend.dao.impl;

import com.example.backend.dao.interfaces.ItemDAO;
import com.example.backend.db.DBSource;
import com.example.backend.entity.Item;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO<Item> {
    @Override
    public boolean save(Item entity) throws SQLException, NamingException {
        System.out.println("entity = " + entity);
        Connection connection = DBSource.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM item WHERE ID=?");
        pst.setObject(1, entity.getId());
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return false;
        } else {
            pst = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
            pst.setString(1, entity.getId());
            pst.setString(2, entity.getName());
            pst.setDouble(3, entity.getPrice());
            pst.setInt(4, entity.getQty());
            return pst.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Item entity) throws SQLException, NamingException {
        Connection connection = DBSource.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM item WHERE ID=?");
        pst.setObject(1, entity.getId());
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pst = connection.prepareStatement("UPDATE item SET Name=?, Price=?, QTY=? WHERE ID=?");
            pst.setString(1, entity.getName());
            pst.setDouble(2, entity.getPrice());
            pst.setInt(3, entity.getQty());
            pst.setString(4, entity.getId());
            return pst.executeUpdate() > 0;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(Item entity) throws SQLException, NamingException {
        Connection connection = DBSource.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM item WHERE ID=?");
        pst.setObject(1, entity.getId());
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pst = connection.prepareStatement("DELETE FROM item WHERE ID=?");
            pst.setObject(1, entity.getId());
            return pst.executeUpdate() > 0;
        } else {
            return false;
        }
    }

    @Override
    public Item findById(Item entity) throws SQLException, NamingException {
        Connection connection = DBSource.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM item WHERE ID=?");
        pst.setObject(1, entity.getId());
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return new Item(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getInt(4));
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Item> findAll() throws SQLException, NamingException {
        Connection connection = DBSource.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("SELECT * FROM item");
        ResultSet rs = pst.executeQuery();
        ArrayList<Item> allItems = new ArrayList<>();
        while (rs.next()) {
            allItems.add(new Item(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getInt(4)));
        }
        return allItems;
    }
}
