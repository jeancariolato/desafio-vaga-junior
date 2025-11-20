package com.jlucacariolato.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    //CREDENCIAIS
    //DB PERSISTIR DURANTE EXECUÇÃO
    private static final String URL = "jdbc:h2:mem:posto;DB_CLOSE_DELAY=-1";
    private static final String USER = "";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
