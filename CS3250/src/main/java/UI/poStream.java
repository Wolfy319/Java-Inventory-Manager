package UI;

import java.lang.reflect.Array;
import java.security.Key;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.DoubleStream;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.graalvm.compiler.loop.MathUtil;







public class poStream {
        //Holds all orders by productID with a collection of quantites
         Multimap<String, Integer> prodAndQuant = ArrayListMultimap.create();

         //Holds ProductIDs and their prices
         HashMap <String, Double> productPrice = new HashMap<String, Double>();

        
         
     

    public void productCostMap() throws SQLException{
        Connection poCon = UIDBConnector.getConnection();
        ResultSet rs = poCon.createStatement().executeQuery("SELECT * FROM DataEntries");
        while (rs.next()){
            productPrice.put(rs.getString("productID"), rs.getDouble("salePrice"));
           
        }
    }


    public void salesOrderMap() throws SQLException{
        Connection poCon = UIDBConnector.getConnection();
        ResultSet rs = poCon.createStatement().executeQuery("SELECT * FROM PO");
        while (rs.next()){
            prodAndQuant.put(rs.getString("productID"), rs.getInt("quantity"));
        }
        //System.out.println("test1: ");
        //System.out.println(prodAndQuant.get("4ULDHPQMSSS9"));
        //System.out.println("test2: ");
        //System.out.println(prodAndQuant.get( "ZYZHSG6G1YCE"));
        //System.out.println("test3: ");
        //System.out.println(prodAndQuant.get("ZRTD35IV3G6X"));
    }

public void totalSalesPrep() throws SQLException{
        productCostMap();
        salesOrderMap();
        
        Set keySet = prodAndQuant.keySet(); 
        Iterator<String> it = keySet.iterator(); 
        while(it.hasNext()) {
            String key = (String) it.next();
            List values = (List) prodAndQuant.get(key);
            Integer sum = 0; 
            if(values.size() > 1){
                List<Integer> tempSumList = new ArrayList(); 
                for(Integer i = 0; i < values.size();){
                     sum += (Integer) values.get(i);
                     i++;
                }
                tempSumList.add(sum);
                prodAndQuant.replaceValues(key, tempSumList);
                tempSumList.clear();
            } 
        }
       // System.out.println(prodAndQuant);
            
}
//calculate total sales
public void calcTotalSales() throws SQLException{
    totalSalesPrep();
    Set quantSet = prodAndQuant.keySet(); 
    Iterator<String> quantIt = quantSet.iterator(); 

    
    
    Double totalSales = 0.0;

    while(quantIt.hasNext()){
        String qauntKey = (String) quantIt.next();
        //Fills a list with the quantitys
        List tempQauntList = (List) prodAndQuant.get(qauntKey);
        
        //Gets corresponding price with key
         //Double tempPrice = productPrice.get(qauntKey);
        
        //totalSales += tempPrice * (Integer) tempQauntList.get(0);
        
    }
    
   // System.out.println(totalSales);
}    



//EOF
}
          
        
          
        


        
        

 