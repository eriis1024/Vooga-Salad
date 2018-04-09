package authoring.frontend;

import java.util.ArrayList;

import javafx.scene.Parent;
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
public class StartScreen extends AuthoringScreen {

    protected StartScreen(AuthoringView view) {
	super(view);
    }
    @Override
    public Parent makeScreenWithoutStyling() {
	Text startHeading = new Text();
	VBox vbox = new VBox();
	startHeading = getUIFactory().makeScreenTitleText(getErrorCheckedPrompt("StartScreenHeader"));
	ArrayList<String> dummyGameNames = new ArrayList<>();
	String gameNamePrompt = getErrorCheckedPrompt("GameEditSelector");
	String prompt = new String();
	dummyGameNames.add(gameNamePrompt);
	dummyGameNames.add("Dummy Game");
	Button newGameButton = new Button();
	String newGameButtonPrompt = getErrorCheckedPrompt("NewGameButtonLabel");
	newGameButton = getUIFactory().makeTextButton("editbutton", newGameButtonPrompt);
	newGameButton.setOnAction(e -> {
		getView().goForwardFrom(this.getClass().getSimpleName()+"New");
	});
	Button editButton = getUIFactory().makeTextButton("editbutton", getErrorCheckedPrompt("EditButtonLabel"));
	ComboBox<String> gameChooser = getUIFactory().makeTextDropdownSelectAction("", dummyGameNames, e -> {
	    editButton.setDisable(false);}, e -> {editButton.setDisable(true);}, prompt);
	editButton.setDisable(true);
	editButton.setOnAction(e -> {getView().goForwardFrom(this.getClass().getSimpleName()+"Edit");});
	vbox.getChildren().add(startHeading);
	vbox.getChildren().add(gameChooser);
	vbox.getChildren().add(editButton);
	vbox.getChildren().add(newGameButton);
	return vbox;

    }
}
