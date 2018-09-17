package scheduler.view.workDisplayer;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import scheduler.bean.StoneBean;
import scheduler.bean.TaskBean;
import scheduler.cellFactory.StoneColorCellFactory;
import scheduler.cellFactory.StoneShapeCell;
import scheduler.common.constant.Constant;
import scheduler.common.utils.Util;
import scheduler.controller.ProjectsController;
import scheduler.facade.StoneFacade;
import scheduler.facade.TaskFacade;
import scheduler.view.dateSelector.DateSelectInput;

public class TaskEditorController implements Initializable{

	private final int INDEX_ATTRIBUTE_LABEL_TITLE = 0;

	private final int INDEX_ATTRIBUTE_INPUT = 1;


	@FXML
	private TextArea detailTextArea;

	@FXML
	private Label labelTaskCode;

	@FXML
	private Label labelProjectCode;

	@FXML
	private TextField textfieldTaskName;

	@FXML
	private DateSelectInput startDateInput;

	@FXML
	private DateSelectInput finishDateInput;

	@FXML
	private ComboBox<String> stoneCombo;

	@FXML
	private Button registButton;

	@FXML
	private FlowPane flowPane;


	private BooleanProperty disableRegistProperty;

	private TaskFacade taskBeanFacade;

	private TaskBean taskBean = null;

	private static  ScrollPane root;

	protected static TaskEditorController instance;

	protected static Scene SCENE;

	protected static Parent view;

	public final StringProperty taskCodeProperty = new SimpleStringProperty();

    /**
     * singletonのインスタンスを返す
     * @return instance
     */
    public static TaskEditorController getInstance() {
        return instance;
    }



    /**
     * 表示する
     */
    public void show() {
    	WorkDispTabsController.getInstance().setTab(Constant.TAB_KIND.TASK,view);
    }


    public Parent getView(){
    	return view;
    }


	static {
        FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("scheduler/view/workDisplayer/taskEdit.fxml");
        root = new ScrollPane();
		root.setMinHeight(Constant.WORK_DISP_TAB_MAX_HEIGHT);
		root.setVmax(Constant.WORK_DISP_SCROLL_VMAX);
		root.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        fxmlLoader.setRoot(root);
        try {
        	view = fxmlLoader.load();
        	SCENE = new Scene(view);
            SCENE.getStylesheets().add("main.css");
            instance = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




	@Override
	public void initialize(URL location, ResourceBundle resources) {

		taskBeanFacade = new TaskFacade();

		startDateInput.selectedProperty.addListener((ov,oldValue,newValue)->{
			if(!newValue){
				return;
			}
			finishDateInput.setDisableBefore(startDateInput.getSelectedDate());
		});

		finishDateInput.selectedProperty.addListener((ov,oldValue,newValue)->{
			if(!newValue){
				return;
			}
			startDateInput.setDisableAfter(finishDateInput.getSelectedDate());
		});

		disableRegistProperty = new SimpleBooleanProperty(true);
		registButton.disableProperty().bind(disableRegistProperty);
		textfieldTaskName.setOnKeyTyped(event->{
			disableRegistProperty.set(!validateInput());
		});
		textfieldTaskName.setOnInputMethodTextChanged(event->{
			disableRegistProperty.set(!validateInput());
		});
		stoneCombo.setOnHidden(event->{
			disableRegistProperty.set(!validateInput());
		});

		initScreen();
	}



	private boolean validateInput(){
		String text = textfieldTaskName.getText();
		if(text == null || text.equals("")){
			return false;
		}
		String status = stoneCombo.getValue();
		if(status == null || status.equals("")){
			return false;
		}
		return true;
	}


	/**
	 * 登録ボタンが押されたとき
	 * @param e
	 */
	@FXML
	public void onRegistButtonClicked(ActionEvent ev){
		if(this.taskBean == null){
			return;
		}
		this.taskBean.setTaskName(textfieldTaskName.getText());
		this.taskBean.setDetail(detailTextArea.getText());
		this.taskBean.setStartAt(startDateInput.getSelectedDate());
		this.taskBean.setFinishAt(finishDateInput.getSelectedDate());
		this.taskBean.setStoneCode(this.stoneCombo.getValue());

		taskBeanFacade.save(taskBean);

		ProjectsController.currentInstance().addTask(this.taskBean);
		TaskDetailController.getInstance().show(this.taskBean);
	}


	/**
	 * クリアボタンが押されたとき
	 * @param e
	 */
	@FXML
	public void onClearButtonClicked(ActionEvent e){
		initScreen();
	}



	public void setTask(TaskBean task){
		this.taskBean = task;
		labelProjectCode.setText(task.getProjectCode());
		labelTaskCode.setText(task.getTaskCode());
		textfieldTaskName.setText(task.getTaskName());
		detailTextArea.setText(task.getDetail());
		startDateInput.setDate(task.getStartAt());
		finishDateInput.setDate(task.getFinishAt());
		stoneCombo.setValue(task.getCode());

		disableRegistProperty.set(!this.validateInput());
	}


	public void createNewTask(String projectCode){
		taskBean = new TaskBean();
		taskBean.setProjectCode(projectCode);
		labelProjectCode.setText(projectCode);

		String newTaskCode = taskBeanFacade.createNewTaskCode(projectCode);
		taskBean.setTaskCode(newTaskCode);
		labelTaskCode.setText(newTaskCode);

	}




	private void initScreen(){
		StoneFacade stoneFacade = new StoneFacade();
		List<StoneBean> stoneList = stoneFacade.findAll();
		for(StoneBean stone : stoneList){
			/*
			 * この部分は https://examples.javacodegeeks.com/desktop-java/javafx/combobox/javafx-combobox-example/
			 * を参照せよ
			 */
			stoneCombo.getItems().add(stone.getCode());
			stoneCombo.setCellFactory(new StoneColorCellFactory());
			stoneCombo.setButtonCell(new StoneShapeCell());
			//stoneCombo.getStyleClass().add("project_edit_attribute_selection");
		}
	}




	public void test(){
		System.out.println("test ...OK");
	}


}
