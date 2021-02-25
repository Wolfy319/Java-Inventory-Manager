package CS3250;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class MainTest {

   @Test
   public void testAdd() {
      String str = "JUnit is working fine";
      assertEquals("JUnit is working fine", str);

   }
}