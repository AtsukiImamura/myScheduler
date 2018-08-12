package scheduler.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import scheduler.common.constant.NameConstant;


/**
 * 案件のビーン
 * @author ohmoon
 *
 */
public class ProjectBean implements DatabaseRelated{

	public String getTableName(){
		return NameConstant.TABLE_NAME_T_PROJECT;
	}


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

	private boolean delFlag;

	/**タスク作成日時*/
	private Date createdAt;

	/** タスク作成者 */
	private String createdBy;

	/**タスク変更日時*/
	private Date changedAt;

	/**タスク変更者*/
	private String changedBy;

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

	public void setStatus(String status) throws Exception{
		this.status = Integer.parseInt(status);
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



	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public List<TaskBean> getTaskBeanList() {
		return taskBeanList;
	}

	public void setTaskBeanList(List<TaskBean> taskBeanList) {
		this.taskBeanList = taskBeanList;
	}



	public void setCreatedAt(String createdAt) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		this.createdAt = format.parse(createdAt);
	}

	public void setChangedAt(String changedAt) throws ParseException {
		this.changedAt = DateFormat.getDateInstance().parse(changedAt);
	}


}
