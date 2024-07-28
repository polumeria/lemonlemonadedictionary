module com.mycompany.dictionary_test2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens com.mycompany.dictionary_test2 to javafx.fxml;
    exports com.mycompany.dictionary_test2;
}
