package UI;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene; 
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent; 
import CS3250.SQLPo;
import CS3250.DisplayPO;
import CS3250.POItem;
import javafx.scene.control.Alert; 
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
    public TextField textUser = new TextField();

    @FXML
    public TextField textDate = new TextField();

    @FXML
    public TextField textTotal = new TextField();

    @FXML
    public TextField textPid = new TextField();

    @FXML
    public TableView<UI.poFact> poTable;

    @FXML
    public TableColumn<UI.poFact, String> user_id;

   
    @FXML
    public TableColumn<UI.poFact, String> date_id;


    @FXML
    public TableColumn<UI.poFact, String> total_id; 

    @FXML
    public TableColumn<UI.poFact, String> ID; 

    
    ObservableList poList = FXCollections.observableArrayList();
 public void displayTable() throws SQLException{
    
    try {
        Connection con = UIDBConnector.getConnection();

        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM PO");
    
        while (rs.next()) {// "should be column names"

            poList.add(new poFact(rs.getString("userID"), rs.getString("date"),
                    rs.getString("total"), rs.getString("ID")));
        }



    }finally{}
    user_id.setCellValueFactory(new PropertyValueFactory<>("userID"));
    date_id.setCellValueFactory(new PropertyValueFactory<>("date"));
    total_id.setCellValueFactory(new PropertyValueFactory<>("total"));
    ID.setCellValueFactory(new PropertyValueFactory<>("ID"));

    poTable.setItems(poList);

    FilteredList<poFact> filteredData = new FilteredList<>(poList, p -> true); 
    searchBox.textProperty().addListener((Observable, oldVal, newVal) -> {
        filteredData.setPredicate(poFact -> { 
            if(newVal == null || newVal.isEmpty()){
                return true; 
            }
            String lowerFilter = newVal.toLowerCase(); 
            if(poFact.getUserID().toLowerCase().contains(lowerFilter)){
                return true;
            }return false;
        });
    });
    SortedList<poFact> sortedData = new SortedList<>(filteredData); 
    sortedData.comparatorProperty().bind(poTable.comparatorProperty());
    poTable.setItems(sortedData);
   
}

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

@FXML
public void viewBtn(ActionEvent event){
    SQLPo sp = new SQLPo();
    sp.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");
    Integer user = Integer.valueOf(textPid.getText());
    DisplayPO fullpo = new DisplayPO();
    fullpo = sp.GenerateFullPO(user);
    POItem pos = new POItem();
    pos = fullpo.getLi().get(0);
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Detailed Purchase View");
    alert.setHeaderText("Detailed PO");
    alert.setContentText("Ordered by: " + fullpo.getName() + "\n" + "BuyerID: " + pos.getBuyerID() + "\n" + "Buyer Email: " + 
    
    "Purchase ID: " + pos.getPOID() + "\n" +
    "Ordered on: " + fullpo.getDate() + "\n" + "Item ID: " +
    pos.getItemID() + "\n" + "Item Quantity: " + pos.getQuantity() + "\n" + "Total: " + fullpo.getTotal());
   
    alert.show();

}



@FXML
public void handleCrud(ActionEvent event) throws SQLException {
    if (event.getSource() == btnAdd) {
        addItem();
    } else if (event.getSource() == btnDelete) {
        deleteItem();
    }
}

Statement st;

private void addItem() throws SQLException {
    Connection con = UIDBConnector.getConnection();
    st =  (Statement) con.createStatement();
    String statement="INSERT INTO PO(userID,date,total,ID) VALUES(" + textUser.getText() + ", '" + textDate.getText() + "', "+ textTotal.getText() + ", " + textPid.getText() + ");";
    st.execute(statement);
    poList.clear();
    displayTable();
}

private void deleteItem() throws SQLException {
    Connection con = UIDBConnector.getConnection();
    st =  (Statement) con.createStatement();
    String toBeDeleted = textPid.getText();
    String statement = "DELETE FROM PO WHERE ID ='"+ toBeDeleted + "';";
    st.execute(statement);
    poList.clear();
    displayTable();
}


@FXML
public void highlightClick(MouseEvent event) {
    UI.poFact selectedItem = poTable.getSelectionModel().getSelectedItem();
    
    textUser.setText(selectedItem.getUserID());
    textDate.setText(selectedItem.getDate());
    textTotal.setText(selectedItem.getTotal());
    textPid.setText(selectedItem.getID());
}

@FXML
public void exit_Button2(ActionEvent event) {
        Stage stage = (Stage) exitBtn2.getScene().getWindow();
        stage.close();
}

 @FXML
 public void returnToDB(ActionEvent event) throws Exception{
    Parent DbsScreen = FXMLLoader.load(getClass().getResource("DBScreen.fxml"));
    Scene DbsScene = new Scene(DbsScreen);
    Stage dbsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    dbsStage.setScene(DbsScene);
    dbsStage.show();
 }

}


