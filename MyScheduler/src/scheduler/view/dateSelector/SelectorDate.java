package scheduler.view.dateSelector;

import java.util.Calendar;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import scheduler.common.constant.Constant;

public class SelectorDate extends Group{

	public static final double DEFAULT_WIDTH = 24;

	public static final double DEFAULT_HEIGHT = 24;

	private final Color DEFAULT_COLOR;

	private final Color HOVERED_COLOR;

	private final Color CLICKED_COLOR;

	public final BooleanProperty clickedProperty;

	private final int dayOfWeek;

	private Calendar date;

	private Label label;


	public int getDayOfWeek(){
		return this.dayOfWeek;
	}

	public Calendar getDate(){
		return this.date;
	}


	public void refresh(){
		label.setBackground(new Background(new BackgroundFill(DEFAULT_COLOR,new CornerRadii(0),new Insets(0))));
		clickedProperty.set(false);
	}


	public SelectorDate(Calendar date){
		this.date = (Calendar)date.clone();
		clickedProperty = new SimpleBooleanProperty(false);

		int dayValue = date.get(Calendar.DAY_OF_MONTH);
		dayOfWeek = date.get(Calendar.DAY_OF_WEEK);

		switch(dayOfWeek){
		case Calendar.SUNDAY:
			DEFAULT_COLOR = Constant.CALENDAR_DATE_VIEW_SUNDAY;
			HOVERED_COLOR = Constant.CALENDAR_DATE_VIEW_SUNDAY_HOVERED;
			CLICKED_COLOR = Constant.CALENDAR_DATE_VIEW_SUNDAY_CLICKED;
			break;
		case Calendar.SATURDAY:
			DEFAULT_COLOR = Constant.CALENDAR_DATE_VIEW_SATURDAY;
			HOVERED_COLOR = Constant.CALENDAR_DATE_VIEW_SATURDAY_HOVERED;
			CLICKED_COLOR = Constant.CALENDAR_DATE_VIEW_SATURDAY_CLICKED;
			break;
		default:
			DEFAULT_COLOR = Constant.CALENDAR_DATE_VIEW_NOMAL;
			HOVERED_COLOR = Constant.CALENDAR_DATE_VIEW_NOMAL_HOVERED;
			CLICKED_COLOR = Constant.CALENDAR_DATE_VIEW_NOMAL_CLICKED;
			break;
		}

		label = new Label(""+dayValue);
		label.setPrefSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		label.setAlignment(Pos.CENTER);
		label.setBackground(new Background(new BackgroundFill(DEFAULT_COLOR,new CornerRadii(0),new Insets(0))));

		this.getChildren().add(label);

		label.setOnMouseClicked((event)->{
			clickedProperty.set(!clickedProperty.get());
			label.setBackground(new Background(new BackgroundFill(CLICKED_COLOR,new CornerRadii(0),new Insets(0))));
		});

		label.setOnMouseEntered((event)->{
			if(clickedProperty.get()){
				return;
			}
			label.setBackground(new Background(new BackgroundFill(HOVERED_COLOR,new CornerRadii(0),new Insets(0))));
		});

		label.setOnMouseExited((event)->{
			if(clickedProperty.get()){
				return;
			}
			label.setBackground(new Background(new BackgroundFill(DEFAULT_COLOR,new CornerRadii(0),new Insets(0))));
		});

	}



	@Override
	public SelectorDate clone(){
		SelectorDate selectorDate = new SelectorDate(date);
		return selectorDate;
	}


}
