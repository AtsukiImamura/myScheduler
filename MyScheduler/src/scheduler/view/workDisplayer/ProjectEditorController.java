package scheduler.view.workDisplayer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProjectEditorController implements Initializable{



	@FXML
	private VBox attributesVBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		System.out.println("ProjectEditorController initializing...");

		attributesVBox = new VBox();



		System.out.println("ProjectEditorController initialized");
	}


	@FXML
	public void onMyButtonClicked(ActionEvent e){
		System.out.println("my_button clicked!");

		for(int i=0;i<3;i++){
			HBox hbox = new HBox();
			Label label = new Label("attribute "+i);
			TextField tf = new TextField();
			hbox.getChildren().addAll(label,tf);
			attributesVBox.getChildren().add(hbox);
		}
	}





}
