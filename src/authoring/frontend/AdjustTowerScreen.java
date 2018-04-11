
/**
 * @author susiechoi
 * Abstract class for developing the fields for customizing (new or existing) tower object
 */

package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import authoring.frontend.exceptions.NoDuplicateNamesException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class AdjustTowerScreen extends AdjustNewOrExistingScreen {
	public static final String TOWER_IMAGES = "images/TowerImageNames.properties";

	private TextField myNameField;
	private ComboBox<String> myImageDropdown;
	private ImageView myImageDisplay; 
	private Slider myTowerHealthValueSlider;
	private Slider myTowerHealthUpgradeCostSlider;
	private Slider myTowerHealthUpgradeValueSlider;
	private Slider myTowerValueSlider;
	private Slider myTowerUpgradeCostSlider;
	private Slider myTowerUpgradeValueSlider; 
	private ComboBox<String> myProjectileImage;
	private double myProjectileDamage;
	//	private double myProjectileUpgradeCost;
	//	private double myProjectileUpgradeValue;
	private double myProjectileSize;
	private double myProjectileSpeed; 
	//	private double myLauncherUpgradeCost;
	//	private double myLauncherValue;
	//	private double myLauncherUpgradeValue;
	private double myLauncherSpeed;
	private double myLauncherRange; 

	protected AdjustTowerScreen(AuthoringView view, String selectedObjectName) {
		super(view, selectedObjectName);
		myProjectileImage = new ComboBox<String>();
		myProjectileDamage = 0.0; 
		//		myProjectileUpgradeCost = 0.0;
		//		myProjectileUpgradeValue = 0.0;
		myProjectileSpeed = 0.0; 
		//		myLauncherValue = 0.0;
		//		myLauncherUpgradeCost = 0.0; 
		//		myLauncherUpgradeValue = 0.0;
		myLauncherSpeed = 0.0;
		myLauncherRange = 0.0;
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
			if(getIsNewObject()) {
				if (validNameField(myNameField)) {
					try {
						getView().makeTower(getIsNewObject(), myNameField.getText(), myImageDropdown.getValue(), 
								myTowerHealthValueSlider.getValue(),  myTowerHealthUpgradeCostSlider.getValue(),  myTowerHealthUpgradeValueSlider.getValue(), 
								myProjectileImage.getValue(), myProjectileDamage, 0, 0,myProjectileSize, myProjectileSpeed, 
								0, 0, 0, myLauncherSpeed, myLauncherRange,
								myTowerValueSlider.getValue(), myTowerUpgradeCostSlider.getValue(), myTowerUpgradeValueSlider.getValue());
						getView().loadScreen(new AdjustLauncherProjectileScreen(getView(), this, myNameField.getText()));
					} catch (NoDuplicateNamesException e1) {
						getView().loadErrorAlert("NoDuplicateNames");
					}
				} 
			}
			else {
				getView().loadScreen(new AdjustLauncherProjectileScreen(getView(), this, myNameField.getText())); 
			}
		});
		Button backButton = setupBackButton();
		vb.getChildren().add(backButton);

		ScrollPane sp = new ScrollPane(vb);
		sp.setFitToWidth(true);
		sp.setFitToHeight(true);

		return sp;
	}

	protected void populateFieldsWithData() {
		myNameField.setText(getMySelectedObjectName());

		setEditableOrNot(myNameField, getIsNewObject());

		//		getUIFactory().setComboBoxToValue(myImageDropdown,getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myImage")); 

		getUIFactory().setSliderToValue(myTowerHealthValueSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myHealthValue"));

		getUIFactory().setSliderToValue(myTowerHealthUpgradeCostSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myHealthUpgradeCost"));

		getUIFactory().setSliderToValue(myTowerHealthUpgradeValueSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myHealthUpgradeValue"));

		getUIFactory().setSliderToValue(myTowerValueSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myTowerValue"));

		//		getUIFactory().setSliderToValue(myTowerUpgradeCostSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myUpgradeCost"));
		//
		//		getUIFactory().setSliderToValue(myTowerUpgradeValueSlider, getView().getObjectAttribute("Tower", getMySelectedObjectName(), "myUpgradeValue"));

	}

	private void makeTowerComponents(VBox vb) {
		TextField nameInputField = getUIFactory().makeTextField(""); 
		myNameField = nameInputField; 

		HBox towerNameSelect = new HBox(); 
		towerNameSelect = getUIFactory().addPromptAndSetupHBox("", nameInputField, getErrorCheckedPrompt("TowerName"));
		vb.getChildren().add(towerNameSelect);

		HBox towerImageSelect = new HBox();
		ComboBox<String> towerImageDropdown;
		myImageDisplay = new ImageView(); 
		try {
			towerImageDropdown = getUIFactory().makeTextDropdown("", getPropertiesReader().allKeys(TOWER_IMAGES));
			myImageDropdown = towerImageDropdown; 
			towerImageSelect = getUIFactory().setupImageSelector(getPropertiesReader(), getErrorCheckedPrompt("Tower") + " " , TOWER_IMAGES, 50, getErrorCheckedPrompt("NewImage"), getErrorCheckedPrompt("LoadImage"),
					getErrorCheckedPrompt("NewImageName"), towerImageDropdown, myImageDisplay);
		} catch (MissingPropertiesException e) {
			getView().loadErrorScreen("NoImageFile");
		}
		vb.getChildren().add(towerImageSelect);

		Slider towerValueSlider = getUIFactory().setupSlider("TowerValueSlider", getMyMaxPrice());
		myTowerValueSlider = towerValueSlider; 
		HBox towerValue = getUIFactory().setupSliderWithValue("TowerValueSlider", towerValueSlider, getErrorCheckedPrompt("TowerValue"));
		vb.getChildren().add(towerValue);

		Slider towerUpgradeCostSlider = getUIFactory().setupSlider("TowerUpgradeCostSlider", getMyMaxPrice());
		myTowerUpgradeCostSlider = towerUpgradeCostSlider;
		HBox towerUpgradeCost = getUIFactory().setupSliderWithValue("TowerUpgradeCostSlider", towerUpgradeCostSlider, getErrorCheckedPrompt("TowerUpgradeCost"));
		vb.getChildren().add(towerUpgradeCost);

		Slider towerUpgradeValueSlider = getUIFactory().setupSlider("TowerUpgradeValueSlider", getMyMaxPrice());
		myTowerUpgradeValueSlider = towerUpgradeValueSlider; 
		HBox towerUpgradeValue = getUIFactory().setupSliderWithValue("TowerUpgradeValueSlider", towerUpgradeValueSlider, getErrorCheckedPrompt("TowerUpgradeValue"));
		vb.getChildren().add(towerUpgradeValue);
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

	protected String getSelectedImage() {
		return myImageDropdown.getValue(); 
	}

	protected double getTowerHealthValue() {
		return myTowerHealthValueSlider.getValue(); 
	}

	protected double getTowerHealthUpgradeCost() {
		return myTowerHealthUpgradeCostSlider.getValue(); 
	}

	protected double getTowerHealthUpgradeValue() {
		return myTowerHealthUpgradeValueSlider.getValue(); 
	}

	protected double getTowerValue() {
		return myTowerValueSlider.getValue(); 
	}

	protected double getTowerUpgradeCost() {
		return myTowerUpgradeCostSlider.getValue(); 
	}

	protected double getTowerUpgradeValue() {
		return myTowerUpgradeValueSlider.getValue(); 
	}

}