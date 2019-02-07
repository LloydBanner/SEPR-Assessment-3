/**
 * Added by Shaun of the Devs to meet the requirement of a minigame
 */
package com.geeselightning.zepr;

import javax.swing.Renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class BonusLevel implements Screen {

	protected Zepr parent;
    private Stage stage;
    private Stage updateStage;
    private SpriteBatch renderer;
    private int score;
    private int goalScore;
    private Table gameInfo;
    private Label scoreLabel;
    private Label goalLabel;
    private BonusGoose goose1;
    private BonusGoose goose2;
    private BonusGoose goose3;
    private int target1X = (1280/2 - 720/2) + 80;
    private int target2X = (1280/2 - 100/2); 
    private int target3X = (1280/2 + 720/2) - 180;
    private int targetY = 720/4 + 210;
	
	public BonusLevel(Zepr zepr) {
		this.parent = zepr;

        // The stage is the controller which will react to inputs from the user.
        this.stage = new Stage(new ScreenViewport());
        this.updateStage = new Stage(new ScreenViewport());
        
        // Score to win minigame
        this.goalScore = Constant.BONUSGOAL;
        
        // For table to display score
        gameInfo = new Table();
        
        // To render sprites for the game.
        renderer = new SpriteBatch();
        
        // Geese settings
        goose1 = new BonusGoose(1, target1X + 100, targetY - 50);
        goose2 = new BonusGoose(2, target2X + 100, targetY + 20);
        goose3 = new BonusGoose(3, target3X + 100, targetY + 100);
	}
	
	@Override
	public void show() {
        // Send any input from the user to the stage.
        Gdx.input.setInputProcessor(stage);

        // Importing the necessary assets for the button textures.
        Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
        
        // Adding buttons to the screen
        TextButton back = new TextButton("Back", skin);

        TextButton right = new TextButton("Right", skin);
        TextButton middle = new TextButton("Middle", skin);
        TextButton left = new TextButton("Left", skin);
        
        Label fire = new Label("Fire Cannon", skin, "subtitle");
        fire.setWrap(true);
        fire.setWidth(100);
        fire.setAlignment(Align.center);
        
        // Adding menu bar.
        Table menuBar = new Table();
        menuBar.setFillParent(true);
        // menuBar.setDebug(true); // Adds borders for the table.
        stage.addActor(menuBar);

        menuBar.top().left();
        menuBar.row();
        menuBar.add(back).pad(10);
        
        // Adding fire buttons at the bottom.
        Table bottomTable = new Table();
        bottomTable.setFillParent(true);
        // bottomTable.setDebug(true); // Adds borders for the table.
        stage.addActor(bottomTable);
        
        bottomTable.bottom();
        bottomTable.row();
        bottomTable.add(fire).width(1000f).colspan(3);
        bottomTable.row().center();
        bottomTable.add(left).pad(10);
        bottomTable.add(middle).pad(10);
        bottomTable.add(right).pad(10);
        
        // Defining actions for the back button.
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Zepr.SELECT);
            }
        });
        
        // Defining actions for the left button.
        left.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	if (goose1.getX() < target1X + 80 && goose1.getX() > target1X - 80) {
            		if (goose1.getY() < targetY + 80 && goose1.getY() > targetY - 30) {
                        score += 1;
            			goose1.respawn();
            		}
            	}
            }
        });
        
        // Defining actions for the middle button.
        middle.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	if (goose2.getX() < target2X + 80 && goose2.getX() > target2X - 80) {
            		if (goose2.getY() < targetY + 80 && goose2.getY() > targetY - 30) {
                        score += 1;
            			goose2.respawn();
            		}
            	}
            }
        });
        
        // Defining actions for the right button.
        right.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	if (goose3.getX() < target3X + 80 && goose3.getX() > target3X - 80) {
            		if (goose3.getY() < targetY + 80 && goose3.getY() > targetY - 30) {
                        score += 1;
            			goose3.respawn();
            		}
            	}
            }
        });
        
		
	}

	@Override
	public void render(float delta) {
        // Clears the screen to black.
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        // Rendering for sprites
        renderer.begin();
        
        // Behind geese
        renderer.draw(new Texture("gooseHuntBackground.png"), (1280/2 - 720/2), 720/4);
        
        // Geese
        goose1.draw(renderer);
        goose2.draw(renderer);
        goose3.draw(renderer);
        
        // In front of geese
        renderer.draw(new Texture("cannonLeft.png"), (1280/2 - 720/2) + 100, 720/4 + 10);
        renderer.draw(new Texture("cannonMiddle.png"), (1280/2 - 100/2), 720/4 + 10);
        renderer.draw(new Texture("cannonRight.png"), (1280/2 + 720/2) - 200, 720/4 + 10);
        renderer.draw(new Texture("target.png"), target1X, targetY);
        renderer.draw(new Texture("target.png"), target2X, targetY);
        renderer.draw(new Texture("target.png"), target3X, targetY);
        
        renderer.end();

        // Importing the necessary assets for the button textures.
        Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
        
        // Creating game labels for gameInfo
        String scoreString = ("Score: " + score);
        String goalString = ("Goal: " + goalScore);
        Label title = new Label("Goose Hunt", skin, "subtitle");
        scoreLabel = new Label(scoreString, skin);
        goalLabel = new Label(goalString, skin);
        
        // Adding game information
        gameInfo.clear();
        gameInfo.setFillParent(true);
        // menuBar.setDebug(true); // Adds borders for the table.
        updateStage.addActor(gameInfo);
        
        gameInfo.center().top();
        gameInfo.add(title).pad(30);
        gameInfo.add(scoreLabel).pad(10);
        gameInfo.add(goalLabel).pad(10);

        // Draws the stage.
        this.updateStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        this.updateStage.draw();
        this.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        this.stage.draw();
        
        if (score == goalScore) {
            parent.setScreen(new TextScreen(parent, "Bonus game completed."));
        }
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
		this.dispose();
		
	}

	@Override
	public void dispose() {
		stage.clear();
		stage.dispose();
		goose1.dispose();
		goose2.dispose();
		goose3.dispose();
	}

}
