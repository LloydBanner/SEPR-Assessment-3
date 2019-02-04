/**
 * Added by Shaun of the Devs to meet the requirement of a minigame
 */
package com.geeselightning.zepr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
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
    private int score;
    private int goalScore;
    private Table gameInfo;
    private Label scoreLabel;
    private Label goalLabel;
	
	public BonusLevel(Zepr zepr) {
		this.parent = zepr;

        // The stage is the controller which will react to inputs from the user.
        this.stage = new Stage(new ScreenViewport());
        
        // Score to win minigame
        this.goalScore = Constant.BONUSGOAL;
        
        //for table to display score
        gameInfo = new Table();
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
        // Clears the screen to black.
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        
        // Creating game labels for gameInfo
        String scoreString = ("Score: " + score);
        String goalString = ("Goal: " + goalScore);
        Label title = new Label("Goose Hunt", skin, "subtitle");
        scoreLabel = new Label(scoreString, skin);
        goalLabel = new Label(goalString, skin);
        
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
        
        // Adding game information
        gameInfo.clear();
        gameInfo.setFillParent(true);
        // menuBar.setDebug(true); // Adds borders for the table.
        stage.addActor(gameInfo);
        
        gameInfo.center().top();
        gameInfo.add(title).pad(30);
        gameInfo.add(scoreLabel).pad(10);
        gameInfo.add(goalLabel).pad(10);
        
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
                score += 1;
            }
        });
        
        // Defining actions for the middle button.
        middle.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                score += 1;
            }
        });
        
        // Defining actions for the right button.
        right.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                score += 1;
            }
        });

        // Draws the stage.
        this.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        this.stage.draw();
        
        if (score == goalScore) {
            parent.setScreen(new TextScreen(parent, "Bonus game completed."));
        }
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
