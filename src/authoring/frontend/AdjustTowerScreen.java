
/**
 * @author susiechoi
 * Abstract class for developing the fields for customizing (new or existing) tower object
 */

package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

abstract class AdjustTowerScreen extends AdjustNewOrExistingScreen {
	public static final String TOWER_IMAGES = "images/TowerImageNames.properties";
	
	private boolean myIsNewObject; 
	private TextField myNameField;
	private ComboBox<String> myImageDropdown;
	private Slider myTowerHealthValueSlider;
	private Slider myTowerHealthUpgradeCostSlider;
	private Slider myTowerHealthUpgradeValueSlider;
	
	private String myProjectileImage;
	private double myProjectileValue;
	private double myProjectileDamage;
	private double myProjectileUpgradeCost;
	private double myProjectileUpgradeValue;
	private double myLauncherUpgradeCost;
	private double myLauncherValue;
	private double myLauncherUpgradeValue;
	private double myLauncherSpeed;
	private double myLauncherRange; 
	
	protected AdjustTowerScreen(AuthoringView view) {
		super(view);
	}

	@Override
	public Parent populateScreenWithFields() {		
		VBox vb = new VBox(); 
		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("CustomizeTower")));

		makeTowerComponents(vb);
		makeHealthComponents(vb);
		
		Button goToProjectileLauncherButton = getUIFactory().makeTextButton("", getErrorCheckedPrompt("CustomizeProjectileLauncher"));
		vb.getChildren().add(goToProjectileLauncherButton);
		goToProjectileLauncherButton.setOnAction(e -> {
		    getView().getScene().setRoot(new HBox());
		    if(getMyIsNewObject()) {
			getView().loadScreen(new AdjustNewLauncherProjectileScreen(getView(), this));
		    }
		    else {
			getView().loadScreen(new AdjustExistingLauncherProjectileScreen(getView(), this, myNameField.getText())); //TODO: figure out not hard coded!
		    }
		});
		
		Button backButton = setupBackButtonSuperclass();
		Button applyButton = getUIFactory().setupApplyButton();
		applyButton.setOnAction(e -> {
			getView().makeTower(myIsNewObject, myNameField.getText(), myImageDropdown.getValue(), myTowerHealthValueSlider.getValue(),  myTowerHealthUpgradeCostSlider.getValue(),  myTowerHealthUpgradeValueSlider.getValue(), myProjectileImage, myProjectileDamage, myProjectileValue, myProjectileUpgradeCost, myProjectileUpgradeValue, myLauncherValue, myLauncherUpgradeCost, myLauncherUpgradeValue, myLauncherSpeed, myLauncherRange);
			getView().goForwardFrom(this.getClass().getSimpleName()+"Apply");
		});
		
		HBox backAndApplyButton = setupBackAndApplyButton(backButton, applyButton);
		vb.getChildren().add(backAndApplyButton);
				
		ScrollPane sp = new ScrollPane(vb);
		sp.setFitToWidth(true);
		sp.setFitToHeight(true);
		
		return sp;
	}

	protected abstract void populateFieldsWithData(); 
	
	private void makeTowerComponents(VBox vb) {
		TextField nameInputField = getUIFactory().makeTextField(""); 
		myNameField = nameInputField; 
		
		HBox towerNameSelect = new HBox(); 
		towerNameSelect = getUIFactory().addPromptAndSetupHBox("", nameInputField, getErrorCheckedPrompt("TowerName"));
		vb.getChildren().add(towerNameSelect);

		HBox towerImageSelect = new HBox();
		ComboBox<String> towerImageDropdown;
		try {
			towerImageDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(TOWER_IMAGES));
			myImageDropdown = towerImageDropdown; 
			towerImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), getErrorCheckedPrompt("Tower") + " " , TOWER_IMAGES, 50, getErrorCheckedPrompt("NewImage"), getErrorCheckedPrompt("LoadImage"),
					getErrorCheckedPrompt("NewImageName"), towerImageDropdown);
		} catch (MissingPropertiesException e) {
			getView().loadErrorScreen("NoImageFile");
		}
		vb.getChildren().add(towerImageSelect);
	}
	
	private void makeHealthComponents(VBox vb) {
		Slider towerHealthValueSlider = getUIFactory().setupSlider("TowerHealthValueSlider", getMyMaxPrice());
		myTowerHealthValueSlider = towerHealthValueSlider; 
		HBox towerHealthValue = getUIFactory().setupSliderWithValue("TowerHealthValueSlider", towerHealthValueSlider, getErrorCheckedPrompt("TowerHealthValue"));
		vb.getChildren().add(towerHealthValue);

		Slider towerHealthUpgradeCostSlider = getUIFactory().setupSlider("TowerHealthUpgradeCostSlider", getMyMaxPrice());
		myTowerHealthUpgradeCostSlider = towerHealthUpgradeCostSlider;
		HBox towerHealthUpgradeCost = getUIFactory().setupSliderWithValue("TowerHealthUpgradeCostSlider", towerHealthUpgradeCostSlider, getErrorCheckedPrompt("TowerHealthUpgradeCost"));
		vb.getChildren().add(towerHealthUpgradeCost);

		Slider towerHealthUpgradeValueSlider = getUIFactory().setupSlider("TowerHealthUpgradeValueSlider", getMyMaxPrice());
		myTowerHealthUpgradeValueSlider = towerHealthUpgradeValueSlider; 
		HBox towerHealthUpgradeValue = getUIFactory().setupSliderWithValue("TowerHealthUpgradeValueSlider", towerHealthUpgradeValueSlider, getErrorCheckedPrompt("TowerHealthUpgradeValue"));
		vb.getChildren().add(towerHealthUpgradeValue);
	}
	
	protected void setIsNewObject(boolean isNewObject) {
		myIsNewObject = isNewObject; 
	}
	
	protected void setLauncherProjectileValues(String projectileImage, double projectileDamage, double projectileValue, double projectileUpgradeCost, double projectileUpgradeValue,
			double launcherValue, double launcherUpgradeCost, double launcherUpgradeValue, double launcherSpeed, double launcherRange) {
		myProjectileImage = projectileImage;
		myProjectileDamage = projectileDamage; 
		myProjectileValue = projectileValue;
		myProjectileUpgradeCost = projectileUpgradeCost;
		myProjectileUpgradeValue = projectileUpgradeValue;
		myLauncherValue = launcherValue;
		myLauncherUpgradeCost = launcherUpgradeCost; 
		myLauncherUpgradeValue = launcherUpgradeValue;
		myLauncherSpeed = launcherSpeed;
		myLauncherRange = launcherRange; 
	}
	
	protected boolean getMyIsNewObject() {
		return myIsNewObject; 
	} 
	
	protected TextField getMyNameField() {
		return myNameField; 
	}
	
	protected ComboBox<String> getMyImageDropdown() {
		return myImageDropdown; 
	}

	protected Slider getMyTowerHealthValueSlider() {
		return myTowerHealthValueSlider;
	}
	
	protected Slider getMyTowerHealthUpgradeCostSlider() {
		return myTowerHealthUpgradeCostSlider;
	}
	
	protected Slider getMyTowerHealthUpgradeValueSlider() {
		return myTowerHealthUpgradeValueSlider; 
	}

}