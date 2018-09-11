package scheduler.view.workDisplayer;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import scheduler.App;
import scheduler.bean.TaskBean;
import scheduler.common.utils.Util;

public class TaskDetailController implements Initializable{


	@FXML
	private Label labelProjectCode;

	@FXML
	private Label labelTaskCode;

	@FXML
	private Label labelTaskName;

	@FXML
	private Label labelDetail;

	@FXML
	private Label startDateLabel;

	@FXML
	private Label finishDateLabel;


	private TaskBean taskBean;

	protected static TaskDetailController instance;

	protected static Parent view;


    /**
     * singletonのインスタンスを返す
     * @return instance
     */
    public static TaskDetailController getInstance() {
        return instance;
    }



    /**
     * 表示する
     */
    public void show() {
    	App.setEditorHBoxField(view,true);
    }

    public void show(TaskBean task){
    	this.setTask(task);
    	this.show();
    }


    public Parent getView(){
    	return view;
    }

	static {
        FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("scheduler/view/workDisplayer/taskDetail.fxml");
        try {
        	view = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        instance = fxmlLoader.getController();
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}


	@FXML
	public void onEditButtonClicked(ActionEvent ev){
		TaskEditorController taskEditorController = TaskEditorController.getInstance();
		taskEditorController.setTask(taskBean);
		taskEditorController.show();
	}


	public void setTask(TaskBean task){
		this.taskBean = task;
		labelProjectCode.setText(taskBean.getProjectCode());
		labelTaskCode.setText(taskBean.getTaskCode());
		labelTaskName.setText(taskBean.getTaskName());
		labelDetail.setText(taskBean.getDetail());
		startDateLabel.setText(Util.getBarFormatCalendarValue(taskBean.getStartAt(), true));
		finishDateLabel.setText(Util.getBarFormatCalendarValue(taskBean.getFinishAt(), true));
	}


}
