package scheduler.view.calendar;

import java.util.Calendar;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import scheduler.common.constant.NameConstant;
import scheduler.common.utils.Util;
import scheduler.view.AbstractView;


/**
 * カレンダーをコントロールするビュークラス。<br>
 * 現在の値を内部で保持する
 * @author ohmoon
 *
 */
public class CalendarDateCTRLView extends AbstractView{


	/** 日付移動用ボタン */
	private final Button 	/** 一週間あとへ */
							forwardWeekButton,
							/** 一日後へ */
							forwardDayButton,
							/** 一週間前へ */
							backWeekButton,
							/** 一日後へ */
							backDayButton;

	/** 現在から何年前・後まで選択できるようにするか */
	private static final int RANGE_OF_VIEW_YEAR  =3;

	private static final int YEAR_TO_NEXT = 3;

	private static final int YEAR_FROM_BEFORE = 2;

	/** 年月日選択用 */
	private final ComboBox<Integer> selectYearBox,selectMonthBox,selectDayBox;


	private static final int[] DAYS_IN_MONTH = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};



	/** このビューが作成されたときの日時 */
	private final Calendar defaultDate;

	/** 現在保持する日時 */
	private Calendar currentDate;

	/**
	 * defaultDateからずらされた差。listenできる
	 */
	private  final IntegerProperty dayOffset;


	/**
	 * このビューが保持する現在の年月日を得る
	 * @return
	 */
	public Calendar getCurrentDate(){
		return this.currentDate;
	}


	/**
	 * defaultDateとの差のプロパティ。listenできる
	 * @return
	 */
	public IntegerProperty dayOffsetProperty(){
		return dayOffset;
	}


	/**defaultDateとdefaultDateの差の絶対値を返す
	 * currentDateと
	 * @return
	 */
	public int getDayOffset(){
		return Util.getOffsetOfDate(currentDate,defaultDate);
	}



	@Override
	protected void init() {

	}


	/**
	 * このビューに指定する年月日を表示させる
	 * @param date
	 */
	public void setDate(Calendar date){
		this.currentDate = date;
		this.resetDateView();
	}



	/**
	 * 保持する値を今日の年月日とするインスタンスを生成する
	 */
	public CalendarDateCTRLView(){

		defaultDate = Calendar.getInstance();
		currentDate = Calendar.getInstance();

		dayOffset = new SimpleIntegerProperty();

		selectYearBox = new ComboBox<Integer>();
		selectYearBox.setPrefSize(110, 35);
		selectYearBox.setTranslateX(160);
		selectYearBox.getStyleClass().add(NameConstant.CALENDAR_DATE_CTRL_SELECT_YEAR_CSS);
		initSelectYearBox();

		selectMonthBox = new ComboBox<Integer>();
		selectMonthBox.setPrefSize(74, 35);
		selectMonthBox.setTranslateX(280);
		selectMonthBox.getStyleClass().add(NameConstant.CALENDAR_DATE_CTRL_SELECT_MONTH_CSS);
		initSelectMonthBox();

		selectDayBox = new ComboBox<Integer> ();
		selectDayBox.setPrefSize(74, 35);
		selectDayBox.setTranslateX(360);
		selectDayBox.getStyleClass().add(NameConstant.CALENDAR_DATE_CTRL_SELECT_DAY_CSS);
		this.setDaysSelection(this.currentDate.get(Calendar.MONTH),true);

		selectDayBox.getSelectionModel().selectedItemProperty().addListener((r,o,newValue)->{
			onChangeDay(newValue);
		});


		backWeekButton = new Button();
		backDayButton = new Button();
		forwardWeekButton = new Button();
		forwardDayButton = new Button();

		//ボタン初期化
		initButtons();

		this.getChildren().addAll(
				this.backWeekButton,
				this.backDayButton,
				this.selectYearBox,
				this.selectMonthBox,
				this.selectDayBox,
				this.forwardDayButton,
				this.forwardWeekButton
				);

		System.out.println("CalendarDateCTRLView initialized");
		System.out.println("   defaultDate = "+Util.getSlashFormatCalendarValue(defaultDate, true));
		System.out.println("   currentDate = "+Util.getSlashFormatCalendarValue(currentDate, true));
		System.out.println("   dayOffset = "+this.dayOffset.intValue());
	}


	/**
	 * 年の選択用ボックスを初期化する
	 */
	private void initSelectYearBox(){
		int firstYear = this.currentDate.get(Calendar.YEAR)-YEAR_FROM_BEFORE;
		int lastYear = this.currentDate.get(Calendar.YEAR)+YEAR_TO_NEXT;
		for(int year = firstYear ; year<lastYear ; year++){
			selectYearBox.getItems().add(year);
		}
		//selectYearBox.setValue(selectYearBox.getItems().get(this.currentDate.get(Calendar.YEAR)-firstYear));
		selectYearBox.setValue(this.currentDate.get(Calendar.YEAR));

		selectYearBox.getSelectionModel().selectedItemProperty().addListener((r,o,newValue)->{
			onChangeYear(newValue);
		});
	}


	/**
	 * 新しい都市が選択されたとき
	 * @param selectedYear 選択された年
	 */
	private void onChangeYear(int selectedYear){
		int numOfYear = 365;
		int currentDayOffset = dayOffset.intValue();
		int currentYear = this.currentDate.get(Calendar.YEAR);

		System.out.printf("\nonChangeYear\n");
		System.out.println("   currentDate = "+Util.getSlashFormatCalendarValue(currentDate, true));
		System.out.println("   dayOffset = "+this.dayOffset.intValue());

		int offset;
		while((offset=currentYear-selectedYear) != 0){
			if(currentYear%100 == 0 && currentYear%400 != 0){
				numOfYear = 366;
			}
			int drc = offset/Math.abs(offset);
			currentDayOffset -= drc*numOfYear;
			currentYear -= drc;
		}
		this.currentDate.set(Calendar.YEAR, selectedYear);
		dayOffset.set(currentDayOffset);

		System.out.println("     --> currentDate = "+Util.getSlashFormatCalendarValue(currentDate, true));
		System.out.println("     --> dayOffset = "+this.dayOffset.intValue());
	}


	/**
	 * 月の選択用ボックスを初期化する
	 */
	private void initSelectMonthBox(){
		for(int month = 1;month<=12;month++){
			selectMonthBox.getItems().add(month);
		}
		selectMonthBox.setValue(selectMonthBox.getItems().get(this.currentDate.get(Calendar.MONTH)));

		selectMonthBox.getSelectionModel().selectedItemProperty().addListener((r,o,newValue)->{
			onChangeMonth(newValue-1);
		});
	}


	/**
	 * 新しい月が選択されたときのアクション
	 * @param selectedValue
	 */
	private void onChangeMonth(int selectedValue){

		System.out.printf("\nonChangeMonth\n");
		System.out.println("   currentDate = "+Util.getSlashFormatCalendarValue(currentDate, true));
		System.out.println("   dayOffset = "+this.dayOffset.intValue());

		this.setDaysSelection(selectedValue,false);

		int currentMonth = this.currentDate.get(Calendar.MONTH);
		int currentYear = this.currentDate.get(Calendar.YEAR);
		int currentDayOffset = dayOffset.intValue();
		int offset;
		while((offset = currentMonth-selectedValue)!=0){
			int numOfMonth = DAYS_IN_MONTH[currentMonth];
			if(currentMonth == 1 && currentYear%100 == 0 && currentYear%400 != 0){
				numOfMonth = 29;
			}
			int drc = offset/Math.abs(offset);
			currentDayOffset -= drc*numOfMonth;
			currentMonth -= drc;
		}
		this.currentDate.set(Calendar.MONTH, selectedValue);
		dayOffset.set(currentDayOffset);

		System.out.println("     --> currentDate = "+Util.getSlashFormatCalendarValue(currentDate, true));
		System.out.println("     --> dayOffset = "+this.dayOffset.intValue());
	}


	/**
	 * 新しい日が選択されたときのアクション
	 * @param selectedValue
	 */
	private void onChangeDay(int selectedValue){
//		System.out.printf("\nonChangeDay\n");
//		System.out.println("   currentDate = "+Util.getSlashFormatCalendarValue(currentDate, true));
//		System.out.println("   dayOffset = "+this.dayOffset.intValue());

		int currentOffset = this.dayOffset.intValue();
		int currentDay = this.currentDate.get(Calendar.DAY_OF_MONTH);
		int newOffset = currentOffset+(selectedValue-currentDay);
		this.currentDate.set(Calendar.DAY_OF_MONTH, selectedValue);
		this.dayOffset.set(newOffset);


//		System.out.println("     --> currentDate = "+Util.getSlashFormatCalendarValue(currentDate, true));
//		System.out.println("     --> dayOffset = "+this.dayOffset.intValue());
	}


	/**
	 * 新しい月が選択されたときに日の選択肢をその月の最後の日までにする
	 * @param month 月の値（月は０から始まる）
	 * @param initDayView 日の選択ボックスの表示も初期化するか
	 */
	private void setDaysSelection(int month,boolean initDayView){

		if(selectDayBox == null){
			return ;
		}
		if(month < 0 || month >= DAYS_IN_MONTH.length){
			return;
		}

		int year = this.currentDate.get(Calendar.YEAR);
		//selectDayBox.getItems().clear();

		int endOfMonth = DAYS_IN_MONTH[month];
		if(month == 1 && year%100 == 0 && year%400 != 0){
			endOfMonth = 29;
		}
		int day = 1;
		int size = selectDayBox.getItems().size();
		for(;day<=endOfMonth;day++){
			if(day>size){
				selectDayBox.getItems().add(day);
				continue;
			}
			selectDayBox.getItems().set(day-1, day);
		}
		for(;day<selectDayBox.getItems().size();day++){
			selectDayBox.getItems().remove(day-1);
		}
		if(initDayView){
			selectDayBox.setValue(selectDayBox.getItems().get(this.currentDate.get(Calendar.DAY_OF_MONTH))-1);
		}
	}


	/**
	 * 現在保持する年月日をビューに表示する
	 */
	private void resetDateView(){
		int firstYear = this.defaultDate.get(Calendar.YEAR);
		int year = currentDate.get(Calendar.YEAR);
		int month = currentDate.get(Calendar.MONTH);
		int day = currentDate.get(Calendar.DAY_OF_MONTH);

		try{
			selectYearBox.setValue(year);
		}catch(ArrayIndexOutOfBoundsException e){

		}
		try{
			selectMonthBox.setValue(selectMonthBox.getItems().get(month));
		}catch(ArrayIndexOutOfBoundsException e){

		}
		try{
			selectDayBox.setValue(selectDayBox.getItems().get(day-1));
		}catch(ArrayIndexOutOfBoundsException e){

		}
	}



	/**
	 * 日付移動用ボタンたちを初期化する
	 */
	private void initButtons(){
		ImageView backWeekButtonImage = new ImageView(NameConstant.PIC_URL_BACK_WEEK);
		backWeekButtonImage.setFitHeight(32);
		backWeekButtonImage.setFitWidth(32);
		backWeekButton.setGraphic(backWeekButtonImage);
		backWeekButton.getStyleClass().add(NameConstant.CALENDAR_DATE_CTRL_BACK_WEEK_CSS);
		backWeekButton.setPrefSize(35, 35);
		backWeekButton.setTranslateX(0);
		backWeekButton.setOnMouseClicked(event->{
			currentDate.add(Calendar.DAY_OF_MONTH, -7);
			dayOffset.set(dayOffset.intValue()-7);
			resetDateView();
		});

		ImageView backDayButtonImage = new ImageView(NameConstant.PIC_URL_BACK_DAY);
		backDayButtonImage.setFitHeight(32);
		backDayButtonImage.setFitWidth(32);
		backDayButton.setGraphic(backDayButtonImage);
		backDayButton.setPrefSize(35, 35);
		backDayButton.setTranslateX(40);
		backDayButton.getStyleClass().add(NameConstant.CALENDAR_DATE_CTRL_BACK_DAY_CSS);
		backDayButton.setOnMouseClicked(event->{
			currentDate.add(Calendar.DAY_OF_MONTH, -1);
			dayOffset.set(dayOffset.intValue()-1);
			resetDateView();
		});


		ImageView forwardWeekButtonImage = new ImageView(NameConstant.PIC_URL_FORWARD_WEEK);
		forwardWeekButtonImage.setFitHeight(32);
		forwardWeekButtonImage.setFitWidth(32);
		forwardWeekButton.setGraphic(forwardWeekButtonImage);
		forwardWeekButton.setPrefSize(35, 35);
		forwardWeekButton.setTranslateX(120);
		forwardWeekButton.getStyleClass().add(NameConstant.CALENDAR_DATE_CTRL_FORWARD_WEEK_CSS);
		forwardWeekButton.setOnMouseClicked(event->{
			currentDate.add(Calendar.DAY_OF_MONTH, 7);
			dayOffset.set(dayOffset.intValue()+7);
			resetDateView();
		});

		ImageView forwardDayButtonImage = new ImageView(NameConstant.PIC_URL_FORWARD_DAY);
		forwardDayButtonImage.setFitHeight(32);
		forwardDayButtonImage.setFitWidth(32);
		forwardDayButton.setGraphic(forwardDayButtonImage);
		forwardDayButton.setPrefSize(35, 35);
		forwardDayButton.setTranslateX(80);
		forwardDayButton.getStyleClass().add(NameConstant.CALENDAR_DATE_CTRL_FORWARD_DAY_CSS);
		forwardDayButton.setOnMouseClicked(event->{
			currentDate.add(Calendar.DAY_OF_MONTH, 1);
			dayOffset.set(dayOffset.intValue()+1);
			resetDateView();
		});
	}


}
