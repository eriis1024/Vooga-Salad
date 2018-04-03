package authoring.frontend;

import java.util.ArrayList;

import authoring.frontend.exceptions.MissingPropertiesException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Class to create the original screen users see when entering the Game Authoring environment. 
 * Dependent on the AuthoringView to create it correctly and on the PartsFactory to make UI
 * element
 * @author Sarahbland
 *
 *
 */
public class GameAuthoringStartScreen extends Screen {
	public static final String DEFAULT_OWN_CSS = "styling/GameAuthoringStartScreen.css";
	private PropertiesReader myPropertiesReader;
	private Button myEditButton;
	private String myLanguage;
	protected GameAuthoringStartScreen(String language) {
		myPropertiesReader = new PropertiesReader();
		setStyleSheet(DEFAULT_OWN_CSS);
		myLanguage = language;
	}
	@Override
	protected Scene makeScreenWithoutStyling() {
		Text startHeading = new Text();
		VBox vbox = new VBox();
		startHeading = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("StartScreenHeader", myLanguage));
		ArrayList<String> dummyGameNames = new ArrayList<>();
		String gameNamePrompt = getErrorCheckedPrompt("GameEditSelector", myLanguage);
		String prompt = new String();
		dummyGameNames.add(gameNamePrompt);
		dummyGameNames.add("Vanilla");
		dummyGameNames.add("Plants vs. Zombies");
		Button newGameButton = new Button();
		String newGameButtonPrompt = getErrorCheckedPrompt("NewGameButtonLabel", myLanguage);
		newGameButton = getUIFactory().makeTextButton("editbutton", newGameButtonPrompt);
		Button editButton = getUIFactory().makeTextButton("editbutton", getErrorCheckedPrompt("EditButtonLabel", myLanguage));
		ComboBox<String> gameChooser = getUIFactory().makeTextDropdownButtonEnable("", dummyGameNames, e -> {
			editButton.setDisable(false);}, e -> {editButton.setDisable(true);}, prompt);
		editButton.setDisable(true);
		vbox.getChildren().add(startHeading);
		vbox.getChildren().add(gameChooser);
		vbox.getChildren().add(editButton);
		vbox.getChildren().add(newGameButton);
		
		return new Scene(vbox, 1500, 900);

	}
}
