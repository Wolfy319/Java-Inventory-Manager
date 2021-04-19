package UI;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
public class jChart {

    public void lineChartTest() throws IOException, NumberFormatException, SQLException{
        poStream threeMonths = new poStream(); 
            DefaultCategoryDataset salesData = new DefaultCategoryDataset();

            String threeMonthsStr = threeMonths.calcThreeMonthSales().replaceAll(",", "");
            Double threeMDouble = Double.parseDouble(threeMonthsStr);

            String twoMonthsStr = threeMonths.calcTwoMonthSales().replaceAll(",", "");
            Double twoMDouble = Double.parseDouble(twoMonthsStr);

            String currMonthsStr = threeMonths.calcCurrentMonthSales().replaceAll(",", "");
            Double currMDouble = Double.parseDouble(currMonthsStr);


            salesData.addValue(threeMDouble, "Sales", "April");
            salesData.addValue(twoMDouble, "Sales", "May");
            salesData.addValue(currMDouble, "Sales", "June");
            

            JFreeChart salesDataChartObj = ChartFactory.createLineChart("Sales of the last 3 Months", "Months", "Sales in Dollars", salesData, PlotOrientation.VERTICAL, 
            true, true, false);

            int width = 487;
            int height  = 480;

            
            File salesLineChart = new File("CS3250\\src\\main\\java\\UI\\Images\\salesLineChart.PNG");
            ChartUtilities.saveChartAsPNG(salesLineChart, salesDataChartObj, width, height);
            
    }
}
