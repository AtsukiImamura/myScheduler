package scheduler.view.workDisplayer;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import scheduler.bean.AttributeSelectionBean;
import scheduler.bean.MAttributeBean;
import scheduler.common.constant.Constant;
import scheduler.common.utils.Util;
import scheduler.facade.AttributeSelectionBeanFacade;
import scheduler.facade.MAttributeBeanFacade;

public class AttributeDetailController  implements Initializable{


	@FXML
	private VBox infoVBox;

	@FXML
	private Label dispNameLabel;

	@FXML
	private Label dispDetailLabel;

	@FXML
	private FlowPane selectionPane;

	@FXML
	private Button addSelectionButton;

	private MAttributeBean attribute;

	protected static AttributeDetailController instance;

	protected static Parent view;

    /**
     * singletonのインスタンスを返す
     * @return instance
     */
    public static AttributeDetailController getInstance() {
        return instance;
    }


    /**
     * 表示する
     */
    public void show() {
    	AttributesConfigController.getInstance().addChild(view);
    }


    public Parent getView(){
    	return view;
    }


	static {
        FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("scheduler/view/workDisplayer/attributeDetail.fxml");
        FlowPane root = new FlowPane(Orientation.HORIZONTAL);
        root.setPrefWidth(1500);
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
		addSelectionButton.getStyleClass().addAll("attr_selection_button_base","attr_selection_add_button");
	}


	public static boolean canEditAny(){
		for(Node row: AttributeDetailController.getInstance().selectionPane.getChildren()){
			if(!(row instanceof AttributeSelectionRow)){
				continue;
			}
			if(((AttributeSelectionRow)row).isEditing()){
				return false;
			}
		}
		return true;
	}


	public void redraw(){
		setAttribute(this.attribute);
	}


	private void clear(){
		dispNameLabel.setText("");
		dispDetailLabel.setText("");
		selectionPane.getChildren().clear();
	}


	public void setAttribute(MAttributeBean attr){
		selectionPane.getChildren().clear();

		this.attribute = attr;
		dispNameLabel.setText(attr.getAttrName());
		dispDetailLabel.setText(attr.getDetail());

		//選択タイプの場合だけ選択肢を表示
		if(attr.getCostamaizeType().equals(Constant.ATTRIBUTE_TYPE.FREE)){
			addSelectionButton.setVisible(false);
			return;
		}

		List<AttributeSelectionBean> selectionBeanList = AttributeSelectionBeanFacade.getInstance().findByAttributeCode(attr.getAttrCode());

		for(AttributeSelectionBean selectionBean: selectionBeanList){
			AttributeSelectionRow selectionRow = new AttributeSelectionRow(selectionBean);
			//selectionLabel.getStyleClass().addAll("attr_detail_selection_label");
			selectionPane.getChildren().add(selectionRow);
		}

		addSelectionButton.setVisible(true);

		addSelectionButton.setOnMouseClicked(event->{
			if(!canEditAny()){
				return;
			}
			AttributeSelectionRow createSelecton = new AttributeSelectionRow(attribute.getAttrCode());
			selectionPane.getChildren().add(createSelecton);
		});
	}


	@FXML
	public void onDeleteButtonClicked(ActionEvent ev){
		AttributesConfigController.getInstance().removeAttribute(this.attribute);
		MAttributeBeanFacade.getInstance().logicalDelete(attribute);
		this.attribute = null;
		this.clear();
	}







}
