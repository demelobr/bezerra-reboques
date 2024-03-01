module org.example.bzreboques {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;

    opens org.example.bzreboques to javafx.fxml;
    exports org.example.bzreboques;
}