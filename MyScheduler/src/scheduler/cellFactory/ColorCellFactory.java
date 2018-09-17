package scheduler.cellFactory;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * ステータスを選ぶコンボボックスのアイテムを作るクラス
 *
 * @author ohmoon
 *
 */
/*
 * コンボボックスについてのjavadocを参照:
 * https://docs.oracle.com/javase/jp/8/javafx/user-interface-tutorial/combo-box.htm
 * https://docs.oracle.com/javase/jp/8/javafx/api/javafx/scene/control/ComboBox.html
 *
 * https://examples.javacodegeeks.com/desktop-java/javafx/combobox/javafx-combobox-example/ も参考になる
 *
 */

public class ColorCellFactory implements Callback<ListView<String>, ListCell<String>>{

	    @Override
	    public ListCell<String> call(ListView<String> listview){
	        return new StatusShapeCell();
	    }
}
