package authoring.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FrontendLauncherForTesting extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		SpecifyEnemyScreen e = new SpecifyEnemyScreen();
		Scene sc = new Scene(e.getScreen(), 1500, 900);
		Stage s = new Stage(); 
		s.setScene(sc);
		s.show();
	}

}
