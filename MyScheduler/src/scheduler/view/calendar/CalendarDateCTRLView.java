package scheduler.view.calendar;

import javafx.scene.control.Button;
import scheduler.view.AbstractView;

public class CalendarDateCTRLView extends AbstractView{



	private final Button 	forwardWeekButton,
							forwardDayButton,
							backWeekButton,
							backDayButton;

	@Override
	protected void init() {

	}


	public CalendarDateCTRLView(){
		forwardWeekButton = new Button();
		forwardWeekButton.setGraphic(null);
		forwardDayButton = new Button();
		backWeekButton = new Button();
		backDayButton = new Button();
	}
}
