/**
 * @author susiechoi
 * @author sarahbland
 * Creates screen in which user can customize the starting resources of the player
 */

package authoring.frontend;

import java.util.ArrayList;
import java.util.List;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AdjustResourcesScreen extends AdjustNewOrExistingScreen {
	
	public static final String DEFAULT_CSS_STYLES = "src/styling/CurrentCSS.properties";
	
    	private TextField myGameNameEntry;
	private Slider myStartingHealthSlider;
	private Slider myStartingCurrencySlider;
	private ComboBox<String> myCSSFilenameChooser;
	
    	protected AdjustResourcesScreen(AuthoringView view) {
		super(view);
	}

	/**
	 * Creates features (specifically, sliders) that users can manipulate to change starting reosurces of player
	 */
	@Override
	public Parent populateScreenWithFields(){
		VBox vb = new VBox(); 

		vb.getChildren().add(getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("SpecifyStartingResources")));

		Text settingsHeading = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("SettingsHeading"));
		myGameNameEntry = getUIFactory().makeTextField("");
		vb.getChildren().add(settingsHeading);

		HBox promptGameName = getUIFactory().addPromptAndSetupHBox("", myGameNameEntry, getErrorCheckedPrompt("GameName"));
		vb.getChildren().add(promptGameName);	
		myStartingHealthSlider = getUIFactory().setupSlider("startingHealth", 100);
		HBox startingHealth = getUIFactory().setupSliderWithValue("startingHealth", myStartingHealthSlider, getErrorCheckedPrompt("StartingHealth"));
		vb.getChildren().add(startingHealth);

		myStartingCurrencySlider = getUIFactory().setupSlider("startingCurrency", 999);
		HBox startingCurrency = getUIFactory().setupSliderWithValue("startingCurrency", myStartingCurrencySlider, getErrorCheckedPrompt("StartingCurrency"));
		vb.getChildren().add(startingCurrency);

		List<String> cssOptions = new ArrayList<String>(); 
		try {
			cssOptions = getPropertiesReader().allKeys(DEFAULT_CSS_STYLES);
		} catch (MissingPropertiesException e1) {
			getView().loadErrorAlert("NoFile");
		}
		myCSSFilenameChooser = getUIFactory().makeTextDropdown("", cssOptions);
		vb.getChildren().add(getUIFactory().addPromptAndSetupHBox("", myCSSFilenameChooser, getErrorCheckedPrompt("CSS")));
		
		Button backButton = setupBackButton();
		Button applyButton = getUIFactory().setupApplyButton();
		applyButton.setOnAction(e -> {
		    	setSaved();
			getView().makeResources(myGameNameEntry.getText(), myStartingHealthSlider.getValue(), myStartingCurrencySlider.getValue(), myCSSFilenameChooser.getSelectionModel().getSelectedItem());
			getView().goForwardFrom(this.getClass().getSimpleName()+"Apply");
		});
		HBox backAndApplyButton = getUIFactory().setupBackAndApplyButton(backButton, applyButton);
		
		vb.getChildren().add(backAndApplyButton);
		
		return vb;
	}

	@Override
	protected void populateFieldsWithData() {
		myGameNameEntry.setText(getView().getObjectAttribute("Settings", "", "myGameName").toString());
		getUIFactory().setSliderToValue(myStartingHealthSlider, getView().getObjectAttribute("Settings", "", "myStartingHealth").toString());
		getUIFactory().setSliderToValue(myStartingCurrencySlider, getView().getObjectAttribute("Settings", "", "myStartingMoney").toString());	
		getUIFactory().setComboBoxToValue(myCSSFilenameChooser, getView().getObjectAttribute("Settings", "", "myCSS").toString());
	}
}
