module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.lang3;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}