/**
 * Added by Shaun of the Devs to meet the requirement of 5 power ups
 */
package com.geeselightning.zepr;

import com.badlogic.gdx.graphics.Texture;

public class PowerUpNoCooldowns extends PowerUp {

    public float timeRemaining = Constant.NOCOOLDOWNSTIME;

    public PowerUpNoCooldowns(Level currentLevel) {
        super(3, new Texture("nocooldowns.png"), currentLevel);
        // added to display power up on screen
        timeRemaining = Constant.NOCOOLDOWNSTIME;
        this.name = "No Ability Cooldown";
    }
    
    @Override
    public void activate() {
        super.activate();
        this.getTexture().dispose();
    }

    @Override
    public void deactivate() {
        super.deactivate();
    }

    @Override
    public void update(float delta) {
        if (active) {
        	player.abilityCooldown = 0;
            timeRemaining -= delta;
        }
        if (timeRemaining < 0) {
            deactivate();
        }
    }
}
