package CS3250;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import UI.observablePO;

public class InventoryUpdateTest {
    SQLPo po = new SQLPo();
	
	@Test
	public void PoExistsWorks() {
		String connectionString = StringParsers.readConfig(".config");

		po.initializeDatabase(connectionString);

        assertEquals(po.poExists("8Q86U5HFQ2I2", 1, "2020-01-01", "sui@icloud.com", "24449"), true);
        assertEquals(po.poExists("FAKEPRODUCT", 1, "2020-01-01", "FAKEEMAIL", "24449"), false);
	}
    
	@Test
    public void UpdateWorks() {
		String connectionString = StringParsers.readConfig(".config");
		observablePO p = new observablePO();
		p.setProductID("000TESTENTRY");
		p.quantity("1");
		p.setDate("2021-10-12");
		p.setEmail("testemail@email.com");
		p.setCustomerLocation("00000");
		po.initializeDatabase(connectionString);

		po.createEntry("1", p);
	}
}
