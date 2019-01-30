package com.geeselightning.zepr.tests;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.geeselightning.zepr.Constant;
import com.geeselightning.zepr.Player;
import com.geeselightning.zepr.Zombie;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ZombieTest {

    Player player = Player.getInstance();

    @Test
    // Test 3.1.1
    public void zombieDoesNoDamageToPlayerWhenAtMaxRange() {
        player.respawn(Constant.ORIGIN, null);

        Zombie zombie = new Zombie(new Sprite(), new Vector2(player.getCenter().x, player.getCenter().y - Constant.ZOMBIERANGE), null);
        double originalHealth = player.getHealth();
        zombie.attack(player, 0);

        assertEquals("Player on the edge of range should not take damage when the zombie attacks.",
                player.getHealth(), originalHealth, 0.1);
    }

    @Test
    // Test 3.1.2
    public void zombieDoesDamageToPlayerWhenInRange() {
        player.respawn(Constant.ORIGIN, null);

        Zombie zombie = new Zombie(new Sprite(), new Vector2(player.getCenter().x, player.getCenter().y - Constant.ZOMBIERANGE + 5), null);
        double originalHealth = player.getHealth();
        zombie.attack(player, 0);

        assertNotEquals("Player within range should take damage when the zombie attacks.",
                player.getHealth(), originalHealth, 0.1);
    }


    @Test
    // Test 3.1.3
    public void zombieDoesNoDamageToPlayerOutOfRange() {
        player.respawn(Constant.ORIGIN, null);

        Zombie zombie = new Zombie(new Sprite(), new Vector2(player.getCenter().x, player.getCenter().y - 100), null);
        double originalHealth = player.getHealth();
        zombie.attack(player, 0);

        assertEquals("Player outside of range should not take damage when the zombie attacks.",
                player.getHealth(), originalHealth, 0.1);
    }

    @Test
    // Test 3.2.1
    public void zombieCannotAttackBeforeCooldownComplete() {
        player.respawn(Constant.ORIGIN, null);

        Zombie zombie = new Zombie(new Sprite(), new Vector2(player.getCenter().x, player.getCenter().y ), null);
        double originalHealth = player.getHealth();
        zombie.attack(player, 0);
        zombie.attack(player, 0);

        assertEquals("Player should only have taken one hit if attacked again before cooldown complete.",
                originalHealth - Constant.ZOMBIEDMG, player.getHealth(), 0.1);
    }

    @Test
    // Test 3.2.2
    public void zombieCanAttackAfterCooldownComplete() {
        player.respawn(Constant.ORIGIN, null);

        Zombie zombie = new Zombie(new Sprite(), new Vector2(player.getCenter().x, player.getCenter().y ), null);
        double originalHealth = player.getHealth();
        zombie.attack(player, 0);
        // zombie will not attack this go so has to be called a third time
        zombie.attack(player, Constant.ZOMBIEHITCOOLDOWN + 1);
        zombie.attack(player, 0);

        assertEquals("Player should have taken two hits if attacked again after cooldown complete.",
                originalHealth - (2 * Constant.ZOMBIEDMG), player.getHealth(), 0.1);
    }

}
