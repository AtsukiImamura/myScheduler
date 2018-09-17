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
import javafx.scene.layout.HBox;
import scheduler.bean.MAttributeBean;
import scheduler.common.constant.Constant;
import scheduler.common.utils.Util;
import scheduler.facade.MAttributeBeanFacade;

public class AttributesConfigController  implements Initializable{

	@FXML
	private FlowPane attrListFlow;

	@FXML
	private FlowPane detailPane;

	@FXML
	private Label divStoneLabel;

	@FXML
	private Label divStatusLabel;

	@FXML
	private Button addAttributeButton;

	private List<MAttributeBean> attrList;



	protected static AttributesConfigController instance;

	protected static Parent view;

    /**
     * singletonのインスタンスを返す
     * @return instance
     */
    public static AttributesConfigController getInstance() {
        return instance;
    }


    /**
     * 表示する
     */
    public void show() {
    	WorkDispTabsController.getInstance().setTab(Constant.TAB_KIND.CONFIG,view);
    }


    public Parent getView(){
    	return view;
    }


	static {
        FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("scheduler/view/workDisplayer/attributesConfigration.fxml");
        HBox root = new HBox();
        root.setStyle("-fx-padding: 20 0 10 20;");
        fxmlLoader.setRoot(root);
        try {
        	view = fxmlLoader.load();
            instance = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	public void addChild(Parent parent){
		detailPane.getChildren().clear();
		detailPane.getChildren().add(parent);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		attrListFlow.setOrientation(Orientation.HORIZONTAL);
		detailPane.setMinWidth(900);
		initAttrFlow(MAttributeBeanFacade.getInstance().findAll());

	}


	public void addAttribute(MAttributeBean attr){
		this.attrList.add(attr);
		AttributeConfigDispRow attrRow = getInitializedRow(attr);
		attrListFlow.getChildren().add(attrRow);
	}


	public void removeAttribute(MAttributeBean attr){
		for(MAttributeBean tmpAttr: attrList){
			if(tmpAttr.getAttrCode().equals(attr.getAttrCode())){
				attrList.remove(attr);
				break;
			}
		}

		for(Node child: attrListFlow.getChildren()){
			if(!(child instanceof AttributeConfigDispRow)){
				continue;
			}
			AttributeConfigDispRow row = (AttributeConfigDispRow)child;
			if(row.getAttribute().getAttrCode().equals(attr.getAttrCode())){
				 attrListFlow.getChildren().remove(row);
				break;
			}
		}
	}


	private void initAttrFlow(List<MAttributeBean> attrList){
		this.attrList = attrList;
		attrListFlow.getChildren().clear();
		for(MAttributeBean attr: attrList){
			AttributeConfigDispRow attrRow = getInitializedRow(attr);
			attrListFlow.getChildren().add(attrRow);
		}
	}


	private AttributeConfigDispRow getInitializedRow(MAttributeBean attr){
		AttributeConfigDispRow attrRow = new AttributeConfigDispRow(attr);

		attrRow.setOnMouseClicked(event->{
			AttributeDetailController.getInstance().show();
			AttributeDetailController.getInstance().setAttribute(attrRow.getAttribute());
			attrListFlow.getChildren().forEach(child->{
				if(!(child instanceof AttributeConfigDispRow) || child.equals(attrRow)){
					return;
				}
				((AttributeConfigDispRow)child).setClicked(false);
			});
			attrRow.setClicked(true);
		});
		attrRow.setOnMouseEntered(event->{
			attrListFlow.getChildren().forEach(child->{
				if(!(child instanceof AttributeConfigDispRow) || child.equals(attrRow)){
					return;
				}
				((AttributeConfigDispRow)child).onMouseExited();
			});
			attrRow.onMouseEntered();
		});
		attrRow.setOnMouseExited(event->{
			attrRow.onMouseExited();
		});

		return attrRow;
	}


	@FXML
	public void onAddAttributeButtonClicked(ActionEvent event){
		AttributeEditController.getInstance().createNewAttribute();
		AttributeEditController.getInstance().show();
	}

}
