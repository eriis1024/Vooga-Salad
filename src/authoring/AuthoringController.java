package authoring;

import java.util.List;
import frontend.StageManager;

/**
 * 
 * @author susiechoi 
 *
 * Class that handles mediating creation of authoring environment objects (towers, enemies, path). 
 * Represents Controller in MVC of the authoring environment. 
 * 
 */


public class AuthoringController {
	
	private AuthoringModel myAuthoringModel; 
	
	public AuthoringController(StageManager stageManager, String languageIn) {
		myAuthoringModel = new AuthoringModel();
	}
	
	/**
	 * Method through which information about object fields can be requested
	 * Invoked when populating authoring frontend screens used to edit existing objects
	 */
	public String getObjectAttribute(int level, String objectType, String name, String attribute) {
		return myAuthoringModel.getObjectAttribute(level, objectType, name, attribute); 
	}
	
	/**
	 * Method through which information can be sent to instantiate or edit an enemy object in Authoring Model;
	 */
	public void makeEnemy(int level, boolean newObject, String name, String image, double speed, double healthImpact, double killReward, double killUpgradeCost, double killUpgradeValue) {
		myAuthoringModel.makeEnemy(level, newObject, name, image, speed, healthImpact, killReward, killUpgradeCost, killUpgradeValue);
	}
	
	/**
	 * Method through which information can be sent to instantiate or edit a tower object in Authoring Model;
	 */
	public void makeTower(int level, boolean newObject, String name, String image, double health, double healthUpgradeCost, double healthUpgradeValue,
							String projectileImage, double projectileDamage, double projectileValue, double projectileUpgradeCost, double projectileUpgradeValue,
							double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange) {
		myAuthoringModel.makeTower(level, newObject, name, image, health, healthUpgradeCost, healthUpgradeValue, 
				projectileImage, projectileDamage, projectileValue, projectileUpgradeCost, projectileUpgradeValue, 
				launcherValue, launcherUpgradeCost, launcherUpgradeValue, launcherSpeed, launcherRange);
	}
	
	/**
	 * Method through which information can be sent to instantiate or edit the Resources object in Authoring Model;
	 */
	public void makeResources(double startingHealth, double starting$) {
		myAuthoringModel.makeResources(startingHealth, starting$);
	}
	
	// TODO
	/**
	 * Method through which information can be sent to instantiate or edit a Path in Authoring Model
	 */
	public void makePath(int level) {
		myAuthoringModel.makePath(level); 
	}
	
	/**
	 * Method through which information can be retrieved from AuthoringMOdel re: the current objects of a given type are available for editing
	 */
	public List<String> getCurrentObjectOptions(int level, String objectType) {
		return myAuthoringModel.getCurrentObjectOptions(level, objectType);
	}
	
    /**
     * Instatiates the game engine to demo the authored game in its current state
     */
    public void demo() {

    }

}