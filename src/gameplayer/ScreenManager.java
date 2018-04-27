package gameplayer;

import authoring.frontend.exceptions.MissingPropertiesException;
import frontend.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.Point;


import engine.Mediator;
import engine.sprites.FrontEndSprite;
import engine.sprites.towers.FrontEndTower;
import gameplayer.screen.GameScreen;
import gameplayer.screen.InstructionScreen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.Parent;

/**
 * 
 * @author Ben Hodgson 4/11/18
 * 
 * Class to manage updating Screen elements that remain across the entire game
 * (score, level, health, currency, etc.)
 */
public class ScreenManager extends View {


	public static final String FILE_ERROR_KEY = "FileErrorPrompt";
	public static final String SCREEN_ERROR_KEY = "ScreenErrorPrompt";
	public static final String DEFAULT_OWN_CSS = "styling/EngineFrontend.css";
	private static final String STARTING_LANGUAGE = "English";


	/**
	 * not sure where we're getting these values to display on the panels and stuff
	 * TALK TO ANDREW ABOUT
	 */

	private String myLanguage;
	private String myGameFilePath;
	private Mediator MEDIATOR;
	private final StageManager STAGE_MANAGER;
	private GameScreen GAME_SCREEN;
	private final PromptReader PROMPTS;
	private PropertiesReader PROP_READ = new PropertiesReader();
	private List<Integer> controlVars;
	private Map<String, String> GAMEPLAYER_PROPERTIES;

	//private final FileIO FILE_READER;

	public ScreenManager(StageManager stageManager, String language, Mediator mediator) {
		super(stageManager, language);
		STAGE_MANAGER = stageManager;
		try {
			GAMEPLAYER_PROPERTIES = PROP_READ.read("src/gameplayer/gameplayer.properties");
		}
		catch (MissingPropertiesException e) {
		}
		PROMPTS = new PromptReader(language, this);
		myLanguage = language;
		MEDIATOR = mediator;
		findSettings();
	}

	public ScreenManager(StageManager stageManager, String language) {
		super(stageManager, language);
		STAGE_MANAGER = stageManager;
		try {
			GAMEPLAYER_PROPERTIES = PROP_READ.read("src/gameplayer/gameplayer.properties");
		}
		catch (MissingPropertiesException e) {
		}
		PROMPTS = new PromptReader(language, this);
		findSettings();
	}

	public List<Integer> getMediatorInts(){
		controlVars = new ArrayList<Integer>();
		for(int i = 0; i < 3; i++) {
			controlVars.add(Integer.valueOf(0));
		}
		return controlVars;
	}

	//TODO set Style sheets
	public void loadInstructionScreen() {
		InstructionScreen instructScreen = new InstructionScreen(this, PROMPTS);
		Parent instructRoot = instructScreen.getScreen();
		STAGE_MANAGER.switchScreen(instructRoot);
	}

	public void loadGameScreenNew(String filepath) {
		setGameFilePath(filepath);
		GAME_SCREEN = new GameScreen(this, PROMPTS, MEDIATOR);
		Parent gameScreenRoot = GAME_SCREEN.getScreen();
		STAGE_MANAGER.switchScreen(gameScreenRoot);
		MEDIATOR.startPlay(filepath);
	}

	public void loadGameScreenNew() {
		GAME_SCREEN = new GameScreen(this, PROMPTS, MEDIATOR);
		Parent gameScreenRoot = GAME_SCREEN.getScreen();
		STAGE_MANAGER.switchScreen(gameScreenRoot);
	}

	public void loadMainScreen() {
		MainScreen mainScreen = new MainScreen(STAGE_MANAGER, this);
	}

	public void loadGameScreenContinuation() {

	}

	//TODO read these in from properties file
	private void findSettings() {
		Double DEFAULT_HEIGHT = Double.parseDouble(GAMEPLAYER_PROPERTIES.get("DefaultHeight"));
		Double DEFAULT_WIDTH = Double.parseDouble(GAMEPLAYER_PROPERTIES.get("DefaultWidth"));
	}

	public void updateLevelCount(Integer newLevelCount) {
		GAME_SCREEN.updateLevel(newLevelCount);
	}

	public void display(FrontEndSprite sprite) {
		GAME_SCREEN.displaySprite(sprite);
	}


	public void toMain() {
		STAGE_MANAGER.switchScreen(new MainScreen(STAGE_MANAGER, this).getScreen());
	}

	public StageManager getStageManager() {
		return STAGE_MANAGER;
	}

	/**
	 * Returns current language
	 * Useful in instantiating new AuthoringController with correct language 
	 * 	when user decides to continue authoring the game
	 * @return String representing the language
	 */
	public String getLanguage() {
		return myLanguage;
	}

	public void setGameFilePath(String filepath) {
		myGameFilePath = filepath; 
	}

	/**
	 * Returns filepath of XML of current game
	 * Useful in instantiating new AuthoringController with correct language 
	 * 	when user decides to continue authoring the game
	 * @return String XML filepath
	 */
	public String getGameFilePath() {
		return myGameFilePath; 
	}

	@Override
	public void loadErrorScreen(String errorMessage) {
		// TODO Auto-generated method stub

	}

	public void remove(FrontEndSprite sprite) {
		GAME_SCREEN.remove(sprite);
	}

	public void setAvailableTowers(List<FrontEndTower> availableTowers) {
		GAME_SCREEN.setAvailbleTowers(availableTowers);
	}



	public void setPath(Map<String, List<Point>> imageMap, String backgroundImageFilePath, int pathSize) {
		GAME_SCREEN.setPath(imageMap, backgroundImageFilePath, pathSize);;
	}

	public void attachListeners(IntegerProperty myCurrency, IntegerProperty myScore,
		IntegerProperty myLives) {
	    GAME_SCREEN.attachListeners(myCurrency, myScore, myLives);	    
	}


	public Map<String,String> getGameplayerProperties() {
		return GAMEPLAYER_PROPERTIES;
	}
}
