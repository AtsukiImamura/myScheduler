package scheduler.view.workDisplayer;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorAttributeRow extends HBox{


	public final double DEFAULT_HEIGHT = 36;

	public final double DEFAULT_WIDTH = 210;

	public final double RATE_OF_TITLE_WIDTH = 0.2;

	private final double RECT_INSETS = 3;

	private String title;

	private Color color;

	private final Label titleLabel;

	private final Pane colorDisp;



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public Color getColor() {
		return color;
	}



	public void setColor(Color color) {
		this.color = color;
	}



	public ColorAttributeRow(String title,Color color){
		this.title = title;
		this.color = color;

		titleLabel = new Label(title);
		titleLabel.setPrefSize(DEFAULT_WIDTH*RATE_OF_TITLE_WIDTH, DEFAULT_HEIGHT);
		colorDisp = new Pane();
		colorDisp.setPrefSize(DEFAULT_WIDTH*(1-RATE_OF_TITLE_WIDTH), DEFAULT_HEIGHT);

		Rectangle rect = new Rectangle(RECT_INSETS,RECT_INSETS,DEFAULT_WIDTH*(1-RATE_OF_TITLE_WIDTH)-RECT_INSETS*2,DEFAULT_HEIGHT-RECT_INSETS*2);
		rect.setFill(color);
		colorDisp.getChildren().add(rect);

		this.getChildren().addAll(titleLabel,colorDisp);
	}
}
