package scheduler.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;

/**
 * GroupとDrawableの合流用。ビューに共通なメソッドなど
 * @author ohmoon
 *
 */
public abstract class AbstractView extends Group implements Drawable{


	public final DoubleProperty viewWidth;
	public final DoubleProperty viewHeight;






	public AbstractView(){
		viewWidth = new SimpleDoubleProperty();
		viewHeight = new SimpleDoubleProperty();
		init();
	}

	protected abstract void init();
}
