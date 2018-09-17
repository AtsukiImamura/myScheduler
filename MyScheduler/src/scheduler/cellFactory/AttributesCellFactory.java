package scheduler.cellFactory;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import scheduler.bean.AttributeSelectionBean;

public class AttributesCellFactory implements Callback<ListView<AttributeSelectionBean>, ListCell<AttributeSelectionBean>>{


	public static ListCell<AttributeSelectionBean> getAttributeCell(){
		return new AttributeCell();
	}

    @Override
    public ListCell<AttributeSelectionBean> call(ListView<AttributeSelectionBean> listview){
        return new AttributeCell();
    }
}


class AttributeCell extends ListCell<AttributeSelectionBean>{

    @Override
    public void updateItem(AttributeSelectionBean selection, boolean empty){
        super.updateItem(selection, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        }else{
        	if(selection == null){
        		setText("");
        		return;
        	}
            setText(selection.getDispName());
       }
    }
}