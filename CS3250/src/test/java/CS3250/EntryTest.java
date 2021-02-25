package CS3250;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import CS3250.Entry;

public class EntryTest {
    Entry e = new Entry();
    @Test
    public void SetProductIDWorks(){
        e.setProductID("test");
        assertEquals("test", e.getProductID());
    }

    @Test
    public void SetSupplierIDWorks(){
        e.setSupplierID("supTest");
        assertEquals("supTest", e.getSupplierID());
    }

    @Test
    public void setStockQuantityWorks(){
        e.setStockQuantity(99);
        assertEquals(99, e.getStockQuantity());
    }

    @Test
    public void setWholesaleCost(){
        e.setWholesaleCost(100);
        assertEquals(100,e.getWholesaleCost());
    }

    @Test
    public void setSalePrice(){
        e.setSalePrice(100);
        assertEquals(100,e.getSalePrice());
    }
}
