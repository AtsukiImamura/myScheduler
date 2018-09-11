package scheduler;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import scheduler.common.constant.Constant;
import scheduler.controller.ProjectsController;
import scheduler.view.workDisplayer.ProjectEditorController;

/**
 * スケジュール管理アプリ
 * @author ohmoon
 *
 */
public class App extends Application{


	private ProjectsController projectsController;

	private VBox appVBox;

	private static HBox editorHBox;

	public static void setEditorHBoxField(Parent parent,boolean clear){
		if(clear){
			editorHBox.getChildren().clear();
		}
		editorHBox.getChildren().add(parent);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		appVBox = new VBox();
		editorHBox = new HBox();
		appVBox.getChildren().add(editorHBox);

		editorHBox.getChildren().add(ProjectEditorController.getInstance().getView());

		projectsController = new ProjectsController();
		appVBox.getChildren().add(projectsController.getView());

		Pane root = new Pane();
		root.setBackground(new Background(new BackgroundFill(Color.WHITE,new CornerRadii(0),new Insets(0))));
		root.getChildren().add(appVBox);
		root.setPrefHeight(Constant.APP_PREF_HEIGHT);
		root.setPrefWidth(Constant.APP_PREF_WIDTH);

		Scene scene = new Scene(root);
		scene.getStylesheets().add("main.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("MyScheduler App 0.0");
		primaryStage.show();




		//横幅変化時のリスナー
		scene.widthProperty().addListener((ov,oldValue,newValue)->{
			projectsController.setWidth(newValue.doubleValue());
		});

		//横幅変化時のリスナー
		scene.heightProperty().addListener((ov,oldValue,newValue)->{
			//projectsController.setWidth(newValue.doubleValue());
		});
	}

	public static void main(String[] args){
		launch(args);
	}

}
