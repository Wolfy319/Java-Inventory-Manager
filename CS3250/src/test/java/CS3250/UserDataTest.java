package CS3250;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class UserDataTest {
    UserData init = new UserData();
    String connectionString = StringParsers.readConfig(".config");

    @Test
    void ConnectionWorks(){
        init.initializeDatabase(connectionString);
        String s = "testin the username today";
        byte[] user = s.getBytes();

        assertNotEquals(user, null);
    }
    @Test 
    void returnSizeWorks(){
        init.initializeDatabase(connectionString);
        assertNotEquals(init.retSize(), 0);
    }

    @Test
    void deleteAndGetUserWorks(){
        init.initializeDatabase(connectionString);
        User e = new User();
        e.setID(200);
        e.setEmail("James-cool@coolGuys.com");
        e.setPassword("password".getBytes());
        e.setUsername("Username".getBytes());
        e.setEmail("email@test.com");
        e.setRole("admin");
        e.setSalt("salt".getBytes());
        init.createEntry("ID", e);
        int initial = init.retSize();
        var li = init.getUser("Username".getBytes());
        int delvalue = li.get(0).getID();
        init.deleteUser(delvalue);
        int after = init.retSize();
        assertNotEquals(initial, after);

    }
    


}

