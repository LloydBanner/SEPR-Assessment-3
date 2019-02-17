package com.geeselightning.zepr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Color;

public class SelectLevelScreen implements Screen {

    private Zepr parent;
    private Stage stage;
    int checkd=0, checkc=0, checkl=0;
    private Label stageDescription;
    private Label difficultyDescription;
    private Label characterDescription;
    private int stageLink = -1;
    private boolean playerSet = false;
    Player player = Player.getInstance();

    public SelectLevelScreen(Zepr zepr) {

        parent = zepr;

        // The stage is the controller which will react to inputs from the user.
        this.stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        // Send any input from the user to the stage.
        Gdx.input.setInputProcessor(stage);

        // Importing the necessary assets for the button textures.
        Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
        
        // Creating difficulty buttons.
        TextButton easy = new TextButton("Easy", skin);
        TextButton intermediate = new TextButton("Intermediate", skin);
        TextButton hard = new TextButton("Hard", skin);
        easy.setTransform(true);
        intermediate.setTransform(true);
        hard.setTransform(true);
        easy.setScale(0.7f);
        intermediate.setScale(0.7f);
        hard.setScale(0.7f);
        
        // Creating difficulty descriptions
        final String easyDescription = "Less zombies and easier to kill.";
        final String intermediateDescription = "More zombies and you have less health.";
        final String hardDescription = "DEATH!";
        final String defaultdifficultyDescription ="Select a difficulty from the buttons above.";
        difficultyDescription = new Label(defaultdifficultyDescription, skin);
        difficultyDescription.setWrap(true);
        difficultyDescription.setWidth(100);
        difficultyDescription.setAlignment(Align.center);

        // Creating stage buttons.
        TextButton town = new TextButton("Town", skin);
        TextButton halifax = new TextButton("Halifax", skin);
        TextButton courtyard = new TextButton("Courtyard", skin);
        // Added buttons for new levels
        TextButton library = new TextButton("Library", skin);
        TextButton physics = new TextButton("Physics", skin);
        TextButton centralHall = new TextButton("Central Hall", skin);
        town.setTransform(true);
        halifax.setTransform(true);
        courtyard.setTransform(true);
        library.setTransform(true);
        physics.setTransform(true);
        centralHall.setTransform(true);
        town.getLabel().setFontScale(0.8f);
        halifax.getLabel().setFontScale(0.8f);
        courtyard.getLabel().setFontScale(0.8f);
        centralHall.getLabel().setFontScale(0.8f);
        physics.getLabel().setFontScale(0.8f);
        library.getLabel().setFontScale(0.8f);
        town.setScale(0.7f);
        halifax.setScale(0.7f);
        courtyard.setScale(0.7f);
        library.setScale(0.7f);
        physics.setScale(0.7f);
        centralHall.setScale(0.7f);

        // Creating character buttons.
        TextButton nerdy = new TextButton("Nerdy",skin);
        TextButton sporty = new TextButton("Sporty",skin);
        // Added for third player type
        TextButton drama = new TextButton("Drama",skin);
        nerdy.setTransform(true);
        sporty.setTransform(true);
        drama.setTransform(true);
        nerdy.setScale(0.7f);
        sporty.setScale(0.7f);
        drama.setScale(0.7f);
     
        // Creating other buttons.
        final TextButton play = new TextButton("Play", skin);
        TextButton save = new TextButton("Save", skin);
        TextButton load = new TextButton("Load", skin);
        TextButton back = new TextButton("Back", skin);
        TextButton bonusGame = new TextButton("Bonus Game", skin);
        save.setTransform(true);
        load.setTransform(true);
        back.setTransform(true);
        bonusGame.setTransform(true);
        play.setTransform(true);
        play.setScale(0.8f);
        save.setScale(0.6f);
        load.setScale(0.6f);
        back.setScale(0.6f);
        bonusGame.setScale(0.6f);

        // Creating stage descriptions.
        Label title = new Label("Choose a stage and character.", skin, "subtitle");
        final String townDescription = "You wake up hungover in town to discover there is a zombie apocalypse.";
        final String halifaxDescription = "You need to get your laptop with the work on it from your accomodation.";
        final String courtyardDescription = "You should go to Courtyard and get some breakfast.";
        // Added descriptions for new levels
        final String libraryDescription = "Take a break from the zombies to study.";
        final String physicsDescription = "You go to Physics to try and find something to help stop the zombies.";
        final String centralHallDescription = "Stop the source of the zombie horde by beating the boss in Central hall.";
        final String lockedDescription = "This stage is locked until you complete the previous one.";
        final String defaultDescription ="Select a stage from the buttons above.";
        stageDescription = new Label(defaultDescription, skin);
        stageDescription.setWrap(true);
        stageDescription.setWidth(100);
        stageDescription.setAlignment(Align.center);

        // Creating character descriptions.
        // Changed descriptions to incorporate player abilities
        final String nerdyDescription = "Construct a mech suit for yourself so you can take more hits. Ability: Power Punch (increases attack briefly)";
        final String sportyDescripton = "Work out so you run faster. Ability: Sprint (run quickly for a short time)";
        // Added for third player type
        final String dramaDescripton = "Borrow a sword prop from the drama department. Ability: Fake Damage (restores 10HP)";
        final String defaultCharacterDescription = "Select a type of student from the buttons above.";
        characterDescription = new Label(defaultCharacterDescription,skin);
        characterDescription.setWrap(true);
        characterDescription.setWidth(100);
        characterDescription.setAlignment(Align.center);

        // Adding menu bar.
        Table menuBar = new Table();
        menuBar.setFillParent(true);
        // menuBar.setDebug(true); // Adds borders for the table.
        stage.addActor(menuBar);

        menuBar.top().left();
        menuBar.row();
        menuBar.add(back);
        menuBar.add(save);
        menuBar.add(load);
        menuBar.add(bonusGame);

        // Adding stage selector buttons.
        Table stageSelect = new Table();
        stageSelect.setFillParent(true);
        // stageSelect.setDebug(true); // Adds borders for the table.
        stage.addActor(stageSelect);

        stageSelect.row().center();
        stageSelect.add(title).colspan(6).pad(40).padTop(110);
        
        //Adding difficulty buttons
        stageSelect.row().pad(-25,0,10,0);
        stageSelect.add(easy);
        stageSelect.add(intermediate);
        stageSelect.add(hard);  
        
        stageSelect.row().center();
        stageSelect.add(difficultyDescription).width(1000f).colspan(3);
        
        stageSelect.row().pad(10,0,10,0);
        stageSelect.add(town).size(210, 100).padRight(-30);
        stageSelect.add(halifax).size(210, 100).padLeft(-290);
        stageSelect.add(courtyard).size(240, 100).padLeft(-610);
        // Added buttons for new levels
        stageSelect.add(library).size(210, 100).padLeft(-530);
        stageSelect.add(physics).size(210, 100).padLeft(-190);
        stageSelect.add(centralHall).size(280, 100).padLeft(-30);

        stageSelect.row().center();
        stageSelect.add(stageDescription).width(1000f).colspan(3);

        // Adding select character Buttons
        stageSelect.row().pad(10,0,10,0);
        stageSelect.add(nerdy);
        stageSelect.add(sporty);
        // Added to support Third Player type
        stageSelect.add(drama);
       
        stageSelect.row().center();
        stageSelect.add(characterDescription).width(1000f).colspan(3);

        // Adding play button at the bottom.
        Table bottomTable = new Table();
        bottomTable.setFillParent(true);
        // bottomTable.setDebug(true); // Adds borders for the table.
        stage.addActor(bottomTable);

        bottomTable.bottom().right();
        bottomTable.add(play).pad(10).center();
        play.setColor(Color.DARK_GRAY);
        play.getLabel().setColor(Color.DARK_GRAY);

        // Adding button logic.

        // Defining actions for the back button.
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Zepr.MENU);
            }
        });
        
        // Added to implement saving requirement
        save.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	try {
            		Writer writer = new FileWriter("save.txt", false);
            		writer.write(Integer.toString(parent.progress));
            		writer.close();
            	} catch (IOException e) {
					e.printStackTrace();
            	}
            }
        });

        // Added to implement saving requirement
        load.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	try {
                	File file = new File("save.txt");
                	Scanner scanner = new Scanner(file);
            		int progress = scanner.nextInt();
            		scanner.close();
            		parent.progress = progress;
            		// Reloads page to update buttons
                    parent.changeScreen(Zepr.SELECT);
            	} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
            }
        });
        
        // Added to change the screen to the minigame
        bonusGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Zepr.BONUS);
            }
        });
        
        //Defining actions for the easy button.
        easy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficultyDescription.setText(easyDescription);
                Constant.difficulty=1;
                checkd=1;
                //Check if play button can be pressed
                if (checkd==1 && checkl==1 && checkc==1) {              
                	play.setColor(Color.WHITE);
                    play.getLabel().setColor(Color.WHITE);
                }
            }
        });
        
        //Defining actions for the intermediate button.
        intermediate.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficultyDescription.setText(intermediateDescription);
                Constant.difficulty=2;
                //Change player stats according to difficulty
                Constant.PLAYERMAXHP=80;
                Constant.PLAYERDMG=22;
                checkd=1;
                if (checkd==1 && checkl==1 && checkc==1) {               
                	play.setColor(Color.WHITE);
                    play.getLabel().setColor(Color.WHITE);
                }
            }
        });
        
        //Defining actions for the hard button.
        hard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                difficultyDescription.setText(hardDescription);
                Constant.difficulty=3;
                Constant.PLAYERMAXHP=60;                                   
                Constant.PLAYERDMG=20;
                checkd=1;
                if (checkd==1 && checkl==1 && checkc==1) {                 
                	play.setColor(Color.WHITE);
                    play.getLabel().setColor(Color.WHITE);
                }
            }
        });


        // Defining actions for the town button.
        town.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stageDescription.setText(townDescription);
                stageLink = Zepr.TOWN;
                checkl=1;
                if (checkd==1 && checkl==1 && checkc==1) {              
                	play.setColor(Color.WHITE);
                    play.getLabel().setColor(Color.WHITE);
                }
            }
        });

        if (parent.progress <= parent.TOWN) {
            halifax.setColor(Color.DARK_GRAY);
            halifax.getLabel().setColor(Color.DARK_GRAY);
        } else {
            // Defining actions for the halifax button.
            halifax.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    stageDescription.setText(halifaxDescription);
                    stageLink = Zepr.HALIFAX;
                    checkl=1;
                    if (checkd==1 && checkl==1 && checkc==1) {               
                    	play.setColor(Color.WHITE);
                        play.getLabel().setColor(Color.WHITE);
                    }
                }
            });
        }

        if (parent.progress <= parent.HALIFAX) {
            courtyard.setColor(Color.DARK_GRAY);
            courtyard.getLabel().setColor(Color.DARK_GRAY);
        } else {
            // Defining actions for the courtyard button.
            courtyard.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    stageDescription.setText(courtyardDescription);
                    stageLink = Zepr.COURTYARD;
                    checkl=1;
                    if (checkd==1 && checkl==1 && checkc==1) {               
                    	play.setColor(Color.WHITE);
                        play.getLabel().setColor(Color.WHITE);
                    }
                }
            });
        }
        
        // Added for LibraryLevel
        if (parent.progress <= parent.COURTYARD) {
            library.setColor(Color.DARK_GRAY);
            library.getLabel().setColor(Color.DARK_GRAY);
        } else {
            // Defining actions for the courtyard button.
            library.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    stageDescription.setText(libraryDescription);
                    stageLink = Zepr.LIBRARY;
                    checkl=1;
                    if (checkd==1 && checkl==1 && checkc==1) {              
                    	play.setColor(Color.WHITE);
                        play.getLabel().setColor(Color.WHITE);
                    }
                }
            });
        }
        
        // Added for physicsLevel
        if (parent.progress <= parent.LIBRARY) {
            physics.setColor(Color.DARK_GRAY);
            physics.getLabel().setColor(Color.DARK_GRAY);
        } else {
            // Defining actions for the courtyard button.
        	physics.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    stageDescription.setText(physicsDescription);
                    stageLink = Zepr.PHYSICS;
                    checkl=1;
                    if (checkd==1 && checkl==1 && checkc==1) {               
                    	play.setColor(Color.WHITE);
                        play.getLabel().setColor(Color.WHITE);
                    }
                }
            });
        }
        
        // Added for CentralHallLevel
        if (parent.progress <= parent.PHYSICS) {
            centralHall.setColor(Color.DARK_GRAY);
            centralHall.getLabel().setColor(Color.DARK_GRAY);
        } else {
            // Defining actions for the courtyard button.
        	centralHall.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    stageDescription.setText(centralHallDescription);
                    stageLink = Zepr.CENTRALHALL;
                    checkl=1;
                    if (checkd==1 && checkl==1 && checkc==1) {              
                    	play.setColor(Color.WHITE);
                        play.getLabel().setColor(Color.WHITE);
                    }
                }
            });
        }

        //Defining actions for the nerdy button.
        nerdy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                characterDescription.setText(nerdyDescription);
                player.setType("nerdy");
                playerSet = true;
                checkc=1;
                if (checkd==1 && checkl==1 && checkc==1) {
                	play.setColor(Color.WHITE);
                    play.getLabel().setColor(Color.WHITE);
                }
            }
        });
        sporty.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                characterDescription.setText(sportyDescripton);
                player.setType("sporty");
                playerSet = true;
                checkc=1;
                if (checkd==1 && checkl==1 && checkc==1) {
                	play.setColor(Color.WHITE);
                    play.getLabel().setColor(Color.WHITE);
                }
            }
        });
        // Added to support third player type
        drama.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                characterDescription.setText(dramaDescripton);
                player.setType("drama");
                playerSet = true;
                checkc=1;
                if (checkd==1 && checkl==1 && checkc==1) {
                	play.setColor(Color.WHITE);
                    play.getLabel().setColor(Color.WHITE);
                }
            }
        });

        // Defining actions for the play button.
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if ((stageLink != -1) && (playerSet == true)) {
                    parent.changeScreen(stageLink);
                }
            }
        });

    }

    @Override
    public void render(float delta) {
        // Clears the screen to black.
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draws the stage.
        this.stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Update the screen when the window resolution is changed.
        this.stage.getViewport().update(width, height, true);
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
        // Dispose of assets when they are no longer needed.
        stage.dispose();
    }

}
