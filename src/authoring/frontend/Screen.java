package authoring.frontend;
import java.util.List;

import javafx.scene.Scene;

/**
 * 
 * @author API - Ben Hodgson 
 * @author transition to abstract class - Sarah Bland
 * @author all populated methods - Susie Choi 
 * Interface used for generating Screen objects, which represent the entirety of the view 
 * displayed to the user. 
 */

abstract class Screen {

	public static final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";
	protected String myStylesheet; 
	protected Scene myScreen;
	protected UIFactory myUIFactory;

	protected Screen() {
		myUIFactory = new UIFactory();
	}

	/**
	 * Creates & returns the styled Screen
	 */
	protected Scene makeScreen() {
		myScreen = makeScreenWithoutStyling();
		applyDefaultStyling();
		applyStyle(myStylesheet);
		return myScreen; 
	}

	protected abstract Scene makeScreenWithoutStyling();

	protected void applyDefaultStyling() {
		if (myScreen != null) {
			myScreen.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);
		}
	}

	protected void applyStyle(String stylesheet) {
		if (myScreen != null) {
			myScreen.getStylesheets().add(stylesheet);
		}
	}

	protected void applyStyles(List<String> stylesheets) {
		if (myScreen != null) {
			for (String s : stylesheets) {
				myScreen.getStylesheets().add(s);
			}		}
	}

	/**
	 * Returns the Scene object to be loaded on the screen
	 */
	protected Scene getScreen() {
		if (myScreen == null) {
			myScreen = makeScreen(); 
		}
		return myScreen; 
	}

	protected void setScreen(Scene newScreen) {
		myScreen = newScreen;
	}

}

