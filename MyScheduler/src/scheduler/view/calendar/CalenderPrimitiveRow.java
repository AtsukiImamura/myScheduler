package scheduler.view.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
		CalendarViewTask calendarViewTask = new CalendarViewTask(task);

		//カレンダーの表示の開始日・終了日を登録
		calendarViewTask.setViewStartAt(viewStartAt);
		calendarViewTask.setViewFinishAt(viewFinishAt);
		this.getChildren().add(calendarViewTask);
	}


	/**
	 * 行の持つタスクを全て消去する
	 */
	public void resettaskList(){
		taskList = new ArrayList<TaskBean>();
		//TODO viewのリセットもする必要がある？ もしくは後でresetLengthされるときに調整？
	}


	/**
	 * 特定の日にハイライトをかける
	 * @param date ハイライトをかける日
	 */
	public void highlightDate(Calendar date){
		if(date.before(viewStartAt) || date.after(viewFinishAt)){
			return;
		}

		//一旦すべてのハイライトを除去
		deleteHighlight();

		//ハイライトをかけるべきCalendarDayのインデックス
		int diffOfDays = getOffsetOfDate(viewStartAt,date);
		CalendarDay calendarDay = findCalendarDay(diffOfDays);
		if(calendarDay == null){
			return;
		}
		calendarDay.setStoneColor(Constant.CALENDAR_HILIGHT_COLOR);
	}



	/**
	 * 二つの日付の差を返す
	 * @param date1
	 * @param date2
	 * @return
	 */
	private int getOffsetOfDate(Calendar date1,Calendar date2){
		int diffOfDays = 0;
		Calendar tmpDate = (Calendar)date1.clone();

		int offset;
		while((offset = tmpDate.compareTo(date2)) != 0){
			diffOfDays++;
			// A.compareTo(B)はAのほうが大きい時に１が返るので、逆の方向に進めるためにー１をかける
			tmpDate.add(Calendar.DAY_OF_MONTH, offset*(-1));
		}
		return diffOfDays;
	}


	private void deleteHighlight(){
		for(Node child : this.getChildren()){
			if(!(child instanceof CalendarDay)){
				continue;
			}
			((CalendarDay)child).setStoneColor(Constant.CALENDAR_NOMAL_COLOR);
		}
	}





	@Override
	protected void init() {
		Util.log("initializing...");
		this.viewWidth.set(DEFAULT_VIEW_WIDTH);
		this.viewHeight.set(DEFAULT_VIEW_HEIGHT);

		taskList = new ArrayList<TaskBean>();


		//表示の開始日・終了日のインスタンスだけ生成。getInstanceで現在時刻が取得できる。
		viewStartAt  = Calendar.getInstance();
		viewFinishAt = Calendar.getInstance();


		//カレンダーの表示長
		reset();

		Util.log("initialized");
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
		Util.log("length="+length+"  viewWidth="+this.viewWidth);
		this.getChildren().clear();
		for(int i=0;i<length;i++){
			CalendarDay calendarDay = new CalendarDay();
			calendarDay.setTranslateX(CalendarDay.DEFAULT_WIDTH*calendarDay.getScaleX()*i);
			calendarDay.setStoneColor(Constant.CALENDAR_NOMAL_COLOR);

			this.getChildren().add(calendarDay);
		}


		//表示の開始日に表示長を足したものを表示の終了日とする
		viewFinishAt = (Calendar)viewStartAt.clone();
		viewFinishAt.add(Calendar.DAY_OF_MONTH, length);


		taskList.forEach(task->{
			CalendarViewTask calendarViewTask = new CalendarViewTask(task);

			//カレンダーの表示の開始日・終了日を登録
			calendarViewTask.setViewStartAt(viewStartAt);
			calendarViewTask.setViewFinishAt(viewFinishAt);
			this.getChildren().add(calendarViewTask);
		});

		//initViewTaskStartAndFinish();
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
			((CalendarViewTask) child).setViewStartAt(this.viewStartAt);
			((CalendarViewTask) child).setViewFinishAt(this.viewFinishAt);
		}

	}


	/**
	 * タスクがカレンダーにかぶっているかどうかを返す
	 * @param bean タスク
	 * @return
	 */
	private boolean inCalendar(TaskBean bean){

		//タスクの開始日がカレンダー表示の最終日より後だったり、終了日が表示の開始日より前だったりしなければtrue
		return !(viewStartAt.after(bean.getFinishAt())||viewFinishAt.before(bean.getStartAt()));

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


}
