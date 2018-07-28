package scheduler.view;

import javafx.scene.Group;

/**
 * GroupとDrawableの合流用。ビューに共通なメソッドなど
 * @author ohmoon
 *
 */
public abstract class AbstractView extends Group implements Drawable{


	protected static final double DEFAULT_VIEW_WIDTH = 33;
	protected static final double DEFAULT_VIEW_HEIGHT = 33;
	protected double viewWidth;
	protected double viewHeight;





	public double getViewWidth() {
		return viewWidth;
	}

	public double getViewHeight() {
		return viewHeight;
	}



	public AbstractView(){
		init();
	}

	protected abstract void init();
}
