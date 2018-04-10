//package gameplayer.screen;
//
//import gameplayer.panel.TowerPanel;
//import gameplayer.panel.UpgradePanel;
//import gameplayer.panel.GamePanel;
//import gameplayer.panel.ScorePanel;
//import gameplayer.panel.BuyPanel;
//import gameplayer.panel.ControlsPanel;
//import frontend.PromptReader;
//import frontend.Screen;
//import frontend.UIFactory;
//import frontend.View;
//import gameplayer.ScreenManager;
//
//
//import javafx.scene.Parent;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.VBox;
//
//
//
//public class GameScreen extends Screen {
//
//    //TODO delete this and re-factor to abstract
//    private  final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";
//    private  final String DEFAULT_ENGINE_STYLESHEET = "styling/EngineFrontEnd.css";
// 
//
//    private final UIFactory UIFACTORY;
//    private final PromptReader PROMPTS;
//    private TowerPanel TOWER_PANEL;
//    private GamePanel GAME_PANEL;
//    private ScorePanel SCORE_PANEL;
//    private ControlsPanel CONTROLS_PANEL;
//    private UpgradePanel UPGRADE_PANEL;
//    private ScreenManager SCREEN_MANAGER;
//    private BuyPanel BUY_PANEL;
//    
//
//    public GameScreen(ScreenManager ScreenController, PromptReader promptReader) {
//        SCREEN_MANAGER = ScreenController;
//        UIFACTORY = new UIFactory();
//        PROMPTS = promptReader;
//
//    }
//
//    @Override
//    public Parent makeScreenWithoutStyling() {
//        BorderPane rootPane = new BorderPane();
//        TOWER_PANEL = new TowerPanel(this, PROMPTS);
//        CONTROLS_PANEL = new ControlsPanel(this);
//        SCORE_PANEL = new ScorePanel(this);
//        GAME_PANEL = new GamePanel(this);
//        UPGRADE_PANEL = new UpgradePanel(this, PROMPTS);
//        BUY_PANEL = new BuyPanel(this, PROMPTS);
//        
//        
//        
//        VBox rightPane = new VBox(TOWER_PANEL.getPanel(), BUY_PANEL.getPanel());
//        VBox.setVgrow(TOWER_PANEL.getPanel(), Priority.ALWAYS);
//        
//        BorderPane leftPane = new BorderPane();
//        leftPane.setMaxWidth(Double.MAX_VALUE);
//        leftPane.setMaxHeight(Double.MAX_VALUE);
//
//        leftPane.setTop(SCORE_PANEL.getPanel());
//        leftPane.setCenter(GAME_PANEL.getPanel());
//        leftPane.setBottom(UPGRADE_PANEL.getPanel());
//
//        rootPane.setId("gameScreenRoot"); //Where is this set up / where does it get the gameScreenRoot from?
//        rootPane.setCenter(leftPane);
//        rootPane.setRight(rightPane);
//        
//        rootPane.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);
//        rootPane.getStylesheets().add(DEFAULT_ENGINE_STYLESHEET);
//        return rootPane;
//    }
//    
//    public void towerSelectedForPlacement(String towerPropName) {
//	GAME_PANEL.towerSelected(towerPropName);
//    }
//    
//    public Integer getMoney() {
//	//TODO call ObserveHandler.triggerEvent(NeedMoney) to get money sent from playState
//	/**
//	 * also might implement money tracking by passing Integer object of 
//	 * currency from playState in initialization of GameScreen/TowerPanel
//	 * 	-if this is the case this method isn't needed and an updateCurrency Method 
//	 * 	should instead be called in towerPanel upon any action which would spend currency 
//	 */
//	Integer money = 1000; //placeholder
//	return money;
//    }
//    
//    //TODO make this method do things
//    public void controlHit(String control) {
//	if(control.equals("play")) {
//	    //do play stuff
//	    System.out.println(control);
//	}
//	else if(control.equals("pause")) {
//	    //do pause stuff
//	    System.out.println(control);
//	}
//	else
//	    System.out.println(control);
//    }
//
//	@Override
//	protected View getView() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//
//}
