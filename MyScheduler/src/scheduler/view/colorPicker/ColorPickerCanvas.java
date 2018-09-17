package scheduler.view.colorPicker;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ColorPickerCanvas extends Pane{


	ColorPickerCanvasBack canvas;

	ColorPikerUpper upper;


	private Color selectedColor = Color.TRANSPARENT;

	public Color getSelectedColor(){
		return this.selectedColor;
	}


	public ColorPickerCanvas(double width,double height){
		canvas = new ColorPickerCanvasBack(width,height);
		upper =  new ColorPikerUpper(width,height);

		this.getChildren().addAll(canvas,upper);

	}

	public void onMouseDragReleased(MouseEvent event){
		double mouseX = event.getX();
		double mouseY = event.getY();
		selectedColor = canvas.createColorByPosition(mouseX, mouseY);
	}

	public void onMouseDragged(MouseEvent event){
		double mouseX = event.getX();
		double mouseY = event.getY();
		upper.drawPointer(mouseX, mouseY);
		selectedColor = canvas.createColorByPosition(mouseX, mouseY);
	}

	public void onMouseClicked(MouseEvent event){
		double mouseX = event.getX();
		double mouseY = event.getY();
		upper.drawPointer(mouseX, mouseY);
		selectedColor = canvas.createColorByPosition(mouseX, mouseY);
	}

}
