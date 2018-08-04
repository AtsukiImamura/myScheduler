package scheduler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scheduler.common.constant.Constant;
import scheduler.controller.ProjectsController;

/**
 * スケジュール管理アプリ
 * @author ohmoon
 *
 */
public class App extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.show();
		Pane root = new Pane();

		ProjectsController projectsController = new ProjectsController();

		root.getChildren().add(projectsController.getView());

		root.setPrefHeight(Constant.APP_PREF_HEIGHT*0.6);
		root.setPrefWidth(Constant.APP_PREF_WIDTH);


		Scene scene = new Scene(root);

		scene.widthProperty().addListener((ov,oldValue,newValue)->{
			projectsController.setWidth(newValue.doubleValue());
		});
		scene.getStylesheets().add("main.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("MyScheduler App 0.0");


	}

	public static void main(String[] args){
		launch(args);
	}

}
