package scheduler;

import java.util.Calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scheduler.bean.TaskBean;
import scheduler.view.calendar.CalendarViewTask;

public class Test extends Application{


	@Override
	public void start(Stage stage){

		stage.show();

		//CalenderDay cd = new CalenderDay();
		//CalenderRow cr = new CalenderRow();
		//cr.setTranslateY(100);


		TaskBean task = new TaskBean();
		Calendar startAt = Calendar.getInstance();
		startAt.set(2018, 1, 1);
		Calendar finishAt = Calendar.getInstance();
		finishAt.set(2018, 1, 5);
		task.setTaskCode("T-001");
		task.setProjectDetail("詳細詳細詳細......");
		task.setStartAt(startAt);
		task.setFinishAt(finishAt);
		CalendarViewTask calendarViewTask = new CalendarViewTask(task);
		Calendar viewStartAt = (Calendar)finishAt.clone();
		viewStartAt.add(Calendar.DAY_OF_MONTH, -1);
		calendarViewTask.setViewFinishAt(viewStartAt);



		/*
		Scene scene = new Scene(new Group(),840,680);




		ObservableList<Node> content = ((Group)scene.getRoot()).getChildren();
		content.addAll(cd,cr);
*/
		Pane root = new Pane();
		root.getChildren().addAll(calendarViewTask);
		root.setPrefHeight(500);
		root.setPrefWidth(700);
		Scene scene = new Scene(root);
		scene.getStylesheets().add("main.css");
		stage.setScene(scene);
		stage.setTitle("test!!");

	}

	public static void main(String[] args){
		launch(args);
	}


}
