package engine;


import java.util.List;

import authoring.AuthoringModel;
import controller.PlayController;
import engine.sprites.FrontEndSprite;
import engine.sprites.towers.FrontEndTower;
import engine.sprites.towers.Tower;
import gameplayer.ScreenManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import xml.AuthoringModelReader;
import xml.PlayLoader;
import xml.PlaySaverWriter;
import xml.XMLFactory;

/**
 * This class serves as a bridge between the front end, back end, and file I/O of our game player
 * Each of these areas holds an instance of its corresponding class
 * 
 * Mediator has a method for every event that can occur in the game. These methods delegate tasks to other classes that are more specialized for handling
 * those behaviors.
 * 
 * @author benauriemma 4/5
 * @author andrewarnold
 * @author Brendan Cheng
 * @author Alexi Kontos
 *
 */
public class Mediator {

    private ScreenManager myScreenManager;
    private GameEngine myGameEngine;
    private PlayController myPlayController;

//    private ObservableList<Tower> placedTowers = FXCollections.observableArrayList();
//    private ObservableList<Tower> availableTowers = FXCollections.observableArrayList();
//    private ObservableValue<Integer> gameSpeed;
//    private ObservableValue<Integer> level;
//    private ObservableValue<Integer> difficulty;
//    private ObservableValue<Tower> placeTower;
//    private ObservableValue<Boolean> saveFileAvailable;
//    private ObservableValue<Boolean> loadGameFromFile;

    /**
     * Constructs Mediator object and sets all fields to null.
     * Before class is used, setGameEngine and setScreenManager methods should be called to set appropriate instance variables
     */
    public Mediator() {
	myScreenManager = null;
	myGameEngine = null;
	myPlayController = null;
//	loadGameFromFile = new ReadOnlyObjectWrapper<>(false);
//	saveFileAvailable = new ReadOnlyObjectWrapper<>(false);
    }

    
    /************************************************ SETUP ********************************************/
    
    /**
     * Sets ScreenManager
     * @param sm	ScreenManager to be set
     */
    public void setScreenManager(ScreenManager sm) {
	myScreenManager = sm;
	
    }
    
    /**
     * Sets GameEngine
     * @param ge	GameEngine to be set
     */
    public void setGameEngine(GameEngine ge) {
	myGameEngine = ge;
    }
    
    /**
     * Sets PlayController
     * @param pc	The PlayController to be set
     */
    public void setPlayController(PlayController pc) {
	myPlayController = pc;
    }
    
    /************************************************ FILE I/O ********************************************/
    
    /**
     * Starts a new play given a path to an AuthoringModel. To be called when a user chooses a file on the front end to start a new Play
     * @param filename	name of file
     */
    public void startPlay(String filename) {
	myPlayController.newPlay(filename);
    }
    
    /**
     * Saves current state of game in xml file
     * @param filename	String representing name of file. Path and ".xml" are handled by method in XML package.
     */
    public void savePlay(String filename) {
	PlaySaverWriter p = (PlaySaverWriter) XMLFactory.generateWriter("PlaySaverWriter");
	p.write(myGameEngine.getPlayState(), filename);
    }
    
    /**
     * Loads a saved Play from an xml file
     * @param filename	name of file where Play is saved
     * @return PlayState object containing data from xml file
     */
    public PlayState loadPlay(String filename) {
	PlayLoader p = (PlayLoader) XMLFactory.generateReader("PlayLoader");
	return p.createModel(filename);
    }
    
    /************************************************ GAMEPLAY ********************************************/
    
    /**
     * To be called by the backend any time a projectile or enemy should be added to the screen
     * @param sprite is the projectile or enemy to be added, cast as a FrontEndSprite
     */
    public void addSpriteToScreen(FrontEndSprite sprite) {
	myScreenManager.display(sprite);
    }
    
    /**
     * to be called by the backend any time a projectile or enemy should be removed to the screen
     * @param sprite is the projectile or enemy to be removed, cast as a FrontEndSprite
     */
    public void removeSpriteFromScreen(FrontEndSprite sprite) {
	myScreenManager.remove(sprite);
    }
    
    public void setAvailableTowers(List<FrontEndTower> availableTowers) {
	myScreenManager.setAvailableTowers(availableTowers);
    }
    

    // a whole slew of other methods
    // but fr there should be a method for every event that can occur



}
