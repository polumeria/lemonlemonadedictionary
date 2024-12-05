package com.mycompany.dictionary_test2.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    public static Connection connect() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.setProperty("stringtype", "unspecified"); // Set the property for uuid
        try {
            // Get database credentials from DatabaseConfig class
            var jdbcUrl = DatabaseConfig.getDbUrl();
            var user = DatabaseConfig.getDbUsername();
            var password = DatabaseConfig.getDbPassword();

            // Open a connection
            return DriverManager.getConnection(jdbcUrl, user, password);
            
        } catch (SQLException  e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
