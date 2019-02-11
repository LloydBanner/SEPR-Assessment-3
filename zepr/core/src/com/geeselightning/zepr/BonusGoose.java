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
	private int velocityConstant = 30;
	private float timer = 0;
	private int movementPhase = 1;
	
	
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
        
        // Used to time movement and animation
		timer += delta;
		if (timer >= 2) {
			timer = 0;
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
		
		// Movement for different geese
		Random rand = new Random();

		if (timer > 0.1) {
			if (movementPhase <= 200) {
				movementPhase += 1;
			} else {
				movementPhase = 1;
			}
		}
		if (movementPhase <= 90 + rand.nextInt(10)) {
			velocityX = -velocityConstant - rand.nextInt(90);
			velocityY = velocityConstant + rand.nextInt(90);
		} else {
			velocityX = velocityConstant + rand.nextInt(90);
			velocityY = -velocityConstant - rand.nextInt(90);
		}
		
	}
		
		
	
	public void dispose() {
		leftOne.dispose();
	}
}
