package com.geeselightning.zepr.tests;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.geeselightning.zepr.Constant;
import com.geeselightning.zepr.Player;
import com.geeselightning.zepr.Zombie;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class PlayerTest {

    @Test
    // Test 2.1
    public void playerPositionResetsWhenRespawned() {
        Player player = Player.getInstance();
        Vector2 originalPosition = new Vector2(player.getX(), player.getY());
        player.setPosition(10, 10);
        player.respawn(new Vector2(0, 0), null);
        assertEquals("Position should reset when the player is respawned.", originalPosition, new Vector2(player.getX(), player.getY()));
    }

    @Test
    // Test 2.2.1
    public void playerDoesNoDamageToZombieWhenAtMaxRange() {
        Player player = Player.getInstance();

        Zombie zombie = new Zombie(new Sprite(), new Vector2(player.getCenter().x, player.getCenter().y + Constant.PLAYERRANGE), null);
        double originalHealth = zombie.getHealth();
        player.attack(zombie, 0);

        assertEquals("Zombie on the edge of range should not take damage when the player attacks.",
                zombie.getHealth(), originalHealth, 0.1);
    }

    @Test
    // Test 2.2.2
    public void playerDoesDamageToZombieWhenInRange() {
        Player player = Player.getInstance();

        Zombie zombie = new Zombie(new Sprite(), new Vector2(player.getCenter().x, player.getCenter().y + Constant.PLAYERRANGE - 10), null);
        double originalHealth = zombie.getHealth();
        player.attack(zombie, 0);

        assertNotEquals("Zombie within range should take damage when the player attacks.",
                zombie.getHealth(), originalHealth, 0.1);
    }

    @Test
    //Test 2.2.3
    public void playerDoesNoDamageToZombieOutOfRange() {
        Player player = Player.getInstance();

        Zombie zombie = new Zombie(new Sprite(), new Vector2(player.getCenter().x, player.getCenter().y +100), null);
        double originalHealth = zombie.getHealth();
        player.attack(zombie, 0);

        assertEquals("Zombie outside of range should not take damage when the player attacks.",
                zombie.getHealth(), originalHealth, 0.1);
    }

    @Test
    // Test 2.3.1
    public void playerTypesHaveDifferentHealth() {
        Player player = Player.getInstance();
        player.setType("nerdy");
        player.respawn(Constant.ORIGIN, null);
        double nerdyHealth = player.getHealth();
        player.setType("sporty");
        player.respawn(Constant.ORIGIN, null);
        assertNotEquals("Sporty and nerdy students should have a different amount of hit points.",
                nerdyHealth, player.getHealth(), 0.1);
    }

    @Test
    // Test 2.3.2
    public void playerTypesHaveDifferentSpeed() {
        Player player = Player.getInstance();
        player.setType("nerdy");
        player.respawn(Constant.ORIGIN, null);
        double nerdySpeed = player.speed;
        player.setType("sporty");
        player.respawn(Constant.ORIGIN, null);
        assertNotEquals("Sporty and nerdy students should have a different amount of hit points.",
                nerdySpeed, player.speed);
    }

}