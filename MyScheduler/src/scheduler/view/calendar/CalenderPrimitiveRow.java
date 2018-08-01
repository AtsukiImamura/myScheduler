package scheduler.view.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import scheduler.bean.TaskBean;
import scheduler.common.constant.Constant;
import scheduler.common.utils.Util;
import scheduler.view.AbstractView;


/**
 * カレンダーでタスクの重複を許さない最も基底の行クラス
 * @author ohmoon
 *
 */
public class CalenderPrimitiveRow extends AbstractView {


	/**デフォルトの表示幅*/
	protected static final double DEFAULT_VIEW_WIDTH = 400;
	/**デフォルトの表示高さ*/
	protected static final double DEFAULT_VIEW_HEIGHT = 33;

	/**タスク*/
	private List<TaskBean> taskList;

	/**表示する最初の日*/
	private Calendar viewStartAt;
	/**表示する最後の日*/
	private Calendar viewFinishAt;


	/** 選択されている日付のインデックス */
	public final IntegerProperty selectedIndex;

	public final IntegerProperty hoveredIndex;




	public Calendar getviewStartAt() {
		return viewStartAt;
	}

	/**
	 * カレンダーに表示する最初の日付を設定する。表示幅に基づいて自動的に表示する最後の日付がセットされる。
	 * @param viewStartAt
	 */
	public void setViewStartAt(Calendar viewStartAt) {
		this.viewStartAt = viewStartAt;

		//変更した開始日で再描画する
		reset();
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
	 * カレンダーの表示幅を設定
	 * @param width 新しい表示幅
	 */
	public void setViewWidth(double width){
		this.viewWidth.set(width);
		reset();
	}


	public List<TaskBean> getTaskList() {
		return taskList;
	}


	/**
	 * このビューが表示するタスクを全て新しいタスクのリストで更新する。<br>
	 * 表示も更新される
	 * @param taskList
	 */
	public void setTaskList(List<TaskBean> taskList) {
		this.taskList = taskList;
		reset();
	}


	/**
	 * タスクを一つ加える。タスク同士がかぶっているかどうかは考慮しない。
	 * @param task
	 */
	public void setTask(TaskBean task){
		this.taskList.add(task);
		CalendarViewTask calendarViewTask = new CalendarViewTask(task,this.viewStartAt,this.viewFinishAt);

		//カレンダーの表示の開始日・終了日を登録
		try{
			//calendarViewTask.setViewStartAt(viewStartAt);
			//calendarViewTask.setViewFinishAt(viewFinishAt);

			this.getChildren().add(calendarViewTask);

			int offset = Util.getAbsOffsetOfDate(calendarViewTask.getTaskViewStartAt(), viewStartAt);
			calendarViewTask.setTranslateX(offset*CalendarDay.DEFAULT_WIDTH);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	/**
	 * 行の持つタスクを全て消去する
	 */
	public void resettaskList(){
		taskList = new ArrayList<TaskBean>();
		//TODO viewのリセットもする必要がある？ もしくは後でresetLengthされるときに調整？
	}


	/**
	 * 特定の日にハイライトをかける<br>
	 * setSelectedとかぶってるような...
	 * @param date ハイライトをかける日
	 */
	public void highlightDate(Calendar date){
		if(date.before(viewStartAt) || date.after(viewFinishAt)){
			return;
		}

		//一旦すべてのハイライトを除去
		deleteHighlight();

		//ハイライトをかけるべきCalendarDayのインデックス
		int diffOfDays;
		try{
			diffOfDays =  Util.getAbsOffsetOfDate(viewStartAt,date);
		}catch(Exception e){
			diffOfDays = 0;
		}
		CalendarDay calendarDay = findCalendarDay(diffOfDays);
		if(calendarDay == null){
			return;
		}
		calendarDay.setCurrentColor(Constant.CALENDAR_HILIGHT_COLOR);
	}





	/**
	 * ハイライトを除去する
	 */
	private void deleteHighlight(){
		for(Node child : this.getChildren()){
			if(!(child instanceof CalendarDay)){
				continue;
			}
			((CalendarDay)child).setCurrentColor(Constant.CALENDAR_NOMAL_COLOR);
		}
	}





	@Override
	protected void init() {

		this.viewHeight.set(DEFAULT_VIEW_HEIGHT);

		taskList = new ArrayList<TaskBean>();

		//表示の開始日・終了日のインスタンスだけ生成。getInstanceで現在時刻が取得できる。
		viewStartAt  = Calendar.getInstance();
		viewFinishAt = Calendar.getInstance();

		reset();
	}



	/**
	 * コンストラクタ
	 * @param width
	 */
	public CalenderPrimitiveRow(double width){
		selectedIndex = new SimpleIntegerProperty();
		hoveredIndex =  new SimpleIntegerProperty();
		this.viewWidth.set(width);

		init();

	}




	private void reset(){
		int length = (int)(this.viewWidth.doubleValue()/CalendarDay.DEFAULT_WIDTH);
		reset(length);
	}



	/**
	 * カレンダーの表示長が変化したときに表示全体をリセットする<br>
	 * 表示する最後の日は自動的に更新される
	 * @param length 表示する日数
	 */
	private void reset(int length){
		if(viewStartAt == null){
			viewStartAt  = Calendar.getInstance();
		}

		Calendar tmpDate = (Calendar)viewStartAt.clone();

		this.getChildren().clear();
		for(int index=0;index<length;index++){
			CalendarDay calendarDay = getInitializedCalendarDay(tmpDate,index);
			this.getChildren().add(calendarDay);

			tmpDate.add(Calendar.DAY_OF_MONTH, 1);
		}


		//表示の開始日に表示長を足したものを表示の終了日とする
		viewFinishAt = (Calendar)viewStartAt.clone();
		viewFinishAt.add(Calendar.DAY_OF_MONTH, length);


		taskList.forEach(task->{
			CalendarViewTask calendarViewTask = new CalendarViewTask(task,this.viewStartAt,this.viewFinishAt);

			//カレンダーの表示の開始日・終了日を登録
			try{
				//calendarViewTask.setViewStartAt(viewStartAt);
				//calendarViewTask.setViewFinishAt(viewFinishAt);

				int offset = Util.getAbsOffsetOfDate(calendarViewTask.getTaskViewStartAt(), viewStartAt);
				calendarViewTask.setTranslateX(offset*CalendarDay.DEFAULT_WIDTH);
				this.getChildren().add(calendarViewTask);
			}catch(Exception e){
				e.printStackTrace();
			}
		});

		//initViewTaskStartAndFinish();
	}




	private CalendarDay getInitializedCalendarDay(Calendar date,int index){
		CalendarDay calendarDay = new CalendarDay();
		calendarDay.setTranslateX(CalendarDay.DEFAULT_WIDTH*calendarDay.getScaleX()*index);
		calendarDay.setIndex(index);


		switch(date.get(Calendar.DAY_OF_WEEK)){
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


		//選択フラグが立った時の処理
		calendarDay.selectedProperty.addListener((ov,oldValue,newValue)->{
			if(oldValue.booleanValue() == newValue.booleanValue()){
				return;
			}
			int selected = newValue.booleanValue() ? index : -1;
			//選択されたCalendarDayのインデックスをセット
			if(selected == selectedIndex.intValue()){
				return;
			}
			this.selectedIndex.set(selected);
			/*
			for(Node child : this.getChildren()){
				if(!(child instanceof CalendarDay)){
					continue;
				}
				if(calendarDay.equals(child)){
					continue;
				}
				((CalendarDay)child).setSelected(false);
			}
			*/
		});

		calendarDay.hoveredProperty.addListener((ov,oldValue,newValue)->{
			if(!newValue){
				return;
			}
			if(newValue){
				this.hoveredIndex.set(index);
			}
		});

		return calendarDay;
	}


	/**
	 * ビューの持つ子要素のうち指定するインデックスのCalendarDay要素を取り出す
	 * @param index
	 * @return
	 */
	private CalendarDay findCalendarDay(int index){
		int count = 0;
		for(Node child : this.getChildren()){
			if(!(child instanceof CalendarDay)){
				continue;
			}
			count ++;
			if(count == index){
				return (CalendarDay)child;
			}
		}
		return null;
	}



	/**
	 * このビューが持つCalendarViewTaskの表示開始日・終了日を調整する
	 */
	private void initViewTaskStartAndFinish(){
		for(Node child : this.getChildren()){
			if(!(child instanceof CalendarViewTask)){
				continue;
			}
			try{
				((CalendarViewTask) child).setViewStartAt(this.viewStartAt);
				((CalendarViewTask) child).setViewFinishAt(this.viewFinishAt);
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	}



	/**
	 * タスクに基づいて表示されるviewを返す
	 * @param task タスク
	 * @return タスクを表示するview
	 */
	public Group getViewTask(TaskBean task){
		String code = task.getProjectCode();

		List<Node> children = this.getChildren();
		int size = children.size();
		for(int k=0;k<size;k++){
			Node view  = children.get(k);
			//viewがViewTaskのインスタンスかつプロジェクトコードがtaskのものと一致していれば
			if(view instanceof CalendarViewTask && ((CalendarViewTask)view).getTask().getProjectCode().equals(code)){
				return (Group) view;
			}
		}
		return null;
	}


	/**
	 * この行に指定するタスクを加えられるかどうかを返す。
	 * @param checkTask
	 * @return true(既存のタスクと重複がない場合） false(既存のタスクと重複がある場合)
	 */
	public boolean canAdd(TaskBean checkTask){
		Calendar startAt = checkTask.getStartAt();
		Calendar finishAt = checkTask.getFinishAt();

		for(TaskBean task : taskList){
			if(!(task.getStartAt().after(finishAt) || task.getFinishAt().before(startAt))){
				return false;
			}
		}
		return true;
	}


	/**
	 * 指定する日をマウスホバー状態にする
	 * @param date
	 */
	public void setHovered(Calendar date,boolean hovered){
		if(Util.compareCalendarDate(date, this.viewStartAt) < 0 || Util.compareCalendarDate(date,this.viewFinishAt) > 0){
			return;
		}
		int offset = Util.getAbsOffsetOfDate(date, viewStartAt);
		setHovered(offset,hovered);
	}


	public void setHovered(int index,boolean hovered){
		int count = 0;
		CalendarDay objectCalendarDay = null;
		for(Node child : this.getChildren()){
			if(!(child instanceof CalendarDay)){
				continue;
			}
			if(count == index){
				objectCalendarDay = (CalendarDay)child;
			}
			((CalendarDay)child).setHovered(false);
			count ++;
		}
		if(objectCalendarDay == null){
			return;
		}
		objectCalendarDay.setHovered(hovered);
	}



	/**
	 * 指定する日を選択状態にする
	 * @param date
	 */
	public void setSelected(Calendar date,boolean selected){
		if(Util.compareCalendarDate(date, this.viewStartAt) < 0 || Util.compareCalendarDate(date,this.viewFinishAt) > 0){
			return;
		}
		int offset = Util.getAbsOffsetOfDate(date, viewStartAt);
		setSelected(offset,selected);
	}



	public void setSelected(int index,boolean selected){
		int count = 0;
		CalendarDay objectCalendarDay = null;
		for(Node child : this.getChildren()){
			if(!(child instanceof CalendarDay)){
				continue;
			}
			if(count == index){
				objectCalendarDay = (CalendarDay)child;
			}else{
				((CalendarDay)child).setSelected(false);
			}
			count ++;
		}
		if(objectCalendarDay == null){
			return;
		}
		objectCalendarDay.setSelected(selected);
	}


}
