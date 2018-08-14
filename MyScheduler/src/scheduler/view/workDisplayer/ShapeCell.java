package scheduler.view.workDisplayer;


import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import scheduler.bean.StatusBean;
import scheduler.common.constant.Constant;
import scheduler.common.utils.Util;
import scheduler.facade.StatusFacade;




/*
 * https://examples.javacodegeeks.com/desktop-java/javafx/combobox/javafx-combobox-example/
 * を参考にして作っている。まだいまいち何をしているか分からん
 */

public class ShapeCell extends ListCell<String>

{

    @Override

    public void updateItem(String item, boolean empty){
        super.updateItem(item, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        }else{
            setText(item);
            Shape shape = this.getShape(item);
            setGraphic(shape);
       }
    }



    public Shape getShape(String value){
        Shape shape = new Rectangle(0, 0, 35, 20);

        String selectionCode = Util.decodeSelectionCodeFromSelection(value,Constant.LENGTH_OF_SELECTION_CODE);
        StatusFacade statusFacade = new StatusFacade();
        StatusBean bean = statusFacade.one(selectionCode);

        if(bean == null){
        	return shape;
        }

        Color statusColor = bean.getColor();
        shape.setFill(statusColor);

        return shape;
    }

}
