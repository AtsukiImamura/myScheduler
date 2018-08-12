package scheduler.bean;

import java.util.Date;

import scheduler.common.constant.NameConstant;

public class MAttributeBean implements DatabaseRelated{

	public String getTableName(){
		return NameConstant.TABLE_NAME_M_ATTRIBUTES;
	}

	/**
	 * 主キー
	 * 属性コード
	 */
	private String attrCode;

	/**属性名*/
	private String attrName;

	/**説明*/
	private String detail;

	/**カスタマイズタイプ*/
	private int costamaizeType;

	/**タスク作成日時*/
	private Date createdAt;

	/**タスク変更日時*/
	private Date changedAt;

	/**タスク変更者*/
	private Date changedBy;


	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getCostamaizeType() {
		return costamaizeType;
	}

	public void setCostamaizeType(int costamaizeType) {
		this.costamaizeType = costamaizeType;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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
