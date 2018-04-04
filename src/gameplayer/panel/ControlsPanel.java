package gameplayer.panel;

import java.util.Map;

import authoring.frontend.PropertiesReader;
import authoring.frontend.UIFactory;
import authoring.frontend.exceptions.MissingPropertiesException;
import gameplayer.screen.GameScreen;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControlsPanel extends Panel{
    
    //TODO read this from settings or properties file, even better would be autoscaling to fit space
    private final int DEFAULT_CONTROL_BUTTON_SIZE = 50;
    
    private final GameScreen GAME_SCREEN;
    private final UIFactory UIFACTORY;
    private final String CONTROL_BUTTON_FILEPATH = "images/ControlPanelImages/ControlButtonNames.properties";
    private final PropertiesReader PROP_READ;
    
    public ControlsPanel(GameScreen gameScreen) {
		GAME_SCREEN = gameScreen;
		UIFACTORY = new UIFactory();
		PROP_READ = new PropertiesReader();
    }
   

    @Override
    public void makePanel() {
	HBox topControls = new HBox();
	topControls.setAlignment(Pos.CENTER);
	HBox botControls = new HBox();
	botControls.setAlignment(Pos.CENTER);

	makeControlButtons(topControls, botControls);
	VBox panelRoot = new VBox(topControls, botControls);
	PANEL = panelRoot;
    }
    
    
    private void makeControlButtons(HBox topControls, HBox botControls) {
	try {
	    Map<String,Image> controlsMap = PROP_READ.keyToImageMap(CONTROL_BUTTON_FILEPATH, DEFAULT_CONTROL_BUTTON_SIZE, DEFAULT_CONTROL_BUTTON_SIZE);
	    int controlsSplit = controlsMap.keySet().size()/2;
	    int count = 0;
	    for(String control : controlsMap.keySet()) {
		Button controlButton = UIFACTORY.makeImageButton("controlButton", controlsMap.get(control));
		controlButton.setOnMouseClicked((arg0) -> GAME_SCREEN.controlHit(control));
		if(count <controlsSplit)
		    topControls.getChildren().add(controlButton);
		else
		    botControls.getChildren().add(controlButton);
		count++;
	    }
	} catch (MissingPropertiesException e) {
	    System.out.println("PropertiesReadFailed: ControlsPanel");
	    //something went wrong and we don't have the control images
	    //TODO something reasonable here
	    	//probably have default images that aren't the ones specified by authoring
	}
    }
}
