package scheduler.view.workDisplayer;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorAttributeRow extends HBox{


	public final double DEFAULT_HEIGHT = 36;

	public final double DEFAULT_WIDTH = 300;

	public final double DEFAULT_TITLE_WIDTH = 105;

	private final double RECT_INSETS = 3;

	private final double PADDING_RIGHT = 20;

	private String title;

	private Color color;

	private final Label titleLabel;

	private final Pane colorDisp;

	private boolean clicked = false;



	public boolean isClicked() {
		return clicked;
	}



	public void setClicked(boolean clicked) {
		this.clicked = clicked;
		if(clicked){
			colorDisp.setStyle("-fx-border-color: #404040;");
		}else{
			colorDisp.setStyle("");
		}
	}


	public void onMouseEntered(){
		if(!clicked){
			colorDisp.setStyle("-fx-border-color: #c0c0c0;");
		}
	}


	public void onMouseExited(){
		if(!clicked){
			colorDisp.setStyle("");
		}
	}



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
		titleLabel.setPrefSize(DEFAULT_TITLE_WIDTH, DEFAULT_HEIGHT);
		colorDisp = new Pane();
		colorDisp.setPrefSize(DEFAULT_WIDTH-DEFAULT_TITLE_WIDTH, DEFAULT_HEIGHT);

		Rectangle rect = new Rectangle(
				RECT_INSETS,
				RECT_INSETS,
				DEFAULT_WIDTH-DEFAULT_TITLE_WIDTH-RECT_INSETS*2-PADDING_RIGHT,
				DEFAULT_HEIGHT-RECT_INSETS*2);
		rect.setFill(color);
		colorDisp.getChildren().add(rect);
		colorDisp.setPrefSize(DEFAULT_WIDTH-DEFAULT_TITLE_WIDTH-RECT_INSETS*2-PADDING_RIGHT + 10, DEFAULT_HEIGHT-RECT_INSETS*2);

		this.getChildren().addAll(titleLabel,colorDisp);

		this.getStyleClass().addAll("color_attribute_row");

	}
}
