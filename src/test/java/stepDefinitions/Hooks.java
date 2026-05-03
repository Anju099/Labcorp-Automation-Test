package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.BaseTest;

public class Hooks {
    
    @Before
    public void setup() {
        System.out.println("\n===== TEST STARTED =====");
    }
    
    @After
    public void teardown() {
        System.out.println("\n===== CLOSING BROWSER =====");
        try {
            BaseTest.tearDown();
        } catch (Exception e) {
            System.out.println("Error in cleanup: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("===== TEST COMPLETED =====\n");
        }
    }
}

