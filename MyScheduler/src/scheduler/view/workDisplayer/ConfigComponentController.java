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

public class ConfigComponentController implements Initializable{


	@FXML
	private TabPane workDispTabs;

	protected static ConfigComponentController instance;

	protected static Parent view;

    /**
     * singletonのインスタンスを返す
     * @return instance
     */
    public static ConfigComponentController getInstance() {
        return instance;
    }


    public Parent getView(){
    	return view;
    }


	static {
        FXMLLoader fxmlLoader = Util.createProjectFxmlLoader("scheduler/view/workDisplayer/configComponent.fxml");
        fxmlLoader.setRoot(new HBox());
        try {
        	view = fxmlLoader.load();
            instance = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		workDispTabs.getTabs().set(Constant.TAB_KIND.COLOR.getIndex(), new Tab(Constant.TAB_KIND.COLOR.getName(),ColorConfigController.getInstance().getView()));
//		workDispTabs.getTabs().set(Constant.TAB_KIND.TASK.getIndex(), new Tab(Constant.TAB_KIND.TASK.getName(),TaskEditorController.getInstance().getView()));
		workDispTabs.getTabs().set(Constant.TAB_KIND.ATTRIBUTE.getIndex(), new Tab(Constant.TAB_KIND.ATTRIBUTE.getName(),AttributesConfigController.getInstance().getView()));
		workDispTabs.getTabs().forEach(tab->{
			tab.setClosable(false);
		});

		//workDispTabs.setPrefHeight(Constant.WORK_DISP_TAB_PREF_HEIGHT);
	}

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
