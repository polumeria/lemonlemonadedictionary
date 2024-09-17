package com.mycompany.dictionary_test2;

import com.mycompany.dictionary_test2.App;
import com.mycompany.dictionary_test2.database.Database;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;




public class LogInController {
    
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;
    
    @FXML
    private void switchBack() throws IOException {
        App.setRoot("home");
    }
    
    @FXML
    private void logIn() throws IOException, SQLException {
        System.out.println("Attempt to log in");
        String login = username.getText();
        String pass = password.getText();
        
        String loginQuery = "SELECT login FROM users_data WHERE login = ? AND password = crypt(?, password);";
        try (var connection = Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(loginQuery)) {

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, pass);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Login successful for user: " + login);
                    App.setRoot("logged_home");
                } else {
                    App.showAlert("Error", "Invalid username or password.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error logging into database: " + e.getMessage());
        }
    } 
}
    


