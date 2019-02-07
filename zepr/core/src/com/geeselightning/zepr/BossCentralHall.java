/**
 * Added by Shaun of the Devs to meet the requirement of Bosses
 */
package com.geeselightning.zepr;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class BossCentralHall extends Character {

    private Player player = Player.getInstance();
    int attackDamage = Constant.ZOMBIEDMG;
    public int hitRange = Constant.ZOMBIERANGE;
    public final float hitCooldown = Constant.ZOMBIEHITCOOLDOWN;

    public BossCentralHall(Sprite sprite, Vector2 zombieSpawn, Level currentLevel) {
        super(sprite, zombieSpawn, currentLevel);
        this.speed = Constant.BOSSCENTRALHALLSPEED;
        this.maxHealth = Constant.BOSSCENTRALHALLMAXHP;
        this.health = maxHealth;
    }

    @Override
    public void attack(Character player, float delta) {
        if (canHitGlobal(player, hitRange) && hitRefresh > hitCooldown) {
            player.takeDamage(attackDamage);
            hitRefresh = 0;
        } else {
            hitRefresh += delta;
        }
    }

    @Override
    public void update(float delta) {
        //move according to velocity
        super.update(delta);
        
        // Spawns zombies near the boss when there are less than 10 zombies on the map
        if (currentLevel.zombiesRemaining < 3) {
            int random = (int )(Math.random() * 40 + 1);
        	if (random > 20) {
        		random = -(random - 20);
        	}
    		Character zombie = (new Zombie(new Sprite(new Texture("zombie01.png")),
                    new Vector2(getX() + random, getY() + random), currentLevel));
            boolean collides = false;
            for (Character otherZombie : currentLevel.aliveZombies) {
                if (zombie.collidesWith(otherZombie)) {
                    collides = true;
                }
            }
            if (!collides) {
            	currentLevel.aliveZombies.add(zombie);
            	currentLevel.zombiesRemaining++;
            }
        }

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
