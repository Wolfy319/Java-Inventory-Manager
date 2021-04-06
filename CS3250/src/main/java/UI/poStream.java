package UI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;



public class poStream {
    
    HashMap <String, Double> productPrice = new HashMap<String, Double>();

     

    public void productCostMap() throws SQLException{
        Connection poCon = UIDBConnector.getConnection();
        ResultSet rs = poCon.createStatement().executeQuery("SELECT * FROM DataEntries");
        while (rs.next()){
            productPrice.put(rs.getString("productID"), rs.getDouble("salePrice"));
        }
   
   
   
   
    }

poStream test = new poStream(); 


}
