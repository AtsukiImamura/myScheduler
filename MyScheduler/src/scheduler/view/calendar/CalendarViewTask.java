package scheduler.view.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import scheduler.bean.TaskBean;
import scheduler.view.AbstractView;
import scheduler.view.TaskPopup;

/**
 * カレンダーで一つのタスクを保持するビューのクラス。
 * @author ohmoon
 *
 */
public class CalendarViewTask  extends AbstractView{


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

		Calendar startAt = task.getStartAt();
		Calendar finishAt = task.getFinishAt();
		Calendar tmpDate = (Calendar) startAt.clone();

		int index = 0;
		while(!tmpDate.after(finishAt)){
			CalendarDay calendarDay = new CalendarDay();
			//cd.setTranslateX(value);
			calendarDay.setTranslateX(CalendarDay.DEFAULT_WIDTH*calendarDay.getScaleX()*index);

			//TODO カラーリング
			calendarDay.setStoneColor(Color.RED);
			//cd.setColor(task.getStoneColor());
			CalendarDayList.add(calendarDay);
			tmpDate.add(Calendar.DAY_OF_MONTH, 1);

			int selectedIndex = index;
			calendarDay.selectedProperty.addListener((ov,oldValue,newValue)->{
				if(newValue){
					//選択されたCalendarDayのインデックスをセット
					this.selectedIndexProperty.set(selectedIndex);
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
				taskPopup.setTranslateX(30);
				taskPopup.setTranslateY(30);
				this.getChildren().add(taskPopup);

			});

			/* マウスが出たとき : ポップアップを消す */
			calendarDay.setOnMouseExited(event->{
				//TODO 無理やり消しているので、もう少し安定的な方法でやりたい
				this.getChildren().remove(this.getChildren().size()-1);
			});

			index ++;
		}
		this.getChildren().addAll(CalendarDayList);


		//TODO 自身のy方向位置を調整
		//this.setTranslateY(value);


	}


	public TaskBean getTask() {
		return task;
	}



	//TODO セットするときにはinitする必要があるが、ほかに影響が出るかもしれないので要修正
	public void setTask(TaskBean task) {
		this.task = task;
	}


	/**
	 * コンストラクタ
	 * @param task このビューが扱うタスク
	 */
	public CalendarViewTask(TaskBean task){
		this.task = task;
		CalendarDayList = new ArrayList<CalendarDay>();
		selectedIndexProperty = new SimpleIntegerProperty();
		taskPopup = new TaskPopup(task);

		init();
	}

}
