/**
 * Class for generating common front end UI elmenets, e.g. comboboxes, sliders
 * To ensure consistency of front end design
 * @author susiechoi 
 */

package authoring.frontend;

import java.io.File;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UIFactory {

	public Button makeImageButton(Image buttonImage) {
		Button newButton = new Button();
		ImageView buttonImageView = new ImageView(buttonImage);
		newButton.setGraphic(buttonImageView);
		return newButton; 
	}

	public Button makeTextButton(String buttonText) {
		Button newButton = new Button(buttonText);
		return newButton; 
	}

	public ComboBox<String> makeTextDropdown(List<String> dropdownOptions) {
		ObservableList<String> options = FXCollections.observableArrayList(dropdownOptions);
		ComboBox<String> newDropdown = new ComboBox<String>(options);
		newDropdown.getSelectionModel().selectFirst(); 
		return newDropdown; 
	}

	/**
	 * Creates HBox with a text prompt to the left of a user input text field
	 * @param promptString - text prompt 
	 * @return HBox 
	 */
	protected HBox setupPromptAndTextField(String promptString) {
		HBox hb = new HBox(); 
		Text prompt = new Text(promptString); 
		TextField tf = new TextField(); 
		hb.getChildren().add(prompt);
		hb.getChildren().add(tf); 
		return hb; 
	}

	/**
	 * Creates HBox with a text prompt to the left, combobox/dropdown to the right
	 * @param promptString - text prompt 
	 * @param dropdownOptions - String options to populate dropdown
	 * @return HBox 
	 */
	protected HBox setupPromptAndDropdown(String promptString, List<String> dropdownOptions) {
		HBox hb = new HBox(); 
		Text prompt = new Text(promptString); 
		ComboBox<String> dropdown = makeTextDropdown(dropdownOptions);
		dropdown.getSelectionModel().selectFirst();
		hb.getChildren().add(prompt);
		hb.getChildren().add(dropdown); 
		return hb; 
	}

	protected VBox setupPromptAndSlider(String promptString, int sliderMax) {
		HBox hb = new HBox();
		Text prompt = new Text(promptString);
		Slider slider = new Slider(0, sliderMax, (0 + sliderMax) / 2);
		Text sliderValue = new Text(Double.toString(slider.getValue()));
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
					Number old_val, Number new_val) {
				sliderValue.setText(Integer.toString((int)(double)new_val));
			}
		});
		hb.getChildren().add(prompt);
		hb.getChildren().add(slider);
		VBox vb = new VBox();
		vb.getChildren().add(hb);
		vb.getChildren().add(sliderValue);
		return vb; 
	}

	public ScrollPane makeTextScrollPane(List<String> options) {
		ScrollPane newPane = new ScrollPane();
		String allOptions = "";
		for (String s : options) {
			allOptions += s+"\n";
		}
		newPane.setContent(new Text(allOptions));
		return newPane; 
	}

	public ScrollPane makeImageScrollPane(File[] files) {
		ScrollPane newPane = new ScrollPane();
		VBox imageConsolidator = new VBox();
		for (File f : files) {
			ImageView oneImageView = new ImageView();
			oneImageView.setImage(new Image(f.toURI().toString()));
			imageConsolidator.getChildren().add(oneImageView);
		}
		newPane.setContent(imageConsolidator);
		return newPane; 
	}

}