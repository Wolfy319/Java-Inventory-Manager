module UI{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.jfoenix;
    exports UI;
    opens UI to javafx.graphics;
} 