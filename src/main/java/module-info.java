module com.example.avto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.mavishop to javafx.fxml;
    exports com.example.mavishop;
}