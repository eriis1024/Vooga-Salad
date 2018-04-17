/**
 * 
 * @author susiechoi 
 * @author Ben Hodgson 4/9/18
 *
 * Class that handles mediating creation of authoring environment objects (towers, enemies, path). 
 * Represents Controller in MVC of the authoring environment. 
 * 
 */

package authoring;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import authoring.frontend.AuthoringView;
import authoring.frontend.exceptions.MissingPropertiesException;
import authoring.frontend.exceptions.NoDuplicateNamesException;
import authoring.frontend.exceptions.ObjectNotFoundException;
import engine.level.Level;
import engine.path.Path;
import engine.sprites.enemies.Enemy;
import engine.sprites.enemies.wave.Wave;
import frontend.StageManager;
import javafx.scene.layout.GridPane;
import xml.AuthoringModelReader;


public class AuthoringController {
	
	private AuthoringView myAuthoringView; 
	private Map<String, List<Point>> myImageMap;
	private AuthoringModel myModel; 

	
	/**
	 * Creates a new AuthoringController
	 * @param stageManager contains Stage game is being authored on
	 * @param languageIn is chosen language for prompts
	 */
	public AuthoringController(StageManager stageManager, String languageIn) {
		myAuthoringView = new AuthoringView(stageManager, languageIn, this);
		try {
			myModel = new AuthoringModel();
		} catch (MissingPropertiesException e) {
			myAuthoringView.loadErrorScreen("NoDefaultObject");
		}
		myAuthoringView.setModel(myModel);
		myAuthoringView.loadInitialScreen();
	}
	
	/**
	 * Method through which information about object fields can be requested
	 * Invoked when populating authoring frontend screens used to edit existing objects
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchFieldException 
	 * @throws ObjectNotFoundException 
	 */
	public String getObjectAttribute(int level, String objectType, String name, String attribute) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, ObjectNotFoundException {
		return myModel.getObjectAttribute(level, objectType, name, attribute);
	}
	
	/**
	 * Method through which information can be sent to instantiate or edit an enemy object in Authoring Model;
	 * @throws MissingPropertiesException 
	 * @throws ObjectNotFoundException 
	 * @throws NoDuplicateNamesException 
	 */
	public void makeEnemy(int level, boolean newObject, String name, String image, double speed, double initialHealth, double healthImpact, double killReward, double killUpgradeCost, double killUpgradeValue) 
			throws MissingPropertiesException, NoDuplicateNamesException, ObjectNotFoundException {
		myModel.makeEnemy(level, newObject, name, image, speed, initialHealth, healthImpact, killReward, killUpgradeCost, killUpgradeValue);
	}
	
	/**
	 * Method through which information can be sent to instantiate or edit a tower object in Authoring Model;
	 * @throws NoDuplicateNamesException 
	 * @throws MissingPropertiesException 
	 * @throws ObjectNotFoundException 
	 */
	public void makeTower(int level, boolean newObject, String name, String image, double health, double healthUpgradeCost, double healthUpgradeValue,
							String projectileImage, double projectileDamage, double projectileUpgradeCost, double projectileUpgradeValue, double projectileSize, double projectileSpeed,
							double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange,
							double towerValue, double towerUpgradeCost, double towerUpgradeValue) throws NoDuplicateNamesException, MissingPropertiesException, ObjectNotFoundException {
		myModel.makeTower(level, newObject, name, image, health, healthUpgradeCost, healthUpgradeValue, 
				projectileImage, projectileDamage, projectileUpgradeCost, projectileUpgradeValue, projectileSize, projectileSpeed,
				launcherValue, launcherUpgradeCost, launcherUpgradeValue, launcherSpeed, launcherRange, 
				towerValue, towerUpgradeCost, towerUpgradeValue);
	}
	
	/**
	 * Method through which information can be sent to instantiate or edit the Resources object in Authoring Model;
	 */
	public void makeResources(String gameName, double startingHealth, double starting$) {
		myModel.makeResources(gameName, startingHealth, starting$);
	}
	
	// TODO
	/**
	 * Method through which information can be sent to instantiate or edit a Path in Authoring Model
	 * @throws ObjectNotFoundException 
	 */
	
	public void makePath(int level, GridPane grid, List<Point> coordinates, Map<String, List<Point>> imageCoordinates, String backgroundImage) throws ObjectNotFoundException { 
	    	myModel.makePath(level, coordinates, imageCoordinates, backgroundImage); 
		myImageMap = imageCoordinates;
	}

	
	/**
	 * Method that wraps Model method to return a Path object given its level and 
	 * int identifier
	 * @param name is int identifier of this path
	 * @param level is current level
	 * @return Path object corresponding to this level/id
	 * @throws ObjectNotFoundException
	 */
	public Path getPathFromName(int name, int level) throws ObjectNotFoundException {
	    return myModel.getPathFromName(name, level);
	}
	
	/**
	 * Method through which information can be retrieved from AuthoringMOdel re: the current objects of a given type are available for editing
	 * @throws ObjectNotFoundException 
	 */
	public List<String> getCurrentObjectOptions(int level, String objectType) throws ObjectNotFoundException {
		return myModel.getCurrentObjectOptions(level, objectType);
	}
	
    /**
     * Instantiates the game engine to demo the authored game in its current state
     */
    public void demo() {

    }

    /**
	 * Invokes a Model method that adds a new level to the authored game, 
	 * based on the previous level that the user has created
	 * (or the default level if the user has not customized any level) 
	 * @return integer of new level created
	 */
	public int addNewLevel() {
		return myModel.addNewLevel(); 
	}
	
	/**
	 * Invokes a Model method that returns a list of level numbers 
	 * as strings currently in the authored game
	 * Useful in displaying the levels available for edit by the user
	 * @return List<String>: A list of level numbers as strings
	 */
	public List<String> getLevels() {
		return myModel.getLevels(); 
	}

	/**
	 * Wraps Model method to autogenerate a level
	 * @return int level number of new level
	 */
	public int autogenerateLevel() {
		return myModel.autogenerateLevel(); 
	}
	
	    /**
	     * Edits/adds an enemy composition in a specified wave
	     * 
	     * @param level: the level the wave pertains to
	     * @param path: the path that specifies the wave
	     * @param waveNumber: the wave number
	     * @param enemyKey: the unique String name that identifies the enemy
	     * @param newAmount: the new amount of the specified enemy to put in the wave
	     * @throws ObjectNotFoundException: thrown if the level isn't found
	     */
	    public void addWaveEnemy(int level, String pathName, int waveNumber, String enemyKey, int newAmount) throws ObjectNotFoundException {
		Path path = getPathFromName(Integer.parseInt(pathName), level);
		//System.out.println("pathname: " + pathName);
		Level thisLevel = myModel.levelCheck(level);
		Enemy thisEnemy = thisLevel.getEnemy(enemyKey);
		//System.out.println(thisLevel.getPaths());
		Wave thisWave;
		//TODO: problem, how is this being saved if none of these are instance variables?
		if(thisLevel.getWaves() == null) {
		    thisWave = new Wave();
		    thisLevel.getWaves().add(thisWave);
		}
		else{

		}
	    }


	
	
	/**
	 * Returns the number of waves in a specified level that belong to a specified
	 * path object.
	 * 
	 * @param level: the current level
	 * @param path: the path that the waves belong to
	 * @return int: the number of waves that belong to the path in the level
	 * @throws ObjectNotFoundException: thrown if the level isn't found
	 */
	public int wavesNumber(int level, Path path) throws ObjectNotFoundException {
	    Level thisLevel = myModel.levelCheck(level);
	    List<Wave> levelWaves = thisLevel.getWaves(path);
	    return levelWaves.size();
	}
	
	/**
	 * Gets a Map of the current Enemy names to the number of that enemy for a given wave/path/
	 * level combination
	 * @param level of wave desired
	 * @param path of wave desired
	 * @param waveNumber of wave desired
	 * @return Map of enemy names to the number of those enemies going down this path in this wave
	 * @throws ObjectNotFoundException
	 */
	public Map<String, Integer> getEnemyNameToNumberMap(int level, Path path, int waveNumber) throws ObjectNotFoundException {
	    Level currentLevel = myModel.levelCheck(level);
	    //TODO: issue here - if there is no wave yet then need to make it first!
	    if(!currentLevel.containsWave(waveNumber)) {
		return new HashMap<String, Integer>();
	    }
	    Map<Enemy, Integer> enemyMap = currentLevel.getWaves(path).get(waveNumber).getUnmodifiableEnemies(path);
	    Map<String,Integer> enemyNameMap = new HashMap<>();
	    for(Enemy enemy : enemyMap.keySet()) {
		enemyNameMap.put(enemy.getName(), enemyMap.get(enemy));
	    }
	    return enemyNameMap;   
	}
	
	
	/**
	 * Returns a List of the enemies contained in the level 
	 * 
	 * @param level: the current level
	 * @return List<String>: a list of the unique String names for each enemy in the
	 * level.
	 * @throws ObjectNotFoundException: thrown if the level isn't found
	 */
	public List<String> levelEnemies(int level) throws ObjectNotFoundException {
	    Level thisLevel = myModel.levelCheck(level);
	    return thisLevel.getAllEnemies();
	}

	/**
	 * Sets name of the game per user entry
	 * @param gameName  is new game name
	 */
	public void setGameName(String gameName) {
		myModel.setGameName(gameName);
	}

	/**
	 * Gets current name of the game
	 * @return current name of the game
	 */
	public String getGameName() {
		return myModel.getGameName(); 
	}
	/**
	 * Sets the current AuthoringModel being used based on an XML file
	 * @param gameName is name of game/XML file being loaded in
	 */
	public void setModel(String gameName) {
	    	AuthoringModelReader reader = new AuthoringModelReader();
		myModel = reader.createModel(gameName);
	}
	
	/**
	 * Returns a map of String image names to a list of Point coordinates where those 
	 * images should be found on the path
	 * @return image name to coordinate map
	 */
	public Map<String, List<Point>> getGrid() {
		return myImageMap;
	}
	/**
	 * Method to retrieve the highest wave number found in a level (including all paths)
	 * @param level is level desired
	 * @return highest wave number found in that level
	 * @throws ObjectNotFoundException
	 */
	public Integer getHighestWaveNumber(int level) throws ObjectNotFoundException{
	    return myModel.getHighestWaveNumber(level);
	}
}
