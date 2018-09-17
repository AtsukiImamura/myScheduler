package scheduler.view.dateSelector;

import java.util.Calendar;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import scheduler.common.utils.Util;

public class DateSelectInput extends Group{


	private final double DEFAULT_WIDTH = 180;

	private final double DEFAULT_HEIGHT = 32;

	private final double BUTTON_WIDTH = 72;

	private final TextField textField;

	private Calendar selectedDate = null;

	public final BooleanProperty selectedProperty;

	private DateSelector dateSelector;

	public Calendar getSelectedDate(){
		return selectedDate;
	}


	/**
	 * 指定した日付よりも前の日を選択不可能にする
	 * @param date
	 */
	public void setDisableBefore(Calendar date){
		this.dateSelector.setDisableBefore(date);
	}

	/**
	 * 指定した日付よりも前の日を選択不可能にする
	 * @param date
	 */
	public void setDisableAfter(Calendar date){
		this.dateSelector.setDisableAfter(date);
	}



	public void setDate(Calendar date){
		selectedDate = date;
		textField.setText(Util.getBarFormatCalendarValue(date, true));
		selectedProperty.set(true);
	}



	public DateSelectInput(){
		textField = new TextField();
		textField.setPrefSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.getChildren().add(textField);

		selectedProperty = new SimpleBooleanProperty();

		dateSelector = new DateSelector();
		dateSelector.setTranslateX(DEFAULT_WIDTH*0.5);
		dateSelector.setTranslateY(DEFAULT_HEIGHT);
		dateSelector.setVisible(false);
		dateSelector.localToScene(new Point2D(0,0));
		this.getChildren().add(dateSelector);

		Button button = new Button("選択");
		button.setPrefSize(BUTTON_WIDTH, DEFAULT_HEIGHT);
		button.setTranslateX(DEFAULT_WIDTH);
		this.getChildren().add(button);

		button.setOnAction(event->{
			dateSelector.refresh();
			dateSelector.setVisible(true);
			selectedProperty.set(false);
		});

		dateSelector.selectedProperty.addListener((ev,oldValue,newValue)->{
			if(!newValue){
				return;
			}
			selectedDate = dateSelector.getSelectedDate();
			textField.setText(Util.getBarFormatCalendarValue(selectedDate, true));
			dateSelector.setVisible(false);
			selectedProperty.set(true);
		});



		//this.setBlendMode(BlendMode.ADD);
		//this.maxHeight(DEFAULT_HEIGHT+20);
	}

}
