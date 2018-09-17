package scheduler.view.workDisplayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import scheduler.bean.MAttributeBean;
import scheduler.common.constant.Constant;
import scheduler.common.utils.Util;
import scheduler.facade.MAttributeBeanFacade;

public class AttributeEditController  implements Initializable{

	@FXML
	private TextField dispNameInput;

	@FXML
	private TextArea detailTextArea;

	@FXML
	private ToggleGroup raidToggle;

	private final int RADIO_INDEX_FREE = 0;

	private final int RADIO_INDEX_SELECTION = 1;

	private MAttributeBean attribute = null;

	protected static AttributeEditController instance;

	protected static Parent view;

    /**
     * singletonのインスタンスを返す
     * @return instance
     */
    public static AttributeEditController getInstance() {
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
        FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("scheduler/view/workDisplayer/attributeEdit.fxml");
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



	public void createNewAttribute(){
		MAttributeBean attr = new MAttributeBean();
		attr.setAttrCode(MAttributeBeanFacade.getInstance().createNewCode());
		attr.setCostamaizeType(Constant.ATTRIBUTE_TYPE.FREE);
		this.setAttribute(attr);
	}


	private void setAttribute(MAttributeBean attribute){
		this.attribute = attribute;
		dispNameInput.setText(attribute.getAttrName());
		detailTextArea.setText(attribute.getDetail());
		switch(attribute.getCostamaizeType()){
		case FREE:
			raidToggle.selectToggle(raidToggle.getToggles().get(this.RADIO_INDEX_FREE));
			break;
		case SELECTION:
			raidToggle.selectToggle(raidToggle.getToggles().get(this.RADIO_INDEX_SELECTION));
			break;
		default:
			break;
		}
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}


	@FXML
	public void onRegistButtonClicked(ActionEvent ev){
		String name = dispNameInput.getText();
		if(name == null || name.equals("")){
			return;
		}
		this.attribute.setAttrName(name);
		this.attribute.setDetail(detailTextArea.getText());

		int radioIndex = raidToggle.getToggles().indexOf(raidToggle.getSelectedToggle());
		switch(radioIndex){
		case RADIO_INDEX_FREE:
			this.attribute.setCostamaizeType(Constant.ATTRIBUTE_TYPE.FREE);
			break;
		case RADIO_INDEX_SELECTION:
			this.attribute.setCostamaizeType(Constant.ATTRIBUTE_TYPE.SELECTION);
			break;
		default:
			return;
		}

		MAttributeBeanFacade.getInstance().save(attribute);
		AttributesConfigController.getInstance().addAttribute(attribute);
		AttributeDetailController.getInstance().setAttribute(attribute);
		AttributeDetailController.getInstance().show();
	}

}
