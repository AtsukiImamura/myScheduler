package scheduler.bean;

import java.util.Calendar;

import javafx.scene.paint.Color;
import scheduler.common.constant.NameConstant;

/**
 * タスクを管理するビーン
 * @author ohmoon
 *
 */
public class TaskBean  implements DatabaseRelated{

	public String getTableName(){
		return NameConstant.TABLE_NAME_T_TASK;
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

	/**タスク作成日時*/
	private Calendar createdAt;

	/** タスク作成者 */
	private String createdBy;

	/**タスク変更日時*/
	private Calendar changedAt;

	/**タスク変更者*/
	private Calendar changedBy;

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

	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Calendar getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(Calendar changedAt) {
		this.changedAt = changedAt;
	}

	public Calendar getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(Calendar changedBy) {
		this.changedBy = changedBy;
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