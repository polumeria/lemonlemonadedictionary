package com.mycompany.dictionary_test2;

import com.mycompany.dictionary_test2.database.Database;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import java.sql.*;


public class GreetingController {

    @FXML
    private void switchToHome() throws IOException {
        System.out.println("Connect button clicked");
        try (var connection =  Database.connect()){
                App.setRoot("home");
            } catch (SQLException e) {
                App.showAlert("Error", e.getMessage());
            }
    }
}