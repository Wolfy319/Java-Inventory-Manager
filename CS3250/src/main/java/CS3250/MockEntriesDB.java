package CS3250;

import java.util.ArrayList;
import java.util.List;

public class MockEntriesDB implements DataMan<Entry>{
    List<Entry> li;
    @Override
    public List<Entry> getEntries() {
       return li;
    }

    @Override
    public void initializeDatabase(String connection) {
        // TODO Auto-generated method stub
        var e1 = new Entry();
        
        e1.setProductID("productID");
        e1.setSalePrice(100);
        e1.setStockQuantity(10);
        e1.setSupplierID("sup");
        e1.setWholesaleCost(200);

        li = new ArrayList();
        li.add(e1);

    }

    @Override
    public void createEntry(String ID, Entry e) {
        // TODO Auto-generated method stub
        li.add(e);
    }

    @Override
    public Entry readEntry(String ID) {
        for (Entry entry : li) {
            if(entry.getProductID() == ID)
                return entry;
        }
         return null;
    }

    @Override
    public void updateEntry(String ID, Entry e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteEntry(String id) {
        for (int i = 0; i < li.size(); i++) {
            if (li.get(i).getProductID() == id)
                li.remove(i);
        }
        
    }
    
}
