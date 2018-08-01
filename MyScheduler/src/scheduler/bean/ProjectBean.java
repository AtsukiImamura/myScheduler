package scheduler.bean;

import java.util.Date;
import java.util.List;


/**
 * 案件のビーン
 * @author ohmoon
 *
 */
public class ProjectBean{


	/**
	 * 主キー
	 * タスクコード
	 * */
	private String projectCode;

	/** 名称 */
	private String projectName;

	/**詳細*/
	private String detail;

	/** ステータス */
	private int status;

	/**タスク作成日時*/
	private Date createdAt;

	/** タスク作成者 */
	private String createdBy;

	/**タスク変更日時*/
	private Date changedAt;

	/**タスク変更者*/
	private Date changedBy;

	/** このタスクに紐づいたプロジェクトのリスト */
	private List<TaskBean> taskBeanList;

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(Date changedAt) {
		this.changedAt = changedAt;
	}

	public Date getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(Date changedBy) {
		this.changedBy = changedBy;
	}

	public List<TaskBean> getTaskBeanList() {
		return taskBeanList;
	}

	public void setTaskBeanList(List<TaskBean> taskBeanList) {
		this.taskBeanList = taskBeanList;
	}




}
