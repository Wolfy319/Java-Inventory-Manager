import com.mysql.jdbc.MySQLConnection;

module UI{
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.jfoenix;
    requires mysql.connector.java;
    requires java.sql;
    exports UI;
    opens UI to javafx.graphics;
    
} 