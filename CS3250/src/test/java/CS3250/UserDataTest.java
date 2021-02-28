package CS3250;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class UserDataTest {
    UserData init = new UserData();
    @Test
    void ConnectionWorks(){
        init.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");
        String s = "testin the username today";
        byte[] user = s.getBytes();

        assertNotEquals(user, null);
    }
    @Test 
    void returnSizeWorks(){
        init.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false& team3 UpdateTrello!1");
        assertNotEquals(init.retSize(), 0);
    }

    @Test
    void deleteAndGetUserWorks(){
        init.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false& team3 UpdateTrello!1");
        User e = new User();
        e.setID(200);
        e.setPassword("password".getBytes());
        e.setUsername("Username".getBytes());
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

