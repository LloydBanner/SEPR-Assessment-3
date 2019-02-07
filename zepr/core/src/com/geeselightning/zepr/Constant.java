package com.geeselightning.zepr;

import com.badlogic.gdx.math.Vector2;

public final class Constant {
    public static final Vector2 ORIGIN = new Vector2(0, 0);
    public static final float PLAYERSPEED = 120;
    public static final int PLAYERMAXHP = 100;
    public static final int PLAYERDMG = 20;
    public static final int PLAYERRANGE = 50;
    public static final float PLAYERHITCOOLDOWN = 0.4f;
    public static final float ZOMBIESPEED = 80;
    public static final int ZOMBIEMAXHP = 100;
    public static final int ZOMBIEDMG = 10;
    public static final int ZOMBIERANGE = 20;
    public static final float ZOMBIEHITCOOLDOWN = 1;
    // Added stats for different zombies
    public static final float ZOMBIEFASTSPEED = 150;
    public static final int ZOMBIEFASTMAXHP = 50;
    public static final int ZOMBIEFASTDMG = 5;
    public static final float NERDYHPMULT = 1.5f;
    public static final float NERDYDMGMULT = 1;
    public static final float NERDYSPEEDMULT = 1;
    // Added to work with player abilities
    public static final float NERDYABILITYDMGMULT = 20;
    public static final float NERDYABILITYDURATION = 5;
    public static final float NERDYABILITYCOOLDOWN = 30;
    public static final float SPORTYHPMULT = 1;
    public static final float SPORTYDMGMULT = 1;
    public static final float SPORTYSPEEDMULT = 1.5f;
    // Added to work with player abilities
    public static final float SPORTYABILITYSPEEDMULT = 3;
    public static final float SPORTYABILITYDURATION = 10;
    public static final float SPORTYABILITYCOOLDOWN = 60;
    // Added to be used for third player type
    public static final float DRAMAHPMULT = 1;
    public static final float DRAMADMGMULT = 2;
    public static final float DRAMASPEEDMULT = 1;
    // Added to work with player abilities
    public static final float DRAMAABILITYDURATION = 0.25f;
    public static final float DRAMAABILITYCOOLDOWN = 40;
    public static final int HEALUP = 30;
    public static final int SPEEDUP = 50;
    // Added for extra power ups
    public static final int INSTAKILL = 100;
    public static final float SPEEDUPTIME = 10;
    public static final float IMMUNITYTIME = 5;
    // Added for extra power ups
    public static final float INSTAKILLTIME = 10;
    public static final float NOCOOLDOWNSTIME = 10;
    // Added for minigame
    public static final int BONUSGOAL = 10;
}
