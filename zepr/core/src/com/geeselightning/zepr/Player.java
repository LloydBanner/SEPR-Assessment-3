package com.geeselightning.zepr;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player extends Character {

    private static final Player instance = new Player(new Sprite(new Texture("player01.png")), new Vector2(0, 0));
    int attackDamage = Constant.PLAYERDMG;
    int hitRange = Constant.PLAYERRANGE;
    final float hitCooldown =  Constant.PLAYERHITCOOLDOWN;
    Texture mainTexture;
    Texture attackTexture;
    boolean attack = false;
    float HPMult;
    float dmgMult;
    float speedMult;
    String playertype;
    public boolean isImmune;


    private Player(Sprite sprite, Vector2 playerSpawn) {
        super(sprite, playerSpawn, null);
    }

    public static Player getInstance(){
        return instance;
    }

    public void setType(String playertype){
        this.playertype = playertype;
    }

    public String getType() {
        return playertype;
    }


    public void attack(Zombie zombie, float delta) {
        if (canHitGlobal(zombie, hitRange) && hitRefresh > hitCooldown) {
            zombie.takeDamage(attackDamage);
            hitRefresh = 0;
        } else {
            hitRefresh += delta;
        }
    }

    public void respawn(Vector2 playerSpawn, Level level){
        setX(playerSpawn.x);
        setY(playerSpawn.y);
        if (playertype == "nerdy"){
            dmgMult = Constant.NERDYDMGMULT;
            HPMult = Constant.NERDYHPMULT;
            speedMult = Constant.NERDYSPEEDMULT;
        }
        else if (playertype == "sporty"){
            dmgMult = Constant.SPORTYDMGMULT;
            HPMult = Constant.SPORTYHPMULT;
            speedMult = Constant.SPORTYSPEEDMULT;
        }
        else if (playertype == null){
            dmgMult =1;
            HPMult = 1;
            speedMult = 1;
        }
        this.attackDamage = (int)(Constant.PLAYERDMG * dmgMult);
        this.speed = (int)(Constant.PLAYERSPEED * speedMult);
        this.health = (int)(HPMult * Constant.PLAYERMAXHP);
        this.currentLevel = level;

        if (playertype == "nerdy") {
            mainTexture = new Texture("player01.png");
            attackTexture = new Texture("player01_attack.png");
            this.setTexture(mainTexture);
        } else {
            // playertype == sporty
            mainTexture = new Texture("player02.png");
            attackTexture = new Texture("player02_attack.png");
            this.setTexture(mainTexture);
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // Update the direction the player is facing.
        direction = getDirectionTo(currentLevel.getMouseWorldCoordinates());


        // When you die, end the level.
        if (health <= 0) {
            currentLevel.gameOver();
        }

        // Gives the player the attack texture for 0.1s after an attack.
        //if (hitRefresh <= 0.1 && getTexture() != attackTexture) {
        if (attack) {
            this.setTexture(attackTexture);
        } else {
        // Changes the texture back to the main one after 0.1s.
        //if (hitRefresh > 0.1 && getTexture() == attackTexture) {
            this.setTexture(mainTexture);
        }
    }

    @Override
    public void takeDamage(int dmg){
        if(!isImmune){
            //If powerUpImmunity is activated
            health -= dmg;
        }
    }

}
