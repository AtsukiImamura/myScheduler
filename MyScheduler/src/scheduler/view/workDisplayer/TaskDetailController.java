package scheduler.view.workDisplayer;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import scheduler.bean.TaskBean;
import scheduler.common.constant.Constant;
import scheduler.common.utils.Util;
import scheduler.controller.ProjectsController;
import scheduler.facade.TaskFacade;

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

	@FXML
	private Button editTaskButton;

	@FXML
	private Button deleteButton;

	/** このビューが持つタスク */
	private TaskBean taskBean;

	private static  ScrollPane root;

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
    	WorkDispTabsController.getInstance().setTab(Constant.TAB_KIND.TASK,view);
    }


    /**
     * 指定するタスクの情報を表示する
     * @param task
     */
    public void show(TaskBean task){
    	this.setTask(task);
    	this.show();
    }


    public Parent getView(){
    	return view;
    }


	static {
        FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("scheduler/view/workDisplayer/taskDetail.fxml");
        root = new ScrollPane();
		root.setMinHeight(Constant.WORK_DISP_TAB_MAX_HEIGHT);
		root.setMaxHeight(Constant.WORK_DISP_TAB_MAX_HEIGHT);
		root.setVmax(Constant.WORK_DISP_SCROLL_VMAX);
		root.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        fxmlLoader.setRoot(root);
        try {
        	view = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        instance = fxmlLoader.getController();
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		editTaskButton.setDisable(true);
		deleteButton.setDisable(true);
	}


	@FXML
	public void onEditButtonClicked(ActionEvent ev){
		TaskEditorController taskEditorController = TaskEditorController.getInstance();
		taskEditorController.setTask(taskBean);
		taskEditorController.show();
	}

	@FXML
	public void onDeleteButtonClicked(ActionEvent ev){
		boolean deleted = ProjectsController.currentInstance().removeTask(this.taskBean);
		if(!deleted){
			return;
		}
		deleteButton.setDisable(true);
		editTaskButton.setDisable(true);
		TaskFacade.getInstance().logicalDelete(taskBean);
		this.clear();
	}


	/**
	 * 表示内容をクリアする
	 */
	private void clear(){
		labelProjectCode.setText("");
		labelTaskCode.setText("");
		labelTaskName.setText("");
		labelDetail.setText("");
		startDateLabel.setText("");
		finishDateLabel.setText("");

	}


	/**
	 * このビューに表示するタスクをセットする
	 * @param task
	 */
	public void setTask(TaskBean task){
		this.taskBean = task;
		labelProjectCode.setText(taskBean.getProjectCode());
		labelTaskCode.setText(taskBean.getTaskCode());
		labelTaskName.setText(taskBean.getTaskName());
		labelDetail.setText(taskBean.getDetail());
		startDateLabel.setText(Util.getBarFormatCalendarValue(taskBean.getStartAt(), true));
		finishDateLabel.setText(Util.getBarFormatCalendarValue(taskBean.getFinishAt(), true));

		editTaskButton.setDisable(false);
		deleteButton.setDisable(false);
	}



}
