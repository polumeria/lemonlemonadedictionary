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
/**
 *
 * @author holodok
 */
public class DeleteController {
    @FXML
    private void switchBack() throws IOException {
        App.setRoot("logged_home");
    }
}
