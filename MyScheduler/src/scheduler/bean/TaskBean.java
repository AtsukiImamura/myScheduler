package scheduler.bean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.scene.paint.Color;
import scheduler.common.constant.NameConstant;
import scheduler.common.utils.Util;

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
	private String taskName;

	/** 詳細 */
	private String detail;

	/** ストーンカラー */
	private Color stoneColor;

	private boolean delFlag;


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

	public void setStartAt(String startAt) throws ParseException {
		this.startAt = Util.createCalendarByStringValue(startAt);
	}

	public Calendar getFinishAt() {
		return finishAt;
	}

	public void setFinishAt(Calendar finishAt) {
		this.finishAt = finishAt;
	}

	public void setFinishAt(String finishAt) throws ParseException {
		this.finishAt = Util.createCalendarByStringValue(finishAt);
	}


	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}


	public boolean isDelFlag() {
		return delFlag;
	}

	public void setDelFlag(boolean delFlag) {
		this.delFlag = delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = Boolean.parseBoolean(delFlag);
	}

	public Color getStoneColor() {
		return stoneColor;
	}

	public void setStoneColor(Color stoneColor) {
		this.stoneColor = stoneColor;
	}

	public void setStoneColor(String stoneColor) throws Exception{
		this.stoneColor = Util.createColorByString(stoneColor);
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