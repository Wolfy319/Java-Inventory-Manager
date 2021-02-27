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
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class dbController {

    @FXML
    public TableView<UI.dataBaseItems> table; 

    @FXML
    public TableColumn<UI.dataBaseItems, String> col_id;

    @FXML
    public TableColumn<UI.dataBaseItems,Integer> col_quantity;

    @FXML
    public TableColumn<UI.dataBaseItems, Float> col_cost;

    @FXML
    public TableColumn<UI.dataBaseItems, Float> col_price;

    @FXML
    public TableColumn<UI.dataBaseItems, String> col_sid;

    ObservableList<UI.dataBaseItems> oblist = FXCollections.observableArrayList();


public void initialize(URL location, ResourceBundle resources) throws SQLException{
    try{
    Connection con = UIDBConnector.getConnection();

    ResultSet rs = con.createStatement().executeQuery("SELECT * FROM testDB.DataEntries");
     
    while (rs.next()){//"should be column names"
        oblist.add(new UI.dataBaseItems(( rs.getString("id")), rs.getInt("quantity"), rs.getFloat("cost"), rs.getFloat("sale"), rs.getString("sid")));
    }

    }catch (SQLException ex){
        
    }
    col_quantity.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
    col_cost.setCellValueFactory(new PropertyValueFactory<>("WholesaleCost"));
    col_price.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
    col_sid.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
    col_id.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
    
    table.setItems(oblist);
}



}
