package com.mycompany.dictionary_test2;

import com.mycompany.dictionary_test2.App;
import com.mycompany.dictionary_test2.FinderController;
import com.mycompany.dictionary_test2.database.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import java.util.UUID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

public class EditController {
    private UUID selectedId;

    public void setSelectedId(UUID id) {
        this.selectedId = FinderController.handleRowClick();
        updateTextField();
    }

    @FXML
    private void switchBack() throws IOException {
        App.setRoot("logged_home");
    }
    
    @FXML
    private void findID() throws IOException {
        App.setRoot("edit_finder");
    }
    
    @FXML
    private TextField terminID;
    
    @FXML
    private TextField editedTermin;
    
    @FXML
    private TextArea editedDefinition;

    private void updateTextField() {
        if (terminID != null && selectedId != null) {
            terminID.setText(selectedId.toString()); // Convert UUID to String and set
        }
    }

//    @FXML
//    private void add() throws IOException, SQLException {   
//        System.out.println("Attempt to edit termin");
//        String word = editedTermin.getText();
//        String definition = editedDefinition.getText();
//        
//        String addQuery = "INSERT INTO termin VALUES (?, ?, ?, false);";
//        try (var connection = Database.connect();
//            PreparedStatement preparedStatement = connection.prepareStatement(addQuery)) {
//            
//
//            preparedStatement.setString(1, termin_description);
//            
//            int rowsAffected = preparedStatement.executeUpdate(); 
//            
//            if (rowsAffected > 0) {
//                    System.out.println("Termin " + termin_name + " added successfully");
//                    App.showAlert("Success", "Термин успешно добавлен в словарь");
//                } else {
//                    App.showAlert("Error", "Sign up failed.");
//                }
//
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    System.out.println("Termin " + termin_name + " added successfully");
//                    App.showAlert("Success", "Термин успешно добавлен в словарь");
//
//                } else {
//                    App.showAlert("Error", "Что-то пошло не так, попробуйте еще раз");
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Error adding to table: " + e.getMessage());
//        }
//    }

    @FXML
    private void edit() {
        

        // Update the word in the database or data structure
        // Validation can be added before this step

        // Show success alert
        App.showAlert("Success", "Word updated successfully!");
    }
//    

}
