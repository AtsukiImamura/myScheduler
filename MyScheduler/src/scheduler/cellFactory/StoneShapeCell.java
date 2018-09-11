package scheduler.cellFactory;

import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import scheduler.bean.StoneBean;
import scheduler.facade.StoneFacade;

public class StoneShapeCell extends ListCell<String>{

	private static StoneFacade stoneFacade;

	static{
		stoneFacade = new StoneFacade();
	}

	@Override
	public void updateItem(String item, boolean empty){
	    super.updateItem(item, empty);
	    if (empty){
	        setText(null);
	        setGraphic(null);
	    }else{
	    	StoneBean stone = stoneFacade.one(item);
	    	if(stone == null){
	    		return;
	    	}
	        setText(stone.getTytle());

	        Shape shape = new Rectangle(0, 0, 120, 24);
	        shape.setFill(stone.getColor());
	        shape.setStroke(Color.rgb(120, 120, 120));
	        setGraphic(shape);
	   }
	}

}
