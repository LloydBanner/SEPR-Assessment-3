package com.geeselightning.zepr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BonusGoose extends Sprite{
	
	private Texture leftOne = new Texture("gooseLeft.png");
	private Texture leftTwo = new Texture("gooseLeftFlap.png");
	private Texture rightOne = new Texture("gooseRight.png");
	private Texture rightTwo = new Texture("gooseRightFlap.png");
	private int spawnX;
	private int spawnY;
	private int type;
	private int velocityX = 0;
	private int velocityY = 0;
	private float timer = 0;
	private int movementPhase = 1;
	
	
	public BonusGoose(int type, int x, int y) {
		super(new Sprite(new Texture("gooseLeft.png")));
		spawnX = x;
		spawnY = y;
        setX(x);
        setY(y);
        this.type = type;
	}
	
	@Override
	public void draw(Batch batch) {
        // Calls the update method of the character. To update its properties, i.e. position.
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
	}
	
	public void respawn() {
        setX(spawnX);
        setY(spawnY);
        movementPhase = 1;
	}
	
	public void update(float delta) {
        // Update x, y position of character.
        // New position is the old position plus the distance moved as a result of the velocity
        float oldX = getX(), oldY = getY();

        setX(getX() + velocityX * delta);
        setY(getY() + velocityY * delta);
        
        // Used to time movement and animation
		timer += delta;
		if (timer >= 2) {
			timer = 0;
		}
		
		// Animation
		if (timer < 0.5 || timer > 1.5) {
			if (velocityX > 0) {
				setTexture(rightOne);
			} else {
				setTexture(leftOne);
			}
		} else {
			if (velocityX > 0) {
				setTexture(rightTwo);
			} else {
				setTexture(leftTwo);
			}
		}
		
		// Movement for different geese
		if (type == 1) {
			if (timer > 1) {
				if (movementPhase <= 200) {
					movementPhase += 1;
				} else {
					movementPhase = 1;
				}
			}
			if (movementPhase <= 100) {
				velocityX = -10;
				velocityY = 10;
			} else {
				velocityX = 10;
				velocityY = -10;
			}
			
		} else if (type == 2) {
			if (timer > 1) {
				if (movementPhase <= 200) {
					movementPhase += 1;
				} else {
					movementPhase = 1;
				}
			}
			if (movementPhase <= 100) {
				velocityX = -10;
			} else {
				velocityX = 10;
			}
			
		} else {
			if (timer > 1) {
				if (movementPhase <= 200) {
					movementPhase += 1;
				} else {
					movementPhase = 1;
				}
			}
			if (movementPhase <= 100) {
				velocityX = -10;
				velocityY = -10;
			} else {
				velocityX = 10;
				velocityY = 10;
			}
		}
		
	}

	public void dispose() {
		leftOne.dispose();
		leftTwo.dispose();
		rightOne.dispose();
		rightTwo.dispose();
	}
}
