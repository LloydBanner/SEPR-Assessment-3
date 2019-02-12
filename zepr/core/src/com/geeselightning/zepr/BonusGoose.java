package com.geeselightning.zepr;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BonusGoose extends Sprite{
	
	private Texture leftOne = new Texture("gooseLeft.png");
	private int velocityX = 0;
	private int velocityY = 0;
	private int speed = 60;
	private float timer = 0;
	private int movementPhase = 1;
	private int direction = 0;
	private int directionCopy = 0;
	private boolean startedMovement = false;
	
	
	public BonusGoose(int x, int y) {
		super(new Sprite(new Texture("gooseLeft.png")));
		Random rand = new Random();
        setX(x);
        setY(y);
	}
	
	@Override
	public void draw(Batch batch) {
        // Calls the update method of the character. To update its properties, i.e. position.
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
	}
	
	public void respawn() {
		Random rand = new Random();
		int randX = rand.nextInt(925);
		int randY = rand.nextInt(600);
		setX(randX);
        setY(randY);
        movementPhase = 1;
	}
	
	public void update(float delta) {
        // Update x, y position of character.
        setX(getX() + velocityX * delta);
        setY(getY() + velocityY * delta);
		
        if (!startedMovement) {
        	velocityX = -speed;
		    velocityY = speed;
		    startedMovement = true;
        }
        
		if(getX() >= 925) {
			setX(925);
		} else if(getX() <= 280) {
			setX(280);
		} 
		
		if(getY() >= 600) {
			setY(600);
		} else if(getY() <= 250) {
			setY(250);
		}
		
        // Increments timer
		timer += delta;
		
		// Used to choose a random direction
		Random rand = new Random();
		
		// Changes direction and increases speed every 4 seconds
		if (timer > 4) {
			speed += 2;
			// Resets timer
			timer = 0;
			
			// Avoids repeats in direction
			while(direction == directionCopy) {
				direction = rand.nextInt(7);				
			}
			directionCopy = direction;

			switch (direction) {
			case(0): // NW
				velocityX = -speed;
			    velocityY = speed;
				break;
			case(1): // N
				velocityX = 0;
		    	velocityY = speed;
				break;
			case(2): // NE
				velocityX = speed;
	    		velocityY = speed;
	    		break;
			case(3): // E
				velocityX = +speed;
	    		velocityY = 0;
	    		break;
			case(4): // SE
				velocityX = speed;
				velocityY = -speed;
				break;
			case(5): // S
				velocityX = 0;
				velocityY = -speed;
				break;
			case(6): // SW
				velocityX = -speed;
				velocityY = -speed;
				break;
			case(7): // W
				velocityX = -speed;
				velocityY = 0;
				break;
			default:
				velocityX = -speed;
			    velocityY = speed;
				break;
			}
		} 
	}
		
		
	
	public void dispose() {
		leftOne.dispose();
	}
}
