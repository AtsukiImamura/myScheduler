package scheduler.view.calendar;

public class CalenderRow extends CalenderPrimitiveRow{


	protected static final double DEFAULT_VIEW_WIDTH = 400;
	protected static final double DEFAULT_VIEW_HEIGHT = 33;


	@Override
	protected void init(){

		this.viewWidth = DEFAULT_VIEW_WIDTH;
		this.viewHeight = DEFAULT_VIEW_HEIGHT;

		/*
		for(int i=0;i<10;i++){
			CalenderDay cd = new CalenderDay();
			cd.setTranslateX((CalenderDay.DEFAULT_WIDTH+5)*i);
			this.getChildren().add(cd);
		}
		*/
		CalenderPrimitiveRow cpr = new CalenderPrimitiveRow();
		cpr.setViewWidth(400);
		this.getChildren().add(cpr);
	}








}
