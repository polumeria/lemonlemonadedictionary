package com.mycompany.dictionary_test2;

import com.mycompany.dictionary_test2.database.Database;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class SingUpController {
    
    @FXML
    private TextField username;

    @FXML
    private PasswordField password0;

    @FXML
    private PasswordField password1;
        
    @FXML
    private void switchBack() throws IOException {
        App.setRoot("home");
    }
    
    @FXML
    private void singUp() throws IOException, SQLException {
        System.out.println("Attempt to sign up");
        String login = username.getText();
        String pass0 = password0.getText();
        String pass1 = password1.getText();
        
        System.out.println("pass0 " + pass0);
        System.out.println("pass1 " + pass1);


        String singupQuery = "INSERT INTO users_data (login, password) VALUES (?, crypt(?, gen_salt('bf')));";
        try (var connection = Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(singupQuery)) {

            if (!pass0.equals(pass1)) {
                App.showAlert("Error", "Password not confirmed");
            } else {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, pass1);

                int rowsAffected = preparedStatement.executeUpdate(); 

                if (rowsAffected > 0) {
                    System.out.println("Sign up successful for user: " + login);
                    try {
                        App.setRoot("login");
                    } catch (IOException e) {
                        e.printStackTrace();
                        App.showAlert("Error", "Failed to change scene: " + e.getMessage());
                    }
                } else {
                    App.showAlert("Error", "Sign up failed.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error signing up to database: " + e.getMessage());
            App.showAlert("Error", "Database error: " + e.getMessage());
        }
        
        
        
//        String loginQuery = "SELECT login FROM users_data WHERE login = ? AND password = crypt(?, password);";
//        try (var connection = Database.connect();
//             PreparedStatement preparedStatement = connection.prepareStatement(loginQuery)) {
//
//            preparedStatement.setString(1, login);
//            preparedStatement.setString(2, pass);
//
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    System.out.println("Login successful for user: " + login);
//                    App.setRoot("home");
//                } else {
//                    App.showAlert("Error", "Invalid username or password.");
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Error logging into database: " + e.getMessage());
//        }
    }
}

