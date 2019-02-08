package com.geeselightning.zepr;

import com.badlogic.gdx.Game;

public class Zepr extends Game {

	private LoadingScreen loadingScreen;
	private MenuScreen menuScreen;
	private Level level;
	private BonusLevel bonusLevel;
	private SelectLevelScreen selectLevelScreen;

	// The progress is the integer representing the last level completed. i.e. 3 for Town
	public int progress = 3;

	public final static int MENU = 0;
	public final static int BONUS = 1;
	public final static int SELECT = 2;
	public final static int TOWN = 3;
	public final static int HALIFAX = 4;
	public final static int COURTYARD = 5;
	public final static int LIBRARY = 6;
	public final static int COMPLETE = 7;


	public void changeScreen(int screen) {
		switch(screen) {
			case MENU:
				if (menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case BONUS:
				bonusLevel = new BonusLevel(this);
				this.setScreen(bonusLevel);
				break;
			case SELECT:
				selectLevelScreen = new SelectLevelScreen(this);
				this.setScreen(selectLevelScreen);
				break;
			case TOWN:
				level = new TownLevel(this);
				this.setScreen(level);
				break;
			case HALIFAX:
				level = new HalifaxLevel(this);
				this.setScreen(level);
				break;
			case COURTYARD:
				level = new CourtyardLevel(this);
				this.setScreen(level);
				break;
			case LIBRARY:
				level = new LibraryLevel(this);
				this.setScreen(level);
				break;
		}
	}

	@Override
	public void create() {
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}
}