package scheduler.view.colorPicker;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorPicker extends Pane{


	public static final double DEFAULT_HEIGHT = 200;

	public static final double DEFAULT_WIDTH = 400;

	private static final double SATURATION_PICKER_WIDTH = 50;

	private static final double RATE_OF_SELECTOR_HEIGHT = 0.8;

	private static final double PADDING_LEFT = 15;

	private static final double PADDING_RIGHT = 15;

	private static final double PADDING_TOP = 15;

	private static final double PADDING_BOTTOM = 15;

	private static final double SPACE = 6;

	private static final double LABEL_HEIGHT = 32;


	private Color selectedColor = Color.TRANSPARENT;

	public Color getSelectedColor(){
		return this.selectedColor;
	}

	private final Rectangle oldColorRect;

	public void setOldColor(Color color){
		if(oldColorRect == null){
			return;
		}
		oldColorRect.setFill(color);
	}


	public ColorPicker(){
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}


	int count = 0;

	public ColorPicker(double width,double height){

		this.setPrefSize(width +PADDING_LEFT + PADDING_RIGHT + SPACE , height + PADDING_TOP + PADDING_BOTTOM + SPACE);

		ColorPickerCanvas picker = new ColorPickerCanvas(width - SATURATION_PICKER_WIDTH,height*RATE_OF_SELECTOR_HEIGHT);
		ColorSaturationPicker saturationPicker = new ColorSaturationPicker(SATURATION_PICKER_WIDTH,height*RATE_OF_SELECTOR_HEIGHT);
		picker.setLayoutX(PADDING_LEFT);
		saturationPicker.setLayoutX(width - SATURATION_PICKER_WIDTH + PADDING_LEFT + SPACE);

		this.getChildren().addAll(picker,saturationPicker);

		double labelWidth = (width - PADDING_LEFT - PADDING_RIGHT)*0.5;
		Label oldColoLabel = new Label("現在の色");
		oldColoLabel.setPrefSize(labelWidth,LABEL_HEIGHT);
		oldColoLabel.setLayoutX(PADDING_LEFT);
		oldColoLabel.setLayoutY(height*RATE_OF_SELECTOR_HEIGHT + SPACE);
		oldColoLabel.setAlignment(Pos.CENTER);
		Label newColorLabel = new Label("新しい色");
		newColorLabel.setPrefSize(labelWidth,LABEL_HEIGHT);
		newColorLabel.setLayoutX(PADDING_LEFT + labelWidth);
		newColorLabel.setLayoutY(height*RATE_OF_SELECTOR_HEIGHT + SPACE);
		newColorLabel.setAlignment(Pos.CENTER);

		oldColorRect = new Rectangle(labelWidth,LABEL_HEIGHT);
		oldColorRect.setFill(selectedColor);
		oldColorRect.setLayoutX(PADDING_LEFT);
		oldColorRect.setLayoutY(height*RATE_OF_SELECTOR_HEIGHT + LABEL_HEIGHT + SPACE);
		Rectangle newColorRect = new Rectangle(labelWidth,LABEL_HEIGHT);
		newColorRect.setFill(selectedColor);
		newColorRect.setLayoutX(PADDING_LEFT + labelWidth);
		newColorRect.setLayoutY(height*RATE_OF_SELECTOR_HEIGHT + LABEL_HEIGHT + SPACE);

		this.getChildren().addAll(oldColoLabel,newColorLabel,oldColorRect,newColorRect);


		picker.setOnMouseDragReleased(event->{
			picker.onMouseDragReleased(event);
			this.selectedColor = picker.getSelectedColor();
			newColorRect.setFill(selectedColor);
			saturationPicker.setColorAttributes(this.selectedColor.getHue(), this.selectedColor.getBrightness());
		});

		picker.setOnMouseDragged(event->{
			picker.onMouseDragged(event);
			this.selectedColor = picker.getSelectedColor();
			newColorRect.setFill(selectedColor);
			saturationPicker.setColorAttributes(this.selectedColor.getHue(), this.selectedColor.getBrightness());
		});

		picker.setOnMouseClicked(event->{
			picker.onMouseClicked(event);
			this.selectedColor = picker.getSelectedColor();
			newColorRect.setFill(selectedColor);
			saturationPicker.setColorAttributes(this.selectedColor.getHue(), this.selectedColor.getBrightness());
		});

		saturationPicker.setOnMouseDragged(event->{
			saturationPicker.onMouseAction(event);
			this.selectedColor = saturationPicker.getSelectedColor();
			newColorRect.setFill(selectedColor);
		});

		saturationPicker.setOnMouseClicked(event->{
			saturationPicker.onMouseAction(event);
			this.selectedColor = saturationPicker.getSelectedColor();
			newColorRect.setFill(selectedColor);
		});
		saturationPicker.setOnMouseDragReleased(event->{
			saturationPicker.onMouseAction(event);
			this.selectedColor = saturationPicker.getSelectedColor();
			newColorRect.setFill(selectedColor);
		});
	}


}
