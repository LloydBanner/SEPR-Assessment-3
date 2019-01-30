package com.geeselightning.zepr;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.Arrays;

public class CourtyardLevel extends Level {

    private static final String mapLocation = "maps/courtyard.tmx";
    private static final Vector2 playerSpawn = new Vector2(300, 300);
    private static final Vector2 powerSpawn = new Vector2(250, 250);

    // Defining possible zombie spawn locations on this map
    public static final ArrayList<Vector2> zombieSpawnPoints = new ArrayList<Vector2>(
            Arrays.asList(new Vector2(120,100), new Vector2(630,600),
                    new Vector2(630,100), new Vector2(120,500))
    );

    // Defining the number of zombies to be spawned for each wave
    private static final int[] waves = new int[]{7, 12, 17};

    public CourtyardLevel(Zepr zepr) {
        super(zepr, mapLocation, playerSpawn, zombieSpawnPoints, waves, powerSpawn);
    }

    @Override
    public void complete() {
        // Update progress
        if (parent.progress == parent.COURTYARD) {
            parent.progress = parent.COMPLETE;
        }
        // The stage is being replayed
    }
}
