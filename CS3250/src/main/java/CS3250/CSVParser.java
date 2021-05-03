package CS3250;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import UI.observablePO;

/*
  This class will eventually hook up to our database,
  so in the future it might change from adding the entries 
  to a HashMap to instead calling the createEntry method
  to add it straight in to the DB
 */
public class CSVParser {
	
	/**
	 * Parses a CSV file full of products and adds each to a database
	 *  
	 * @param filename - Path to the csv file to be parsed
	 * @param database - Inventory Database object
	 * 
	 */
	public void readProductsCSV(String filename, DataInterface database){
		String line;  	// Current row contents
		String[] fields;// Array to store individual product fields
		
		// Try to open the file
		try (InputStream inputStream = getClass().getResourceAsStream(filename);
			    BufferedReader reader = new BufferedReader(new FileReader(filename))) {
				// Skip column names
				reader.readLine();
				// Read line by line
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

/**
	 * Parses a CSV file full of customer orders and adds each to a database
	 *  
	 * @param filename - Path to the csv file to be parsed
	 * @param PoDB - Product Order Database object
	 * @param inventory - Inventory Database object
	 */
	public void readOrdersCSV(String filename, SQLPo PoDB, SQLData inventory){
		String line;  	// Current row contents
		String[] fields;// Array to store individual product fields
		
		// Try to open the file 
		try (InputStream inputStream = getClass().getResourceAsStream(filename);
			    BufferedReader reader = new BufferedReader(new FileReader(filename))) {
				reader.readLine();
				// Read line by line
			    while((line = reader.readLine()) != null) {
			    	fields = line.split(",");     // Split the row into individual fields
			    	
			    	// Fill in fields
			    	populateDB(fields[0], fields[1], fields[2], fields[3], fields[4], PoDB);
			    }
		} catch (IOException e) {
				e.printStackTrace();
		}
		return;
	}

	/** Subtracts stock from inventory based on ordered quantity
	 * 
	 * @param productID - Product to be updated
	 * @param orderedQuantity - # of items ordered
	 * @param inventory - Inventory Database object
	 */
	public void updateInventory(String productID, int orderedQuantity, SQLData inventory) {
		// Pull entry from database
		Entry inventoryItem = inventory.readEntry(productID);
		if(inventoryItem == null) {
			System.out.println("Ordered item " + productID + " doesn't exist!");
		}
		else {
			// Check if # ordered is less than is currently in stock
			if(inventoryItem.getStockQuantity() < orderedQuantity) {
				System.out.println("Order quantity exceeds quantity in inventory!");
			}
			else {
				// Subtract ordered quantity from current inventory and update database
				int currentQuantity = inventoryItem.getStockQuantity();
				inventoryItem.setStockQuantity(currentQuantity - orderedQuantity);
				inventory.updateEntry(productID, inventoryItem);
			}
		}
	}

	/** Adds an observable PO object to the database
	 * 
	 * @param date 
	 * @param customerEmail
	 * @param customerLocation
	 * @param productID
	 * @param productQuantity
	 * @param PoDB - Product Orders database object
	 */
	private void populateDB(String date, String customerEmail, String customerLocation, String productID, String productQuantity, SQLPo PoDB) {
		System.out.print(date + " " + customerEmail);
		observablePO po = new observablePO();
		po.setDate(date);
		po.setEmail(customerEmail);
		po.setCustomerLocation(customerLocation);
		po.setProductID(productID);
		po.quantity(productQuantity);

		PoDB.createEntry("1", po);
		 
		return;
	}
}
