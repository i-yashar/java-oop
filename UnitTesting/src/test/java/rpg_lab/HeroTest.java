package rpg_lab;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class HeroTest {

    @Test
    public void testHeroGainsExperienceWhenTargetDies(){
        Target mockTarget = Mockito.mock(Target.class);
        Weapon mockWeapon = Mockito.mock(Weapon.class);

        Mockito.when(mockTarget.isDead()).thenReturn(true);
        Mockito.when(mockTarget.giveExperience()).thenReturn(13);

        Hero hero = new Hero("Java", mockWeapon);

        hero.attack(mockTarget);

        assertEquals(13, hero.getExperience());
    }

}