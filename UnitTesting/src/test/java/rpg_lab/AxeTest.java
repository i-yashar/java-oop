package rpg_lab;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static rpg_lab.Axe.AXE_DAMAGE_TAKEN;

public class AxeTest {
    private static final int AXE_ATTACK = 10;
    private static final int AXE_DURABILITY = 10;
    private static final int DUMMY_HEALTH = 20;
    private static final int DUMMY_XP = 10;

    private Axe axe;
    private Dummy dummy;

    @Before
    public void setUp(){
        axe = new Axe(AXE_ATTACK, AXE_DURABILITY);
        dummy = new Dummy(DUMMY_HEALTH, DUMMY_XP);
    }

    @Test
    public void testWeaponLosesDurabilityAfterAttack(){
        axe.attack(dummy);

        assertEquals(AXE_DURABILITY - AXE_DAMAGE_TAKEN, axe.getDurabilityPoints());
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotAttackWithABrokenWeapon(){
        Axe axe = new Axe(10, 0);

        axe.attack(dummy);
    }

}