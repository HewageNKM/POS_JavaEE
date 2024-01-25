package com.example.backend.dao.impl;

import com.example.backend.dao.interfaces.LoginDAO;
import com.example.backend.db.DBSource;
import com.example.backend.entity.User;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginDAOImpl implements LoginDAO<User> {
    @Override
    public boolean save(User entity) throws SQLException, NamingException {
        Connection connection = DBSource.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO user VALUES (?,?)");
        pstm.setString(1, entity.getUserName());
        pstm.setObject(2, entity.getPassword());
        boolean b = pstm.executeUpdate() > 0;
        connection.close();
        return b;
    }

    @Override
    public boolean update(User entity) throws SQLException, NamingException {
        Connection connection = DBSource.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE user SET password=? WHERE username=?");
        pstm.setObject(1, entity.getPassword());
        pstm.setString(2, entity.getUserName());
        boolean b = pstm.executeUpdate() > 0;
        connection.close();
        return b;
    }

    @Override
    public boolean delete(User entity) throws SQLException, NamingException {
        Connection connection = DBSource.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM user WHERE userName=?");
        pstm.setString(1, entity.getUserName());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public User findById(User entity) throws SQLException, NamingException {
        Connection connection = DBSource.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM user WHERE username=? and password=?");
        pstm.setString(1, entity.getUserName());
        pstm.setString(2, entity.getPassword());
        ResultSet resultSet = pstm.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = new User(resultSet.getString(1), resultSet.getString(2));
        }
        System.out.println(user);
        return user;
    }

    @Override
    public ArrayList<User> findAll() {
        return null;
    }
}
