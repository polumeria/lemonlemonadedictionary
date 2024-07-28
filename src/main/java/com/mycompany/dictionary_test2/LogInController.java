package com.mycompany.dictionary_test2;

import com.mycompany.dictionary_test2.App;
import java.io.IOException;
import javafx.fxml.FXML;


public class LogInController {
    
    @FXML
    private void switchBack() throws IOException {
        App.setRoot("home");
    }
    
    @FXML
    private void logIn() throws IOException {
        App.setRoot("home");
    }
    

}
