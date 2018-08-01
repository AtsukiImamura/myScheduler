package scheduler.view.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import scheduler.bean.ProjectBean;
import scheduler.bean.TaskBean;
import scheduler.common.constant.Constant;
import scheduler.common.utils.Util;
import scheduler.view.AbstractView;


/**
 * カレンダー部で一つの案件と対応するカレンダーを担うビュークラス
 * @author ohmoon
 *
 */
public class CalenderRow extends AbstractView{


	/** このビューが持つ案件 */
	private final ProjectBean project;

	/** タスク表示用のカレンダー行のリスト */
	private final List<CalenderPrimitiveRow> primitiveRowList;


	public final IntegerProperty selectedIndex;


	public final IntegerProperty hoveredIndex;






	@Override
	protected void init(){

		this.viewWidth.set(Constant.CALENDAR_ROW_VIEW_WIDTH);
		this.viewHeight.set(Constant.CALENDAR_ROW_VIEW_HEIGHT);


		//primitiveRowListの初期化
		initPrimitiveRowList();

		if(primitiveRowList != null){

			for(int index = primitiveRowList.size()-1;index>=0;index--){
				this.getChildren().add(primitiveRowList.get(index));
			}
			//this.getChildren().addAll(primitiveRowList);
		}
	}



	/**
	 * primitiveRowListの初期化
	 */
	private void initPrimitiveRowList(){
		if(project == null || project.getTaskBeanList() == null){
			this.viewHeight.set(0);
			return;
		}

		//projectの中のタスクリストを開始日順で並び替えたもの
		List<TaskBean> taskBeanList = Util.sortTasksByStartAt(project.getTaskBeanList());

		//primitiveRowList[0]に直前に入れたタスク
		TaskBean primaryTask = null;
		try{
			primaryTask = taskBeanList.get(0);
		}catch(ArrayIndexOutOfBoundsException e){
			this.resetViewHeight();
		}
		int primitiveRowListIndex = 0;
		int primitiveRowListSize;
		for(TaskBean task : taskBeanList){
			primitiveRowListSize = primitiveRowList.size();

			//taskとprimaryTaskがかぶっていなければ
			if(!validateConfrict(task,primaryTask)){
				//primitiveRowListの[0]から新たに入れ始める
				primitiveRowListIndex = 0;
				primaryTask = task;
			}

			//primitiveRowListIndexがprimitiveRowListの容量を超えた場合は新たに作成していれる
			if(primitiveRowListIndex ==primitiveRowListSize ){
				CalenderPrimitiveRow newPrimitiveRow = getInitializedPrimitiveRow();
				newPrimitiveRow.setTranslateY(CalendarDay.DEFAULT_HEIGHT*primitiveRowListIndex);

				//選択されている列に変化があった場合
				newPrimitiveRow.selectedIndex.addListener((ov,oldValue,newValue)->{
					System.out.println("newPrimitiveRow clicked :");
					System.out.println(" oldValue						="+oldValue);
					System.out.println(" newValue						="+newValue);
					System.out.println(" selectedIndex					="+selectedIndex.intValue());
					System.out.println(" hoveredIndex					="+hoveredIndex.intValue());
					System.out.println(" newPrimitiveRow.selectedIndex	="+newPrimitiveRow.selectedIndex.intValue());
					System.out.println(" newPrimitiveRow.hoveredIndex	="+newPrimitiveRow.hoveredIndex.intValue());
					boolean selected;
					if(this.selectedIndex.intValue() == newValue.intValue()){
						selected = false;
						this.selectedIndex.set(-1);
					}else{
						selected = true;
						this.selectedIndex.set(newValue.intValue());
					}
					System.out.println("   --> selected						="+selected);
					System.out.println("   --> selectedIndex				="+selectedIndex.intValue());
					System.out.println("   --> hoveredIndex					="+hoveredIndex.intValue());
					System.out.println("   --> newPrimitiveRow.selectedIndex="+newPrimitiveRow.selectedIndex.intValue());
					System.out.println("   --> newPrimitiveRow.hoveredIndex	="+newPrimitiveRow.hoveredIndex.intValue());
					for(CalenderPrimitiveRow primitiveRow: primitiveRowList){
						primitiveRow.setSelected(oldValue.intValue(),!selected);
					}
					for(CalenderPrimitiveRow primitiveRow: primitiveRowList){
						primitiveRow.setSelected(newValue.intValue(),selected);
					}
				});
				//マウスホバーに変化があった場合
				newPrimitiveRow.hoveredIndex.addListener((ov,oldValue,newValue)->{
					if(newValue.intValue() == selectedIndex.intValue()){
						return;
					}
/*
					if(this.hoveredIndex.intValue() == newValue.intValue()){
						return;
					}
					*/
					hoveredIndex.set(newValue.intValue());
					for(CalenderPrimitiveRow primitiveRow: primitiveRowList){
						primitiveRow.setHovered(oldValue.intValue(),false);
					}

					for(CalenderPrimitiveRow primitiveRow: primitiveRowList){
						primitiveRow.setHovered(newValue.intValue(),true);
					}
				});

				primitiveRowList.add(newPrimitiveRow);
			}
			primitiveRowList.get(primitiveRowListIndex).setTask(task);
			primitiveRowListIndex++;
		}
		this.resetViewHeight();
	}





	/**
	 * このビューで用いるために初期化されたCalenderPrimitiveRowを返す
	 * @return
	 */
	private CalenderPrimitiveRow getInitializedPrimitiveRow(){
		CalenderPrimitiveRow newPrimitiveRow = new CalenderPrimitiveRow(this.viewWidth.doubleValue());
		newPrimitiveRow.setViewWidth(this.viewWidth.doubleValue());

		return newPrimitiveRow;
	}




	/**
	 * 二つのタスクが重なり合っているかを返す
	 * @param task1
	 * @param task2
	 * @return
	 */
	private boolean validateConfrict(TaskBean task1,TaskBean task2){
		return !(task1.getFinishAt().before(task2.getStartAt()) || task1.getStartAt().after(task2.getFinishAt()));
	}



	/**
	 * コンストラクタ<br>
	 * 案件がもつタスクを表示するカレンダーのビューを作成する。
	 * タスク同士の期間が重なっている部分は複数行に分けて表示される。
	 * デフォルトでは表示の開始時点は今日である。変更はsetViewStartAtを用いる。
	 * viewWidthは初期時点ではデフォルト値に設定されるから、あとでsetViewWidthを用いる必要がある。
	 * viewHeightはタスクの表示に要された行数に基づいて適切に設定される。
	 * @param project
	 */
	public CalenderRow(ProjectBean project){
		this.project = project;
		primitiveRowList = new ArrayList<CalenderPrimitiveRow>();
		selectedIndex = new SimpleIntegerProperty();
		hoveredIndex = new SimpleIntegerProperty();

		init();
	}




	/**
	 * 特定の日にハイライトをかける
	 * @param date ハイライトをかける日
	 */
	public void highlightDate(Calendar date){
		for(CalenderPrimitiveRow primitiveRow : primitiveRowList){
			primitiveRow.highlightDate(date);
		}
	}



	/**
	 * 指定の日付を先頭に表示する
	 * @param date
	 */
	public void setViewStartAt(Calendar date){
		for(CalenderPrimitiveRow primitiveRow : primitiveRowList){
			primitiveRow.setViewStartAt(date);
		}
	}


	/**
	 * カレンダーを一日前にする
	 */
	public void backViewDate(){
		for(CalenderPrimitiveRow primitiveRow : primitiveRowList){
			primitiveRow.backViewDate();
		}
	}


	/**
	 * カレンダーを一日後ろにする
	 */
	public void forwardViewDate(){
		for(CalenderPrimitiveRow primitiveRow : primitiveRowList){
			primitiveRow.forwardViewDate();
		}
	}


	/**
	 * 幅を変更する。カレンダーも更新される
	 * @param width
	 */
	public void setViewWidth(double width){
		this.viewWidth.set(width);
		for(CalenderPrimitiveRow primitiveRow : primitiveRowList){
			primitiveRow.setViewWidth(width);
		}
	}


	/**
	 * タスクを追加する
	 * @param task
	 */
	public void addTask(TaskBean task){
		for(CalenderPrimitiveRow primitiveRow : primitiveRowList){
			if(primitiveRow.canAdd(task)){
				primitiveRow.setTask(task);
				return;
			}
		}
		CalenderPrimitiveRow newPrimitiveRow = this.getInitializedPrimitiveRow();
		newPrimitiveRow.setTask(task);
		primitiveRowList.add(newPrimitiveRow);

		resetViewHeight();
	}



	private void resetViewHeight(){
		this.viewHeight.set(primitiveRowList.size()*Constant.PROJECT_CALENDAR_ROW_HEIGHT);
	}



	/**
	 * 指定する日をマウスホバー状態にする
	 * @param date
	 */
	private void setHovered(Calendar date){
		//TODO 実装
	}

















}
