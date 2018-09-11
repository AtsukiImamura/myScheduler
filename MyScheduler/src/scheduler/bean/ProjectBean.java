package scheduler.bean;

import java.util.ArrayList;
import java.util.List;

import scheduler.common.constant.NameConstant;


/**
 * 案件のビーン
 * @author ohmoon
 *
 */
public class ProjectBean extends DatabaseRelated{

	public String getTableName(){
		return NameConstant.TABLE_NAME_T_PROJECT;
	}

	public List<String> getPrimaryKeyList(){
		 List<String> primaryKeys = new ArrayList<String>();
		 primaryKeys.add("PROJECT_CODE");
		 return primaryKeys;
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
	private String status;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public List<TaskBean> getTaskBeanList() {
		return taskBeanList;
	}

	public void setTaskBeanList(List<TaskBean> taskBeanList) {
		this.taskBeanList = taskBeanList;
	}



}
