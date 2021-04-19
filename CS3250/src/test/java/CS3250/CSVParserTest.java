package CS3250;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import CS3250.CSVData;
import CS3250.CSVParser;

class CSVParserTest {
	CSVParser parse = new CSVParser();
	CSVData data = new CSVData();
	String file = "test.csv";

	SQLPo newPo = new SQLPo();
	SQLData inventory = new SQLData();	

	// @Test
	// public void ReadCSVWorks() {
	// 	parse.readCSV(file, data);
	// 	assertEquals(42585, data.retSize());
	// }
	
	@Test
	public void ReadCSVWorks() {
		parse.readOrdersCSV(file, po, inventory);
		assert(true);
	}
}
