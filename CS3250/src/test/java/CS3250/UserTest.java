package CS3250;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class UserTest {
	User e = new User();
	byte[] testArray = {0,1,2,3,4,5,6,7};

	@Test
    public void SetUsernameWorks(){
        e.setUsername(testArray);
        assertEquals(Arrays.equals(testArray, e.getUsername()), true);
    }

    @Test
    public void SetPasswordWorks(){
        e.setPassword(testArray);
        assertEquals(Arrays.equals(testArray, e.getPassword()), true);
    }

    @Test
    public void SetSaltWorks(){
    	e.setSalt(testArray);
    	assertEquals(Arrays.equals(testArray, e.getSalt()), true);
    }
}
