package com.geeselightning.zepr.tests;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.geeselightning.zepr.Character;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class CharacterTest {

    @Test
    // Test 1.1.1
    public void charactersWithSamePositionShouldCollide() {
        Character character = new Character(new Sprite(), new Vector2(0,0), null);
        assertTrue("A Character should collide with itself.", character.collidesWith(character));
    }

    @Test
    // Test 1.1.2
    public void touchingCharactersShouldCollide() {
            Character anotherCharacter = new Character(new Sprite(), new Vector2(0,10), null);
            Character character = new Character(new Sprite(), new Vector2(0,0), null);
            assertTrue("Characters that touch should collide.", character.collidesWith(anotherCharacter));
    }

    @Test
    // Test 1.1.3
    public void nonTouchingCharactersShouldNotCollide() {
        Character anotherCharacter = new Character(new Sprite(), new Vector2(20,20), null);
        Character character = new Character(new Sprite(), new Vector2(0,0), null);
        assertFalse("Characters that don't touch should not collide.", character.collidesWith(anotherCharacter));
    }


    @Test
    // Test 1.2.1
    public void getCenterOnCharacterWithPositivePosition() {
        Character character = new Character(new Sprite(new Texture("player01.png")),
                new Vector2(1,1), null);
        assertEquals("Testing center calculation with positive position.", new Vector2((float) 17.0, (float) 17.0),
                character.getCenter());
    }

    @Test
    // Test 1.2.2
    public void getCenterOnCharacterWithNegativePosition() {
        Character character = new Character(new Sprite(new Texture("player01.png")),
                new Vector2(-50,-50), null);
        assertEquals("Testing center calculation with negative position.", new Vector2(-34, -34),
                character.getCenter());
    }

    @Test
    // Test 1.3.1
    public void getDirectionInTopRightQuadrant() {
        Character character = new Character(new Sprite(new Texture("player01.png")), new Vector2(-16,-16), null);
        assertEquals("North-East direction should be 0.785.", 0.785, character.getDirectionTo(new Vector2(1,1)), 0.001);

    }

    @Test
    // Test 1.3.2
    public void getDirectionInBottomRightQuadrant() {
        Character character = new Character(new Sprite(new Texture("player01.png")), new Vector2(-16,-16), null);
        assertEquals("South-East direction should be 2.356.", 2.356, character.getDirectionTo(new Vector2(1,-1)), 0.001);

    }

    @Test
    // Test 1.3.3
    public void getDirectionInBottomLeftQuadrant() {
        Character character = new Character(new Sprite(new Texture("player01.png")), new Vector2(-16,-16), null);
        assertEquals("South-West direction should be 3.927.", 3.927, character.getDirectionTo(new Vector2(-1,-1)), 0.001);
    }

    @Test
    // Test 1.3.4
    public void getDirectionInTopLeftQuadrant() {
        Character character = new Character(new Sprite(new Texture("player01.png")), new Vector2(-16,-16), null);
        assertEquals("North-West direction should be 5.498.", 5.498, character.getDirectionTo(new Vector2(-1,1)), 0.001);
    }

    @Test
    // Test 1.4
    public void charactersTakeSpecifiedDamage() {
        Character character = new Character(new Sprite(), new Vector2(0, 0), null);
        double originalHealth = character.getHealth();
        character.takeDamage(50);
        assertEquals("Character's health should be reduced by 50 when takeDamage(50).", originalHealth - 50,
                character.getHealth(), 0.1);
    }

    @Test
    // Test 1.5.1
    public void getDirNormVectorToNegativePosition() {
        Character character = new Character(new Sprite(new Texture("player01.png")), new Vector2(-16,-16), null);
        Vector2 position = new Vector2(-10, -30);
        Vector2 normalizedDirection = character.getDirNormVector(position);
        double expectedX = -10 / Math.sqrt(1000);
        double expectedY = -30 / Math.sqrt(1000);
        assertEquals("Correct x component for player with center at origin to (-10, -30).", expectedX, normalizedDirection.x, 0.1);
        assertEquals("Correct y component for player with center at origin to (-10, -30).", expectedY, normalizedDirection.y, 0.1);
    }

    @Test
    // Test 1.5.2
    public void getDirNormVectorToPositivePosition() {
        Character character = new Character(new Sprite(new Texture("player01.png")), new Vector2(-16,-16), null);
        Vector2 position = new Vector2(47, 20);
        Vector2 normalizedDirection = character.getDirNormVector(position);
        double expectedX = 47 / Math.sqrt(2609);
        double expectedY = 20 / Math.sqrt(2609);
        assertEquals("Correct x component for player with center at origin to (47, 20).", expectedX, normalizedDirection.x, 0.1);
        assertEquals("Correct y component for player with center at origin to (47, 20).", expectedY, normalizedDirection.y, 0.1);
    }
}