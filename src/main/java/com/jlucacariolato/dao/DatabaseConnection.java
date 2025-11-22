package com.jlucacariolato.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // CREDENCIAIS
    private static final String URL = "jdbc:mysql://localhost:3306/posto_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // Estabelece e retorna uma conexão com o banco de dados MySQL
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
