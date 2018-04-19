package gameplayer.screen;

import authoring.AuthoringModel;
import authoring.frontend.FrontendLauncherForTesting;
import authoring.frontend.exceptions.MissingPropertiesException;
import controller.PlayController;
import engine.Settings;
import gameplayer.panel.*;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import authoring.AuthoringController;
import authoring.AuthoringModel;
import authoring.frontend.exceptions.MissingPropertiesException;
import engine.Mediator;
import engine.sprites.FrontEndSprite;
import engine.sprites.towers.CannotAffordException;
import engine.sprites.towers.FrontEndTower;
import frontend.PromptReader;
import frontend.Screen;
import frontend.StageManager;
import frontend.UIFactory;
import frontend.View;
import gameplayer.ScreenManager;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;



public class GameScreen extends Screen {

	//TODO delete this and re-factor to abstract
	private final String DEFAULT_SHARED_STYLESHEET = "styling/theme1.css";
	private final String DEFAULT_ENGINE_STYLESHEET = "styling/EngineFrontEnd.css";

	private final UIFactory UIFACTORY;
	private final PromptReader PROMPTS;
	private TowerPanel TOWER_PANEL;
	private TowerInfoPanel TOWER_INFO_PANEL;
	private GamePanel GAME_PANEL;
	private ScorePanel SCORE_PANEL;
	private ControlsPanel CONTROLS_PANEL;
	private UpgradePanel UPGRADE_PANEL;
	private ScreenManager SCREEN_MANAGER;
	private BuyPanel BUY_PANEL;
	private SettingsPanel SETTINGS_PANEL;
	private VBox displayPane;
	private BorderPane gamePane;
	private final Mediator MEDIATOR;
	private BorderPane rootPane;

	public GameScreen(ScreenManager ScreenController, PromptReader promptReader, Mediator mediator) {
		SCREEN_MANAGER = ScreenController;
		UIFACTORY = new UIFactory();
		PROMPTS = promptReader;
		MEDIATOR = mediator;
		TOWER_PANEL = new TowerPanel(this, PROMPTS);
		CONTROLS_PANEL = new ControlsPanel(this, PROMPTS);
		SCORE_PANEL = new ScorePanel(this);
		GAME_PANEL = new GamePanel(this);
		UPGRADE_PANEL = new UpgradePanel(this, PROMPTS);
		BUY_PANEL = new BuyPanel(this, PROMPTS);
		SETTINGS_PANEL = new SettingsPanel(this, PROMPTS);
	}

	@Override
	public Parent makeScreenWithoutStyling() {
		rootPane = new BorderPane();

		displayPane = new VBox(TOWER_PANEL.getPanel(), CONTROLS_PANEL.getPanel());
		VBox.setVgrow(TOWER_PANEL.getPanel(), Priority.ALWAYS);

		gamePane = new BorderPane();
		gamePane.setMaxWidth(Double.MAX_VALUE);
		gamePane.setMaxHeight(Double.MAX_VALUE);


		gamePane.setTop(SCORE_PANEL.getPanel());
		gamePane.setCenter(GAME_PANEL.getPanel());
		//leftPane.setBottom(UPGRADE_PANEL.getPanel());

		rootPane.setId("gameScreenRoot"); //Where is this set up / where does it get the gameScreenRoot from?
		rootPane.setCenter(gamePane);
		setVertPanelsLeft();

		rootPane.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);
		rootPane.getStylesheets().add(DEFAULT_ENGINE_STYLESHEET);
		return rootPane;
	}

	public void towerSelectedForPlacement(FrontEndTower tower) {
		GAME_PANEL.towerSelected(tower);
	}

	public Integer getMoney() {
		//TODO call ObserveHandler.triggerEvent(NeedMoney) to get money sent from playState
		/**
		 * also might implement money tracking by passing Integer object of
		 * currency from playState in initialization of GameScreen/TowerPanel
		 * 	-if this is the case this method isn't needed and an updateCurrency Method
		 * 	should instead be called in towerPanel upon any action which would spend currency
		 */
		Integer money = 0; //placeholder
		return money;
	}


	@Override
	protected View getView() {
		// TODO Auto-generated method stub
		return null;
	}

	public void displaySprite(FrontEndSprite sprite) {
		GAME_PANEL.addSprite(sprite);
	}

	public void remove(FrontEndSprite sprite) {
		GAME_PANEL.removeSprite(sprite);
	}

	public void setAvailbleTowers(List<FrontEndTower> availableTowers) {
		TOWER_PANEL.setAvailableTowers(availableTowers);
	}

	public void loadErrorScreen(String message) {
		SCREEN_MANAGER.loadErrorScreen(message);
	}

	//TODO implement reflection//rest of controls
	public void controlTriggered(String control) {
		if(control.equals("play"))
			MEDIATOR.play();
		else if(control.equals("pause"))
			MEDIATOR.pause();
		else if(control.equals("speedup"))
			MEDIATOR.fastForward(10);
		else if(control.equals("quit")) //WHY DO I HAVE TO MAKE A NEW PLAY-CONTROLLER OH MY GOD
			try {
				new PlayController(SCREEN_MANAGER.getStageManager(), DEFAULT_LANGUAGE, new AuthoringModel())
						.loadInstructionScreen();
			} catch (MissingPropertiesException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else if (control.equals("edit")) { // Susie added this
			AuthoringController authoringController = new AuthoringController(SCREEN_MANAGER.getStageManager(), SCREEN_MANAGER.getLanguage());
			authoringController.setModel(SCREEN_MANAGER.getGameFilePath());
		}
		else if (control.equals("settings")) {
			settingsClickedOn();
		}
	}

	public void settingsTriggered(String setting) {
		if (setting.equals("volume")) {

		}
		else if (setting.equals("instructions")) {

		}
		else if (setting.equals("help")) {

		}
	}

	public void updateCurrency(Integer newBalence) {
		TOWER_PANEL.updateCurrency(newBalence);
	}

	public void updateHealth(Integer newHealth) {
		SCORE_PANEL.updateHealth(newHealth);
	}

	public void updateScore(Integer newScore) {
		SCORE_PANEL.updateScore(newScore);
	}

	public void updateLevel(Integer newLevel) {
		SCORE_PANEL.updateLevel(newLevel);
	}

	public FrontEndTower placeTower(FrontEndTower tower, Point position) throws CannotAffordException {
		FrontEndTower placedTower = MEDIATOR.placeTower(position, tower.getName());
		return placedTower;
	}

	public void towerClickedOn(FrontEndTower tower) {
		TOWER_INFO_PANEL = new TowerInfoPanel(this,PROMPTS,tower);
		displayPane.getChildren().clear();
		displayPane.getChildren().addAll(TOWER_PANEL.getPanel(), TOWER_INFO_PANEL.getPanel());
		gamePane.setBottom(UPGRADE_PANEL.getPanel());
	}

	public void upgradeClickedOn() {
		BUY_PANEL = new BuyPanel(this,PROMPTS);
		displayPane.getChildren().clear();
		displayPane.getChildren().addAll(TOWER_PANEL.getPanel(), BUY_PANEL.getPanel());
		gamePane.setBottom(UPGRADE_PANEL.getPanel());
	}

	private void settingsClickedOn() {
		SETTINGS_PANEL = new SettingsPanel(this, PROMPTS);
		displayPane.getChildren().clear();
		displayPane.getChildren().addAll(TOWER_PANEL.getPanel(), SETTINGS_PANEL.getPanel());
	}

	public void blankGamePanelClick() {
		gamePane.setBottom(null);
		displayPane.getChildren().clear();
		displayPane.getChildren().addAll(TOWER_PANEL.getPanel(), CONTROLS_PANEL.getPanel());
	}

	public void sellTower(FrontEndTower tower) {
		GAME_PANEL.removeTower(tower);
		MEDIATOR.sellTower(tower);
		blankGamePanelClick();
	}


	public void setPath(Map<String, List<Point>> imageMap, String backgroundImageFilePath, int pathSize) {
		GAME_PANEL.setPath(imageMap, backgroundImageFilePath, pathSize);
	}

	private void setVertPanelsLeft() {
		rootPane.getChildren().remove(displayPane);
		rootPane.setRight(null);
		rootPane.setLeft(displayPane);

	}
	private void setVertPanelsRight() {
		rootPane.getChildren().remove(displayPane);
		rootPane.setLeft(null);
		rootPane.setRight(displayPane);
	}

	public void swapVertPanel() {
		if(rootPane.getRight() == null) {
			setVertPanelsRight();
		}
		else {
			setVertPanelsLeft();
		}
	}

	public ScreenManager getScreenManager() {
		return SCREEN_MANAGER;
	}


}
