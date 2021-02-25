package CS3250;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/*
  This class will eventually hook up to our database,
  so in the future it might change from adding the entries 
  to a HashMap to instead calling the createEntry method
  to add it straight in to the DB
 */
public class CSVParser {
	
	/**
	 * Parses a CSV file full of products into Entry objects
	 *  
	 * @param filename - Path to the csv file to be parsed
	 * @param database - Database object
	 * @return - A HashMap full of filled in entry objects
	 */
	public void readCSV(String filename, DataInterface database){
		String line;  	// Current row contents
		String[] fields;// Array to store individual product fields
		
		// Try to open the file and start reading
		try (InputStream inputStream = getClass().getResourceAsStream(filename);
			    BufferedReader reader = new BufferedReader(new FileReader(filename))) {
				reader.readLine();
			    while((line = reader.readLine()) != null) {
			    	Entry newEntry = new Entry(); // Create new entry
			    	fields = line.split(",");     // Split the row into individual fields
			    	
			    	// Fill in newEntry's fields
			    	newEntry.setProductID(fields[0]);
			    	newEntry.setStockQuantity(Integer.parseInt(fields[1]));
			    	newEntry.setWholesaleCost(Double.parseDouble(fields[2]));
			    	newEntry.setSalePrice(Double.parseDouble(fields[3]));
			    	newEntry.setSupplierID(fields[4]);
			    	
			    	// Add the entry to the result map
			    	database.createEntry(newEntry.getProductID(), newEntry);
			    }
		} catch (IOException e) {
				e.printStackTrace();
		}
		return;
	}
}
