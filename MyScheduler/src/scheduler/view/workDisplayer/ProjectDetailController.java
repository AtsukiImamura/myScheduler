package scheduler.view.workDisplayer;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import scheduler.bean.AttributeSelectionBean;
import scheduler.bean.MAttributeBean;
import scheduler.bean.ProjectBean;
import scheduler.bean.TAttributeBean;
import scheduler.common.constant.Constant;
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


	private ProjectBean projectBean;



	private MAttributeBeanFacade mAttributeBeanFacade;

	private TAttributeBeanFacade tAttributeBeanFacade;

	private AttributeSelectionBeanFacade attributeSelectionBeanFacade;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}


	@FXML
	public void onEditButtonClicked(ActionEvent ev){

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
		attributeSelectionBeanFacade = new  AttributeSelectionBeanFacade();

		attributeSelectionBeanFacade = new AttributeSelectionBeanFacade();


		int count = 0;

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
			attributeTitleLabel.getStyleClass().add("project_detail_title");
			selectedValueLabel.getStyleClass().addAll("project_detail_output_label","project_detail_output_basic","project_detail_font");
			attributeHBox.getChildren().addAll(attributeTitleLabel,selectedValueLabel);

			attributesVBox.getChildren().add(attributeHBox);
		}
	}




	/*
	@FXML
	public void onRegistButtonClicked(ActionEvent ev){

	}
	*/

}
