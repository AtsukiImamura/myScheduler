package scheduler.cellFactory;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class StoneColorCellFactory implements Callback<ListView<String>, ListCell<String>>{

    @Override
    public ListCell<String> call(ListView<String> listview){
        return new StoneShapeCell();
    }
}
