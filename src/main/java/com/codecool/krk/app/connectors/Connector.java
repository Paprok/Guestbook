package com.codecool.krk.app.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private Connection connection;

    public Connection getConnection() {
     if(connection == null){
         try {
             connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/codecool", "host", "admin123");
         } catch (SQLException e){

         }
    }
        return connection;
    }
}
