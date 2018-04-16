package authoring.frontend;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

/**
 * Abstract class representing the bottom toolbar portion of screens displaying a Path.
 * Used to add basic sizing functionality to those toolbars and extended to provide other 
 * functionality.
 * @author Sarahbland
 *
 */
public abstract class PathToolBar extends AuthoringScreen {
	public static final String DEFAULT_PLUS_IMAGE = "file:images/plus.png";
	public static final String DEFAULT_MINUS_IMAGE = "file:images/minus.png";
	private List<Button> mySizingButtons;
	private Button myPlusButton;
	private Button myMinusButton;
	private HBox myPathToolBar;
	public PathToolBar(AuthoringView view) {
		super(view);
		mySizingButtons = new ArrayList<>();
		setUpSizing();
		makePanel();
	}
	
	protected void setUpSizing() {
		myPathToolBar = new HBox();
		myPathToolBar.setMaxSize(1021, 101);
		myPathToolBar.getStylesheets();
		myPathToolBar.getChildren().add(makeSizingButtons());
	}
	
	protected HBox getToolBar() {
		return myPathToolBar;
	}
	
	protected HBox getPanel() { //TODO: fix this!!
		return myPathToolBar;
	}
	
	protected abstract void makePanel();
	
	protected HBox makeSizingButtons() {
		HBox hb = new HBox();
		Image plusImg = new Image(DEFAULT_PLUS_IMAGE, 60, 40, true, false);
		myPlusButton = getUIFactory().makeImageButton("", plusImg);
		myPlusButton.getStyleClass().add("button-pathsize");
		mySizingButtons.add(myPlusButton);

		Image minusImg = new Image(DEFAULT_MINUS_IMAGE, 60, 40, true, false);
		myMinusButton = getUIFactory().makeImageButton("", minusImg);
		myMinusButton.getStyleClass().add("button-pathsize");
		mySizingButtons.add(myMinusButton);
		hb.getChildren().addAll(mySizingButtons);
		return hb;
	}
	
	protected Button getPlusButton() {
		return myPlusButton;
	}

	protected Button getMinusButton() {
		return myMinusButton;
	}
}
