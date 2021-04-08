package UI;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Path;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfXrefTable;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import org.apache.pdfbox.printing.PDFPrintable;

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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import CS3250.SQLData;

/**
 * Controls the db screen
 * @author Hunter DeArment Kyle Brown
 * 
 */


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
    public Button btnPO = new Button(); 

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
    public TextField searchBox = new TextField(); 

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
    
    /**
     * Intializes the database into the table view
     * @throws SQLException - throws if their is an sql related error
     */
    @FXML
    public void initialize() throws SQLException {
        try {
            Connection con = UIDBConnector.getConnection();

            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM DataEntries");
            
            while (rs.next()) {// "should be column names"

                oblist.add(new dataBaseItems(rs.getString("productID"), rs.getString("stockQuantity"),
                        rs.getString("wholesaleCost"), rs.getString("salePrice"), rs.getString("supplierID")));
                
            }



        }finally{}
        col_quantityid.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        col_costid.setCellValueFactory(new PropertyValueFactory<>("wholesaleCost"));
        col_priceid.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        col_sid.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        col_id.setCellValueFactory(new PropertyValueFactory<>("productID"));

        table.setItems(oblist);


        FilteredList<dataBaseItems> filteredData = new FilteredList<>(oblist, p -> true); 
        searchBox.textProperty().addListener((Observable, oldVal, newVal) -> {
            filteredData.setPredicate(dataBaseItems -> { 
                if(newVal == null || newVal.isEmpty()){
                    return true; 
                }
                String lowerFilter = newVal.toLowerCase(); 
                if(dataBaseItems.getProductID().toLowerCase().contains(lowerFilter)){
                    return true;
                }return false;
            });
        });
        SortedList<dataBaseItems> sortedData = new SortedList<>(filteredData); 
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

/**
 * Handles if one of the buttons is selected and executes the designated code
 * @param event - clicking button event
 * @throws SQLException - throws if there is an sql related error
 */
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


/**
 * Inserts an item into the database
 * @throws SQLException - Throws if sql related error
 */

@FXML
public void highlightClick(MouseEvent event) {
    UI.dataBaseItems selectedItem = table.getSelectionModel().getSelectedItem();
    
    textId.setText(selectedItem.getProductID());
    textQuantity.setText(selectedItem.getStockQuantity());
    textCost.setText(selectedItem.getWholesaleCost());
    textPrice.setText(selectedItem.getSalePrice());
    textSid.setText(selectedItem.getSupplierID());
}

private void insertItem() throws SQLException {
    Connection con = UIDBConnector.getConnection();
    st =  (Statement) con.createStatement();
    String statement="INSERT INTO DataEntries(productID,supplierID,stockQuantity,WholesaleCost,salePrice) VALUES('" + textId.getText() + "', '" + textSid.getText() + "' , '"+ textQuantity.getText() + "' , '" + textCost.getText() + "' , '" + textPrice.getText() + "');" ;
    st.execute(statement);
    oblist.clear();
    initialize();
}

/**
 * Updates an item in the database
 * @throws SQLException - Throws if sql related error
 */
private void updateItem() throws SQLException {
    Connection con = UIDBConnector.getConnection();
    st =  (Statement) con.createStatement();
    String statement = "UPDATE DataEntries SET supplierID = '" + textSid.getText() + "', stockQuantity = " + textQuantity.getText() + ", wholesaleCost = " + textCost.getText() + ", salePrice = " + textPrice.getText() + "WHERE productID = '" + textId.getText() + "'";
    st.execute(statement);
    oblist.clear();
    initialize();

}

Statement st;

/**
 * Deletes an item from the database
 * @throws SQLException - Throws if sql related error
 */
private void deleteItem() throws SQLException{
    Connection con = UIDBConnector.getConnection();
    st =  (Statement) con.createStatement();
    String toBeDeleted = textId.getText();
    String statement = "DELETE FROM DataEntries WHERE productID ='"+ toBeDeleted + "';";
    st.execute(statement);
    oblist.clear();
    initialize();
    
}

/**
 * Attemps to initialize the database into the table view
 * @param event - Loading values into the table view
 */
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


/**
 * Exits the db screen
 * @param event - Button click event
 */
@FXML
public void exit_Button2(ActionEvent event) {
        Stage stage = (Stage) exitBtn2.getScene().getWindow();
        stage.close();
}

@FXML
public void po_button(ActionEvent event) throws IOException{
    Parent poScreen = FXMLLoader.load(getClass().getResource("POScreen.fxml"));
    Scene poScene = new Scene(poScreen);
    Stage poStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    poStage.setScene(poScene);
    poStage.show();
}

@FXML
public void showReport(ActionEvent event) throws IOException{
    //Creates temporary sales pdf
    File tempSales = File.createTempFile("SalesReport", ".pdf"); 
    PdfWriter writer = new PdfWriter(tempSales);
    PdfDocument salesDoc = new PdfDocument(writer);
    PdfPage pageOne = salesDoc.addNewPage();
    Document doc = new Document(salesDoc);

    //Adds Rt3 Logo
    String rt3Loc = "CS3250\\src\\main\\java\\UI\\Images\\RT3.png";
    ImageData rt3Data = ImageDataFactory.create(rt3Loc);
    Image rt3Image = new Image(rt3Data);
    rt3Image.scaleAbsolute(100, 100);
    rt3Image.setFixedPosition(250,675);
    doc.add(rt3Image);

    
    //Sales Report heading
    String headingText = "Sales Report Generated by IMS";
    Paragraph headingBreak = new Paragraph(headingText);
    headingBreak.setTextAlignment(TextAlignment.CENTER);
    doc.add(headingBreak);
    
    //Add Table
    float[] columnWidths = {1.5f, 2f, 5f, 2f};
    Table table = new Table(UnitValue.createPercentArray(columnWidths));
    Cell cells = new Cell(4,4)
                .add(new Paragraph("Sales Snap-Shot"))
                .setTextAlignment(TextAlignment.CENTER);
    table.addHeaderCell(cells);            
    table.setFixedPosition(100, 650,400);

    Cell totalSalesCell = new Cell(4, 4)
                       .add(new Paragraph("Total Sales: ")); 
    table.addFooterCell(totalSalesCell);
    doc.add(table); 

    Cell thisMonthSalesCell = new Cell(4, 4)
                       .add(new Paragraph("This Months Sales: ")); 
    table.addFooterCell(thisMonthSalesCell);
    doc.add(table);

    Cell thisWeekSalesCell = new Cell(4, 4)
                       .add(new Paragraph("This Weeks Sales: ")); 
    table.addFooterCell(thisWeekSalesCell);
    doc.add(table);

    Cell mostPopularItemCell = new Cell(4, 4)
                       .add(new Paragraph("Most Popular Item: ")); 
    table.addFooterCell(mostPopularItemCell);
    doc.add(table);

    Cell bestCustomerCell = new Cell(4, 4)
                       .add(new Paragraph("Best Customer by revenue: ")); 
    table.addFooterCell(bestCustomerCell);
    doc.add(table);

    doc.close();
    Desktop.getDesktop().open(tempSales);
    tempSales.deleteOnExit();
}




}
