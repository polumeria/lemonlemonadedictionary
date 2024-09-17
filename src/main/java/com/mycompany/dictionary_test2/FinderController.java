package com.mycompany.dictionary_test2;

import com.mycompany.dictionary_test2.database.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.UUID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class FinderController implements Initializable {
    @FXML
    private void switchBack() throws IOException {
        App.setRoot("edit");
    }
    
    @FXML
    private TextField find;

    @FXML
    private TableView<FinderModel> tableView;

    @FXML
    private TableColumn<FinderModel, String> terminColumn;

    @FXML
    private TableColumn<FinderModel, UUID> idColumn; // Changed from descriptionColumn to idColumn

    ObservableList<FinderModel> FinderModelObservableList = FXCollections.observableArrayList();
    
    private void setItems(ObservableList<FinderModel> items, TableView<FinderModel> tableView) {
        tableView.setItems(items);
    }
    
    private void initHandler(Scene scene) {
        if (scene != null) {
            scene.setOnKeyPressed(event -> {
                try {
                    if (event.getCode() == KeyCode.ENTER) {
                        System.out.println("Enter has been pressed.");
                        find(); // Call the search method
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
    private void find() throws IOException, SQLException {
        String findString = find.getText();

        String findQuery = "select termin, id from public.termin where termin LIKE ?"; 
        try (var connection = Database.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(findQuery)) {
            preparedStatement.setString(1, "%" + findString + "%");

            try (ResultSet output = preparedStatement.executeQuery()) {
                FinderModelObservableList.clear();

                if (output.isBeforeFirst()) {
                    while (output.next()) {
                        String termin = output.getString("termin");
                        Object id = output.getObject("id"); 
                        FinderModelObservableList.add(new FinderModel(termin, (UUID) id));
                    }

                    setItems(FinderModelObservableList, tableView);
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
        
        Scene scene = find.getScene();
        initHandler(scene);
        find.requestFocus(); // Ensure focus is set

        terminColumn.setCellValueFactory(new PropertyValueFactory<>("termin"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id")); 

        String viewQuery = "select termin, id from termin where is_deleted = false"; 

        try (var connection = Database.connect();
             Statement view = connection.createStatement();
             ResultSet output = view.executeQuery(viewQuery)) {

            while (output.next()) {
                String termin = output.getString("termin");
                Object id = output.getObject("id"); 
                FinderModelObservableList.add(new FinderModel(termin, (UUID) id)); // Adjusted constructor call
            }

            tableView.setItems(FinderModelObservableList);

        } catch (SQLException e) {
            System.err.println("Error loading data from database: " + e.getMessage());
        }

        if (FinderModelObservableList.isEmpty()) {
            System.out.println("No data found in the database.");
        } else {
            System.out.println("Data successfully loaded into TableView.");
        }

        find.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    find(); // Call the search method
                } catch (IOException | SQLException e) {
                    App.showAlert("Error", "An error occurred while searching: " + e.getMessage());
                }
            }
        });
        
        tableView.setOnMouseClicked(this::handleRowClick);

    }

    public void handleRowClick(MouseEvent event) {
        if (event.getClickCount() == 2) { // Double-click to select
            FinderModel selectedModel = tableView.getSelectionModel().getSelectedItem();
            if (selectedModel != null) {
                try {
                    System.out.println("Selected Termin: " + selectedModel.getTermin());
                    System.out.println("Selected ID: " + selectedModel.getId());
                    UUID selectedId = selectedModel.getId();
                    
                    EditController editController = new EditController();
                    editController.setSelectedId(selectedId);
                    App.setRoot("edit");
                } catch (IOException e) {
                    App.showAlert("Error", "An error occurred while switching: " + e.getMessage());
                }
            }            
        }
    }
}