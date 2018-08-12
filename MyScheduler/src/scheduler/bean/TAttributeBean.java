package scheduler.bean;

import java.util.Date;

import scheduler.common.constant.NameConstant;

/**
 * 属性のトランザクション用ビーン
 * @author ohmoon
 *
 */
public class TAttributeBean  implements DatabaseRelated{

	public String getTableName(){
		return NameConstant.TABLE_NAME_T_ATTRIBUTES;
	}


	/*
	 * 	案件番号	|	PROJECT_CODE	|	String
		属性番号	|	ATTRIBUTE_CODE	|	String
		値			|	VALUE			|	String
		選択肢番号	|	SELECTION_CODE	|	String
		表示順		|	DISP_ORDER		|	int
		作成日		|	CREATED_AT		|	Date
		作成者		|	CREATED_BY		|	String
		変更日		|	CHANGED_AT		|	Date
		変更者		|	CHANGED_BY		|	String
	 */

	/** 案件番号 */
	private String projectCode;

	/** 属性番号 */
	private String attributeCode;

	/** 値 */
	private String value;

	/** 選択肢番号 */
	private String selectionCode;

	/** 表示順 */
	private int dispOrder;

	/**タスク作成日時*/
	private Date createdAt;

	/** タスク作成者 */
	private String createdBy;

	/**タスク変更日時*/
	private Date changedAt;

	/**タスク変更者*/
	private Date changedBy;

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getAttributeCode() {
		return attributeCode;
	}

	public void setAttributeCode(String attributeCode) {
		this.attributeCode = attributeCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSelectionCode() {
		return selectionCode;
	}

	public void setSelectionCode(String selectionCode) {
		this.selectionCode = selectionCode;
	}

	public int getDispOrder() {
		return dispOrder;
	}

	public void setDispOrder(int dispOrder) {
		this.dispOrder = dispOrder;
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





}
