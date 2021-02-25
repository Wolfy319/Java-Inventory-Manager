module CS3250{
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    exports CS3250;
    opens CS3250 to javafx.graphics;
} 