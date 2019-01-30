package com.geeselightning.zepr;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.Arrays;

public class HalifaxLevel extends Level {

    private static final String mapLocation = "maps/halifaxmap.tmx";
    private static final Vector2 playerSpawn = new Vector2(300, 300);
    private static final Vector2 powerSpawn = new Vector2(200, 200);

    // Defining possible zombie spawn locations on this map
    public static final ArrayList<Vector2> zombieSpawnPoints = new ArrayList<Vector2>(
            Arrays.asList(new Vector2(600,100), new Vector2(100,200),
                    new Vector2(600,500), new Vector2(100,600))
    );

    // Defining the number of zombies to be spawned for each wave
    private static final int[] waves = new int[]{6, 11, 16};

    public HalifaxLevel(Zepr zepr) {
        super(zepr, mapLocation, playerSpawn, zombieSpawnPoints, waves, powerSpawn);
    }

    @Override
    public void complete() {
        if (parent.progress == parent.HALIFAX) {
            parent.progress = parent.COURTYARD;
        }
        // The stage is being replayed
    }
}
