/**
 * Added by Shaun of the Devs to meet the requirement of different 6 locations
 */
package com.geeselightning.zepr;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.Arrays;

public class CentralHallLevel extends Level {

    private static final String mapLocation = "maps/Central hall.tmx";
    private static final Vector2 playerSpawn = new Vector2(300, 300);
    private static final Vector2 powerSpawn = new Vector2(550, 550);

    // Defining possible zombie spawn locations on this map
    public static final ArrayList<Vector2> zombieSpawnPoints = new ArrayList<Vector2>(
            Arrays.asList(new Vector2(900,100), new Vector2(100,200),
                    new Vector2(900,800), new Vector2(100,800))
    );

    // Defining the number of zombies to be spawned for each wave
    private static final int[] waves = new int[]{23, 27, 30, 150};

    public CentralHallLevel(Zepr zepr) {
        super(zepr, mapLocation, playerSpawn, zombieSpawnPoints, waves, powerSpawn);
    }

    @Override
    public void complete() {
        if (parent.progress == parent.CENTRALHALL) {
            parent.progress = parent.COMPLETE;
        }
        // The stage is being replayed
    }
}
