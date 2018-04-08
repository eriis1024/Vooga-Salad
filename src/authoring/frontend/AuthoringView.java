package authoring.frontend;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import authoring.AuthoringController;
import authoring.frontend.exceptions.MissingPropertiesException;
import frontend.PromptReader;
import frontend.PropertiesReader;
import frontend.Screen;
import frontend.StageManager;
import frontend.View;
import gameplayer.ScreenManager;

public class AuthoringView extends View {
	
	public static final String DEFAULT_SCREENFLOW_FILEPATH = "src/frontend/ScreenFlow.properties";

	private StageManager myStageManager; 
	private PromptReader myPromptReader;
	private PropertiesReader myPropertiesReader;
	private AuthoringController myController; 

	public AuthoringView(StageManager stageManager, String languageIn, AuthoringController controller) {
		super(stageManager);
		myPromptReader = new PromptReader(languageIn, this);
		myPropertiesReader = new PropertiesReader();
		myStageManager = stageManager; 
		myController = controller; 
		myStageManager.switchScreen((new SpecifyTowerScreen(this)).getScreen());
	}

	protected void goBackFrom(String id) {
	    goForwardFrom(id+"Back");
	}
	protected void goForwardFrom(String id) {
		try {
		    	System.out.println("id getting sent: " + id);
			String nextScreenClass = myPropertiesReader.findVal(DEFAULT_SCREENFLOW_FILEPATH, id);
			Class<?> clazz = Class.forName(nextScreenClass);
			Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
			if(constructor.getParameterTypes()[0].equals(AuthoringView.class)) {
				AuthoringScreen nextScreen = (AuthoringScreen) constructor.newInstance(this);
				myStageManager.switchScreen(nextScreen.getScreen());
			}
			else if(constructor.getParameterTypes()[0].equals(ScreenManager.class)) {
				Screen nextScreen = (Screen) constructor.newInstance(new ScreenManager(myStageManager, myPromptReader));
				myStageManager.switchScreen(nextScreen.getScreen());
			} //TODO: handle case where switching to gameplay
			else {
				throw new MissingPropertiesException("");
			}

		}
		catch(MissingPropertiesException | ClassNotFoundException | InvocationTargetException
				| IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
			loadErrorScreen("NoScreenFlow");
		}
	}

	protected String getErrorCheckedPrompt(String prompt) {
		return myPromptReader.resourceDisplayText(prompt);
	}

	public void makeTower(boolean newObject, String name, String image, double health, double healthUpgradeCost, double healthUpgradeValue,
			String projectileImage, double projectileDamage, double projectileValue, double projectileUpgradeCost, double projectileUpgradeValue,
			double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange) {
		myController.makeTower(newObject, name, image, health, healthUpgradeCost, healthUpgradeValue, 
				projectileImage, projectileDamage, projectileValue, projectileUpgradeCost, projectileUpgradeValue, 
				launcherValue, launcherUpgradeCost, launcherUpgradeValue, launcherSpeed, launcherRange);
	}

	public void makeEnemy(boolean newObject, String name, String image, double speed, double healthImpact, double killReward, double killUpgradeCost, double killUpgradeValue) {
		myController.makeEnemy(newObject, name, image, speed, healthImpact, killReward, killUpgradeCost, killUpgradeValue);
	}

	//TODO 
	public void makePath() {
		myController.makePath();
	}

	public String getObjectAttribute(String objectType, String objectName, String attribute) {
		return myController.getObjectAttribute(objectType, objectName, attribute);
	}

	//	protected String getLanguage() {
	//		return myLanguage;
	//	}

	//	protected void goForwardFrom(Screen currentScreen) {
	//		String currentScreenName = currentScreen.getClass().getSimpleName();
	//		if (currentScreenName.equals("SpecifyEnemyScreen")) {
	//			myStageManager.switchScreen((new AdjustEnemyScreen(this)).getScreen()); 	// TODO replace with reflection?
	//		}
	//		else if (currentScreenName.equals("SpecifyTowerScreen")) {
	//			myStageManager.switchScreen((new AdjustTowerScreen(this)).getScreen());
	//		}
	//	}
	//
	//	protected void goBackFrom(Screen currentScreen) {
	//		String currentScreenName = currentScreen.getClass().getSimpleName();
	//		if (currentScreenName.equals("AdjustEnemyScreen")) {
	//			myStageManager.switchScreen((new SpecifyEnemyScreen(this).getScreen())); 	// TODO replace with reflection?
	//		}
	//		else if (currentScreenName.equals("AdjustTowerScreen")) {
	//			myStageManager.switchScreen((new SpecifyTowerScreen(this).getScreen())); 	
	//		}
	//	}

}
