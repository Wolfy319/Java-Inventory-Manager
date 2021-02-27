package CS3250;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class dbController {


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

}
