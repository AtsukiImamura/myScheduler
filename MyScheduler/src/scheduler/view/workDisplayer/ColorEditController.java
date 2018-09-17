package scheduler.view.workDisplayer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import scheduler.bean.ColorAttribute;
import scheduler.bean.StatusBean;
import scheduler.bean.StoneBean;
import scheduler.common.constant.Constant.COLOR_TYPE;
import scheduler.common.utils.Util;
import scheduler.facade.StatusFacade;
import scheduler.facade.StoneFacade;
import scheduler.view.colorPicker.ColorPicker;

public class ColorEditController  implements Initializable{


	public static final int COLOR_TYPE_STATUS = 10;

	public static final int COLOR_TYPE_STONE = 20;


	/* ----------------------------
	 *  編集
	 ------------------------------ */
	@FXML
	private VBox colorEditVBox;

	@FXML
	private TextField editNameForm;

	@FXML
	private Button colorSelectionButton;

	@FXML
	private Rectangle selectedColorRect;

	@FXML
	private TextArea editDetailForm;

	@FXML
	private Button clearButton;

	@FXML
	private Button registButton;

	@FXML
	private ColorAttribute colorAttr = null;

	protected static ColorEditController instance;

	protected static Parent view;

    /**
     * singletonのインスタンスを返す
     * @return instance
     */
    public static ColorEditController getInstance() {
        return instance;
    }


    /**
     * 表示する
     */
    public void show() {
    	ColorConfigController.getInstance().addChild(view);
    }


    public Parent getView(){
    	return view;
    }


	static {
        FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("scheduler/view/workDisplayer/colorEdit.fxml");
        try {
        	view = fxmlLoader.load();
            instance = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//this.selectedColorRect.setVisible(false);
		colorAttr = new StoneBean();

	}


	public void createNewColor(COLOR_TYPE colorType){
		String newCode = null;
		ColorAttribute colorAttr = null;
		switch(colorType){
		case STATUS:
			newCode = StatusFacade.getInstance().createNewCode();
			colorAttr = new StatusBean();
			colorAttr.setCode(newCode);
			break;
		case STONE:
			newCode = StoneFacade.getInstance().createNewCode();
			colorAttr = new StoneBean();
			colorAttr.setCode(newCode);
			break;
		default:
			break;
		}
		if(colorAttr == null){
			return;
		}
		this.setAttibute(colorAttr);
	}


	public void setAttibute(ColorAttribute colorAttr){
		this.colorAttr = colorAttr;
		this.selectedColorRect.setFill(colorAttr.getColor());
		this.editNameForm.setText(colorAttr.getTitle());
		this.editDetailForm.setText(colorAttr.getDetail());
	}


	public void onSelectColorButtonClicked(ActionEvent event){
		Dialog<Color> dialog = new Dialog<>();
		dialog.setTitle("色を選択");
		dialog.setHeaderText("色を選択してください");

		ButtonType registButtonType = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(registButtonType, ButtonType.CANCEL);
		dialog.getDialogPane().lookupButton(registButtonType).getStyleClass().addAll("regist_button","work_disp_button_base");
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().addAll("clear_button","work_disp_button_base");

		ColorPicker picker = new ColorPicker();
		if(this.colorAttr != null){
			Color col = this.colorAttr.getColor();
			picker.setOldColor(col);
		}

		VBox content = new VBox();
		content.setPrefSize(480, 320);
		content.getChildren().add(picker);
		dialog.getDialogPane().setContent(content);

		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == registButtonType) {
		        return picker.getSelectedColor();
		    }
		    return null;
		});

		Optional<Color> result = dialog.showAndWait();

		result.ifPresent(color -> {
		    selectedColorRect.setFill(color);
		    this.colorAttr.setColor(color);
		});
	}


	/**
	 * クリア
	 * @param event
	 */
	@FXML
	public void onClearButtonClicked(ActionEvent event){
		switch(this.colorAttr.getColorType()){
		case STATUS:
			this.colorAttr = new StatusBean();
			break;
		case STONE:
			this.colorAttr = new StoneBean();
			break;
		default:
			break;
		}
		this.setAttibute(colorAttr);
	}


	/**
	 * 登録
	 * @param event
	 */
	@FXML
	public void onRegistButtonClicked(ActionEvent event){
		if(!validateInput()){
			//TODO:エラーメッセージ
			return;
		}
		this.colorAttr.setTitle(editNameForm.getText());
		this.colorAttr.setDetail(editDetailForm.getText());
		this.colorAttr.setColor((Color)selectedColorRect.getFill());

		switch(this.colorAttr.getColorType()){
		case STATUS:
			StatusFacade.getInstance().save((StatusBean) this.colorAttr);
			break;
		case STONE:
			StoneFacade.getInstance().save((StoneBean) this.colorAttr);
			break;
		default:
			break;
		}
		ColorDetailController.getInstance().setAttibute(this.colorAttr);
		ColorDetailController.getInstance().show();
		ColorConfigController.getInstance().redraw();
	}


	/**
	 * 値が入力されているかチェック
	 * @return
	 */
	private boolean validateInput(){
		if(this.selectedColorRect.getFill() == null){
			return false;
		}
		String text = this.editNameForm.getText();
		if(text == null || text.equals("")){
			return false;
		}
		text = this.editDetailForm.getText();
		if(text == null || text.equals("")){
			return false;
		}

		return true;
	}

}
