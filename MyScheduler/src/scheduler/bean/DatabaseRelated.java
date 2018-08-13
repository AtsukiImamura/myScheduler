package scheduler.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public abstract class DatabaseRelated {

	public abstract String getTableName();

	public abstract List<String> getPrimaryKeyList();


	/**作成日時*/
	protected Calendar createdAt;

	/** 作成者 */
	protected String createdBy;

	/**変更日時*/
	protected Calendar changedAt;

	/**変更者*/
	protected String changedBy;


	public void setCreatedAt(String createdAt) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		this.createdAt = Calendar.getInstance();
		this.createdAt.setTime(format.parse(createdAt));
	}




	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	public Calendar getChangedAt() {
		return changedAt;
	}

	public void setChangedAt(Calendar changedAt) {
		this.changedAt = changedAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public void setChangedAt(String changedAt) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		this.createdAt = Calendar.getInstance();
		this.changedAt.setTime(format.parse(changedAt));
	}


	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}
}