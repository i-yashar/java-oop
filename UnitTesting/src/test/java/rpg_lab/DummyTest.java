package rpg_lab;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DummyTest {

    private static final int DUMMY_HEALTH = 10;
    private static final int DUMMY_XP = 10;

    private Dummy dummy;

    @Before
    public void setUp(){
        dummy = new Dummy(DUMMY_HEALTH, DUMMY_XP);
    }

    @Test
    public void testDummyLosesHealthWhenAttacked(){
        dummy.takeAttack(1);

        assertEquals(9, dummy.getHealth());
    }

    @Test(expected = IllegalStateException.class)
    public void testDeadDummyCannotBeAttacked(){
        Dummy dummy = new Dummy(0, 10);

        dummy.takeAttack(1);
    }

    @Test
    public void testDeadDummyShouldGiveExperience(){
        Dummy dummy = new Dummy(0, 10);

        assertEquals(10, dummy.giveExperience());
    }

    @Test(expected = IllegalStateException.class)
    public void testAliveDummyCannotGiveExperience(){
        dummy.giveExperience();
    }

}