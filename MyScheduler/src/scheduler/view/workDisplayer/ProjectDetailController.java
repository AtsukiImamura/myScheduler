package scheduler.view.workDisplayer;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import scheduler.bean.AttributeSelectionBean;
import scheduler.bean.MAttributeBean;
import scheduler.bean.ProjectBean;
import scheduler.bean.TAttributeBean;
import scheduler.common.constant.Constant;
import scheduler.common.utils.Util;
import scheduler.controller.ProjectsController;
import scheduler.facade.AttributeSelectionBeanFacade;
import scheduler.facade.MAttributeBeanFacade;
import scheduler.facade.TAttributeBeanFacade;

public class ProjectDetailController implements Initializable{


	@FXML
	private Label labelProjectCode;

	@FXML
	private Label labelProjectName;

	@FXML
	private Label labelDetail;

	@FXML
	private VBox attributesVBox;

	@FXML
	private Button addTaskButton;

	@FXML
	private Button editProjectButton;

	@FXML
	private Button deleteButton;


	private List<Label> attributeLabelList;

	private ProjectBean projectBean;

	private static  ScrollPane root;

	protected static ProjectDetailController instance;

	protected static Parent view;


    /**
     * singletonのインスタンスを返す
     * @return instance
     */
    public static ProjectDetailController getInstance() {
        return instance;
    }



    /**
     * 表示する
     */
    public void show() {
    	WorkDispTabsController.getInstance().setTab(Constant.TAB_KIND.PROJECT,view);
    }


    public Parent getView(){
    	return view;
    }

	static {
        FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("scheduler/view/workDisplayer/projectDetail.fxml");
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
        System.out.println("ProjectDetailController");
    }




	@Override
	public void initialize(URL location, ResourceBundle resources) {
		attributeLabelList = new ArrayList<Label>();
		deleteButton.setDisable(true);
	}


	/* 編集ボタン */
	@FXML
	public void onEditButtonClicked(ActionEvent ev){
		ProjectEditorController projectEditorController = ProjectEditorController.getInstance();
		projectEditorController.setProject(projectBean);
		projectEditorController.show();
	}


	/* 削除ボタン */
	@FXML
	public void onDeleteButtonClicked(ActionEvent ev){
		ProjectsController.currentInstance().removeProject(projectBean);
		this.projectBean = null;
		clearText();
		addTaskButton.setDisable(true);
		editProjectButton.setDisable(true);
		deleteButton.setDisable(true);
	}


	/* 追加ボタン */
	@FXML
	public void onAddTaskButtonClicked(ActionEvent ev){
		TaskEditorController.getInstance().show();
		TaskEditorController.getInstance().createNewTask(this.projectBean.getProjectCode());
	}


	/* 新規プロジェクトボタン */
	@FXML
	public void onCreateProjectButtonClicked(ActionEvent ev){
		ProjectEditorController.getInstance().show();
		ProjectEditorController.getInstance().createNewProject();
	}


	/**
	 * 表示している内容をクリアする
	 */
	private void clearText(){
		labelProjectCode.setText("");
		labelProjectName.setText("");
		labelDetail.setText("");
		attributeLabelList.forEach(label->{
			label.setText("");
		});
	}


	/**
	 * このビューにプロジェクトをセットする。
	 * @param project 表示するプロジェクト
	 */
	public void setProject(ProjectBean project){
		addTaskButton.setDisable(false);
		editProjectButton.setDisable(false);
		deleteButton.setDisable(false);
		String projectCode = project.getProjectCode();
		this.projectBean = project;

		this.labelProjectCode.setText(projectCode);
		this.labelProjectName.setText(project.getProjectName());
		this.labelDetail.setText(project.getDetail());

		List<MAttributeBean> mAttributeBeanList = MAttributeBeanFacade.getInstance().findAll();

		attributesVBox.getChildren().clear();

		//表示している属性ごとに処理する
		for(MAttributeBean mAttr : mAttributeBeanList){
			String attributeCode = mAttr.getAttrCode();

			HBox attributeHBox = new HBox();
			//属性名
			Label attributeTitleLabel = new Label(mAttr.getAttrName());
			attributeTitleLabel.getStyleClass().addAll("title_label");
			Label selectedValueLabel = new Label();
			selectedValueLabel.getStyleClass().addAll("project_detail_color","output_label","output_basic","displayer_font");
			TAttributeBean tAttr = TAttributeBeanFacade.getInstance().one(projectCode, attributeCode);

			//タイプごとに処理
			switch(mAttr.getCostamaizeType()){
			//選択
			case SELECTION:
				String selectionCode = tAttr.getSelectionCode();
				AttributeSelectionBean selectedValue = AttributeSelectionBeanFacade.getInstance().one(attributeCode, selectionCode);
				if(selectedValue == null){
					break;
				}
				selectedValueLabel.setText(selectedValue.getDispName());
				break;
			//自由入力
			case FREE:
				selectedValueLabel.setText(tAttr.getValue());
				break;
			}
			attributeLabelList.add(selectedValueLabel);
			attributeHBox.getChildren().addAll(attributeTitleLabel,selectedValueLabel);
			attributesVBox.getChildren().add(attributeHBox);
		}
	}

}
