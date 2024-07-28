package com.mycompany.dictionary_test2.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseQuery {

    public static void main(String[] args) {
        // Подключение к базе данных
        try (Connection con = Database.connect();
             Statement stmt = con.createStatement();
             // Выполнение запроса
             ResultSet rs = stmt.executeQuery("SELECT * FROM your_table")) {

            // Обработка результатов запроса
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }   
}
