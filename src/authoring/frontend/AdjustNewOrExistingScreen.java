/**
 * @author susiechoi
 * Abstract class of screens that have both "new" and "existing" object edit options 
 * (e.g. AdjustTowerScreen extends AdjustNewOrExistingScreen because a designer can edit 
 * a new or existing Tower) 
 *
 */

package authoring.frontend;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;

abstract class AdjustNewOrExistingScreen extends AdjustScreen {

	public static final String DEFAULT_CONSTANTS = "frontend/Constants.properties";
	
	private String mySelectedObjectName; 
	
	private String myDefaultObjectName; 
	private int myMaxHealthImpact;
	private int myMaxSpeed;
	private int myMaxRange;
	private int myMaxPrice; 
	private int myMaxUpgradeIncrement; 

	private boolean myIsNewObject; 	
	
	protected AdjustNewOrExistingScreen(AuthoringView view, String selectedObjectName) {
		super(view);
		mySelectedObjectName = selectedObjectName; 
		myIsNewObject = mySelectedObjectName.equals(myDefaultObjectName);
		setConstants();
	}

	private void setConstants() {
		try {
			myDefaultObjectName = getPropertiesReader().findVal(DEFAULT_CONSTANTS, "DefaultObjectName");
			myMaxHealthImpact = Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS, "MaxHealthImpact"));
			myMaxSpeed = Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS, "MaxSpeed"));
			myMaxRange = Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS, "MaxRange"));
			myMaxPrice = Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS, "MaxPrice"));
			myMaxUpgradeIncrement = Integer.parseInt(getPropertiesReader().findVal(DEFAULT_CONSTANTS, "MaxUpgradeIncrement"));
		} catch (NumberFormatException e) {
			getView().loadErrorScreen("BadConstants");
		} catch (MissingPropertiesException e) {
			getView().loadErrorScreen("NoConstants");
		}
		
	}
	
	/**
	 * For all screens in which users can edit either new or existing objects, the makeScreenWithoutStyling method should 
	 * ensure that the screen is populated with fields and that, if deemed necessary by the subclass, 
	 * the fields are populated with data (in the case that an existing object is being edited) 
	 */
	@Override
	public Parent makeScreenWithoutStyling() {
		Parent constructedScreen = populateScreenWithFields();
		populateFieldsWithData(); 
		return constructedScreen;
	}
	
	protected abstract Parent populateScreenWithFields();
	protected abstract void populateFieldsWithData(); 
	
	/**
	 * Method used in appropriately-setting the ComboBox when populating data fields with the existing object values
	 * @param combobox - combobox to be set to a value
	 * @param selectionValue - the value that the combobox should be set to 
	 */
	protected void setComboBoxToValue(ComboBox<String> combobox, String selectionValue) {
		int dropdownIdx = combobox.getItems().indexOf(selectionValue); 
		combobox.getSelectionModel().select(dropdownIdx);
	}
	
	/**
	 * Method used in appropriately-setting the slider when populating data fields with the existing object values
	 * @param slider - slider to be set to a value
	 * @param valueAsString - the value that the slider should be set to 
	 */
	protected void setSliderToValue(Slider slider, String valueAsString) {
		Double value = Double.parseDouble(valueAsString);
		slider.setValue(value);
	}
	
	/** 
	 * The following methods are getters for range-specifying constants so that subclasses may know what range to depict on their sliders
	 * @return int max of the sliders 
	 */
	protected int getMyMaxHealthImpact() {
		return myMaxHealthImpact;
	}
	
	protected int getMyMaxSpeed() {
		return myMaxSpeed;
	}
	
	protected int getMyMaxRange() {
		return myMaxRange; 
	}
	
	protected int getMyMaxPrice() {
		return myMaxPrice;
	}
	
	protected int getMyMaxUpgradeIncrement() {
		return myMaxUpgradeIncrement; 
	}
	
	protected String getMySelectedObjectName() {
		return mySelectedObjectName; 
	}
	
}