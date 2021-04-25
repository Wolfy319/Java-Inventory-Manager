package CS3250;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import UI.observablePO;

public class InventoryUpdateTest {
    SQLPo po = new SQLPo();
	
	@Test
	public void PoExistsWorks() {
		po.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");

        assertEquals(po.poExists("8Q86U5HFQ2I2", 1, "2020-01-01", "sui@icloud.com", "24449"), true);
        assertEquals(po.poExists("FAKEPRODUCT", 1, "2020-01-01", "FAKEEMAIL", "24449"), false);
	}
    
	@Test
    public void UpdateWorks() {
		observablePO p = new observablePO();
		p.setProductID("000TESTENTRY");
		p.quantity("" + 1);
		p.setDate("2021-10-12");
		p.setEmail("testemail@email.com");
		p.setCustomerLocation("00000");
		po.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");

		po.createEntry("1", p);
	}
}
