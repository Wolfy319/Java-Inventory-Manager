package CS3250;

import java.util.ArrayList;
import java.util.List;

public class MockUserDB implements DataMan<User>{

    List<User> li;
    @Override
    public List<User> getEntries() {
        return li;
    }

    @Override
    public void initializeDatabase(String connection) {
        // TODO Auto-generated method stub
        var e1 = new User();
        
        e1.setPassword("password".getBytes());
        e1.setRole("role");
        e1.setUsername("username".getBytes());
        e1.setEmail("email");
        e1.setSalt("salt".getBytes()); 
        li = new ArrayList();
        li.add(e1);

    }

    @Override
    public void createEntry(String ID, User e) {
        // TODO Auto-generated method stub
        li.add(e);
    }

    @Override
    public User readEntry(String ID) {
        for (User entry : li) {
            if(new String(entry.getUsername()) == ID)
                return entry;
        }
         return null;
    }

    @Override
    public void updateEntry(String ID, User e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteEntry(String id) {
        for (int i = 0; i < li.size(); i++) {
            if (new String(li.get(i).getUsername()) == id)
                li.remove(i);
        }
        
    }
    
}
