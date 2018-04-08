/**
 * @author Sarah Bland
 * @author susiechoi
 * 
 * Represents View of authoring environment's MVC. 
 * Allows for screen transitions and the communication of object altering/creation to Controller. 
 */

package authoring.frontend;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import authoring.AuthoringController;
import authoring.frontend.exceptions.MissingPropertiesException;
import authoring.frontend.exceptions.NoDuplicateNamesException;
import frontend.PromptReader;
import frontend.PropertiesReader;
import frontend.Screen;
import frontend.StageManager;
import frontend.View;
import gameplayer.ScreenManager;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class AuthoringView extends View {


    public static final String DEFAULT_SCREENFLOW_FILEPATH = "src/frontend/ScreenFlow.properties";
    public static final String DEFAULT_AUTHORING_CSS = "styling/GameAuthoringStartScreen.css";
    private StageManager myStageManager; 
    private PromptReader myPromptReader;
    private PropertiesReader myPropertiesReader;
    private AuthoringController myController; 
    private String myCurrentCSS;
    private int myLevel; 

    public AuthoringView(StageManager stageManager, String languageIn, AuthoringController controller) {
	super(stageManager);
	myPromptReader = new PromptReader(languageIn, this);
	myPropertiesReader = new PropertiesReader();
	myStageManager = stageManager; 
	myController = controller; 
	myCurrentCSS = new String(DEFAULT_AUTHORING_CSS);
	myStageManager.switchScreen((new StartScreen(this)).getScreen());
    }
    protected void loadScreen(Screen screen) {
	myStageManager.switchScreen(screen.getScreen());
    }
    protected void loadScene(Scene scene) { //TODO: refactor so no duplication?
	myStageManager.switchScene(scene);
    }
    protected String getCurrentCSS() {
	return myCurrentCSS;
    }

    protected void goBackFrom(String id) {
	goForwardFrom(id+"Back");
    }
    protected void goForwardFrom(String id) {
	goForwardFrom(id, "");
    }
    protected void goForwardFrom(String id, String name) {
	try {
	    String nextScreenClass = myPropertiesReader.findVal(DEFAULT_SCREENFLOW_FILEPATH, id);
	    Class<?> clazz = Class.forName(nextScreenClass);
	    Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
	    if(constructor.getParameterTypes().length == 2) {
		AuthoringScreen nextScreen = (AuthoringScreen) constructor.newInstance(this, name);
		myStageManager.switchScreen(nextScreen.getScreen());
	    }
	    else if(constructor.getParameterTypes()[0].equals(AuthoringView.class)) {
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




    /**
     * Method through which information can be sent to instantiate or edit a tower object in Authoring Model;
     * @throws NoDuplicateNamesException 
     */
    public void makeTower(boolean newObject, String name, Image image, double health, double healthUpgradeCost, double healthUpgradeValue,
	    Image projectileImage, double projectileDamage, double projectileUpgradeCost, double projectileUpgradeValue,
	    double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange) throws NoDuplicateNamesException {
	myController.makeTower(myLevel, newObject, name, image, health, healthUpgradeCost, healthUpgradeValue, 
		projectileImage, projectileDamage, projectileUpgradeCost, projectileUpgradeValue, 
		launcherValue, launcherUpgradeCost, launcherUpgradeValue, launcherSpeed, launcherRange);
    }

    /**
     * Method through which information can be sent to instantiate or edit an enemy object in Authoring Model;
     */
    public void makeEnemy(boolean newObject, String name, Image image, double speed, double healthImpact, double killReward, double killUpgradeCost, double killUpgradeValue) {
	myController.makeEnemy(myLevel, newObject, name, image, speed, healthImpact, killReward, killUpgradeCost, killUpgradeValue);
    }

    //TODO 
    /**
     * Method through which information can be sent to instantiate or edit a Path in Authoring Model;
     */
    public void makePath() {
	myController.makePath(myLevel);
    }


    /**
     * Method through which information can be sent to instantiate or edit the Resources object in Authoring Model;
     */
    public void makeResources(double startingHealth, double starting$) {
	myController.makeResources(startingHealth, starting$);
    }

    /**
     * Method through which information can be retrieved from AuthoringMOdel re: the current objects of a given type are available for editing
     */
    public List<String> getCurrentObjectOptions(String objectType) {
	return myController.getCurrentObjectOptions(myLevel, objectType);
    }

    /**
     * Method through which information about object fields can be requested
     * Invoked when populating authoring frontend screens used to edit existing objects
     */
    public String getObjectAttribute(String objectType, String objectName, String attribute) {
	return myController.getObjectAttribute(myLevel, objectType, objectName, attribute);
    }

    /**
     * Enumerates the current level that the user is editing 
     * Useful when creating new objects so that Model may organize objects by the level in which they become accessible to the user 
     * @param level - the level that the user has selected to edit
     */
    protected void setLevel(int level) {
	myLevel = level; 
    }

    protected Scene getScene() {
	return myStageManager.getScene();
    }

    protected String getErrorCheckedPrompt(String prompt) {
	return myPromptReader.resourceDisplayText(prompt);
    }

}
