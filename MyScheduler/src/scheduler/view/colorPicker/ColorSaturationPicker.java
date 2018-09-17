package scheduler.view.colorPicker;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ColorSaturationPicker extends Canvas{


	public static final double DEFAULT_HEIGHT = 200;

	public static final double DEFAULT_WIDTH = 200;

	private static final double INGICATOR_WIDTH = 14;

	private static final double INGICATOR_HEIGHT = 14;

	private final GraphicsContext gc;


	private double definedWidth;

	private double definedHeight;

	private double hue = 0;

	private double lightness = 0.5;

	private Color selectedColor = Color.TRANSPARENT;

	public Color getSelectedColor(){
		return this.selectedColor;
	}



	public void setColorAttributes(double hue,double lightness){
		this.hue = hue;
		this.lightness = lightness;
		this.initPicker(hue, lightness);
	}


	public ColorSaturationPicker(double width,double height){
		super(width + INGICATOR_WIDTH,height);
		gc = this.getGraphicsContext2D();
		this.definedWidth = width - INGICATOR_WIDTH;
		this.definedHeight = height;

		this.initPicker(hue, lightness);
	}


	public void onMouseAction(MouseEvent event){
		double mouseY = event.getY();
		this.drawIngicator(mouseY);
		this.selectedColor = this.getColorByPoint(mouseY);
	}


	public Color getColorByPoint(double y){
		if( y > 0 && y <definedHeight ){
			return Color.hsb(hue, y/definedHeight, lightness);
		}
		return Color.TRANSPARENT;
	}


	private void drawIngicator(double y){
		gc.clearRect(definedWidth, 0, INGICATOR_WIDTH, definedHeight);

		gc.setFill(Color.BLACK);
		double[] xPoints = {
				this.definedWidth,
				this.definedWidth + INGICATOR_WIDTH,
				this.definedWidth + INGICATOR_WIDTH
		};
		double[] yPoints = {
				y,
				y - INGICATOR_HEIGHT*0.5,
				y + INGICATOR_HEIGHT*0.5
		};
		gc.fillPolygon(xPoints, yPoints,3);
	}

	private void initPicker(double hue,double lightness){
		gc.clearRect(0, 0, definedWidth, definedHeight);
		for(int y = 0;y<definedHeight;y++){
			gc.setStroke(Color.hsb(hue, (y)/definedHeight, lightness));
			gc.strokeLine(0, y, definedWidth, y);
		}
	}

}
