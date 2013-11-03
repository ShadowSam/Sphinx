package tests.modelTests;

import models.*;
import org.junit.*;
import static org.junit.Assert.*;
import play.test.WithApplication;
import static play.test.Helpers.*;

public class UserTest extends WithApplication {
    @Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase()));
    }


   @Test
    public void createAndRetrieveUser() {
        new User( "Bob").insert();
        User bob = User.findByName("Bob");
        assertNotNull(bob);
        assertEquals("Bob", bob.name);
    }


}


