package com.geeselightning.zepr;

import com.badlogic.gdx.graphics.Texture;

public class PowerUpSpeed extends PowerUp {

    public float timeRemaining = Constant.SPEEDUPTIME;

    public PowerUpSpeed(Level currentLevel) {
        super(2, new Texture("speed.png"), currentLevel);
    }

    @Override
    public void activate() {
        super.activate();
        super.player.speed += Constant.SPEEDUP;
        this.getTexture().dispose();
    }

    @Override
    public void deactivate() {
        super.deactivate();
        super.player.speed -= Constant.SPEEDUP;
    }

    @Override
    public void update(float delta) {
        if (active) {
            timeRemaining -= delta;
        }
        if (timeRemaining < 0) {
            deactivate();
        }
    }
}
