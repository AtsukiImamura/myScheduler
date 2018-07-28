package scheduler.view.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Node;
import scheduler.bean.TaskBean;
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
	private List<TaskBean> tasks;


	/**表示する最初の日*/
	private Calendar firstViewStartAt;
	/**表示する最後の日*/
	private Calendar lastViewStartAt;



	/**
	 * カレンダーの表示幅を設定
	 * @param width
	 */
	public void setViewWidth(double width){
		this.viewWidth = width;
		reset();
	}


	public List<TaskBean> getTasks() {
		return tasks;
	}


	public void setTasks(List<TaskBean> tasks) {
		this.tasks = tasks;
		reset();
	}


	/**
	 * タスクを一つ加える。タスク同士がかぶっているかどうかは考慮しない。
	 * @param task
	 */
	public void setTask(TaskBean task){
		this.tasks.add(task);
		this.getChildren().add(new CalendarViewTask(task));
	}


	/**
	 * 行の持つタスクを全て消去する
	 */
	public void resetTasks(){
		tasks = new ArrayList<TaskBean>();
		//TODO viewのリセットもする必要がある？ もしくは後でresetLengthされるときに調整？
	}


	@Override
	protected void init() {
		Util.log("initializing...");
		this.viewWidth = DEFAULT_VIEW_WIDTH;
		this.viewHeight = DEFAULT_VIEW_HEIGHT;

		tasks = new ArrayList<TaskBean>();


		//表示の開始日・終了日のインスタンスだけ生成。調整はresetLengthで。
		firstViewStartAt  = Calendar.getInstance();
		lastViewStartAt = Calendar.getInstance();


		//カレンダーの表示長
		int length = (int)(this.viewWidth/CalendarDay.DEFAULT_WIDTH);
		reset(length);

		Util.log("initialized");
	}


	private void reset(){
		int length = (int)(this.viewWidth/CalendarDay.DEFAULT_WIDTH);
		reset(length);
	}


	/**
	 * カレンダーの表示長が変化したときに表示全体をリセットする
	 * @param length 新しい表示長
	 */
	private void reset(int length){
		Util.log("length="+length+"  viewWidth="+this.viewWidth);
		this.getChildren().clear();
		for(int i=0;i<length;i++){
			CalendarDay cd = new CalendarDay();
			cd.setTranslateX(CalendarDay.DEFAULT_WIDTH*cd.getScaleX()*i);

			this.getChildren().add(cd);
		}
		tasks.forEach(task->{
			if(inCalendar(task)){
				//TODO カレンダーにタスク表示を加える
			}
		});
		//表示の開始日に表示長を足したものを表示の終了日とする
		lastViewStartAt.set(Calendar.DAY_OF_MONTH, firstViewStartAt.get(Calendar.DAY_OF_MONTH)+ length);
	}


	/**
	 * タスクがカレンダーにかぶっているかどうかを返す
	 * @param bean タスク
	 * @return
	 */
	private boolean inCalendar(TaskBean bean){

		//タスクの開始日がカレンダー表示の最終日より後だったり、終了日が表示の開始日より前だったりしなければtrue
		return !(firstViewStartAt.after(bean.getFinishAt())||lastViewStartAt.before(bean.getStartAt()));

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


}
