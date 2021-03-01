package UI;


import java.net.URL;
import java.util.ResourceBundle;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import CS3250.SQLData;

public class dbController {

    @FXML
    public Button exitBtn2;

    @FXML
    public Button try_it;

    @FXML
    public Button btnAdd;

    @FXML
    public Button btnUpdate;

    @FXML
    public Button btnDelete;

    @FXML
    public TextField textId = new TextField();

    @FXML
    public TextField textQuantity = new TextField();

    @FXML
    public TextField textCost = new TextField();

    @FXML
    public TextField textPrice = new TextField();

    @FXML
    public TextField textSid = new TextField();

    @FXML
    public TableView<UI.dataBaseItems> table;

    @FXML
    public TableColumn<UI.dataBaseItems, String> col_id;

    @FXML

    public TableColumn<UI.dataBaseItems, Integer> col_quantityid;

    @FXML
    public TableColumn<UI.dataBaseItems, Float> col_costid;

    @FXML
    public TableColumn<UI.dataBaseItems, Float> col_priceid;

    @FXML
    public TableColumn<UI.dataBaseItems, String> col_sid;


    ObservableList oblist = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        try {
            Connection con = UIDBConnector.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM DataEntries");
            int counter = 0;
            while (rs.next() && counter < 10) {// "should be column names"

                oblist.add(new dataBaseItems(rs.getString("productID"), rs.getString("stockQuantity"),
                        rs.getString("wholesaleCost"), rs.getString("salePrice"), rs.getString("supplierID")));
                counter += 1;
            }



        }finally{}
        col_quantityid.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        col_costid.setCellValueFactory(new PropertyValueFactory<>("wholesaleCost"));
        col_priceid.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        col_sid.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        col_id.setCellValueFactory(new PropertyValueFactory<>("productID"));

        table.setItems(oblist);
    }


@FXML
public void handleCrud(ActionEvent event) throws SQLException {
    if (event.getSource() == btnAdd) {
        insertItem();
    } else if (event.getSource() == btnUpdate) {
        updateItem();
    } else if (event.getSource() == btnDelete) {
        deleteItem();
    }
}

private void insertItem() throws SQLException {
    Connection con = UIDBConnector.getConnection();
    st =  (Statement) con.createStatement();
    String statement="INSERT INTO DataEntries(productID,supplierID,stockQuantity,WholesaleCost,salePrice) VALUES('" + textId.getText() + "', '" + textSid.getText() + "' , '"+ textQuantity.getText() + "' , '" + textCost.getText() + "' , '" + textPrice.getText() + "');" ;
    st.execute(statement);
}

private void updateItem() throws SQLException {
    Connection con = UIDBConnector.getConnection();
    st =  (Statement) con.createStatement();
    String statement = "UPDATE DataEntries SET supplierID = '" + textSid.getText() + "', stockQuantity = " + textQuantity.getText() + ", wholesaleCost = " + textCost.getText() + ", salePrice = " + textPrice.getText() + "WHERE productID = '" + textId.getText() + "'";
    st.execute(statement);
    oblist.clear();
    initialize();

}

Statement st;

private void deleteItem() throws SQLException{
    Connection con = UIDBConnector.getConnection();
    st =  (Statement) con.createStatement();
    String toBeDeleted = textId.getText();
    String statement = "DELETE FROM DataEntries WHERE productID ='"+ toBeDeleted + "';";
    st.execute(statement);
    oblist.clear();
    initialize();
    
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


// Exits db screen
@FXML
public void exit_Button2(ActionEvent event) {
        Stage stage = (Stage) exitBtn2.getScene().getWindow();
        stage.close();
}

}
