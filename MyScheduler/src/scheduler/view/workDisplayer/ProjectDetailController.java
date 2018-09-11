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
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import scheduler.App;
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

	private List<Label> attributeLabelList;

	private ProjectBean projectBean;

	private MAttributeBeanFacade mAttributeBeanFacade;

	private TAttributeBeanFacade tAttributeBeanFacade;

	private AttributeSelectionBeanFacade attributeSelectionBeanFacade;

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
    	App.setEditorHBoxField(view,true);
    }


    public Parent getView(){
    	return view;
    }

	static {
        FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("scheduler/view/workDisplayer/projectDetail.fxml");
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
	}


	@FXML
	public void onEditButtonClicked(ActionEvent ev){
		ProjectEditorController projectEditorController = ProjectEditorController.getInstance();
		projectEditorController.setProject(projectBean);
		projectEditorController.show();
	}


	@FXML
	public void onDeleteButtonClicked(ActionEvent ev){
		ProjectsController.currentInstance().removeProject(projectBean);
		this.projectBean = null;
		clearText();

	}

	@FXML
	public void onAddTaskButtonClicked(ActionEvent ev){
		TaskEditorController.getInstance().show();
		TaskEditorController.getInstance().createNewTask(this.projectBean.getProjectCode());
	}


	private void clearText(){
		labelProjectCode.setText("");
		labelProjectName.setText("");
		labelDetail.setText("");
		attributeLabelList.forEach(label->{
			label.setText("");
		});
	}


	public void setProject(ProjectBean project){
		String projectCode = project.getProjectCode();
		this.projectBean = project;

		this.labelProjectCode.setText(projectCode);
		this.labelProjectName.setText(project.getProjectName());
		this.labelDetail.setText(project.getDetail());

		mAttributeBeanFacade = new MAttributeBeanFacade();
		List<MAttributeBean> mAttributeBeanList = mAttributeBeanFacade.findAll();

		tAttributeBeanFacade = new TAttributeBeanFacade();
		attributeSelectionBeanFacade = new AttributeSelectionBeanFacade();

		attributesVBox.getChildren().clear();

		//表示している属性ごとに処理する
		for(MAttributeBean mAttr : mAttributeBeanList){
			String attributeCode = mAttr.getAttrCode();

			HBox attributeHBox = new HBox();
			//属性名
			Label attributeTitleLabel = new Label(mAttr.getAttrName());
			Label selectedValueLabel = new Label();
			TAttributeBean tAttr = tAttributeBeanFacade.one(projectCode, attributeCode);

			//タイプごとに処理
			switch(mAttr.getCostamaizeType()){
			//選択
			case Constant.ATTRIBUTE_COSTAMIZE_TYPE_SELECT:
				String selectionCode = tAttr.getSelectionCode();
				AttributeSelectionBean selectedValue = attributeSelectionBeanFacade.one(attributeCode, selectionCode);
				selectedValueLabel.setText(selectedValue.getDispName());
				break;
			//自由入力
			case Constant.ATTRIBUTE_COSTAMIZE_TYPE_FREE:
				selectedValueLabel.setText(tAttr.getValue());
				break;
			}
			attributeLabelList.add(selectedValueLabel);
			attributeTitleLabel.getStyleClass().add("project_detail_title");
			selectedValueLabel.getStyleClass().addAll("project_detail_output_label","project_detail_output_basic","project_detail_font");
			attributeHBox.getChildren().addAll(attributeTitleLabel,selectedValueLabel);

			attributesVBox.getChildren().add(attributeHBox);
		}
	}

}
