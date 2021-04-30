package UI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import CS3250.SQLPo;

public class poStream {

    Multimap<String, Integer> prodAndQuant = ArrayListMultimap.create();

    Multimap<String, Integer> currentMonthMap = ArrayListMultimap.create();

    Multimap<String, Integer> twoMonthMap = ArrayListMultimap.create();

    Multimap<String, Integer> threeMonthMap = ArrayListMultimap.create();

    Multimap<String, Integer> currentWeekMap = ArrayListMultimap.create();

    HashMap<String, Double> bestCustomerMap = new HashMap<String, Double>();

    HashMap<String, Double> productPrice = new HashMap<String, Double>();

    HashMap<String, Integer> daysAndMonths = new HashMap<String, Integer>();

    HashMap<String, String> numMonth = new HashMap<String, String>();

    HashMap<String, Double> productSales = new HashMap<String, Double>();

    public void daysAndMonths() {
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

    public void numMonth() {
        numMonth.put("01", "January");
        numMonth.put("02", "Febuary");
        numMonth.put("03", "March");
        numMonth.put("04", "April");
        numMonth.put("05", "May");
        numMonth.put("06", "June");
        numMonth.put("07", "July");
        numMonth.put("08", "August");
        numMonth.put("09", "September");
        numMonth.put("10", "October");
        numMonth.put("11", "November");
        numMonth.put("12", "December");
    }

    public void productCostMap() throws SQLException {
        Connection poCon = UIDBConnector.getConnection();
        ResultSet rs = poCon.createStatement().executeQuery("SELECT * FROM DataEntries");
        while (rs.next()) {
            productPrice.put(rs.getString("productID"), rs.getDouble("salePrice"));
        }

    }

    public void salesOrderMap() throws SQLException {
        Connection poCon = UIDBConnector.getConnection();
        ResultSet rs = poCon.createStatement().executeQuery("SELECT * FROM PO");
        while (rs.next()) {
            prodAndQuant.put(rs.getString("productID"), rs.getInt("quantity"));
        }

    }

    public String[] salesCalc() throws SQLException {
        numMonth();
        productCostMap();

        Double totalSales = 0.0;
        Double currentMonthTotal = 0.0;
        Double twoMonthTotal = 0.0;
        Double threeMonthTotal = 0.0;

        String filename = "jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1";
        SQLPo SQLPO = new SQLPo();
        SQLPO.initializeDatabase(filename);

        String latestDateSQL = "SELECT * FROM testDB.PO ORDER BY date DESC Limit 1";
        String totalSalesSQL = "SELECT * FROM testDB.PO";

        List<observablePO> allPos = SQLPO.GenerateWeeklyPO(totalSalesSQL);
        List<observablePO> latestPO = SQLPO.GenerateWeeklyPO(latestDateSQL);

        int totalOrders = allPos.size();

        String currentMonthPO = latestPO.get(0).getDate();
        String currentMonthNum = currentMonthPO.substring(5, 7);
        String currentMonth = numMonth.get(currentMonthNum);
        String twoMonthNum = "0" + String.valueOf(Integer.valueOf(currentMonthNum) - 1);
        String twoMonth = numMonth.get(twoMonthNum);
        String threeMonthNum = "0" + String.valueOf(Integer.valueOf(currentMonthNum) - 2);
        String threeMonth = numMonth.get(threeMonthNum);

        String currentMonthSQL = "SELECT * FROM PO WHERE monthname (date) = " + "'" + currentMonth + "'";
        String twoMonthSQL = "SELECT * FROM PO WHERE monthname (date) = " + "'" + twoMonth + "'";
        String threeMonthSQL = "SELECT * FROM PO WHERE monthname (date) = " + "'" + threeMonth + "'";

        List<observablePO> currentPos = SQLPO.GenerateWeeklyPO(currentMonthSQL);
        List<observablePO> twoPos = SQLPO.GenerateWeeklyPO(twoMonthSQL);
        List<observablePO> threePos = SQLPO.GenerateWeeklyPO(threeMonthSQL);

        for (int i = 0; i < currentPos.size();) {
            String tmpProductID = currentPos.get(i).getProductID();
            int tmpQuant = Integer.valueOf(currentPos.get(i).getQuantity());
            currentMonthTotal += productPrice.get(tmpProductID) * tmpQuant;
            i++;
        }

        for (int i = 0; i < twoPos.size();) {
            String tmpProductID = twoPos.get(i).getProductID();
            int tmpQuant = Integer.valueOf(twoPos.get(i).getQuantity());
            twoMonthTotal += productPrice.get(tmpProductID) * tmpQuant;
            i++;
        }

        for (int i = 0; i < threePos.size();) {
            String tmpProductID = threePos.get(i).getProductID();
            int tmpQuant = Integer.valueOf(threePos.get(i).getQuantity());
            threeMonthTotal += productPrice.get(tmpProductID) * tmpQuant;
            i++;
        }

        for (int i = 0; i < allPos.size();) {
            String tmpProductID = allPos.get(i).getProductID();

            int tmpQuant = Integer.valueOf(allPos.get(i).getQuantity());
            if (productPrice.get(tmpProductID) == null) {
                i++;
                continue;
            }
            totalSales += productPrice.get(tmpProductID) * tmpQuant;
            i++;
        }

        // 0 1 2 3 4 5 6
        return new String[] { String.valueOf(totalSales), currentMonth, String.valueOf(currentMonthTotal), twoMonth,
                String.valueOf(twoMonthTotal), threeMonth, String.valueOf(threeMonthTotal)
                // 7
                , String.valueOf(totalOrders) };
    }

    public String[] bestCustomer() throws SQLException {
        String filename = "jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1";
        SQLPo SQLPO = new SQLPo();
        SQLPO.initializeDatabase(filename);
        List<observablePO> Pos = SQLPO.GenerateShortPOs();
        productCostMap();

        Double intialTotal = 0.0;
        String bestCustomer = "";
        Double highestTotal = 0.0;

        for (int i = 0; i < Pos.size();) {
            bestCustomerMap.put(Pos.get(i).getEmail(), intialTotal);
            i++;
        }

        for (int i = 0; i < Pos.size();) {
            String customerEmail = Pos.get(i).getEmail();
            String productId = Pos.get(i).getProductID();
            String quanitity = Pos.get(i).getQuantity();
            Double tempPrice = productPrice.get(productId);
            if (tempPrice == null) {
                i++;
                continue;
            }
            Double tempTotal = (tempPrice * Integer.valueOf(quanitity) + bestCustomerMap.get(customerEmail));

            bestCustomerMap.put(customerEmail, tempTotal);

            if (tempTotal > highestTotal) {
                bestCustomer = customerEmail;
                highestTotal = tempTotal;

            }
            i++;
        }

        return new String[] { bestCustomer, String.valueOf(highestTotal) };
    }

    public String[] thisWeeksSales() throws SQLException {
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
        String endDateDay = endDate.substring(endDate.length() - 2);
        String endWeekMonth = endDate.substring(5, 7);
        String endWeekYear = endDate.substring(0, 4);

        String beginWeekDate = "";

        if (Integer.valueOf(endDateDay) < 7) {
            int tempdayval = Math.abs(Integer.valueOf(endDateDay) - 7);
            int prevMonthDays = daysAndMonths.get(Integer.valueOf(endWeekMonth));
            beginWeekDate = endWeekYear + "-" + endWeekMonth + "-" + String.valueOf(prevMonthDays - tempdayval);
        } else if (Integer.valueOf(endDateDay) < 7 && Integer.valueOf(endWeekMonth) == 12) {
            int tempdayval = Math.abs(Integer.valueOf(endDate) - 7);
            int prevMonthDays = daysAndMonths.get(Integer.valueOf(endWeekMonth));
            int prevYear = Integer.valueOf(endWeekYear) - 1;
            beginWeekDate = prevYear + "-" + endWeekMonth + "-" + String.valueOf(prevMonthDays - tempdayval);
        } else {
            int beginWeekDayInt = Integer.valueOf(endDateDay) - 7;
            String beginWeekDayString = String.valueOf(beginWeekDayInt);

            if (beginWeekDayString.length() < 2) {
                beginWeekDayString = "0" + beginWeekDayString;
            }
            beginWeekDate = endWeekYear + "-" + endWeekMonth + "-" + beginWeekDayString;

        }

        String currentWeekSQL = "SELECT * FROM testDB.PO WHERE date >= " + "'" + beginWeekDate + "'" + " AND date <= "
                + "'" + endDate + "'";

        List<observablePO> weeklyPos = SQLPO.GenerateWeeklyPO(currentWeekSQL);

        for (int i = 0; i < weeklyPos.size();) {
            String tempProductID = weeklyPos.get(i).getProductID();
            int tempQuant = Integer.valueOf(weeklyPos.get(i).getQuantity());
            currentWeeklyTotal += productPrice.get(tempProductID) * tempQuant;
            i++;
        }

        String weekTwoEndDay = beginWeekDate.substring(beginWeekDate.length() - 2);
        Integer tempWeekTwoEndDay = Integer.valueOf(weekTwoEndDay) - 1;
        weekTwoEndDay = String.valueOf(tempWeekTwoEndDay);
        String weekTwoEndMonth = beginWeekDate.substring(5, 7);
        String weekTwoEndYear = beginWeekDate.substring(0, 4);
        String weekTwoStartDate = "";

        String weekTwoEndDate = weekTwoEndYear + "-" + weekTwoEndMonth + "-" + weekTwoEndDay;

        if (Integer.valueOf(weekTwoEndDay) < 7) {
            int tempdayval = Math.abs(Integer.valueOf(weekTwoEndDay) - 7);
            int prevMonthDays = daysAndMonths.get(Integer.valueOf(weekTwoEndMonth));
            weekTwoStartDate = weekTwoEndYear + "-" + weekTwoEndMonth + "-"
                    + String.valueOf(prevMonthDays - tempdayval);
        } else if (Integer.valueOf(weekTwoEndDay) < 7 && Integer.valueOf(weekTwoEndMonth) == 12) {
            int tempdayval = Math.abs(Integer.valueOf(weekTwoEndDay) - 7);
            int prevMonthDays = daysAndMonths.get(Integer.valueOf(weekTwoEndMonth));
            int prevYear = Integer.valueOf(weekTwoEndYear) - 1;
            weekTwoStartDate = prevYear + "-" + weekTwoEndMonth + "-" + String.valueOf(prevMonthDays - tempdayval);
        } else {
            int weekTwoStartDayInt = Integer.valueOf(weekTwoEndDay) - 7;
            String weekTwoStartDayString = String.valueOf(weekTwoStartDayInt);

            if (weekTwoStartDayString.length() < 2) {
                weekTwoStartDayString = "0" + weekTwoStartDayString;
            }
            weekTwoStartDate = endWeekYear + "-" + endWeekMonth + "-" + weekTwoStartDayString;

        }

        String twoWeekSQL = "SELECT * FROM testDB.PO WHERE date >= " + "'" + weekTwoStartDate + "'" + " AND date <= "
                + "'" + weekTwoEndDate + "'";

        List<observablePO> twoWeekPos = SQLPO.GenerateWeeklyPO(twoWeekSQL);

        for (int i = 0; i < twoWeekPos.size();) {
            String tempProductID = twoWeekPos.get(i).getProductID();
            int tempQuant = Integer.valueOf(twoWeekPos.get(i).getQuantity());
            twoWeekTotal += productPrice.get(tempProductID) * tempQuant;
            i++;
        }

        String weekThreeEndDay = weekTwoStartDate.substring(weekTwoStartDate.length() - 2);
        Integer tempWeekThreeEndDay = Integer.valueOf(weekThreeEndDay) - 1;
        weekTwoEndDay = String.valueOf(tempWeekTwoEndDay);
        String weekThreeEndMonth = weekTwoStartDate.substring(5, 7);
        String weekThreeEndYear = weekTwoStartDate.substring(0, 4);
        String weekThreeStartDate = "";

        String weekThreeEndDate = weekThreeEndYear + "-" + weekThreeEndMonth + "-" + weekThreeEndDay;

        if (Integer.valueOf(weekThreeEndDay) < 7) {
            int tempdayval = Math.abs(Integer.valueOf(weekThreeEndDay) - 7);
            int prevMonthDays = daysAndMonths.get(Integer.valueOf(weekThreeEndMonth));
            weekThreeStartDate = weekThreeEndYear + "-" + weekThreeEndMonth + "-"
                    + String.valueOf(prevMonthDays - tempdayval);
        } else if (Integer.valueOf(weekThreeEndDay) < 7 && Integer.valueOf(weekThreeEndMonth) == 12) {
            int tempdayval = Math.abs(Integer.valueOf(weekThreeEndDay) - 7);
            int prevMonthDays = daysAndMonths.get(Integer.valueOf(weekThreeEndMonth));
            int prevYear = Integer.valueOf(weekThreeEndYear) - 1;
            weekThreeStartDate = prevYear + "-" + weekThreeEndMonth + "-" + String.valueOf(prevMonthDays - tempdayval);
        } else {
            int weekThreeStartDayInt = Integer.valueOf(weekThreeEndDay) - 7;
            String weekThreeStartDayString = String.valueOf(weekThreeStartDayInt);

            if (weekThreeStartDayString.length() < 2) {
                weekThreeStartDayString = "0" + weekThreeStartDayString;
            }
            weekThreeStartDate = weekTwoEndYear + "-" + weekTwoEndMonth + "-" + weekThreeStartDayString;

        }

        String threeWeekSQL = "SELECT * FROM testDB.PO WHERE date >= " + "'" + weekThreeStartDate + "'"
                + " AND date <= " + "'" + weekThreeEndDate + "'";

        List<observablePO> threeWeekPos = SQLPO.GenerateWeeklyPO(threeWeekSQL);

        for (int i = 0; i < threeWeekPos.size();) {
            String tempProductID = threeWeekPos.get(i).getProductID();
            int tempQuant = Integer.valueOf(threeWeekPos.get(i).getQuantity());
            threeWeekTotal += productPrice.get(tempProductID) * tempQuant;
            i++;
        }

        String numWeekOrders = String.valueOf(weeklyPos.size());
        String numTwoWeekOrders = String.valueOf(twoWeekPos.size());
        String numThreeWeekOrders = String.valueOf(threeWeekPos.size());

        return new String[] { beginWeekDate, String.valueOf(currentWeeklyTotal), weekTwoStartDate,
                String.valueOf(twoWeekTotal), weekThreeStartDate, String.valueOf(threeWeekTotal), numWeekOrders,
                numTwoWeekOrders, numThreeWeekOrders };
    }

    public String[] dailySales() throws SQLException {
        productCostMap();

        Double dayTotal = 0.0;

        String filename = "jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1";
        SQLPo SQLPO = new SQLPo();
        SQLPO.initializeDatabase(filename);
        String latestDateSQL = "SELECT * FROM testDB.PO ORDER BY date DESC Limit 1";

        List<observablePO> latestPO = SQLPO.GenerateWeeklyPO(latestDateSQL);
        List<observablePO> dailyPos = SQLPO
                .GenerateWeeklyPO("Select * From testDB.PO where date = " + "'" + latestPO.get(0).getDate() + "'");

        for (int i = 0; i < dailyPos.size(); i++) {
            String tmpID = dailyPos.get(i).getProductID();
            Double tmpPrice = productPrice.get(tmpID);
            int tmpQuant = Integer.valueOf(dailyPos.get(i).getQuantity());
            Double tmpTotal = tmpQuant * tmpPrice;
            dayTotal += tmpTotal;

        }
        return new String[] { String.valueOf(dayTotal), latestPO.get(0).getDate() };

    }

    public String[] popularItems() throws SQLException {
        productCostMap();

        String filename = "jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1";
        SQLPo SQLPO = new SQLPo();
        SQLPO.initializeDatabase(filename);
        String bestProductSQL = "SELECT * FROM testDB.PO";
        List<observablePO> allPos = SQLPO.GenerateWeeklyPO(bestProductSQL);

        String bestProdId = "";
        String secProdId = "";
        String thirdProdId = "";

        Double bestTotal = 0.0;
        Double secTotal = 0.0;
        Double thirdTotal = 0.0;

        for (int i = 0; i < allPos.size(); i++) {
            if (productSales.get(allPos.get(i).getProductID()) == null) {
                int tmpQuant = Integer.valueOf(allPos.get(i).getQuantity());
                Double tmpPrice = productPrice.get(allPos.get(i).getProductID());

                if (tmpPrice == null) {
                    continue;
                }

                Double tmpTotal = tmpQuant * tmpPrice;

                productSales.put(allPos.get(i).getProductID(), tmpTotal);

                if (tmpTotal > bestTotal) {
                    bestTotal = tmpTotal;
                    bestProdId = allPos.get(i).getProductID();
                }
                if (tmpTotal < bestTotal && tmpTotal > thirdTotal && tmpTotal > secTotal) {
                    secTotal = tmpTotal;
                    secProdId = allPos.get(i).getProductID();
                }
                if (tmpTotal > thirdTotal && tmpTotal < secTotal) {
                    thirdTotal = tmpTotal;
                    thirdProdId = allPos.get(i).getProductID();
                }

            }

            else {
                String tmpProdId = allPos.get(i).getProductID();
                int tmpQuant = Integer.valueOf(allPos.get(i).getQuantity());
                Double tmpPrice = productPrice.get(allPos.get(i).getProductID());

                if (tmpPrice == null) {
                    continue;
                }

                Double tmpTotal = tmpQuant * tmpPrice;
                Double currentTotal = productSales.get(tmpProdId);
                currentTotal += tmpTotal;

                productSales.put(tmpProdId, currentTotal);

                if (currentTotal > bestTotal) {
                    bestTotal = currentTotal;
                    bestProdId = allPos.get(i).getProductID();
                } else if (currentTotal < bestTotal && currentTotal > thirdTotal && currentTotal > secTotal) {
                    secTotal = currentTotal;
                    secProdId = allPos.get(i).getProductID();
                } else if (currentTotal > thirdTotal && currentTotal < secTotal) {
                    thirdTotal = currentTotal;
                    thirdProdId = allPos.get(i).getProductID();
                }

            }

        }
        return new String[] { bestProdId, String.valueOf(bestTotal), secProdId, String.valueOf(secTotal), thirdProdId,
                String.valueOf(thirdTotal) };
    }

}
