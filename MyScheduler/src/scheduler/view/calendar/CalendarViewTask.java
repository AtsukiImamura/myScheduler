package scheduler.view.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import scheduler.bean.StoneBean;
import scheduler.bean.TaskBean;
import scheduler.common.constant.Constant;
import scheduler.common.utils.Util;
import scheduler.facade.StoneFacade;
import scheduler.view.AbstractView;
import scheduler.view.projectDisplayer.TaskPopup;
import scheduler.view.workDisplayer.TaskDetailController;

/**
 * カレンダーで一つのタスクを保持するビューのクラス。
 * @author ohmoon
 *
 */
public class CalendarViewTask  extends AbstractView{

	private final StoneFacade stoneFacade;

	/** カレンダーの日のリスト */
	private final List<CalendarDay> CalendarDayList;

	/** 選択されているCalendarDayのインデックス */
	public final IntegerProperty selectedIndexProperty;

	/** このタスクのストーンカラー */
	private Color stoneColor;

	/** マウスが入ったときに出すポップアップ */
	private final TaskPopup taskPopup;

	/**viewが表すタスク*/
	private TaskBean task;

	/** ビューに表示する開始日 */
	private Calendar viewStartAt;

	/** ビューに表示する終了日 */
	private Calendar viewFinishAt;


	/**
	 * このビューの表示対象の開始日を設定する。<br>
	 * それに伴ってタスクの表示長さを調節する
	 * @param viewStartAt
	 * @throws Exception
	 */
	public void setViewStartAt(Calendar viewStartAt){
		//更新
		this.viewStartAt = viewStartAt;

		//調整を加える
		ajustNumOfCalendarDays();
	}


	public void setViewPeriod(Calendar viewStartAt,Calendar viewFinishAt){
		this.viewStartAt = viewStartAt;
		this.viewFinishAt = viewFinishAt;

		ajustNumOfCalendarDays();
	}

	/**
	 * 表示の対象期間とタスクに基づいてCalendarDayの数を調節する。<br>
	 * initと同様の作業を行っているが、すでにあるCalendarDayは破棄されずに再利用される点に違いがある
	 */
	public void ajustNumOfCalendarDays(){
		int numOfDuplicatedPeriod = getNumOfDuplicatedPeriod();
		if(numOfDuplicatedPeriod >= 0){
			this.setVisible(true);
		}else{
			this.setVisible(false);
			return;
		}
		int index = 0;
		List<Node> deleteChildList = new ArrayList<Node>();
		for(Node child : this.getChildren()){
			if(!(child instanceof CalendarDay)){
				continue;
			}
			if(index >numOfDuplicatedPeriod){
				//以前より重なる期間が少なくなった場合は無駄なものを削除する
				deleteChildList.add(child);
				continue;
			}
			//インデックスを振りなおす
			((CalendarDay)child).setIndex(index);
			child.setTranslateX(CalendarDay.DEFAULT_WIDTH*child.getScaleX()*index);
			index ++;
		}
		//ビューの持つCalendarDayが必要数に足りていなければ新たに作成する
		while(index <= numOfDuplicatedPeriod){
			CalendarDay calendarDay = this.getInitializedCalendarDay(stoneColor);
			calendarDay.setIndex(index);
			calendarDay.setTranslateX(CalendarDay.DEFAULT_WIDTH*calendarDay.getScaleX()*index);
			this.getChildren().add(calendarDay);
			index++;
		}

		//不要なCalendarDayたちは削除する
		this.getChildren().removeAll(deleteChildList);
	}


	/**
	 * 塗りつぶす色をリフレッシュする。stoneColorを変更したときに呼び出す
	 */
	private void refreshColor(){
		for(Node child : this.getChildren()){
			if(!(child instanceof CalendarDay)){
				continue;
			}
			((CalendarDay)child).setStoneColor(stoneColor);
		}
	}

	/**
	 * このビューの表示対象の期間とタスクの期間の重なっている日数を返す
	 * @return
	 */
	private int getNumOfDuplicatedPeriod(){
		Calendar start = this.getTaskViewStartAt();
		Calendar finish = this.getTaskViewFinishAt();

		return Util.getOffsetOfDate(finish, start);
	}


	public void setViewFinishAt(Calendar viewFinishAt)  throws Exception{
		this.viewFinishAt = viewFinishAt;

		ajustNumOfCalendarDays();
	}


	/**
	 * 表示の開始日・終了日を考慮して、タスクとしてあらわされる部分の最初の日付を返す。<br>
	 * 具体的には、viewStartAtとタスクの開始日の後ろのほうの日付を返す。
	 * @return
	 */
	public Calendar getTaskViewStartAt(){
		return Util.compareCalendarDate(viewStartAt, task.getStartAt())==1 ?  (Calendar)viewStartAt.clone() : (Calendar)task.getStartAt().clone();
	}


	/**
	 * 表示の開始日・終了日を考慮して、タスクとしてあらわされる部分の最後の日付を返す。<br>
	 * 具体的には、viewFinishAtとタスクの終了日の早いのほうの日付を返す。
	 * @return
	 */
	public Calendar getTaskViewFinishAt(){
		return Util.compareCalendarDate(viewFinishAt,task.getFinishAt())==1 ? (Calendar)task.getFinishAt().clone() : (Calendar)viewFinishAt.clone();
	}


	public Color getStoneColor() {
		if(stoneColor==null){
			return Color.WHITE;
		}else{
			return stoneColor;
		}
	}


	public void setStoneColor(Color stoneColor) {
		this.stoneColor = stoneColor;
		for(CalendarDay calendarDay : CalendarDayList){
			calendarDay.setStoneColor(stoneColor);
		}
	}


	@Override
	protected void init() {

		if(task == null){
			return;
		}

		this.getChildren().clear();
		this.CalendarDayList.clear();

		Calendar startAt = task.getStartAt();
		Calendar finishAt = task.getFinishAt();

		Calendar tmpDate;
		if(viewStartAt == null){
			tmpDate  = (Calendar) startAt.clone();
		}else{
			//startAtとviewStartAtの後のほうの日付を取得
			tmpDate = this.getTaskViewStartAt();
		}

		Calendar tmpFinishAt;
		if(viewFinishAt == null){
			tmpFinishAt  = (Calendar) finishAt.clone();
		}else{
			//finishAtとviewFinishAtの後のほうの日付を取得
			tmpFinishAt = this.getTaskViewFinishAt();
		}


		int index = 0;
		while(Util.compareCalendarDate(tmpDate, tmpFinishAt)!=1){
			CalendarDay calendarDay = getInitializedCalendarDay(stoneColor);
			calendarDay.setTranslateX(CalendarDay.DEFAULT_WIDTH*calendarDay.getScaleX()*index);
			calendarDay.setIndex(index);

			CalendarDayList.add(calendarDay);
			tmpDate.add(Calendar.DAY_OF_MONTH, 1);
			index ++;
		}
		this.getChildren().addAll(CalendarDayList);


		//TODO 自身のy方向位置を調整
		//this.setTranslateY(value);
	}




	/**
	 * このビューのために初期化されたCalendarDayを返す。<br>
	 * ただし、index,translateXはあとで登録する必要がある。
	 * @return
	 */
	private CalendarDay getInitializedCalendarDay(Color stoneColor){
		CalendarDay calendarDay = new CalendarDay();

		//カラーリング

		calendarDay.setStoneColor(stoneColor);

		calendarDay.selectedProperty.addListener((ov,oldValue,newValue)->{
			if(newValue){
				//選択されたCalendarDayのインデックスをセット
				if(calendarDay.getIndex() != -1){
					this.selectedIndexProperty.set(calendarDay.getIndex());
				}
				for(CalendarDay day : CalendarDayList){
					if(calendarDay.equals(day)){
						continue;
					}
					day.setSelected(false);
				}
			}
		});


		/* マウスが入ったとき : ポップアップを出す */
		calendarDay.setOnMouseEntered(event->{
			double mouseX = event.getX();
			double mouseY = event.getY();

			/* TODO ここを、マウスからの相対位置にしたい。
			 * そのためには、thisの絶対位置を知らないといけない
			 */
			taskPopup.setTranslateX(Constant.POPUP_TRANSLATE_X);
			taskPopup.setTranslateY(Constant.POPUP_TRANSLATE_Y);
			this.getChildren().add(taskPopup);

		});

		/* マウスが出たとき : ポップアップを消す */
		calendarDay.setOnMouseExited(event->{
			undisplayPopup();
		});

		/* クリックされたとき : タスクの詳細を表示する */
		calendarDay.setOnMouseClicked(event->{
			TaskDetailController.getInstance().setTask(task);
			TaskDetailController.getInstance().show();
		});

		return calendarDay;

	}



	/**
	 * ポップアップを消す（ポップアップは同時に複数出ていないことが前提）
	 */
	private void undisplayPopup(){
		for(Node child : this.getChildren()){
			if(!(child instanceof TaskPopup)){
				continue;
			}
			this.getChildren().remove(child);
			return;
		}
	}


	public TaskBean getTask() {
		return task;
	}


	public void setTask(TaskBean task) {
		this.task = task;
		stoneColor = findStoneColor(task);
		ajustNumOfCalendarDays();
		refreshColor();
	}


	/**
	 * コンストラクタ
	 * @param task このビューが扱うタスク
	 */
	public CalendarViewTask(TaskBean task,Calendar viewStartAt,Calendar viewFinishAt){

		stoneFacade = new StoneFacade();
		this.task = task;
		CalendarDayList = new ArrayList<CalendarDay>();
		selectedIndexProperty = new SimpleIntegerProperty();
		taskPopup = new TaskPopup(task);

		this.viewStartAt = viewStartAt;
		this.viewFinishAt = viewFinishAt;

		stoneColor = findStoneColor(task);

		init();
	}


	private Color findStoneColor(TaskBean task){
		String stoneCode = task.getCode();
		StoneBean stoneBean = stoneFacade.one(stoneCode);
		stoneColor = stoneBean == null ? Color.RED : stoneBean.getColor();

		return stoneColor;
	}
}
