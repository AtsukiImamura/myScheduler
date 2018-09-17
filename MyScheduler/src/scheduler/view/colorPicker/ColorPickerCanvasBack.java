package scheduler.view.colorPicker;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ColorPickerCanvasBack extends Canvas{


	public static final double DEFAULT_HEIGHT = 200;

	public static final double DEFAULT_WIDTH = 200;

	private final GraphicsContext gc;

	private double definedWidth;

	private double definedHeight;

	private Color selectedColor = Color.WHITE;

	public Color getSelectedColor(){
		return this.selectedColor;
	}



	public double getDefinedWidth() {
		return definedWidth;
	}


	public double getDefinedHeight() {
		return definedHeight;
	}


	public ColorPickerCanvasBack(){
		this(DEFAULT_WIDTH,DEFAULT_HEIGHT);
	}


	public ColorPickerCanvasBack(double width,double height){
		super(width,height);
		this.definedWidth = width;
		this.definedHeight = height;
		gc = this.getGraphicsContext2D();
		initColorRing(width,height);

	}

	public Color createColorByPosition(double x,double y){

		if(x > this.definedWidth || x < 0 ||y > this.definedHeight || y<0 ){
			return Color.WHITE;
		}
		double hue = (x/definedWidth)*360;
		double lightness = (definedHeight-y)/definedHeight;
		return Color.hsb(hue, 1.0, lightness);
	}



	private void initColorRing(double width,double height){
		initColorRing(0,0,width,height);
	}


	private void initColorRing(double startX,double startY,double width,double height){

		int x1 = (int)( startX < 0 ? 0 : startX < this.definedWidth ? startX : this.definedWidth);
		double lastX = startX + width;
		int x2 = (int)(lastX > this.definedWidth ? this.definedWidth : lastX > 0 ? lastX : 0);
		int y1 = (int)(startY < 0 ? 0 : startY < this.definedHeight ? startY : this.definedHeight);
		double lastY = startY + height;
		int y2 = (int)(lastY > this.definedHeight ? this.definedHeight : lastY > 0 ? lastY : 0);

		gc.clearRect(x1, y1,x2 - x1, y2 - y1);

		double heightAll = this.definedHeight;

		double hue = 0;
		double lightness = 0;
		double stepX = 360/this.definedWidth;
		gc.setLineWidth(1);
		for(int x=x1;x<=x2;x++){
			hue = x*stepX;
			for(int y=y1;y<=y2;y++){
				lightness = (heightAll-y)/heightAll;
				gc.setStroke( Color.hsb(hue, 1.0, lightness));
				gc.strokeLine(x,y,x,y);
			}
		}
	}

}
