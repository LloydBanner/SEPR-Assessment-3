/**
 * Added by Shaun of the Devs to meet the requirement of Bosses
 */
package com.geeselightning.zepr;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class BossCourtyard extends Character {

    private Player player = Player.getInstance();
    int attackDamage = Constant.ZOMBIEDMG;
    public int hitRange = Constant.ZOMBIERANGE;
    public final float hitCooldown = Constant.ZOMBIEHITCOOLDOWN;
    private int zombiesToSpawn = 0;
    private boolean spawningZombies = false;
    private float spawnTimer = 0;

    public BossCourtyard(Sprite sprite, Vector2 zombieSpawn, Level currentLevel) {
        super(sprite, zombieSpawn, currentLevel);
        this.speed = Constant.BOSSCOURTYARDSPEED;
        this.maxHealth = Constant.BOSSCOURTYARDMAXHP;
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
        
        // Spawns zombies near the boss when there are less than 8 zombies on the map
        // spawns every 10 seconds for 5 seconds near the boss if there are no zombies in the way
        if (spawnTimer <= 15) {
        	spawnTimer += delta;
        } else {
        	spawnTimer = 0;
        }
        
        if (currentLevel.zombiesRemaining + zombiesToSpawn < 8) {
        	zombiesToSpawn++;
        }
        
        if (spawnTimer <= 5 && zombiesToSpawn > 0) {
        	spawningZombies = true;
            int random = (int )(Math.random() * 40 + 1);
        	if (random > 20) {
        		random = -(random - 20);
        	}
    		Character zombie = (new ZombieFast(new Sprite(new Texture("FastZombie.png")),
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
            	zombiesToSpawn--;
            }
        }
        
        if (spawningZombies && spawnTimer > 5) {
        	spawningZombies = false;
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
