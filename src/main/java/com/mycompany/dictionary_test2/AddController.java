package com.mycompany.dictionary_test2;

import com.mycompany.dictionary_test2.App;
import com.mycompany.dictionary_test2.database.Database;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javafx.fxml.FXML;
import java.util.UUID;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;


public class AddController {
    @FXML
    private TextField termin;

    @FXML
    private TextArea definition;
    
    @FXML
    private void switchBack() throws IOException {
        App.setRoot("logged_home");
    }   
    
    @FXML
    private void add() throws IOException, SQLException {   
        System.out.println("Attempt to add termin");
        String termin_name = termin.getText();
        String termin_description = definition.getText();
        
        String addQuery = "INSERT INTO termin VALUES (?, ?, ?, false);";
        try (var connection = Database.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(addQuery)) {
            
            UUID generated_id = UUID.randomUUID();
            preparedStatement.setObject(1, generated_id);
            preparedStatement.setString(2, termin_name);
            preparedStatement.setString(3, termin_description);
            System.out.println("given id: " + generated_id.toString());
            
            int rowsAffected = preparedStatement.executeUpdate(); 
            
            if (rowsAffected > 0) {
                    System.out.println("Termin " + termin_name + " added successfully");
                    App.showAlert("Success", "Термин успешно добавлен в словарь");
                } else {
                    App.showAlert("Error", "Sign up failed.");
                }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Termin " + termin_name + " added successfully");
                    App.showAlert("Success", "Термин успешно добавлен в словарь");

                } else {
                    App.showAlert("Error", "Что-то пошло не так, попробуйте еще раз");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding to table: " + e.getMessage());
        }
    } 
    
}
