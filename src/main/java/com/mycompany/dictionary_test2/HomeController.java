package com.mycompany.dictionary_test2;

import com.mycompany.dictionary_test2.database.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomeController implements Initializable {

    @FXML
    private TableView<SearchModel> tableView;

    @FXML
    private TableColumn<SearchModel, String> terminColumn;

    @FXML
    private TableColumn<SearchModel, String> descriptionColumn;

    ObservableList<SearchModel> SearchModelObservableList = FXCollections.observableArrayList();

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void switchToSingup() throws IOException {
        App.setRoot("singup");
    }

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        terminColumn.setCellValueFactory(new PropertyValueFactory<>("termin"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        String viewQuery = "select termin, description from termin where is_deleted = false";
                
        try (var connection =  Database.connect();
            Statement view =  connection.createStatement();
            ResultSet output = view.executeQuery(viewQuery)) {
            
            while (output.next()) {
                String termin = output.getString("termin");
                String description = output.getString("description");
                SearchModelObservableList.add(new SearchModel(termin, description));
            }

            tableView.setItems(SearchModelObservableList);

        } catch (SQLException e) {
            System.err.println("Error loading data from database: " + e.getMessage());
        }

        if (SearchModelObservableList.isEmpty()) {
            System.out.println("No data found in the database.");
        } else {
            System.out.println("Data successfully loaded into TableView.");
        }
        
        
    }
}