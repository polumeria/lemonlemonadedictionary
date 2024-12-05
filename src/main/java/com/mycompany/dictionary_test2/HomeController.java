package com.mycompany.dictionary_test2;

import com.mycompany.dictionary_test2.database.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

public class HomeController implements Initializable {
    @FXML
    private TextField search;

    @FXML
    private TableView<SearchModel> tableView;

    @FXML
    private TableColumn<SearchModel, String> terminColumn;

    @FXML
    private TableColumn<SearchModel, String> descriptionColumn;

    ObservableList<SearchModel> SearchModelObservableList = FXCollections.observableArrayList();
    
    private void setItems(ObservableList<SearchModel> items, TableView<SearchModel> tableView) {
        tableView.setItems(items);
    }
    
    private void initHandler(Scene scene) {
        if (scene != null) {
            scene.setOnKeyPressed(event -> {
                try {
                    if (event.getCode() == KeyCode.ENTER) {
                        System.out.println("Enter has been pressed.");
                        search(); // Call the search method
                    }
                } catch (Exception e) {
                    System.err.println("Error in key handler: " + e.getMessage());
                    e.printStackTrace(); // For debugging
                }
            });
        } else {
            System.err.println("Scene is null. Cannot set key handler.");
        }
    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void switchToSingup() throws IOException {
        App.setRoot("singup");
    }

    @FXML
    private void search() throws IOException, SQLException {
        String searchString = search.getText();

        String searchQuery = "select termin, description from public.termin where termin LIKE ? or description LIKE ?";
        try (var connection = Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {
            preparedStatement.setString(1, "%" + searchString + "%");
            preparedStatement.setString(2, "%" + searchString + "%");

            try (ResultSet output = preparedStatement.executeQuery()) {
                SearchModelObservableList.clear(); // Clear previous results

                if (output.isBeforeFirst()) { // Check if there are results
                    while (output.next()) {
                        String termin = output.getString("termin");
                        String description = output.getString("description");
                        SearchModelObservableList.add(new SearchModel(termin, description));
                    }

                    setItems(SearchModelObservableList, tableView);
                } else {
                    App.showAlert("Info", "No results found.");
                }
            } catch (SQLException e) {
                System.err.println("Error loading data from database: " + e.getMessage());
            }
        } catch (SQLException e) {
            App.showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resource) {
        
        Scene scene = search.getScene();
        initHandler(scene);
        search.requestFocus(); // Ensure focus is set

        
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

        search.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    search(); // Call the search method
                } catch (IOException | SQLException e) {
                    App.showAlert("Error", "An error occurred while searching: " + e.getMessage());
                }
            }
        });

    }
}