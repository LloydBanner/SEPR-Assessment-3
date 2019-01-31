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
    boolean abilityActivated = false;
    float abilityCooldown = 0;
    float abilityDuration = 0;
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
    
    // Added to implement player abilities
    public void activateAbility() {
    	if (playertype == "nerdy") {
    		abilityCooldown = Constant.NERDYABILITYCOOLDOWN;
    		abilityDuration = Constant.NERDYABILITYDURATION;
            dmgMult = Constant.NERDYABILITYDMGMULT;
    		this.attackDamage = (int)(Constant.PLAYERDMG * dmgMult);
            mainTexture = new Texture("player01_power.png");
            attackTexture = new Texture("player01_attack_power.png");
    	}
    	if (playertype == "sporty") {
    		abilityCooldown = Constant.SPORTYABILITYCOOLDOWN;
    		abilityDuration = Constant.SPORTYABILITYDURATION;
            speedMult = Constant.SPORTYABILITYSPEEDMULT;
    		this.speed = (int)(Constant.PLAYERSPEED * speedMult);
    	}
    	if (playertype == "drama") {
    		abilityCooldown = Constant.DRAMAABILITYCOOLDOWN;
    		abilityDuration = Constant.DRAMAABILITYDURATION;
    		this.health += 10;
            mainTexture = new Texture("player03_heal.png");
            attackTexture = new Texture("player03_attack_heal.png");
    	}
    }
    
    // Added to implement player abilities
    public void deactivateAbility() {
    	if (playertype == "nerdy") {
            dmgMult = Constant.NERDYDMGMULT;
            this.attackDamage = (int)(Constant.PLAYERDMG * dmgMult);
            mainTexture = new Texture("player01.png");
            attackTexture = new Texture("player01_attack.png");
    	}
    	if (playertype == "sporty") {
            speedMult = Constant.SPORTYSPEEDMULT;
            speedMult = Constant.SPORTYSPEEDMULT;
    		this.speed = (int)(Constant.PLAYERSPEED * speedMult);
    	}
    	if (playertype == "drama") {
            mainTexture = new Texture("player03.png");
            attackTexture = new Texture("player03_attack.png");
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
        // Added for third player type
        else if (playertype == "drama"){
            dmgMult = Constant.DRAMADMGMULT;
            HPMult = Constant.DRAMAHPMULT;
            speedMult = Constant.DRAMASPEEDMULT;
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
        } else if (playertype == "sporty"){
            mainTexture = new Texture("player02.png");
            attackTexture = new Texture("player02_attack.png");
            this.setTexture(mainTexture);
        } else if (playertype == "drama"){
            // Added for third player type
            mainTexture = new Texture("player03.png");
            attackTexture = new Texture("player03_attack.png");
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
        
        // Added to implement player abilities
        if (abilityCooldown <= 0) {
        	if (abilityActivated) {
            	this.activateAbility();
            }
        } else {
        	abilityCooldown -= delta;
        }
        
        // Added to implement player abilities
        if (abilityDuration <= 0 && abilityActivated) {
        	this.deactivateAbility();
        	abilityActivated = false;
        } else if (abilityDuration > 0) {
        	abilityDuration -= delta;
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
