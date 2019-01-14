package com.codecool.krk.app;

import com.codecool.krk.app.connectors.Connector;
import com.codecool.krk.app.dao.DAO;
import com.codecool.krk.app.dao.DAOSQL;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.sql.Connection;

public class App {
    public static void main(String[] args) throws Exception {
        Connection connection = (new Connector()).getConnection();
        DAO dao = new DAOSQL(connection);
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        server.createContext("/hello", new Hello());
        server.createContext("/form", new Form(dao));
        server.createContext("/static", new Static());

        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
    }
}
