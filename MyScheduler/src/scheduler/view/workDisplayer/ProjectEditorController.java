package scheduler.view.workDisplayer;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import scheduler.bean.AttributeSelectionBean;
import scheduler.bean.MAttributeBean;
import scheduler.bean.ProjectBean;
import scheduler.bean.StatusBean;
import scheduler.bean.TAttributeBean;
import scheduler.cellFactory.AttributesCellFactory;
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

	@FXML
	private Button clearButton;

	@FXML
	private Button registButton;

	private BooleanProperty disableRegistProperty;

	private ProjectBean projectBean;

	private List<MAttributeBean> mAttributeBeanList;

	protected static ProjectEditorController instance;

	protected static Parent view;

	private static  ScrollPane root;

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
    	WorkDispTabsController.getInstance().setTab(Constant.TAB_KIND.PROJECT,view);
    }


    public Parent getView(){
    	return view;
    }


    public void setWidth(double width){
    	root.setPrefWidth(width);
    }


	static {
        FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("scheduler/view/workDisplayer/workDisplayer.fxml");
        root = new ScrollPane();
		root.setMinHeight(Constant.WORK_DISP_TAB_MAX_HEIGHT);
		root.setMaxHeight(Constant.WORK_DISP_TAB_MAX_HEIGHT);
		root.setVmax(Constant.WORK_DISP_SCROLL_VMAX);
		root.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        fxmlLoader.setRoot(root);
        try {
        	view = fxmlLoader.load();
            instance = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




	@Override
	public void initialize(URL location, ResourceBundle resources) {

		projectBean = new ProjectBean();
		disableRegistProperty = new SimpleBooleanProperty(true);

		//registButton.setDisable(true);
		registButton.disableProperty().bind(disableRegistProperty);
		textfieldProjectName.setOnKeyTyped(event->{
			disableRegistProperty.set(!validateInput());
		});
		textfieldProjectName.setOnInputMethodTextChanged(event->{
			disableRegistProperty.set(!validateInput());
		});
		statusCombo.setOnHidden(event->{
			disableRegistProperty.set(!validateInput());
		});

		String newProjectCode = ProjectBeanFacade.getInstance().createNewProjectCode();
		this.projectBean.setProjectCode(newProjectCode);



		initScreen();
	}


	private boolean validateInput(){
		String text = textfieldProjectName.getText();
		if(text == null || text.equals("")){
			return false;
		}
		String status = statusCombo.getValue();
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

		if(!validateInput()){
			return;
		}

		this.projectBean.setProjectName(this.textfieldProjectName.getText());
		this.projectBean.setDetail(this.detailTextArea.getText());
		this.projectBean.setStatus(statusCombo.getValue());

		ProjectBeanFacade.getInstance().save(projectBean);

		String projectCode = projectBean.getProjectCode();

		int index = 0;
		for(MAttributeBean mAttr : mAttributeBeanList){
			String mAttrCode = mAttr.getAttrCode();
			TAttributeBean tAttr = TAttributeBeanFacade.getInstance().one(this.projectBean.getProjectCode(), mAttrCode);
			if(tAttr == null){
				tAttr = new TAttributeBean(projectCode,mAttrCode);
			}

			//属性の入力タイプで分ける
			switch(mAttr.getCostamaizeType()){
			//タイプ：選択
			case SELECTION:
				try{
					@SuppressWarnings("unchecked")
					ComboBox<AttributeSelectionBean> attributeSelectBox = (ComboBox<AttributeSelectionBean>)(((HBox)attributesVBox.getChildren().get(index)).getChildren().get(INDEX_ATTRIBUTE_INPUT));

					AttributeSelectionBean selectedValue = (AttributeSelectionBean)attributeSelectBox.getValue();
					if(selectedValue == null){
						break;
					}
					String selectionCode = selectedValue.getSelectionCode();
					tAttr.setSelectionCode(selectionCode);
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			//タイプ：自由入力
			case FREE:
				try{
					TextField attributeInput = (TextField)(((HBox)attributesVBox.getChildren().get(index)).getChildren().get(INDEX_ATTRIBUTE_INPUT));
					tAttr.setValue(attributeInput.getText());
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			}
			TAttributeBeanFacade.getInstance().save(tAttr);
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
	 * 新しいプロジェクトをセットする
	 */
	public void createNewProject(){
		ProjectBean project = new ProjectBean();
		project.setProjectCode(ProjectBeanFacade.getInstance().createNewProjectCode());
		this.setProject(project);
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

		statusCombo.setValue(project.getStatus());

		this.disableRegistProperty.set(!this.validateInput());


		AttributeSelectionBeanFacade attributeSelectionBeanFacade = new  AttributeSelectionBeanFacade();
		int count = 0;

		//表示している属性ごとに処理する
		for(MAttributeBean aAttr : mAttributeBeanList){
			//案件のもつ属性の値を引き当て
			String attrCode = aAttr.getAttrCode();
			TAttributeBean prjAttr = TAttributeBeanFacade.getInstance().one(projectCode,attrCode);
			if(prjAttr == null){
				continue;
			}

			//属性の入力タイプで分ける
			switch(aAttr.getCostamaizeType()){
			//選択
			case SELECTION:
				//案件の選択値番号
				String selectionCode =  prjAttr.getSelectionCode();
				if(selectionCode == null){
					continue;
				}

				//案件の選択コードから選択された値を引き当て
				AttributeSelectionBean selectedValue = attributeSelectionBeanFacade.one(aAttr.getAttrCode(),selectionCode);
				if(selectedValue == null){
					break;
				}
				try{
					//コンボボックスにセット
					@SuppressWarnings("unchecked")
					ComboBox<AttributeSelectionBean> attributeSelectBox = (ComboBox<AttributeSelectionBean>)(((HBox)attributesVBox.getChildren().get(count)).getChildren().get(INDEX_ATTRIBUTE_INPUT));

					//Pair<String,String> codes = new Pair<String,String>(aAttr.getAttrCode(),selectedValue.getSelectionCode());
					int index = attributeSelectBox.getItems().indexOf(selectedValue);
					if(index < 0){
						break;
					}
					attributeSelectBox.setValue(attributeSelectBox.getItems().get(index));
					//attributeSelectBox.value
				}catch(Exception e){
					e.printStackTrace();
				}
				break;
			//自由入力
			case FREE:
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
			statusCombo.getItems().add(status.getCode());
		}

		statusCombo.setButtonCell(new StatusShapeCell());
		statusCombo.setCellFactory(new ColorCellFactory());
		statusCombo.getStyleClass().add("project_edit_attribute_selection");

		//属性リスト
		mAttributeBeanList = MAttributeBeanFacade.getInstance().findAll();

		for(MAttributeBean mAttribute : mAttributeBeanList){
			HBox attributeHBox = new HBox();
			//属性名
			Label attributeTitleLabel = new Label(mAttribute.getAttrName());
			attributeTitleLabel.getStyleClass().add("title_label");
			attributeHBox.getChildren().add(attributeTitleLabel);

			//タイプごとに処理
			switch(mAttribute.getCostamaizeType()){
			//選択
			case SELECTION:
				String attributeCode = mAttribute.getAttrCode();
				//属性に対する選択肢
				List<AttributeSelectionBean> selectionList = AttributeSelectionBeanFacade.getInstance().findByAttributeCode(attributeCode);

				ComboBox<AttributeSelectionBean> attributeSelectBox = new ComboBox<AttributeSelectionBean>();
				attributeSelectBox.setButtonCell(AttributesCellFactory.getAttributeCell());
				attributeSelectBox.setCellFactory(new AttributesCellFactory());
				for(AttributeSelectionBean selectBean : selectionList){
					attributeSelectBox.getItems().add(selectBean);
				}
				//attributeSelectBox.setValue(attributeSelectBox.getItems().get(0));
				attributeSelectBox.getStyleClass().add("project_edit_attribute_selection");
				attributeHBox.getChildren().add(attributeSelectBox);

				break;
			//自由入力
			case FREE:
				TextField attributeInput = new TextField();
				attributeInput.getStyleClass().addAll("project_editor_color","input_textfield","input_basic","displayer_font");
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
