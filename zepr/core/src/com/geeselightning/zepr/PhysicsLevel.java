/**
 * Added by Shaun of the Devs to meet the requirement of different 6 locations
 */
package com.geeselightning.zepr;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.Arrays;

public class PhysicsLevel extends Level {

    private static final String mapLocation = "maps/Physics.tmx";
    private static final Vector2 playerSpawn = new Vector2(300, 320);
    private static final Vector2 powerSpawn = new Vector2(300, 500);

    // Defining possible zombie spawn locations on this map
    public static final ArrayList<Vector2> zombieSpawnPoints = new ArrayList<Vector2>(
            Arrays.asList(new Vector2(100,400), new Vector2(100,800),
                    new Vector2(900,400), new Vector2(900,800))
    );

    // Defining the number of zombies to be spawned for each wave
    private static final int[] waves = new int[]{12, 21, 26};

    public PhysicsLevel(Zepr zepr) {
        super(zepr, mapLocation, playerSpawn, zombieSpawnPoints, waves, powerSpawn);
    }

    @Override
    public void complete() {
        if (parent.progress == parent.PHYSICS) {
            parent.progress = parent.CENTRALHALL;
        }
        // The stage is being replayed
    }
}