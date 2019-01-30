package com.geeselightning.zepr;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PowerUp extends Sprite {

    Player player = Player.getInstance();
    public int type;
    Level currentLevel;
    public boolean active;

    public PowerUp(int type, Texture texture, Level currentLevel) {
        super(new Sprite(texture));
        this.type = type;
        this.currentLevel = currentLevel;
        if (currentLevel != null) {
            // Tests pass a null currentLevel
            setPosition(currentLevel.powerSpawn.x, currentLevel.powerSpawn.y);
        }
    }

    public void activate(){
        active = true;
    }

    public void deactivate(){
        active = false;
        if (currentLevel != null) {
            // Tests pass a null currentLevel
            currentLevel.currentPowerUp = null;
        }
    }

    public boolean overlapsPlayer(){
        Rectangle rectanglePlayer = player.getBoundingRectangle();
        Rectangle rectanglePower = this.getBoundingRectangle();
        return rectanglePlayer.overlaps(rectanglePower);
    }

    public void update(float delta) {}

}
