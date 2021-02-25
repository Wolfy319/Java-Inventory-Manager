package CS3250;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class dbController {


    @FXML
    public TableColumn<CS3250.dataBaseItems, String> col_id;

    @FXML
    public TableColumn<CS3250.dataBaseItems,Integer> col_quantity;

    @FXML
    public TableColumn<CS3250.dataBaseItems, Float> col_cost;

    @FXML
    public TableColumn<CS3250.dataBaseItems, Float> col_price;

    @FXML
    public TableColumn<CS3250.dataBaseItems, String> col_sid;

}
