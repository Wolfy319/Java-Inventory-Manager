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
    public TextField textId;

    @FXML
    public TextField textQuantity;

    @FXML
    public TextField textCost;

    @FXML
    public TextField textPrice;

    @FXML
    public TextField textSid;

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
public void handle(ActionEvent event) throws SQLException {
    if (event.getSource() == btnAdd) {
        insertItem();
    }
}

private void insertItem() throws SQLException {
    String query = "INSERT INTO DataEntries VALUES (" + textId.getText() + "," + textQuantity.getText() + "," + textCost.getText() + "," + textPrice.getText() + "," + textSid.getText() + ")";
    executeQuery(query);
    initialize();
}

public void executeQuery(String query) throws SQLException {
    Connection conn;
    conn = UIDBConnector.getConnection();
    Statement st;
    try{
        st = conn.createStatement();
        st.executeUpdate(query);
    }catch(Exception ex) {
        ex.printStackTrace();
    }

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
