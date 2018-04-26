
package gameplayer.panel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public class ScorePanel extends Panel {

    private static final String DEFAULT_SHARED_STYLESHEET = "styling/SharedStyling.css";

    private Integer SCORE;
    private Integer HEALTH;
    private Integer LEVEL;
    private Label ScoreText;
    private Label LevelText;
    private Label HealthText;

    public ScorePanel() {
	SCORE = 0;
	HEALTH = 100;
	LEVEL = 1;
    }


    @Override
    public void makePanel() {

	//TODO Read words SCORE, LEVEL, and + from properties file
	ScoreText = new Label("Score: " + SCORE);
	LevelText = new Label("Level " + LEVEL);
	HealthText = new Label("+" + HEALTH);


	ScoreText.setMaxWidth(Double.MAX_VALUE);

	LevelText.setMaxWidth(Double.MAX_VALUE);

	HealthText.setMaxWidth(Double.MAX_VALUE);

	HBox panelRoot = new HBox();

	HBox.setHgrow(ScoreText, Priority.ALWAYS);
	HBox.setHgrow(LevelText, Priority.ALWAYS);
	HBox.setHgrow(HealthText, Priority.ALWAYS);
	panelRoot.getChildren().addAll(ScoreText, LevelText, HealthText);

	panelRoot.setMaxWidth(Double.MAX_VALUE);
	panelRoot.setMaxHeight(Double.MAX_VALUE);
	panelRoot.getStylesheets().add(DEFAULT_SHARED_STYLESHEET);
	PANEL = panelRoot;
    }

    private void updateScore(Integer newScore) {
	ScoreText.setText("Score: " + newScore);
    }

    private void updateHealth(Integer newHealth) {
	HealthText.setText("+" +newHealth);
    }

    public void updateLevel(Integer newLevel) {
	LevelText.setText("Level: " + newLevel);
    }
    
    public void setInitialScore(Integer score) {
	SCORE = score;
    }
    
    public void setInitialLives(Integer lives) {
	HEALTH = lives;
    }
    

    public ChangeListener createScoreListener() {
	return new ChangeListener() {
	    @Override
	    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
		updateScore((Integer)observableValue.getValue());
	    }
	};
    }
    
    public ChangeListener createHealthListener() {
   	return new ChangeListener() {
   	    @Override
   	    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
   		updateHealth((Integer)observableValue.getValue());
   	    }
   	};
       }

}
