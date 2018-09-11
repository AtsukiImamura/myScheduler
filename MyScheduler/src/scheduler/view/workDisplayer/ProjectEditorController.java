package scheduler.view.workDisplayer;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import scheduler.App;
import scheduler.bean.AttributeSelectionBean;
import scheduler.bean.MAttributeBean;
import scheduler.bean.ProjectBean;
import scheduler.bean.StatusBean;
import scheduler.bean.TAttributeBean;
import scheduler.cellFactory.ColorCellFactory;
import scheduler.cellFactory.StatusShapeCell;
import scheduler.common.constant.Constant;
import scheduler.common.utils.Util;
import scheduler.controller.ProjectsController;
import scheduler.facade.AttributeSelectionBeanFacade;
import scheduler.facade.MAttributeBeanFacade;
import scheduler.facade.ProjectBeanFacade;
import scheduler.facade.StatusFacade;
import scheduler.facade.TAttributeBeanFacade;

public class ProjectEditorController implements Initializable{

	private final int INDEX_ATTRIBUTE_LABEL_TITLE = 0;

	private final int INDEX_ATTRIBUTE_INPUT = 1;



	@FXML
	private VBox attributesVBox;

	@FXML
	private TextArea detailTextArea;

	@FXML
	private Label labelProjectCode;

	@FXML
	private TextField textfieldProjectName;

	@FXML
	private ComboBox<String> statusCombo;

	private ProjectBeanFacade projectBeanFacade;

	private ProjectBean projectBean;

	private MAttributeBeanFacade mAttributeBeanFacade;

	private TAttributeBeanFacade tAttributeBeanFacade;

	private AttributeSelectionBeanFacade attributeSelectionBeanFacade;

	private List<MAttributeBean> mAttributeBeanList;

	protected static ProjectEditorController instance;

	protected static Scene SCENE;

	protected static Parent view;

	public final StringProperty projectCodeProperty = new SimpleStringProperty();

    /**
     * singletonのインスタンスを返す
     * @return instance
     */
    public static ProjectEditorController getInstance() {
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
        FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("scheduler/view/workDisplayer/workDisplayer.fxml");
		//FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("./workDisplayer.fxml");
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

		projectBeanFacade = new ProjectBeanFacade();
		projectBean = new ProjectBean();

		String newProjectCode = projectBeanFacade.createNewProjectCode();
		this.projectBean.setProjectCode(newProjectCode);

		mAttributeBeanFacade = new MAttributeBeanFacade();
		attributeSelectionBeanFacade = new AttributeSelectionBeanFacade();
		tAttributeBeanFacade = new TAttributeBeanFacade();

		initScreen();
	}


	/**
	 * 登録ボタンが押されたとき
	 * @param e
	 */
	@FXML
	public void onRegistButtonClicked(ActionEvent ev){

		System.out.println("onRegistButtonClicked");

		this.projectBean.setProjectName(this.textfieldProjectName.getText());
		this.projectBean.setDetail(this.detailTextArea.getText());
		this.projectBean.setStatus(statusCombo.getValue());

		projectBeanFacade.save(projectBean);

		String projectCode = projectBean.getProjectCode();

		int index = 0;
		for(MAttributeBean mAttr : mAttributeBeanList){
			String mAttrCode = mAttr.getAttrCode();
			TAttributeBean tAttr = tAttributeBeanFacade.one(this.projectBean.getProjectCode(), mAttrCode);
			if(tAttr == null){
				tAttr = new TAttributeBean(projectCode,mAttrCode);
			}

			//属性の入力タイプで分ける
			switch(mAttr.getCostamaizeType()){
			//タイプ：選択
			case Constant.ATTRIBUTE_COSTAMIZE_TYPE_SELECT:
				try{
					@SuppressWarnings("unchecked")
					ComboBox<String> attributeSelectBox = (ComboBox<String>)(((HBox)attributesVBox.getChildren().get(index)).getChildren().get(INDEX_ATTRIBUTE_INPUT));

					String selectedValue = (String)attributeSelectBox.getValue();
					String selectionCode = Util.decodeSelectionCodeFromSelection(selectedValue,Constant.LENGTH_OF_SELECTION_CODE);
					tAttr.setSelectionCode(selectionCode);
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			//タイプ：自由入力
			case Constant.ATTRIBUTE_COSTAMIZE_TYPE_FREE:
				try{
					TextField attributeInput = (TextField)(((HBox)attributesVBox.getChildren().get(index)).getChildren().get(INDEX_ATTRIBUTE_INPUT));
					tAttr.setValue(attributeInput.getText());
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			}
			tAttributeBeanFacade.save(tAttr);
			index++;
		}

		ProjectsController.currentInstance().addProject(projectBean);
		ProjectDetailController.getInstance().setProject(projectBean);
		ProjectDetailController.getInstance().show();
	}


	/**
	 * クリアボタンが押されたとき
	 * @param e
	 */
	@FXML
	public void onClearButtonClicked(ActionEvent e){
		attributesVBox.getChildren().clear();
		initScreen();
	}



	/**
	 * プロジェクトの内容をセットする（編集用）
	 * @param project
	 */
	public void setProject(ProjectBean project){
		String projectCode = project.getProjectCode();
		this.projectBean = project;

		this.labelProjectCode.setText(projectCode);

		textfieldProjectName.setText(project.getProjectName());
		detailTextArea.setText(project.getDetail());



		AttributeSelectionBeanFacade attributeSelectionBeanFacade = new  AttributeSelectionBeanFacade();
		int count = 0;

		//表示している属性ごとに処理する
		for(MAttributeBean aAttr : mAttributeBeanList){
			//案件のもつ属性の値を引き当て
			String attrCode = aAttr.getAttrCode();
			TAttributeBean prjAttr = tAttributeBeanFacade.one(projectCode,attrCode);
			if(prjAttr == null){
				continue;
			}

			//属性の入力タイプで分ける
			switch(aAttr.getCostamaizeType()){
			//選択
			case Constant.ATTRIBUTE_COSTAMIZE_TYPE_SELECT:
				//案件の選択値番号
				String selectionCode =  prjAttr.getSelectionCode();
				if(selectionCode == null){
					continue;
				}

				//案件の選択コードから選択された値を引き当て
				AttributeSelectionBean selectedValue = attributeSelectionBeanFacade.one(aAttr.getAttrCode(),selectionCode);
				try{
					//コンボボックスにセット
					@SuppressWarnings("unchecked")
					ComboBox<String> attributeSelectBox = (ComboBox<String>)(((HBox)attributesVBox.getChildren().get(count)).getChildren().get(INDEX_ATTRIBUTE_INPUT));
					attributeSelectBox.setValue(Util.createSelection(selectedValue));

				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			//自由入力
			case Constant.ATTRIBUTE_COSTAMIZE_TYPE_FREE:
				try{
					TextField attributeInput = (TextField)(((HBox)attributesVBox.getChildren().get(count)).getChildren().get(INDEX_ATTRIBUTE_INPUT));
					attributeInput.setText(prjAttr.getValue());
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			}
			count++;
		}

	}


	private void initScreen(){
		this.labelProjectCode.setText(this.projectBean.getProjectCode());
		this.textfieldProjectName.setText("");
		this.detailTextArea.setText("");


		StatusFacade statusFacade = new StatusFacade();
		List<StatusBean> statusList = statusFacade.findAll();
		for(StatusBean status : statusList){
			/*
			 * この部分は https://examples.javacodegeeks.com/desktop-java/javafx/combobox/javafx-combobox-example/
			 * を参照せよ
			 */
			statusCombo.getItems().add(status.getStatusCode());
			statusCombo.setButtonCell(new StatusShapeCell());
			statusCombo.setCellFactory(new ColorCellFactory());
			statusCombo.getStyleClass().add("project_edit_attribute_selection");
		}

		//属性リスト
		mAttributeBeanList = mAttributeBeanFacade.findAll();

		for(MAttributeBean mAttribute : mAttributeBeanList){
			HBox attributeHBox = new HBox();
			//属性名
			Label attributeTitleLabel = new Label(mAttribute.getAttrName());
			attributeTitleLabel.getStyleClass().add("project_edit_title");
			attributeHBox.getChildren().add(attributeTitleLabel);

			//タイプごとに処理
			switch(mAttribute.getCostamaizeType()){
			//選択
			case Constant.ATTRIBUTE_COSTAMIZE_TYPE_SELECT:
				String attributeCode = mAttribute.getAttrCode();
				//属性に対する選択肢
				List<AttributeSelectionBean> selectionList = attributeSelectionBeanFacade.findByAttributeCode(attributeCode);

				ComboBox<String> attributeSelectBox = new ComboBox<String>();
				for(AttributeSelectionBean selectBean : selectionList){
					attributeSelectBox.getItems().add(Util.createSelection(selectBean));
				}
				//attributeSelectBox.setValue(attributeSelectBox.getItems().get(0));
				attributeSelectBox.getStyleClass().add("project_edit_attribute_selection");
				attributeHBox.getChildren().add(attributeSelectBox);

				break;
			//自由入力
			case Constant.ATTRIBUTE_COSTAMIZE_TYPE_FREE:
				TextField attributeInput = new TextField();
				attributeInput.getStyleClass().add("project_edit_input_basic");
				attributeInput.getStyleClass().add("project_edit_input_textfield");
				attributeHBox.getChildren().add(attributeInput);
				break;
			}

			attributesVBox.getChildren().add(attributeHBox);
		}
	}




	public void test(){
		System.out.println("test ...OK");
	}


}
