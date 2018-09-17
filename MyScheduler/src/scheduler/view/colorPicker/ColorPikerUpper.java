package scheduler.view.colorPicker;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ColorPikerUpper extends Canvas{


	public static final double DEFAULT_HEIGHT = 200;

	public static final double DEFAULT_WIDTH = 200;


	private final double CROSS_POINTER_PIECE_WIDTH = 10;

	private final double CROSS_POINTER_PIECE_HEIGHT = 3;

	private final double CROSS_POINTER_SPACE = 4;

	private double definedWidth;

	private double definedHeight;

	private final GraphicsContext gc;

	public ColorPikerUpper(){
		this(DEFAULT_WIDTH,DEFAULT_HEIGHT);
	}


	public ColorPikerUpper(double width,double height){
		super(width,height);
		gc = this.getGraphicsContext2D();
		definedWidth = width;
		definedHeight = height;

//		this.setOnMouseDragged(event->{
//			double mouseX = event.getX();
//			double mouseY = event.getY();
//
//			this.drawPointer(mouseX, mouseY);
//		});
//
//		this.setOnMouseClicked(event->{
//			double mouseX = event.getX();
//			double mouseY = event.getY();
//
//			this.drawPointer(mouseX, mouseY);
//		});
	}

	public void drawPointer(double mouseX,double mouseY){
		gc.clearRect(0, 0, this.definedWidth, this.definedHeight);
		gc.setFill(Color.TRANSPARENT);
		gc.fillRect(0, 0, this.definedWidth, this.definedHeight);

		gc.setFill(Color.BLACK);

		gc.fillRect(
				mouseX - CROSS_POINTER_PIECE_WIDTH - CROSS_POINTER_SPACE,
				mouseY - CROSS_POINTER_PIECE_HEIGHT*0.5,
				CROSS_POINTER_PIECE_WIDTH,
				CROSS_POINTER_PIECE_HEIGHT
				);
		gc.fillRect(
				mouseX + CROSS_POINTER_SPACE,
				mouseY - CROSS_POINTER_PIECE_HEIGHT*0.5,
				CROSS_POINTER_PIECE_WIDTH,
				CROSS_POINTER_PIECE_HEIGHT
				);
		gc.fillRect(
				mouseX - CROSS_POINTER_PIECE_HEIGHT*0.5,
				mouseY - CROSS_POINTER_PIECE_WIDTH -CROSS_POINTER_SPACE ,
				CROSS_POINTER_PIECE_HEIGHT,
				CROSS_POINTER_PIECE_WIDTH
				);
		gc.fillRect(
				mouseX - CROSS_POINTER_PIECE_HEIGHT*0.5,
				mouseY + CROSS_POINTER_SPACE,
				CROSS_POINTER_PIECE_HEIGHT,
				CROSS_POINTER_PIECE_WIDTH
				);
	}

}
