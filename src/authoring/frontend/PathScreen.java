package authoring.frontend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public abstract class PathScreen extends AdjustScreen {

	public static final String DEFAULT_OWN_STYLESHEET = "styling/CreatePath.css";

	private StackPane pathRoot;
	protected GridPane pathGrid;
	protected CreatePathGrid grid;

	protected PathScreen(AuthoringView view) {	
		super(view);
//		setStyleSheet(view.getCurrentCSS());
		grid = new CreatePathGrid(view);
	}
	
	protected void setPathPanel(PathPanel panel, PathToolBar toolbar) {
		pathRoot.getChildren().clear();
		pathRoot.getChildren().add(panel.getPanel());
		pathRoot.getChildren().add(toolbar.getPanel());
		pathRoot.getChildren().add(pathGrid);

		StackPane.setAlignment(pathGrid, Pos.TOP_LEFT);
		StackPane.setAlignment(panel.getPanel(), Pos.CENTER_RIGHT);
		StackPane.setAlignment(toolbar.getPanel(), Pos.BOTTOM_LEFT);
	}
	

	@Override
	public Parent makeScreenWithoutStyling() {
		setStyleSheet(DEFAULT_OWN_STYLESHEET);
		pathGrid = grid.makePathGrid();
		pathRoot = new StackPane();
		initializeGridSettings(grid);
		setSpecificUIComponents();
		return pathRoot; 	
	}

	public abstract void initializeGridSettings(CreatePathGrid grid);
	public abstract void setSpecificUIComponents();
	protected CreatePathGrid getGrid() {
	    return grid;
	}

	protected void setGridUIComponents(PathPanel panel, PathToolBar toolbar) {
		Button pathSizePlusButton = toolbar.getPlusButton();
		pathSizePlusButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (grid.getPathSize() < 100) {
					grid.setGridConstraints(grid.getGrid(), grid.getPathSize() + 10);
				}
			}
		});

		Button pathSizeMinusButton = toolbar.getMinusButton();
		pathSizeMinusButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (grid.getPathSize() > 30) {
					grid.setGridConstraints(grid.getGrid(), grid.getPathSize() - 10);

				}
			}
		});
	}


}
