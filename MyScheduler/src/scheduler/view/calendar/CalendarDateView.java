package scheduler.view.calendar;

import java.util.Calendar;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import scheduler.common.constant.Constant;
import scheduler.view.AbstractView;

public class CalendarDateView extends AbstractView{



	private final IntegerProperty selectedIndex;


	/**表示する最初の日*/
	private Calendar viewStartAt;
	/**表示する最後の日*/
	private Calendar viewFinishAt;

	@Override
	protected void init() {

		if(viewWidth == null){
			return;
		}

		viewStartAt = Calendar.getInstance();
		initCalendarDays();
	}


	/**
	 * このビューで表示する日付を初期化する。<br>
	 * このメソッドを呼ぶ前にはviewStartAtを適切に設定しておく必要がある
	 */
	private void initCalendarDays(){
		if(viewWidth == null ||viewStartAt == null){
			return;
		}

		this.getChildren().clear();

		Calendar date =  (Calendar)viewStartAt.clone();
		int length = (int)(this.viewWidth.doubleValue()/CalendarDay.DEFAULT_WIDTH);

		Label monthNameLabel = new Label();
		double monthNameLabelWidth = 0;
		double monthNameLabelTransX = 0;
		int currentMonth = -1;
		for(int index=0;index<length;index++){

			if(date.get(Calendar.MONTH) != currentMonth){
				currentMonth = date.get(Calendar.MONTH);
				monthNameLabelTransX += monthNameLabelWidth;
				monthNameLabel = new Label((currentMonth+1)+Constant.POSTFIX_CALEDAR_MONTH);
				monthNameLabel.setTranslateX(monthNameLabelTransX);
				monthNameLabelWidth = 0;
				int monoColorValue = Constant.MONTH_NAME_LABEL_BACK_BASE + Constant.VAR_OF_BACK_BASE*(1-(currentMonth%2)*2);
				monthNameLabel.setBackground(new Background(new BackgroundFill(Color.rgb(monoColorValue, monoColorValue, monoColorValue),new CornerRadii(0),new Insets(0))));
				monthNameLabel.setPrefHeight(Constant.MONTH_NAME_LABEL_HEIGHT);
				this.getChildren().add(monthNameLabel);
			}

			//初期化されたCalendarDayを得る
			CalendarDay calendarDay = getInitializedCalendarDay(date,index);
			this.getChildren().add(calendarDay);

			date.add(Calendar.DAY_OF_MONTH, 1);

			monthNameLabelWidth += calendarDay.viewWidth.doubleValue();
			monthNameLabel.setPrefWidth(monthNameLabelWidth);
			monthNameLabel.setMaxWidth(monthNameLabelWidth);
		}
		date.add(Calendar.DAY_OF_MONTH, -1);
		viewFinishAt =  (Calendar)date.clone();
	}



	/**
	 * カレンダーの表示開始日を設定する<br>
	 * 表示の最終日はこのビューの幅から暗黙的に設定される
	 * @param date
	 */
	public void setViewStartAt(Calendar date){
		viewStartAt = date;
		initCalendarDays();
	}



	public Calendar getViewStartAt(){
		return this.viewStartAt;
	}


	/**
	 * カレンダーを一日前にする
	 */
	public void backViewDate(){
		viewStartAt.add(Calendar.DAY_OF_MONTH, -1);
		setViewStartAt(viewStartAt);
	}


	/**
	 * カレンダーを一日後ろにする
	 */
	public void forwardViewDate(){
		viewStartAt.add(Calendar.DAY_OF_MONTH, 1);
		setViewStartAt(viewStartAt);
	}



	/**
	 * 所定の日付に関して初期化されたCalendarDayを得る
	 * @param date 設定する日付
	 * @param index 何番目の要素であるか
	 * @return
	 */
	private CalendarDay getInitializedCalendarDay(Calendar date,int index){
		CalendarDay calendarDay = new CalendarDay();
		calendarDay.setTranslateX(CalendarDay.DEFAULT_WIDTH*calendarDay.getScaleX()*index);
		calendarDay.setTranslateY(Constant.MONTH_NAME_LABEL_HEIGHT);
		calendarDay.setDisplayDate(date,true);
		//曜日で色を分ける
		//setDateColor(calendarDay);

		//選択フラグが立った時の処理
		calendarDay.selectedProperty.addListener((ov,oldValue,newValue)->{
			if(!newValue){
				return;
			}
			if(newValue){
				//選択されたCalendarDayのインデックスをセット
				this.selectedIndex.set(index);
				for(Node child : this.getChildren()){
					if(!(child instanceof CalendarDay)){
						continue;
					}
					if(calendarDay.equals(child)){
						continue;
					}
					((CalendarDay)child).setSelected(false);
				}
			}
		});
		return calendarDay;
	}



	/**
	 * 与えられたCalendarDay（日付のビュー）が何番目の要素化を返す
	 * @param calendarDay
	 * @return
	 */
	private int getIndexOf(CalendarDay calendarDay){
		int index = 0;
		for(Node child : this.getChildren()){
			if(!(child instanceof CalendarDay)){
				continue;
			}
			index ++;
		}
		return index;
	}



	/**
	 * コンストラクタ<br>
	 * カレンダーを作成する。デフォルトでは表示の開始日は今日に設定される。表示の最終日は指定する幅によって暗黙的に決定される
	 * @param width
	 */
	public CalendarDateView(double width){
		this.viewWidth.set(width);
		this.viewHeight.set(Constant.PROJECT_CALENDAR_ROW_HEIGHT);
		selectedIndex = new SimpleIntegerProperty();

		init();
	}



	public void setWidth(double width){
		this.viewWidth.set(width);
		initCalendarDays();

	}


}
