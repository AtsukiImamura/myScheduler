package scheduler;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scheduler.common.constant.Constant;

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



		//root.getChildren().addAll(cr);
		root.setPrefHeight(Constant.APP_PREF_HEIGHT);
		root.setPrefWidth(Constant.APP_PREF_WIDTH);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("MyScheduler App 0.0");


	}

	public static void main(String[] args){
		launch(args);
	}

}
