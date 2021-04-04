package UI;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import CS3250.PO;
import CS3250.SQLPo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage; 
public class poController {

    @FXML
    public Button exitBtn2;

    @FXML
    public Button try_it;

    @FXML
    public Button btnAdd;

    @FXML
    public Button btnShipped;

    @FXML
    public Button btnDelete;
    
    @FXML
    public Button btnDisp;

    @FXML
    public Button rptView;

    @FXML
    public Button ret_db; 

    @FXML
    public TextField searchBox = new TextField();
    
    @FXML
    public TextField textLoc = new TextField();

    @FXML
    public TextField textDate = new TextField();

    @FXML
    public TextField textTotal = new TextField();

    @FXML
    public TextField textPid = new TextField();

    @FXML 
    public TextField textEmail = new TextField();

    @FXML
    public TableView<UI.observablePO> poTable;

    @FXML
    public TableColumn<UI.observablePO, String> user_id;

   
    @FXML
    public TableColumn<UI.observablePO, String> date_id;


    @FXML
    public TableColumn<UI.observablePO, String> total_id; 

    @FXML
    public TableColumn<UI.observablePO, String> ID; 

    
    SQLPo po = new SQLPo();
    
    ObservableList poList;
    @FXML
    public void initialize() {
        try {
            displayTable();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }    
    }
  
    /**
     * Displays the PO screen data
     * 
     * @throws SQLException - thrown if there is an error in the sql syntax
     */
 public void displayTable() throws SQLException{
    po.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");
    poList = FXCollections.observableArrayList(po.GenerateShortPOs());
    user_id.setCellValueFactory(new PropertyValueFactory<>("productID"));
    date_id.setCellValueFactory(new PropertyValueFactory<>("date"));
    total_id.setCellValueFactory(new PropertyValueFactory<>("email"));
    ID.setCellValueFactory(new PropertyValueFactory<>("ID"));

    
    FilteredList<observablePO> filteredList = new FilteredList<>(poList);
    searchBox.textProperty().addListener((Observable, oldVal, newVal) -> {
        filteredList.setPredicate(poFact -> { 
            if(newVal == null || newVal.isEmpty()){
                return true; 
            }
            String lowerFilter = newVal.toLowerCase(); 
            if(poFact.getProductID().toLowerCase().contains(lowerFilter)){
                return true;
            }return false;
        });
    });
    SortedList<observablePO> sortedData = new SortedList<>(filteredList); 
    poTable.setItems(sortedData);
}

/**
 * Displays the data
 * 
 * @param event - on click event
 */
public void dispBtn(ActionEvent event){
    btnDisp.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
            try { 
               displayTable();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    });
}


/** 
 * Opens a detailed purchase order view
 * 
 * @param event - on button click
 */
@FXML
public void viewBtn(ActionEvent event){
    SQLPo sp = new SQLPo();
    sp.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");
    Integer PID = Integer.valueOf(textPid.getText());
    PO fullpo = new PO();
    fullpo = sp.getPo(PID);
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Detailed Purchase View");
    alert.setHeaderText("Detailed PO");
    alert.setContentText("Ordered by: " + fullpo.getEmail()  + "Loacted: " + fullpo.getCustomerLocation() + "\n" + 
    "Purchase ID: " + fullpo.getID() + "\n" +
    "Ordered on: " + fullpo.getDate() + "\n" + "Item ID: " +
    fullpo.getProductID() + "\n" + "Item Quantity: " + fullpo.getQuantity());
    alert.show();

}


/**
 * Handles the add and delete button being clicked
 * 
 * @param event - on click event
 * 
 * @throws SQLException - throws if an error in the sql syntax
 */
@FXML
public void handleCrud(ActionEvent event) throws SQLException {
    if (event.getSource() == btnAdd) {
        addItem();
    } else if (event.getSource() == btnDelete) {
        deleteItem();
    }
}

Statement st;

/**
 * Adds an item to the po database
 * 
 * @throws SQLException - throws if an error in the sql
 */
private void addItem() throws SQLException {
    observablePO p = new observablePO();
    p.setCustomerLocation(textLoc.getText());
    p.setDate(textDate.getText());
    p.setEmail(textEmail.getText());
    p.setProductID(textPid.getText());
    p.quantity(Integer.parseInt(textTotal.getText()));
    po.createEntry("0", p);

    poList.clear();
    displayTable();
}

/**
 * Deletes an item in the po database
 * 
 * @throws SQLException - throws if an error in the sql
 */
private void deleteItem() throws SQLException {
    Connection con = UIDBConnector.getConnection();
    st =  (Statement) con.createStatement();
    String toBeDeleted = textPid.getText();
    String statement = "DELETE FROM PO WHERE ID ='"+ toBeDeleted + "';";
    st.execute(statement);
    poList.clear();
    displayTable();
}


/**
 * Copies clicked row into the text fields
 * 
 * @param event - mouse click event
 */
@FXML
public void highlightClick(MouseEvent event) {
    UI.observablePO selectedItem = poTable.getSelectionModel().getSelectedItem();
    
    textEmail.setText(selectedItem.getEmail());
    textDate.setText(selectedItem.getDate());
    textLoc.setText(selectedItem.getCustomerLocation());
    textTotal.setText(Integer.toString(selectedItem.getQuantity()));
    textPid.setText(selectedItem.getProductID());
}

/**
 * Exits the po screen and application
 * 
 * @param event - on click event
 */
@FXML
public void exit_Button2(ActionEvent event) {
        Stage stage = (Stage) exitBtn2.getScene().getWindow();
        stage.close();
} 


/** 
 * Returns to the database view
 * 
 * @param event - on button click
 * @throws Exception - if DBScreen.fxml is unreachable
 */

 @FXML
 public void returnToDB(ActionEvent event) throws Exception{
    Parent DbsScreen = FXMLLoader.load(getClass().getResource("DBScreen.fxml"));
    Scene DbsScene = new Scene(DbsScreen);
    Stage dbsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    dbsStage.setScene(DbsScene);
    dbsStage.show();
 }



}
