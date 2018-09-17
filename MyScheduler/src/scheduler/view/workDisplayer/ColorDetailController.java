package scheduler.view.workDisplayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import scheduler.bean.ColorAttribute;
import scheduler.common.utils.Util;

public class ColorDetailController  implements Initializable{


	public final double DEFAULT_HEIGHT = 46;

	public final double RECT_WIDTH = 320;

	private final double RECT_INSETS_V = 8;

	private final double RECT_INSETS_H = 10;



	/* ----------------------------
	 *  詳細表示
	 ------------------------------ */
	@FXML
	private VBox colorConfigVBox;

	@FXML
	private Label dispNameLabel;

	@FXML
	private Pane dispColorPane;

	@FXML
	private Label dispDetailLabel;

	@FXML
	private Button deleteButton;

	@FXML
	private Button editButton;

	private ColorAttribute colorAttr;


	protected static ColorDetailController instance;

	protected static Parent view;

    /**
     * singletonのインスタンスを返す
     * @return instance
     */
    public static ColorDetailController getInstance() {
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
        FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("scheduler/view/workDisplayer/colorDetail.fxml");
        try {
        	view = fxmlLoader.load();
            instance = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}


	public void setAttibute(ColorAttribute colorAttr){
		this.colorAttr = colorAttr;
		this.dispNameLabel.setText(colorAttr.getTitle());
		this.dispDetailLabel.setText(colorAttr.getDetail());
		Rectangle rect = new Rectangle(
				RECT_INSETS_H,
				RECT_INSETS_V,
				RECT_WIDTH-RECT_INSETS_H,
				DEFAULT_HEIGHT-RECT_INSETS_V*2);
		rect.setFill(colorAttr.getColor());
		this.dispColorPane.getChildren().add(rect);

	}


	/**
	 * 削除
	 * @param event
	 */
	@FXML
	public void onDeleteButtonClicked(ActionEvent event){

	}


	/**
	 * 編集
	 * @param event
	 */
	@FXML
	public void onEditButtonClicked(ActionEvent event){
		ColorEditController.getInstance().show();
		ColorEditController.getInstance().setAttibute(colorAttr);
	}
}
