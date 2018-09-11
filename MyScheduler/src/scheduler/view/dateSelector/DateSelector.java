package scheduler.view.dateSelector;

import java.util.Calendar;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import scheduler.common.utils.Util;

public class DateSelector extends Group{

	private final double YEAR_LABEL_WIDTH = 45;

	private final double MONTH_LABEL_WIDTH = 24;

	private final double YEAR_MONTH_LABEL_HEIGHT = 32;

	private final double TRIANGLE_WIDTH = 17;

	private final double YEAR_TRIANGLE_WIDTH = 13;

	private final double TRIANGLE_HEIGHT = 19;

	private final double BUTTON_SPACE = 5;

	private final double BUTTON_TOP_X = 7;

	private final double DATE_SELECTION_TOP_X = 45;

	private final Color TRIANGLE_FILL_COLOR = Color.rgb(0, 0, 0);

	private final Color TURN_MONTH_BTN_STROKE_COLOR = Color.rgb(0, 0,0);

	private final double DATE_SELECTION_SPACE = 3;

	private Calendar selectedDate;

	private Group dateSelection;

	private final Calendar currentCalendar;

	public final BooleanProperty selectedProperty;

	private Label yearLabel;

	private Label monthLabel;

	private double viewHeight = 0;

	public double getViewHeight(){
		return this.viewHeight;
	}


	public Calendar getSelectedDate(){
		return selectedDate;
	}



	public DateSelector(){
		selectedProperty = new SimpleBooleanProperty(false);
		selectedDate = Calendar.getInstance();
		currentCalendar = Calendar.getInstance();

		Rectangle rect = new Rectangle(SelectorDate.DEFAULT_WIDTH*7,SelectorDate.DEFAULT_HEIGHT*5 + DATE_SELECTION_TOP_X);
		rect.setFill(Color.WHITE);
		this.getChildren().add(rect);

		Group backYearButton = this.createBackYearButton();
		Group backMonthButton = this.createBackMonthButton();
		Group forwardMonthButton = this.createForwardMonthButton();
		Group forwardYearButton = this.createForwardYearButton();

		int currentYear = currentCalendar.get(Calendar.YEAR);
		int currentMonth = currentCalendar.get(Calendar.MONTH);

		yearLabel = new Label(currentYear+"");
		yearLabel.setAlignment(Pos.CENTER_LEFT);
		yearLabel.setPrefSize(YEAR_LABEL_WIDTH, YEAR_MONTH_LABEL_HEIGHT);
		monthLabel = new Label((currentMonth+1)+"");
		monthLabel.setAlignment(Pos.CENTER_LEFT);
		monthLabel.setPrefSize(MONTH_LABEL_WIDTH, YEAR_MONTH_LABEL_HEIGHT);

		backYearButton		.setTranslateX(TRIANGLE_WIDTH*0		+YEAR_TRIANGLE_WIDTH*0	+ BUTTON_SPACE*0	);
		backMonthButton		.setTranslateX(TRIANGLE_WIDTH*0		+YEAR_TRIANGLE_WIDTH*2	+ BUTTON_SPACE*1	);
		yearLabel			.setTranslateX(TRIANGLE_WIDTH*1		+YEAR_TRIANGLE_WIDTH*2	+ BUTTON_SPACE*3	);
		monthLabel			.setTranslateX(TRIANGLE_WIDTH*1		+YEAR_TRIANGLE_WIDTH*2	+ BUTTON_SPACE*4	+ YEAR_LABEL_WIDTH);
		forwardMonthButton	.setTranslateX(TRIANGLE_WIDTH*1		+YEAR_TRIANGLE_WIDTH*2	+ BUTTON_SPACE*5	+ YEAR_LABEL_WIDTH + MONTH_LABEL_WIDTH);
		forwardYearButton	.setTranslateX(TRIANGLE_WIDTH*2		+YEAR_TRIANGLE_WIDTH*2	+ BUTTON_SPACE*6	+ YEAR_LABEL_WIDTH + MONTH_LABEL_WIDTH);

		backYearButton		.setTranslateY(BUTTON_TOP_X);
		backMonthButton		.setTranslateY(BUTTON_TOP_X);
		forwardMonthButton	.setTranslateY(BUTTON_TOP_X);
		forwardYearButton	.setTranslateY(BUTTON_TOP_X);


		backYearButton.setOnMouseClicked(event->{
			currentCalendar.add(Calendar.YEAR, -1);
			this.createDateSelection();
		});

		backMonthButton.setOnMouseClicked(event->{
			currentCalendar.add(Calendar.MONTH, -1);
			this.createDateSelection();
		});

		forwardMonthButton.setOnMouseClicked(event->{
			currentCalendar.add(Calendar.MONTH, 1);
			this.createDateSelection();
		});

		forwardYearButton.setOnMouseClicked(event->{
			currentCalendar.add(Calendar.YEAR, 1);
			this.createDateSelection();
		});

		this.getChildren().addAll(
				backYearButton,
				backMonthButton,
				yearLabel,
				monthLabel,
				forwardMonthButton,
				forwardYearButton
				);

		dateSelection = new Group();
		dateSelection.setTranslateY(DATE_SELECTION_TOP_X);
		this.getChildren().add(dateSelection);

		this.createDateSelection();
	}


	public void refresh(){
		dateSelection.getChildren().forEach(selectorDate->{
			((SelectorDate)selectorDate).refresh();
		});
		selectedProperty.set(false);
	}


	/**
	 * 指定した日付よりも前の日を選択不可能にする
	 * @param date
	 */
	public void setDisableBefore(Calendar date){
		this.createDateSelection(date,null);
	}

	/**
	 * 指定した日付よりも前の日を選択不可能にする
	 * @param date
	 */
	public void setDisableAfter(Calendar date){
		this.createDateSelection(null,date);
	}

	private void createDateSelection(){
		createDateSelection(null,null);
	}


	private void createDateSelection(Calendar from,Calendar to){

		dateSelection.getChildren().clear();

		int currentYear = currentCalendar.get(Calendar.YEAR);
		int currentMonth = currentCalendar.get(Calendar.MONTH);


		int varticalIndex = 0;
		Calendar tmpDate = Calendar.getInstance();
		tmpDate.set(currentYear, currentMonth, 1);
		while(tmpDate.get(Calendar.MONTH) == currentMonth){
			SelectorDate selectorDate = new SelectorDate(tmpDate);

			int dayOfWeek = selectorDate.getDayOfWeek();

			selectorDate.setTranslateX((((8-dayOfWeek)/7)*7 + (dayOfWeek-2))*(SelectorDate.DEFAULT_WIDTH + DATE_SELECTION_SPACE));
			selectorDate.setTranslateY(varticalIndex*(SelectorDate.DEFAULT_HEIGHT+DATE_SELECTION_SPACE));

			selectorDate.clickedProperty.addListener((ev,oldValue,newValue)->{
				if(!newValue){
					return;
				}
				selectedDate = selectorDate.getDate();
				selectedProperty.set(true);
			});

			if(dayOfWeek == Calendar.SUNDAY){
				varticalIndex ++;
			}

			if(from != null && Util.compareCalendarDate(tmpDate, from) < 0){
				selectorDate.setDisable(true);
			}

			if(to != null && Util.compareCalendarDate(tmpDate, to) > 0){
				selectorDate.setDisable(true);
			}

			dateSelection.getChildren().add(selectorDate);

			tmpDate.add(Calendar.DAY_OF_MONTH, 1);
		}
		this.refreshLabel();
	}


	private void refreshLabel(){
		int currentYear = currentCalendar.get(Calendar.YEAR);
		int currentMonth = currentCalendar.get(Calendar.MONTH);
		yearLabel.setText(currentYear+"");
		monthLabel.setText((currentMonth+1)+"");
	}



	private Rectangle createTurnButtonBase(double width){
		Rectangle buttonBase = new Rectangle(0,0,width,TRIANGLE_HEIGHT);
		buttonBase.setFill(Color.TRANSPARENT);
		buttonBase.setStroke(TURN_MONTH_BTN_STROKE_COLOR);

		return buttonBase;
	}


	private Group createTurnYearButtonBase(){
		Group backYearButton = new Group();
		Rectangle backYearRect = this.createTurnButtonBase(YEAR_TRIANGLE_WIDTH*2);
		backYearButton.getChildren().add(backYearRect);

		return backYearButton;
	}


	private Group createBackYearButton(){
		Group backYearButton = createTurnYearButtonBase();

		for(int index=0;index<2;index++){
			Polygon leftPolygon = getInitializedLeftTriangle(this.YEAR_TRIANGLE_WIDTH,this.TRIANGLE_HEIGHT);
			double transX = leftPolygon.getTranslateX();
			leftPolygon.setTranslateX(transX + YEAR_TRIANGLE_WIDTH*index);
			backYearButton.getChildren().add(leftPolygon);
		}

		return backYearButton;
	}

	private Group createForwardYearButton(){
		Group forwardYearButton = createTurnYearButtonBase();

		for(int index=0;index<2;index++){
			Polygon rightPolygon = getInitializedRightTriangle(this.YEAR_TRIANGLE_WIDTH,this.TRIANGLE_HEIGHT);
			double transX = rightPolygon.getTranslateX();
			rightPolygon.setTranslateX(transX + YEAR_TRIANGLE_WIDTH*index);
			forwardYearButton.getChildren().add(rightPolygon);
		}

		return forwardYearButton;
	}


	private Group createTurnMonthButtonBase(){
		Group backYearButton = new Group();
		Rectangle backYearRect = createTurnButtonBase(TRIANGLE_WIDTH);
		backYearButton.getChildren().add(backYearRect);

		return backYearButton;
	}

	private Group createBackMonthButton(){
		Group backMonthButton = createTurnMonthButtonBase();

		Polygon leftTriangle = getInitializedLeftTriangle(this.TRIANGLE_WIDTH,this.TRIANGLE_HEIGHT);
		backMonthButton.getChildren().add(leftTriangle);

		return backMonthButton;
	}

	private Group createForwardMonthButton(){
		Group forwardMonthButton = createTurnMonthButtonBase();

		Polygon rightTriangle = getInitializedRightTriangle(this.TRIANGLE_WIDTH,this.TRIANGLE_HEIGHT);
		forwardMonthButton.getChildren().add(rightTriangle);

		return forwardMonthButton;
	}

	private Polygon getInitializedLeftTriangle(double width,double height){
		Polygon leftTriangle =this.createTriangleBase(width, height);
		leftTriangle.setRotate(-90.0);
		leftTriangle.setTranslateY((height-width)*0.5);
		leftTriangle.setTranslateX(-(height-width)*0.5);

		return leftTriangle;
	}

	private Polygon getInitializedRightTriangle(double width,double height){
		Polygon rightTriangle = this.createTriangleBase(width, height);
		rightTriangle.setRotate(90.0);
		rightTriangle.setTranslateY((height-width)*0.5);
		rightTriangle.setTranslateX(-(height-width)*0.5);

		return rightTriangle;
	}


	private Polygon createTriangleBase(double width,double height){
		Polygon rightTriangle = new Polygon();
		rightTriangle.getPoints().addAll(new Double[]{
				height*0.5,	0.0,
				height,		width,
				0.0,		width });
		rightTriangle.setFill(TRIANGLE_FILL_COLOR);

		return rightTriangle;
	}

}
