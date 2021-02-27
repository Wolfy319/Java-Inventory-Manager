package UI;

import java.net.URL;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

public class dbController {

    @FXML
    public Button try_it;

    
    @FXML
    public TableView<UI.dataBaseItems> table; 

    @FXML
    public TableColumn<UI.dataBaseItems, String> col_id;

    @FXML
    public TableColumn<UI.dataBaseItems,Integer> col_quantityid;

    @FXML
    public TableColumn<UI.dataBaseItems, Float> col_costid;

    @FXML
    public TableColumn<UI.dataBaseItems, Float> col_priceid;

    @FXML
    public TableColumn<UI.dataBaseItems, String> col_sid;

    ObservableList oblist =  FXCollections.observableArrayList(); 

@FXML
public void initialize() throws SQLException{
    try{
    Connection con = UIDBConnector.getConnection();

    ResultSet rs = con.createStatement().executeQuery("SELECT * FROM DataEntries");
     int counter =0;
    while (rs.next()&& counter < 100){//"should be column names"

        oblist.add(new dataBaseItems(rs.getString("productID"), rs.getString("stockQuantity"), rs.getString("wholesaleCost"), rs.getString("salePrice"), rs.getString("supplierID")));
        counter +=1; 
    }

    }catch (SQLException ex){
        
    }
    col_quantityid.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
    col_costid.setCellValueFactory(new PropertyValueFactory<>("wholesaleCost"));
    col_priceid.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
    col_sid.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
    col_id.setCellValueFactory(new PropertyValueFactory<>("productID"));
    
    table.setItems(oblist);
}

public void ibutton(ActionEvent event){
    try_it.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
            try {
                initialize();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    });
}

}
