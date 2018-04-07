package authoring;

import authoring.frontend.AuthoringView;
import frontend.StageManager;
import javafx.scene.image.ImageView;

/**
 * 
 * @author susiechoi 
 *
 * Class that handles mediating program functionality specific to authoring. 
 * Represents Controller in MVC of the authoring environment. 
 */

public class AuthoringController {
	
	private AuthoringView myAuthoringView; 
	private AuthoringModel myAuthoringModel; 
	
	public AuthoringController(StageManager stageManager, String languageIn) {
		myAuthoringModel = new AuthoringModel();
		myAuthoringView = new AuthoringView(stageManager, languageIn, this);
	}
	
	public String getObjectAttribute(String objectType, String name, String attribute) {
		return myAuthoringModel.getObjectAttribute(objectType, name, attribute); 
	}
	
	public void makeEnemy(boolean newObject, String name, String image, double speed, double healthImpact, double moneyImpact, double killReward, double killUpgradeCost, double killUpgradeValue) {
		myAuthoringModel.makeEnemy(newObject, name, image, speed, healthImpact, moneyImpact, killReward, killUpgradeCost, killUpgradeValue);
	}
	
	public void makeTower(boolean newObject, String name, String image, double health, double healthUpgradeCost, double healthUpgradeValue,
							String projectileImage, double projectileDamage, double projectileValue, double projectileUpgradeCost, double projectileUpgradeValue,
							double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange) {
		myAuthoringModel.makeTower(newObject, name, image, health, healthUpgradeCost, healthUpgradeValue, 
				projectileImage, projectileDamage, projectileValue, projectileUpgradeCost, projectileUpgradeValue, 
				launcherValue, launcherUpgradeCost, launcherUpgradeValue, launcherSpeed, launcherRange);
	}
	
	// TODO 
	public void makePath() {
		myAuthoringModel.makePath(); 
	}
	
    /**
     * Instatiates the game engine to demo the authored game in its current state
     */
    public void demo() {

    }

}