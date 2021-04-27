package UI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.errorprone.annotations.Var;
import com.mysql.jdbc.CallableStatement;

import CS3250.SQLPo;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;







public class poStream {

        
        
         Multimap<String, Integer> prodAndQuant = ArrayListMultimap.create();

         Multimap<String, Integer> currentMonthMap = ArrayListMultimap.create();

         Multimap<String, Integer> twoMonthMap = ArrayListMultimap.create();

         Multimap<String, Integer> threeMonthMap = ArrayListMultimap.create();

         Multimap<String, Integer> currentWeekMap = ArrayListMultimap.create();

         HashMap <String, Double> bestCustomerMap = new HashMap<String, Double>(); 

         HashMap <String, Double> productPrice = new HashMap<String, Double>();

        HashMap <String, Integer> daysAndMonths = new HashMap<String, Integer>(); 
        



    public void daysAndMonths(){
        daysAndMonths.put("01", 21);
        daysAndMonths.put("02", 28);
        daysAndMonths.put("03", 31);
        daysAndMonths.put("04", 30);
        daysAndMonths.put("05", 31);
        daysAndMonths.put("06", 30);
        daysAndMonths.put("07", 31);
        daysAndMonths.put("08", 31);
        daysAndMonths.put("09", 30);
        daysAndMonths.put("10", 31);
        daysAndMonths.put("11", 30);
        daysAndMonths.put("12", 31);
    }
        

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
}

public void currentMonthMap() throws SQLException{
    Connection poCon = UIDBConnector.getConnection();
        ResultSet rs = poCon.createStatement().executeQuery("SELECT * FROM PO WHERE monthname (date) = 'June' " );
        while (rs.next()){
            currentMonthMap.put(rs.getString("productID"), rs.getInt("quantity"));
        }
    }

public void twoMonthMap() throws SQLException{
    Connection poCon = UIDBConnector.getConnection();
    ResultSet ss = poCon.createStatement().executeQuery("SELECT * FROM PO WHERE monthname (date) = 'May' " );
    while (ss.next()){
        twoMonthMap.put(ss.getString("productID"), ss.getInt("quantity"));
    }
}

public void threeMonthMap() throws SQLException{
    Connection poCon = UIDBConnector.getConnection();
    ResultSet ts = poCon.createStatement().executeQuery("SELECT * FROM PO WHERE monthname (date) = 'April' " );
        while (ts.next()){
            threeMonthMap.put(ts.getString("productID"), ts.getInt("quantity"));
        }
}

public String calcTotalSales() throws SQLException{
    totalSalesPrep();
    Set quantSet = prodAndQuant.keySet();
    
    Iterator<String> quantIt = quantSet.iterator(); 

    Double totalSales = 0.0;
    Integer quantTotal = 0;
    
    while(quantIt.hasNext()){
        String qauntKey = (String) quantIt.next();
        List tempQauntList = (List) prodAndQuant.get(qauntKey);
        
        Double tempPrice = productPrice.get(qauntKey);

        if(tempPrice == null){
            continue;
        }
        totalSales += tempPrice * (Integer) tempQauntList.get(0);
        quantTotal = quantTotal + (Integer) tempQauntList.get(0);
    }
    return String.format("%,.2f", totalSales);     
}    

public void currentMonthPrep() throws SQLException {
    //Gives current month
    //Date date = Calendar.getInstance().getTime();
    //DateFormat dateFormatMonth = new SimpleDateFormat("M");
    //String strDate = dateFormatMonth.format(date);
    //DateFormat dateFormatYear = new SimpleDateFormat("YYYY");
    //String strDateYear = dateFormatYear.format(date); 
    //System.out.println(strDate);
    //System.out.println(strDateYear);
    //String currentMon = strDate;

    //Integer tempInt = Integer.parseInt(strDate);
    //Integer tempOneMonthAgoInt = tempInt-1;    
    //Integer temptwoMonthAgoInt = tempInt - 2;
    
    //String oneMonthAgo = tempOneMonthAgoInt.toString();
    //String twoMonthAgo = temptwoMonthAgoInt.toString(); 

    productCostMap();
    currentMonthMap();
        
        Set keySet = currentMonthMap.keySet(); 
        Iterator<String> it = keySet.iterator(); 
        while(it.hasNext()) {
            String key = (String) it.next();
            List values = (List) currentMonthMap.get(key);
            Integer sum = 0; 
            if(values.size() > 1){
                List<Integer> tempSumList = new ArrayList(); 
                for(Integer i = 0; i < values.size();){
                     sum += (Integer) values.get(i);
                     i++;
                }
                tempSumList.add(sum);
                currentMonthMap.replaceValues(key, tempSumList);
                tempSumList.clear();
            } 
        }
}

public void twoMonthPrep() throws SQLException{
    productCostMap();
    twoMonthMap();
        
        Set keySet = twoMonthMap.keySet(); 
        Iterator<String> it = keySet.iterator(); 
        while(it.hasNext()) {
            String key = (String) it.next();
            List values = (List) twoMonthMap.get(key);
            Integer sum = 0; 
            if(values.size() > 1){
                List<Integer> tempSumList = new ArrayList(); 
                for(Integer i = 0; i < values.size();){
                     sum += (Integer) values.get(i);
                     i++;
                }
                tempSumList.add(sum);
                twoMonthMap.replaceValues(key, tempSumList);
                tempSumList.clear();
            } 
        }
        
}
    
public void threeMonthPrep() throws SQLException{
    productCostMap();
    threeMonthMap();
        
        Set keySet = threeMonthMap.keySet(); 
        Iterator<String> it = keySet.iterator(); 
        while(it.hasNext()) {
            String key = (String) it.next();
            List values = (List) threeMonthMap.get(key);
            Integer sum = 0; 
            if(values.size() > 1){
                List<Integer> tempSumList = new ArrayList(); 
                for(Integer i = 0; i < values.size();){
                     sum += (Integer) values.get(i);
                     i++;
                }
                tempSumList.add(sum);
                threeMonthMap.replaceValues(key, tempSumList);
                tempSumList.clear();
            } 
        }
}

public String calcCurrentMonthSales() throws SQLException{
    currentMonthPrep();
    Set quantSet = currentMonthMap.keySet(); 
    Iterator<String> quantIt = quantSet.iterator(); 

    Double currentSales = 0.0;

    while(quantIt.hasNext()){
        String qauntKey = (String) quantIt.next();
        
        List tempQauntList = (List) currentMonthMap.get(qauntKey);
        
        Double tempPrice = productPrice.get(qauntKey);
        currentSales += tempPrice * (Integer) tempQauntList.get(0);
    }
    return String.format("%,.2f", currentSales); 
}    

public String calcTwoMonthSales() throws SQLException{
    twoMonthPrep();
    Set quantSet = twoMonthMap.keySet(); 
    Iterator<String> quantIt = quantSet.iterator(); 

    Double twoMonthSales = 0.0;

    while(quantIt.hasNext()){
        String qauntKey = (String) quantIt.next();
        
        List tempQauntList = (List) twoMonthMap.get(qauntKey);
        
        Double tempPrice = productPrice.get(qauntKey);
        twoMonthSales += tempPrice * (Integer) tempQauntList.get(0);
    }
    return String.format("%,.2f", twoMonthSales); 
}   

public String calcThreeMonthSales() throws SQLException{
   threeMonthPrep();
    Set quantSet = threeMonthMap.keySet(); 
    Iterator<String> quantIt = quantSet.iterator(); 

    Double threeMonthSales = 0.0;

    while(quantIt.hasNext()){
        String qauntKey = (String) quantIt.next();
        
        List tempQauntList = (List) threeMonthMap.get(qauntKey);
        
        Double tempPrice = productPrice.get(qauntKey);
        threeMonthSales += tempPrice * (Integer) tempQauntList.get(0);
    }
    return String.format("%,.2f", threeMonthSales); 
}   

public String totalOrderCount() throws SQLException{
    Connection poCon = UIDBConnector.getConnection();
    ResultSet rs = poCon.createStatement().executeQuery("SELECT COUNT(*) FROM PO" );
    rs.next();
    return String.format("%,8d%n", rs.getInt("count(*)" )); 

}
          

public String[] bestCustomer() throws SQLException{
    String filename = "jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1"; 
    SQLPo SQLPO = new SQLPo(); 
    SQLPO.initializeDatabase(filename);
    List<observablePO> Pos = SQLPO.GenerateShortPOs();
    productCostMap(); 
    
    Double intialTotal = 0.0; 
    String bestCustomer = "";
    Double highestTotal = 0.0; 
    
    for(int i = 0; i < Pos.size();){
        bestCustomerMap.put(Pos.get(i).getEmail(), intialTotal);
        i++;
    }

    for(int i = 0; i < Pos.size();){
        String customerEmail = Pos.get(i).getEmail();
        String productId = Pos.get(i).getProductID();
        String quanitity = Pos.get(i).getQuantity(); 
        Double tempPrice = productPrice.get(productId);
        if(tempPrice == null){
            i++;
            continue;
        }
        Double tempTotal = (tempPrice * Integer.valueOf(quanitity) + bestCustomerMap.get(customerEmail));
         
        bestCustomerMap.put(customerEmail, tempTotal);
        
        if(tempTotal > highestTotal){
            bestCustomer = customerEmail;
            highestTotal = tempTotal;
            
            
        }
        i++;
    }

    return new String[]{bestCustomer, String.valueOf(highestTotal)}; 
}

public String[] thisWeeksSales() throws SQLException{
    productCostMap();

    Double currentWeeklyTotal = 0.0;
    Double twoWeekTotal = 0.0;
    Double threeWeekTotal = 0.0; 

    String filename = "jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1"; 
    SQLPo SQLPO = new SQLPo(); 
    SQLPO.initializeDatabase(filename);
    String latestDateSQL = "SELECT * FROM testDB.PO ORDER BY date DESC Limit 1";

    List<observablePO> latestPO = SQLPO.GenerateWeeklyPO(latestDateSQL);

    String endDate = latestPO.get(0).getDate();
    String endDateDay = endDate.substring(endDate.length()-2);
    String endWeekMonth = endDate.substring(5,7);
    String endWeekYear = endDate.substring(0,4);

    

    String beginWeekDate ="";

    if(Integer.valueOf(endDateDay) < 7){
        int tempdayval = Math.abs(Integer.valueOf(endDateDay) - 7);
        int prevMonthDays = daysAndMonths.get(Integer.valueOf(endWeekMonth));                                                                                         
        beginWeekDate = endWeekYear + "-" + endWeekMonth + "-" + String.valueOf(prevMonthDays - tempdayval);
}
else if(Integer.valueOf(endDateDay) < 7 && Integer.valueOf(endWeekMonth) == 12){
        int tempdayval = Math.abs(Integer.valueOf(endDate) - 7);
        int prevMonthDays = daysAndMonths.get(Integer.valueOf(endWeekMonth)); 
        int prevYear = Integer.valueOf(endWeekYear) - 1;                                                                                        
        beginWeekDate = prevYear + "-" + endWeekMonth + "-" + String.valueOf(prevMonthDays - tempdayval);
}
else{
     int beginWeekDayInt = Integer.valueOf(endDateDay) - 7;
    String beginWeekDayString = String.valueOf(beginWeekDayInt);
    
    if(beginWeekDayString.length() < 2){
        beginWeekDayString = "0" + beginWeekDayString; 
    }
   beginWeekDate = endWeekYear + "-" + endWeekMonth + "-" + beginWeekDayString;
   
}

String currentWeekSQL = "SELECT * FROM testDB.PO WHERE date >= "+ "'" + beginWeekDate+ "'" +" AND date <= " + "'" + endDate + "'";

List<observablePO> weeklyPos = SQLPO.GenerateWeeklyPO(currentWeekSQL);

for(int i = 0; i < weeklyPos.size();){
    String tempProductID = weeklyPos.get(i).getProductID();
    int tempQuant = Integer.valueOf(weeklyPos.get(i).getQuantity());
    currentWeeklyTotal += productPrice.get(tempProductID) * tempQuant; 
    i++; 
}
//_______________________________________________________________________________________________________________________________//
String weekTwoEndDay = beginWeekDate.substring(beginWeekDate.length() - 2);
Integer tempWeekTwoEndDay = Integer.valueOf(weekTwoEndDay) - 1; 
weekTwoEndDay = String.valueOf(tempWeekTwoEndDay);
String weekTwoEndMonth = beginWeekDate.substring(5,7);
String weekTwoEndYear = beginWeekDate.substring(0,4);
String weekTwoStartDate = "";

String weekTwoEndDate = weekTwoEndYear + "-" + weekTwoEndMonth + "-" + weekTwoEndDay;

if(Integer.valueOf(weekTwoEndDay) < 7){
    int tempdayval = Math.abs(Integer.valueOf(weekTwoEndDay) - 7);
    int prevMonthDays = daysAndMonths.get(Integer.valueOf(weekTwoEndMonth));                                                                                         
    weekTwoStartDate = weekTwoEndYear + "-" + weekTwoEndMonth + "-" + String.valueOf(prevMonthDays - tempdayval);
}
else if(Integer.valueOf(weekTwoEndDay) < 7 && Integer.valueOf(weekTwoEndMonth) == 12){
    int tempdayval = Math.abs(Integer.valueOf(weekTwoEndDay) - 7);
    int prevMonthDays = daysAndMonths.get(Integer.valueOf(weekTwoEndMonth)); 
    int prevYear = Integer.valueOf(weekTwoEndYear) - 1;                                                                                        
    weekTwoStartDate = prevYear + "-" + weekTwoEndMonth + "-" + String.valueOf(prevMonthDays - tempdayval);
}
else{
 int weekTwoStartDayInt = Integer.valueOf(weekTwoEndDay) - 7;
String weekTwoStartDayString = String.valueOf(weekTwoStartDayInt);

if(weekTwoStartDayString.length() < 2){
    weekTwoStartDayString = "0" + weekTwoStartDayString; 
}
weekTwoStartDate = endWeekYear + "-" + endWeekMonth + "-" + weekTwoStartDayString;

}

String twoWeekSQL = "SELECT * FROM testDB.PO WHERE date >= "+ "'" + weekTwoStartDate + "'" +" AND date <= " + "'" + weekTwoEndDate + "'";

List<observablePO> twoWeekPos = SQLPO.GenerateWeeklyPO(twoWeekSQL);

for(int i = 0; i < twoWeekPos.size();){
    String tempProductID = twoWeekPos.get(i).getProductID();
    int tempQuant = Integer.valueOf(twoWeekPos.get(i).getQuantity());
    twoWeekTotal += productPrice.get(tempProductID) * tempQuant; 
    i++; 
}

//_____________________________________________________________________________________________________________________________________________________________//
String weekThreeEndDay = weekTwoStartDate.substring(weekTwoStartDate.length() - 2);
Integer tempWeekThreeEndDay = Integer.valueOf(weekThreeEndDay) - 1; 
weekTwoEndDay = String.valueOf(tempWeekTwoEndDay);
String weekThreeEndMonth = weekTwoStartDate.substring(5,7);
String weekThreeEndYear = weekTwoStartDate.substring(0,4);
String weekThreeStartDate = "";

String weekThreeEndDate = weekThreeEndYear + "-" + weekThreeEndMonth + "-" + weekThreeEndDay;

if(Integer.valueOf(weekThreeEndDay) < 7){
    int tempdayval = Math.abs(Integer.valueOf(weekThreeEndDay) - 7);
    int prevMonthDays = daysAndMonths.get(Integer.valueOf(weekThreeEndMonth));                                                                                         
    weekThreeStartDate = weekThreeEndYear + "-" + weekThreeEndMonth + "-" + String.valueOf(prevMonthDays - tempdayval);
}
else if(Integer.valueOf(weekThreeEndDay) < 7 && Integer.valueOf(weekThreeEndMonth) == 12){
    int tempdayval = Math.abs(Integer.valueOf(weekThreeEndDay) - 7);
    int prevMonthDays = daysAndMonths.get(Integer.valueOf(weekThreeEndMonth)); 
    int prevYear = Integer.valueOf(weekThreeEndYear) - 1;                                                                                        
    weekThreeStartDate = prevYear + "-" + weekThreeEndMonth + "-" + String.valueOf(prevMonthDays - tempdayval);
}
else{
 int weekThreeStartDayInt = Integer.valueOf(weekThreeEndDay) - 7;
String weekThreeStartDayString = String.valueOf(weekThreeStartDayInt);

if(weekThreeStartDayString.length() < 2){
    weekThreeStartDayString = "0" + weekThreeStartDayString; 
}
weekThreeStartDate = weekTwoEndYear + "-" + weekTwoEndMonth + "-" + weekThreeStartDayString;

}

String threeWeekSQL = "SELECT * FROM testDB.PO WHERE date >= "+ "'" + weekThreeStartDate + "'" +" AND date <= " + "'" + weekThreeEndDate + "'";

List<observablePO> threeWeekPos = SQLPO.GenerateWeeklyPO(threeWeekSQL);

for(int i = 0; i < threeWeekPos.size();){
    String tempProductID = threeWeekPos.get(i).getProductID();
    int tempQuant = Integer.valueOf(threeWeekPos.get(i).getQuantity());
    threeWeekTotal += productPrice.get(tempProductID) * tempQuant; 
    i++; 
}

//_______________________________________________________________________________________________________________________________________________________________//

//return{1st week date, 1st week sales, 2nd week date, 2nd week sales, 3rd week date, 3rd week sales}
//index       0             1               2             3                4             5
 return new String[]{beginWeekDate, String.valueOf(currentWeeklyTotal), weekTwoStartDate, String.valueOf(twoWeekTotal),
     weekThreeStartDate, String.valueOf(threeWeekTotal)};
}        
          
//EOF        
}

        
        

 