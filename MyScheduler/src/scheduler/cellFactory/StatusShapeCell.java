package scheduler.cellFactory;

import javafx.scene.control.ListCell;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import scheduler.bean.StatusBean;
import scheduler.facade.StatusFacade;

public class StatusShapeCell extends ListCell<String>{

    @Override
    public void updateItem(String item, boolean empty){
        super.updateItem(item, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        }else{
        	StatusFacade statusFacade = new StatusFacade();
        	StatusBean bean = statusFacade.one(item);
        	if(bean == null){
        		return;
        	}
            setText(bean.getTitle());

            Shape shape = new Rectangle(0, 0, 35, 20);
            shape.setFill(bean.getColor());
            setGraphic(shape);
       }
    }
}
