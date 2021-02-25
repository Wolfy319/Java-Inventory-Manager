package CS3250;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import CS3250.CSVData;
import CS3250.DataInterface;
import CS3250.Entry;

public class CSVDataTest {  
    DataInterface testCSV = new CSVData();
    String File = "inventory_team3.csv";
    @Test
    public void initDBTest() {
        // creates test db
        testCSV.initializeDatabase(File);

        assertEquals(42585, testCSV.retSize());
    }
    @Test
     void retSizeTest(){
        // once Create Entry is finished we can make this less tightly 
        // coupled by starting with a blank testCSV and adding one entry to check if we only have a size of one 
        testCSV.initializeDatabase(File);
        testCSV.deleteEntry("6JKIQ99UY8OH");
        assertEquals(42584,testCSV.retSize());
    }

    @Test
    void deleteEntryTest(){
        testCSV.initializeDatabase(File);
        int first = testCSV.retSize();
        testCSV.deleteEntry("B67VWX6R77AQ");
        assertNotEquals(first, testCSV.retSize());
    }

    @Test
    void updateEntryTest(){
        testCSV.initializeDatabase(File);
        Entry holder = testCSV.readEntry("B67VWX6R77AQ");
        holder.setStockQuantity(1);
        testCSV.updateEntry("B67VWX6R77AQ", holder);
        holder = testCSV.readEntry("B67VWX6R77AQ");
        assertEquals(holder.getStockQuantity(), 1);

    }

    @Test
    void createEntryTest(){
        Entry e = new Entry();
        testCSV.createEntry("ID",e );
        assertEquals(testCSV.retSize(), 1);
    }

    @Test
    void saveEntryTest(){
        Entry e = new Entry();
        e.setProductID("productID");
        testCSV.saveEntry(e);
        assertEquals(testCSV.retSize(), 1);
    }

}
