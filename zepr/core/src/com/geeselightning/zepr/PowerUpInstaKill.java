/**
 * Added by Shaun of the Devs to meet the requirement of 5 power ups
 */
package com.geeselightning.zepr;

import com.badlogic.gdx.graphics.Texture;

public class PowerUpInstaKill extends PowerUp {

    public PowerUpInstaKill(Level currentLevel) {
        super(3, new Texture("instakill.png"), currentLevel);
        // added to display power up on screen
        timeRemaining = Constant.INSTAKILLTIME;
        this.name = "INSTA KILL";
    }
    
    @Override
    public void activate() {
        super.activate();
        super.player.attackDamage += Constant.INSTAKILL;
        this.getTexture().dispose();
    }

    @Override
    public void deactivate() {
        super.deactivate();
        super.player.attackDamage -= Constant.INSTAKILL;
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
