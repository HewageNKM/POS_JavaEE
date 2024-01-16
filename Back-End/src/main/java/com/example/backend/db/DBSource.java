package com.example.backend.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBSource {
    private static DBSource dbSource;
    private final Connection connection;

    private DBSource() throws NamingException, SQLException {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup("DBPool");
        connection = ds.getConnection();
    }
    public static DBSource getInstance() throws NamingException, SQLException {
        if(dbSource == null){
            dbSource = new DBSource();
        }
        return dbSource;
    }
    public Connection getConnection(){
        return connection;
    }
}
