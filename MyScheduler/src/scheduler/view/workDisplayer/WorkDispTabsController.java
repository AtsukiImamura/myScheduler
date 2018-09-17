package scheduler.view.workDisplayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import scheduler.common.constant.Constant;
import scheduler.common.constant.Constant.TAB_KIND;
import scheduler.common.utils.Util;

public class WorkDispTabsController implements Initializable{


	@FXML
	private TabPane workDispTabs;

	protected static WorkDispTabsController instance;

	protected static Parent view;

    /**
     * singletonのインスタンスを返す
     * @return instance
     */
    public static WorkDispTabsController getInstance() {
        return instance;
    }


    public Parent getView(){
    	return view;
    }


	static {
        FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("scheduler/view/workDisplayer/workDisplayerComponent.fxml");
        fxmlLoader.setRoot(new HBox());
        try {
        	view = fxmlLoader.load();
            instance = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



	public void setWidth(double width){
		workDispTabs.setPrefWidth(width);
		ProjectEditorController.getInstance().setWidth(width);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		workDispTabs.setPrefWidth(Constant.APP_PREF_WIDTH);
		workDispTabs.setMaxHeight(Constant.WORK_DISP_TAB_MAX_HEIGHT);
		workDispTabs.getTabs().set(Constant.TAB_KIND.PROJECT.getIndex(), new Tab(Constant.TAB_KIND.PROJECT.getName(),ProjectEditorController.getInstance().getView()));
		workDispTabs.getTabs().set(Constant.TAB_KIND.TASK.getIndex(), new Tab(Constant.TAB_KIND.TASK.getName(),TaskEditorController.getInstance().getView()));
		workDispTabs.getTabs().set(Constant.TAB_KIND.CONFIG.getIndex(), new Tab(Constant.TAB_KIND.CONFIG.getName(),ConfigComponentController.getInstance().getView()));
		//タブを閉じるのは禁止
		workDispTabs.getTabs().forEach(tab->{
			tab.setClosable(false);

		});
	}


	/**
	 * タブに子要素をセットする。タブの位置はtabKindのindexで調整する
	 * @param tabKind
	 * @param parent
	 */
	public void setTab(TAB_KIND tabKind,Parent parent){
		try{
			Tab tab = new Tab(tabKind.getName(),parent);
			tab.setClosable(false);
			workDispTabs.getTabs().get(tabKind.getIndex()).setContent(parent);
			//workDispTabs.getTabs().set(tabKind.getIndex(),tab);
			workDispTabs.getSelectionModel().select(tabKind.getIndex());
		}catch(Exception e){

		}
	}

}
