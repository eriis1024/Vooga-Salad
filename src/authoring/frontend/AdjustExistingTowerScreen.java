/**
 * @author susiechoi
 * Creates screen in which user can edit an existing tower's attributes
 */
package authoring.frontend;

public class AdjustExistingTowerScreen extends AdjustTowerScreen {

	private String mySelectedObjectName; 
	
	protected AdjustExistingTowerScreen(AuthoringView view, String selectedObjectName) {
		super(view);
		setIsNewObject(false);
		mySelectedObjectName = selectedObjectName;
	}

	/**
	 * Because the user is editing an existing tower, 
	 * this method populates fields on the Screen with the tower's current attributes
	 */
	@Override
	protected void populateFieldsWithData() {

		getMyNameField().setText(getView().getObjectAttribute( "Tower", mySelectedObjectName, "myName"));

		setComboBoxToValue(getMyImageDropdown(),getView().getObjectAttribute("Tower", mySelectedObjectName, "myImage")); 

		setSliderToValue(getMyTowerHealthValueSlider(), getView().getObjectAttribute( "Tower", mySelectedObjectName, "myHealthValue"));

		setSliderToValue(getMyTowerHealthUpgradeCostSlider(), getView().getObjectAttribute( "Tower", mySelectedObjectName, "myHealthUpgradeCost"));

		setSliderToValue(getMyTowerHealthUpgradeValueSlider(), getView().getObjectAttribute( "Tower", mySelectedObjectName, "myHealthUpgradeValue"));
	
	}

}
