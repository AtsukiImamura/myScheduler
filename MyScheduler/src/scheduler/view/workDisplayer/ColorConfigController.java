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
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import scheduler.bean.ColorAttribute;
import scheduler.bean.StatusBean;
import scheduler.bean.StoneBean;
import scheduler.common.constant.Constant;
import scheduler.common.constant.Constant.COLOR_TYPE;
import scheduler.common.utils.Util;
import scheduler.facade.StatusFacade;
import scheduler.facade.StoneFacade;

public class ColorConfigController  implements Initializable{


	private final Color DIV_COLOR_LABEL_SELECTED = Color.rgb(0, 129, 153);

	private final Color DIV_COLOR_LABEL_UNSELECTED = Color.rgb(196,226,239);

	private final Color DIV_COLOR_LABEL_TEXT_SELECTED = Color.WHITE;

	private final Color DIV_COLOR_LABEL_TEXT_UNSELECTED = Color.rgb(0, 129, 153);



	@FXML
	private FlowPane colorListFlow;

	@FXML
	private FlowPane detailPane;

	@FXML
	private Label divStoneLabel;

	@FXML
	private Label divStatusLabel;

	@FXML
	private Button addColorButton;

	private List<StoneBean> stoneList;

	private List<StatusBean> statusList;

	private COLOR_TYPE colorType = null;



	protected static ColorConfigController instance;

	protected static Parent view;

    /**
     * singletonのインスタンスを返す
     * @return instance
     */
    public static ColorConfigController getInstance() {
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
        FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("scheduler/view/workDisplayer/colorConfigration.fxml");
        fxmlLoader.setRoot(new HBox());
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

		stoneList = new ArrayList<StoneBean>();
		statusList = new ArrayList<StatusBean>();

		divStatusLabel.setTextFill(DIV_COLOR_LABEL_TEXT_SELECTED);
		divStoneLabel.setTextFill(DIV_COLOR_LABEL_TEXT_SELECTED);
		this.setType(Constant.COLOR_TYPE.STATUS);
		divStatusLabel.setOnMouseClicked(event->{
			this.setType(Constant.COLOR_TYPE.STATUS);
		});
		divStoneLabel.setOnMouseClicked(event->{
			this.setType(Constant.COLOR_TYPE.STONE);
		});

		colorListFlow.setOrientation(Orientation.HORIZONTAL);



	}



	public void redraw(){
		if(colorType == null){
			return;
		}
		this.setType(colorType);
	}


	/**
	 * この設定画面のタイプを設定する
	 * @param type
	 */
	public void setType(COLOR_TYPE type){
		Color colorStatus = Color.WHITE;
		Color colorStone = Color.WHITE;
		colorType = type;
		switch(type){
		case STATUS:
			initAsConfigStatus();
			colorStatus = DIV_COLOR_LABEL_SELECTED;
			colorStone = DIV_COLOR_LABEL_UNSELECTED;
			break;
		case STONE:
			initAsConfigStone();
			colorStone = DIV_COLOR_LABEL_SELECTED;
			colorStatus = DIV_COLOR_LABEL_UNSELECTED;
			break;
		}
		divStoneLabel.setBackground(new Background(new BackgroundFill(colorStone,new CornerRadii(0),new Insets(0))));
		divStatusLabel.setBackground(new Background(new BackgroundFill(colorStatus,new CornerRadii(0),new Insets(0))));
	}


	/**
	 * ステータスカラー設定として初期化する
	 */
	public void initAsConfigStatus(){
		statusList = StatusFacade.getInstance().findAll();
		this.initColorSelection(statusList);

	}


	/**
	 * ストーンカラー設定として初期化する
	 */
	public void initAsConfigStone(){
		stoneList = StoneFacade.getInstance().findAll();
		this.initColorSelection(stoneList);
	}



	private void initColorSelection(List<? extends ColorAttribute> colorList){
		colorListFlow.getChildren().clear();
		for(ColorAttribute colorAttr: colorList){
			ColorAttributeRow attrRow = new ColorAttributeRow(colorAttr.getTitle(),colorAttr.getColor());
			colorListFlow.getChildren().add(attrRow);

			ColorAttribute tmpAttr = colorAttr.clone();
			attrRow.setOnMouseClicked(event->{
				ColorDetailController.getInstance().show();
				ColorDetailController.getInstance().setAttibute(tmpAttr);
				attrRow.setClicked(true);
				colorListFlow.getChildren().forEach(child->{
					if(!(child instanceof ColorAttributeRow) || child.equals(attrRow)){
						return;
					}
					((ColorAttributeRow)child).setClicked(false);
				});
			});

			attrRow.setOnMouseEntered(event->{
				attrRow.onMouseEntered();
				colorListFlow.getChildren().forEach(child->{
					if(!(child instanceof ColorAttributeRow) || child.equals(attrRow)){
						return;
					}
					((ColorAttributeRow)child).onMouseExited();
				});
			});

			attrRow.setOnMouseExited(event->{
				attrRow.onMouseExited();
			});
		}
		//colorListVbox.getChildren().add(addColorButton);
	}



	/**
	 * カラー追加
	 * @param event
	 */
	@FXML
	public void onAddColorButtonClicked(ActionEvent event){
		ColorEditController.getInstance().createNewColor(colorType);
		ColorEditController.getInstance().show();
	}
}
