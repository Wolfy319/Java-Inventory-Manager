package CS3250;

import java.util.ArrayList;
import java.util.List;

import UI.observablePO;

public class MockPODB  implements DataMan<observablePO>{

    List<observablePO> li;
    @Override
    public List<observablePO> getEntries() {
        return li;
    }

    @Override
    public void initializeDatabase(String connection) {
        // TODO Auto-generated method stub
        var e1 = new observablePO();
        
        e1.setProductID("productID");
        e1.setCustomerLocation("customerLocation");
        e1.setDate("12-3-12");
        e1.setEmail("email");
        e1.setID("iD");

        li = new ArrayList();
        li.add(e1);

    }

    @Override
    public void createEntry(String ID, observablePO e) {
        // TODO Auto-generated method stub
        li.add(e);
    }

    @Override
    public observablePO readEntry(String ID) {
        for (observablePO entry : li) {
            if(entry.getProductID() == ID)
                return entry;
        }
         return null;
    }

    @Override
    public void updateEntry(String ID, observablePO e) {
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
