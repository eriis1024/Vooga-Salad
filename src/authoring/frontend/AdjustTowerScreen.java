package authoring.frontend;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

class AdjustTowerScreen implements Screen {

	private Scene myScreen; 

	@Override
	public void makeScreen() {
		VBox v = new VBox();
		v.getChildren().add(new Text("testing tower screen"));
		Scene sc = new Scene(v, 1500, 900);
		myScreen = sc; 
	}

	@Override
	public Scene getScreen() {
		if (myScreen == null) {
			makeScreen(); 
		}
		return myScreen; 
	}

}
