package scheduler.view.calendar;

import java.util.Calendar;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
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

		for(int index=0;index<length;index++){
			//初期化されたCalendarDayを得る
			CalendarDay calendarDay = getInitializedCalendarDay(date,index);
			this.getChildren().add(calendarDay);

			date.add(Calendar.DAY_OF_MONTH, 1);
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
		calendarDay.setDayOfWeek(date.get(Calendar.DAY_OF_WEEK));
		calendarDay.setDisplayDate(date.get(Calendar.DAY_OF_MONTH));
		//曜日で色を分ける
		setDateColor(calendarDay);

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
	 * 曜日に基づいて色をセットする
	 * @param calendarDay
	 */
	private void setDateColor(CalendarDay calendarDay){
		switch(calendarDay.getDayOfWeek()){
		case Calendar.SUNDAY:
			calendarDay.setStoneColor(Constant.CALENDAR_DATE_VIEW_SUNDAY);
			calendarDay.setMouseClickedColor(Constant.CALENDAR_DATE_VIEW_SUNDAY_CLICKED);
			calendarDay.setMouseHoveredColor(Constant.CALENDAR_DATE_VIEW_SUNDAY_HOVERED);
			break;
		case Calendar.SATURDAY:
			calendarDay.setStoneColor(Constant.CALENDAR_DATE_VIEW_SATURDAY);
			calendarDay.setMouseClickedColor(Constant.CALENDAR_DATE_VIEW_SATURDAY_CLICKED);
			calendarDay.setMouseHoveredColor(Constant.CALENDAR_DATE_VIEW_SATURDAY_HOVERED);
			break;
		default:
			calendarDay.setStoneColor(Constant.CALENDAR_DATE_VIEW_NOMAL);
			calendarDay.setMouseClickedColor(Constant.CALENDAR_DATE_VIEW_NOMAL_CLICKED);
			calendarDay.setMouseHoveredColor(Constant.CALENDAR_DATE_VIEW_NOMAL_HOVERED);
			break;
		}
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
		selectedIndex = new SimpleIntegerProperty();

		init();
	}



}
