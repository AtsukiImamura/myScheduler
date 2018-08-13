package scheduler.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.scene.paint.Color;
import scheduler.common.constant.NameConstant;

/**
 * タスクを管理するビーン
 * @author ohmoon
 *
 */
public class TaskBean  extends DatabaseRelated{

	public String getTableName(){
		return NameConstant.TABLE_NAME_T_TASK;
	}

	public List<String> getPrimaryKeyList(){
		 List<String> primaryKeys = new ArrayList<String>();
		 primaryKeys.add("PROJECT_CODE");
		 primaryKeys.add("TASK_CODE");
		 return primaryKeys;
	}

	/** タスクコード */
	private String projectCode;

	/** 案件コード */
	private String taskCode;

	/** 開始日 */
	private Calendar startAt;

	/** 終了日 */
	private Calendar finishAt;

	/** 名称 */
	private String projectName;

	/** 詳細 */
	private String projectDetail;

	/** ストーンカラー */
	private Color stoneColor;


	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public Calendar getStartAt() {
		return startAt;
	}

	public void setStartAt(Calendar startAt) {
		this.startAt = startAt;
	}

	public Calendar getFinishAt() {
		return finishAt;
	}

	public void setFinishAt(Calendar finishAt) {
		this.finishAt = finishAt;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(String projectDetail) {
		this.projectDetail = projectDetail;
	}

	public Color getStoneColor() {
		return stoneColor;
	}

	public void setStoneColor(Color stoneColor) {
		this.stoneColor = stoneColor;
	}


	/**
	 * 指定した日付がタスクの開始日から終了日の間にあるかどうかを返す
	 * @param date
	 * @return
	 */
	public boolean isInPeriod(Calendar date){
		if(date.after(startAt) && date.before(finishAt)){
			return true;
		}else{
			return false;
		}
	}



}