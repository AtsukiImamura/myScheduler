package scheduler.bean;

import java.util.Date;

public class StatusColorBean {

	/**
	 * 主キー
	 * ステータスカラーコード*/
	private String statusColorCode;

	/**色*/
	private String color;

	/**タイトル*/
	private String tytle;

	/**説明*/
	private String detail;

	/**作成日時*/
	private Date createdAt;

	/**変更日時*/
	private Date changedAt;

	/**変更者*/
	private Date changedBy;


	public String getStatusColorCode() {
		return statusColorCode;
	}

	public void setStatusColorCode(String statusColorCode) {
		this.statusColorCode = statusColorCode;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTytle() {
		return tytle;
	}

	public void setTytle(String tytle) {
		this.tytle = tytle;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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
