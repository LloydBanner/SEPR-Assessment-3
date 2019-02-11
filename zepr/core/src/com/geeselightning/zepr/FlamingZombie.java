/**
 * Added by Shaun of the Devs to meet the requirement of different zombie types
 */
package com.geeselightning.zepr;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class FlamingZombie extends Character {

    private Player player = Player.getInstance();
    int attackDamage = Constant.FLAMINGZOMBIEDMG;
    public int hitRange = Constant.FLAMINGZOMBIERANGE;
    public final float hitCooldown = Constant.FLAMINGZOMBIEHITCOOLDOWN;
    private boolean playerBurning = false;
    private int count = 0;
    private float timer = 0;
    

    public FlamingZombie(Sprite sprite, Vector2 zombieSpawn, Level currentLevel) {
        super(sprite, zombieSpawn, currentLevel);
        Random rand = new Random();
        this.speed = Constant.FLAMINGZOMBIESPEED + rand.nextInt(10);
        this.maxHealth = Constant.FLAMINGZOMBIEMAXHP;
        this.health = maxHealth;
    }

    @Override
    public void attack(Character player, float delta) {
    	if (canHitGlobal(player, hitRange) && hitRefresh > hitCooldown) {
            playerBurning = true;
            hitRefresh = 0;
        } else {
            hitRefresh += delta;
        }
    	
    	// Added fire damage: A large amount of damage spread out over a longer time       
    	timer += delta; 
    	if(timer>1 && playerBurning) {
    		player.takeDamage(attackDamage);  
        	timer = 0;
        	count += 1;
    	}
        if(count==5) {
        	playerBurning = false;
            count = 0;	      
        }
    }

    @Override
    public void update(float delta) {
        //move according to velocity
        super.update(delta);

        // update velocity to move towards player
        // Vector2.scl scales the vector
        velocity = getDirNormVector(player.getCenter()).scl(speed);

        // update direction to face the player
        direction = getDirectionTo(player.getCenter());

        if (health <= 0) {
            currentLevel.zombiesRemaining--;
            currentLevel.aliveZombies.remove(this);
            // Removed disposal of texture to prevent texture glitch
        }
    }
}