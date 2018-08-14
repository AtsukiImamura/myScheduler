package scheduler.bean;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import scheduler.common.utils.Util;

/**
 * csvでデータを扱う際にデータベース操作に必要な機能を集めた抽象クラス。
 * <br>また、作成日・作成者・変更日・変更者はデフォルトで整備されている
 * @author ohmoon
 *
 */
public abstract class DatabaseRelated extends Object{

	/**
	 * テーブルの名前を呼び出す。テーブル名は任意につけてよい。
	 * @return
	 */
	public abstract String getTableName();


	/**
	 * 継承先のbeanのもつ属性のうち、主キーとして働くもののカラム名のリストを返すように実装する。<br>
	 * カラム名は、facadeでcsvを作成してからカラム名をコピペするのがよい
	 * @return
	 */
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
		this.createdAt = Util.createCalendarByStringValue(createdAt);
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
		this.changedAt = Util.createCalendarByStringValue(changedAt);
	}


	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}
}