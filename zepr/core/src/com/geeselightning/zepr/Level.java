package com.geeselightning.zepr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.ArrayList;


public class Level implements Screen {

    protected Zepr parent;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Player player;
    // Changed a live zombies to Character to allow different zombies to be spawned
    protected ArrayList<Character> aliveZombies = new ArrayList<Character>();
    private String mapLocation;
    private Vector2 playerSpawn;
    public ArrayList<Vector2> zombieSpawnPoints;
    private ZeprInputProcessor inputProcessor = new ZeprInputProcessor();
    protected boolean isPaused;
    private Stage stage;
    private Table table;
    private Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
    private int[] waves;
    private int currentWave = 1;
    protected int zombiesRemaining; // the number of zombies left to kill to complete the wave
    private int zombiesToSpawn; // the number of zombies that are left to be spawned this wave
    private boolean pauseButton = false;
    Texture blank;
    Vector2 powerSpawn;
    PowerUp currentPowerUp = null;

    Label progressLabel = new Label("", skin);
    Label healthLabel = new Label("", skin);
    // Added for player Abilities
    Label AbilityCooldownLabel = new Label("", skin);
    Label AbilityDurationLabel = new Label("", skin);
    Label powerupLabel = new Label("", skin);

    public Level(Zepr zepr, String mapLocation, Vector2 playerSpawn, ArrayList<Vector2> zombieSpawnPoints, int[] waves, Vector2 powerSpawn) {
        parent = zepr;
        this.mapLocation = mapLocation;
        this.playerSpawn = playerSpawn;
        this.zombieSpawnPoints = zombieSpawnPoints;
        this.isPaused = false;
        this.blank = new Texture("blank.png");
        this.powerSpawn = powerSpawn;

        // Set up data for first wave of zombies
        this.waves = waves;
        this.zombiesRemaining = waves[0];
        this.zombiesToSpawn = zombiesRemaining;

        // Creating a new libgdx stage to contain the pause menu and in game UI
        this.stage = new Stage(new ScreenViewport());

        // Creating a table to hold the UI and pause menu
        this.table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
    }


    /**
     * Called once the stage is complete to update the game progress
     */
    protected void complete() {
        // implemented in subclasses
    }


    /**
     * Called when the player's health <= 0 to end the stage.
     */
    public void gameOver() {
        isPaused = true;
        parent.setScreen(new TextScreen(parent, "You died."));
    }


    /**
     * Used for collision detection between characters in Character.update()
     *
     * @return ArrayList containing all the characters currently in the level
     */
    public ArrayList<Character> getCharacters() {
        ArrayList<Character> characters = new ArrayList<Character>();

        for (Character zombie : aliveZombies) {
            characters.add(zombie);
        }

        characters.add(player);

        return characters;
    }

    /**
     * Spawns multiple zombies cycling through spawnPoints until the given amount have been spawned.
     *
     * @param spawnPoints locations where zombies should be spawned on this stage
     * @param amount number of zombies to spawn
     *
     * @return the number of zombies that failed to spawn
     */
    public int spawnZombies(int amount, ArrayList<Vector2> spawnPoints) {
        int notSpawned = 0;
        
        // Added to spawn bosses
        if (amount == 100) {
        	Character boss = (new BossCourtyard(new Sprite(new Texture("bossCourtyard.png")),
                    spawnPoints.get(1), this));
        	zombiesRemaining = 1;
        	amount = 0;
            aliveZombies.add(boss);
        } else if (amount == 150) {
        	Character boss = (new BossCentralHall(new Sprite(new Texture("bossCentralHall.png")),
                    spawnPoints.get(1), this));
        	zombiesRemaining = 1;
        	amount = 0;
            aliveZombies.add(boss);
        }
        
        for (int i = 0; i < amount; i++) {
        	Character zombie;
        	
        	// Added different zombies to spawn
        	// Used to determine which zombie to spawn
            int random = (int )(Math.random() * 10 + 1);
        	if (random >= 7 && random <= 9) {
        		zombie = (new ZombieFast(new Sprite(new Texture("FastZombie.png")),
                        spawnPoints.get(i % spawnPoints.size()), this));
        	} else {
                zombie = (new Zombie(new Sprite(new Texture("zombie01.png")),
                        spawnPoints.get(i % spawnPoints.size()), this));
        	}

            // Check there isn't already a zombie there, or they will be stuck
            boolean collides = false;
            for (Character otherZombie : aliveZombies) {
                if (zombie.collidesWith(otherZombie)) {
                    collides = true;
                    // Decrement counter as it didn't spawn.
                    notSpawned++;
                }
            }

            if (!collides) {
                aliveZombies.add(zombie);
            }
        }

        return notSpawned;
    }

    /**
     * Used for collision detection between the player and map
     *
     * @return boolean if the point (x, y) is in a blocked tile
     */
    public boolean isBlocked(float x, float y) {
        TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("collisionLayer");
        Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));

        // have to include this in case this cell is transparent on the collisionLayer
        if (cell == null || cell.getTile() == null) {
            return false;
        }

        MapProperties properties = cell.getTile().getProperties();
        // we have to add the property now because the properties don't load from the map file
        properties.put("solid", null);

        return properties.containsKey("solid");
    }


    /**
     * Converts the mousePosition which is a Vector2 representing the coordinates of the mouse within the game window
     * to a Vector2 of the equivalent coordinates in the world.
     *
     * @return Vector2 of the mouse position in the world.
     */
    public Vector2 getMouseWorldCoordinates() {
        // Must first convert to 3D vector as camera.unproject() does not take 2D vectors.
        Vector3 screenCoordinates = new Vector3(inputProcessor.mousePosition.x, inputProcessor.mousePosition.y, 0);
        Vector3 worldCoords3 = camera.unproject(screenCoordinates);

        return new Vector2(worldCoords3.x, worldCoords3.y);
    }

    @Override
    public void show() {
        // Start the stage unpaused.
        isPaused = false;

        // Loads the testmap.tmx file as map.
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load(mapLocation);

        // renderer renders the .tmx map as an orthogonal (top-down) map.
        renderer = new OrthogonalTiledMapRenderer(map);
        // It is only possible to view the render of the map through an orthographic camera.
        camera = new OrthographicCamera();

        //retrieve player instance and reset it
        player = Player.getInstance();
        player.respawn(playerSpawn, this);

        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void render(float delta) {
        if (isPaused) {
            // Clears the screen to black.
            Gdx.gl.glClearColor(0f, 0f, 0f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            TextButton resume = new TextButton("Resume", skin);
            TextButton exit = new TextButton("Exit", skin);

            if (!pauseButton) {

                table.clear();

                table.center();
                table.add(resume).pad(10);
                table.row();
                table.add(exit);
                pauseButton = true;

            }

            // Input processor has to be changed back once unpaused.
            Gdx.input.setInputProcessor(stage);

            // Defining actions for the resume button.
            resume.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    isPaused = false;
                    // Change input processor back
                    Gdx.input.setInputProcessor(inputProcessor);
                    pauseButton = false;
                }
            });

            // Defining actions for the exit button.
            exit.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    parent.changeScreen(Zepr.SELECT);
                }
            });

            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        } else {
            // Clears the screen to black.
            Gdx.gl.glClearColor(0f, 0f, 0f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            table.clear();

            // Try to spawn all zombies in the stage and update zombiesToSpawn with the amount that failed to spawn
            zombiesToSpawn = spawnZombies(zombiesToSpawn, zombieSpawnPoints);

            // Keep the player central in the screen.
            camera.position.set(player.getCenter().x, player.getCenter().y, 0);
            camera.update();
            
            // Added to prevent the game from crashing, Stops the renderer from being used after it is disposed 
            if (renderer != null) {
            	renderer.setView(camera);
            	renderer.render();

            	renderer.getBatch().begin();
            }

            player.draw(renderer.getBatch());

            // Resolve all possible attacks
            if (renderer != null) {
            	for (int i = 0; i < aliveZombies.size(); i++) {
            		Character zombie = aliveZombies.get(i);
            		// Zombies will only attack if they are in range, the attack has cooled down, and they are
            		// facing a player.
            		// Player will only attack in the reverse situation but player.attack must also be true. This is
            		// controlled by the ZeprInputProcessor. So the player will only attack when the user clicks.
            		if (player.attack) {
            			player.attack(zombie, delta);
            		}
            		zombie.attack(player, delta);

            		// Draw zombies
            		zombie.draw(renderer.getBatch());

            		// Draw zombie health bars
            		// Changed health bars so that they update properly 
            		int fillAmount = (int) ((zombie.getHealth() / zombie.maxHealth) * 30);
            		renderer.getBatch().setColor(Color.BLACK);
            		renderer.getBatch().draw(blank, zombie.getX(), zombie.getY()+32, 32, 3);
            		renderer.getBatch().setColor(Color.RED);
            		renderer.getBatch().draw(blank, zombie.getX()+1, zombie.getY()+33, fillAmount, 1);
            		renderer.getBatch().setColor(Color.WHITE);
            	}
            }
            	
            if ((currentPowerUp != null) && (renderer != null)) {
                // Activate the powerup up if the player moves over it and it's not already active
            	String powerupString;
                if (currentPowerUp.overlapsPlayer() && !currentPowerUp.active) {
                    currentPowerUp.activate();
                }
                // Only render the powerup if it is not active, otherwise it disappears
                if (!currentPowerUp.active) {
                    currentPowerUp.draw(renderer.getBatch());
                    powerupString = (currentPowerUp.name + ": Available");
                } else {
                	powerupString = (currentPowerUp.name + ": " + ((int) currentPowerUp.timeRemaining) + "s");
                }
                currentPowerUp.update(delta);
                powerupLabel.setText(powerupString);
            } else {
                powerupLabel.setText("");
            }
            
            // Added to prevent the game from crashing, Stops the renderer from being used after it is disposed
            if (renderer != null) {
            	renderer.getBatch().end();
            }


            String progressString = ("Wave " + Integer.toString(currentWave) + ", " + Integer.toString(zombiesRemaining) + " zombies remaining.");
            String healthString = ("Health: " + Integer.toString(player.health) + "HP");
            // Added for player abilities
            String abilityCooldownString = ("Ability Cooldown: " + Integer.toString((int) player.abilityCooldown) + "s");
            String abilityDurationString = ("Ability Duration: " + Integer.toString((int) player.abilityDuration) + "s (Press E to use)");

            progressLabel.setText(progressString);
            healthLabel.setText(healthString);
            // Added for player abilities
            AbilityCooldownLabel.setText(abilityCooldownString);
            AbilityDurationLabel.setText(abilityDurationString);

            table.top().left();
            table.add(progressLabel).pad(10);
            table.row().pad(10);
            table.add(healthLabel).pad(10).left();
            table.row();
            // Added for player abilities
            table.add(AbilityCooldownLabel).pad(10).left();;
            table.row();
            table.add(AbilityDurationLabel).pad(10).left();
            table.row();
            table.add(powerupLabel).pad(10).left();
            // Added to prevent the game from crashing, Stops the stage from being used after it is disposed
            if (stage != null) {
            	stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            	stage.draw();
            }
            
            if (zombiesRemaining == 0) {
            	// Spawn a power up and the end of a wave, if there isn't already a powerUp on the level
            	// Moved power up logic so a power up always spawns at the end of the wave
                if (currentPowerUp == null) {
                    int random = (int )(Math.random() * 5 + 1);
                    if (random == 1) {
                        currentPowerUp = new PowerUpHeal(this);
                    } else if (random == 2){
                        currentPowerUp = new PowerUpSpeed(this);
                    } else if (random == 3){
                        currentPowerUp = new PowerUpImmunity(this);
                    } else if (random == 4){
                    	// added for extra power ups
                        currentPowerUp = new PowerUpInstaKill(this);
                    } else {
                    	// added for extra power ups
                        currentPowerUp = new PowerUpNoCooldowns(this);
                    }
                }

                // Wave complete, increment wave number
                currentWave++;
                if (currentWave > waves.length) {
                    // Level completed, back to select screen and complete stage.
                    // If stage is being replayed complete() will stop progress being incremented.
                    isPaused = true;
                    complete();
                    if (parent.progress == parent.COMPLETE) {
                        parent.setScreen(new TextScreen(parent, "Game completed."));
                        dispose();
                    } else {
                        parent.setScreen(new TextScreen(parent, "Level completed."));
                        dispose();
                    }
                } else {
                    // Update zombiesRemaining with the number of zombies of the new wave
                    zombiesRemaining = waves[currentWave - 1];
                    zombiesToSpawn = zombiesRemaining;
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // Resize the camera depending the size of the window.
        camera.viewportHeight = height;
        camera.viewportWidth = width;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        skin.dispose();
        // Added to prevent the game from crashing, Stops the stage from being used after it is disposed
        if (stage != null) {
            stage.dispose();
            stage = null;
        }
        map.dispose();
        if (currentPowerUp != null) {
            currentPowerUp.getTexture().dispose();
        }
        player.getTexture().dispose();
        for (Character zombie : aliveZombies) {
            zombie.getTexture().dispose();
        }
        // Added to prevent the game from crashing, Stops the renderer from being used after it is disposed
        if (renderer != null) {
            renderer.dispose();
        	renderer = null;
        }
    }
}
