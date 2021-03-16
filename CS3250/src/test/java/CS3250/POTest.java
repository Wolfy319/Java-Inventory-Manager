package CS3250;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class POTest {
    SQLPo init = new SQLPo();
    @Test
    void ConnectionWorks(){
        init.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");
        System.out.println("...done");
        var p = init.GenerateFullPO(2);
        var sp = init.GenerateShortPOs();
        assertNotEquals(p, null);
    }
    
}
