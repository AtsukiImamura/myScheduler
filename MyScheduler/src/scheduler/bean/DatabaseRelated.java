package scheduler.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	protected boolean deleted;


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


	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = Boolean.parseBoolean(deleted);
	}

	@Override
	public String toString(){
		Map<String,String> dataMap = this.toDataMap(false);
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		dataMap.forEach((key,value)->{
			sb.append("  ");
			sb.append(key);
			sb.append(": ");
			sb.append(value);
		});
		sb.append(" ]");

		return sb.toString();
	}



	/**
	 * insert,updateで送信するためのbeanのデータマップを作成する
	 * @param bean
	 * @return bean情報を詰めたマップ (key: データベースのカラム名 value: 送信する値 )
	 */
	public Map<String,String> toDataMap(){
		return this.toDataMap(true);
	}



	protected Map<String,String> toDataMap(boolean snakeCase){

		if(this.getClass().getSuperclass() != DatabaseRelated.class){

		}

		List<Field[]> fieldsList = new ArrayList<Field[]>();
		Class<? extends DatabaseRelated> clazz = this.getClass();
		int count = 0;
		for(int index = 0;index<3;index ++){
			try{
				Field[] decFields = clazz.getDeclaredFields();
				count += decFields.length;
				fieldsList.add(decFields);
				clazz = (Class<? extends DatabaseRelated>) clazz.getSuperclass();
			}catch(Exception e){
				break;
			}
		}

		Field[] fields = new Field[count];
		int index = 0;
		for(Field[] decFields: fieldsList){
			for(Field decField : decFields){
				fields[index] = decField;
				index++;
			}
		}

		Map<String,String> dataMap = new HashMap<String,String>();
		for(Field field : fields){
			field.setAccessible(true);
			String keyArg = field.getName();
			String key = this.getColumnKey(keyArg);
			dataMap.put(snakeCase ? key:keyArg, this.getFieldValue(keyArg));
		}
		return dataMap;
	}


	/**
	 * キャメルケースのフィールド名をDB用の大文字スネークケースに変換する
	 * @param keyArg
	 * @return
	 */
	private String getColumnKey(String keyArg){
		StringBuilder key = new StringBuilder();
		for(char ch : keyArg.toCharArray()){
			if((int)ch >= 65 && (int)ch <=90){
				//大文字だった場合はアンダーバーを前につける
				key.append("_");
				key.append(String.valueOf(ch));
			}else{
				//小文字だった場合は大文字に変換
				int chv = (int)ch;
				char nch = (char)(chv-32);
				key.append(String.valueOf(nch));
			}
		}

		return key.toString();
	}



	/**
	 * 指定されたフィールド名の要素を文字列として返す
	 * @param keyArg
	 * @return
	 */
	private String getFieldValue(String keyArg){
		Object value = "";
		String stValue = "null";
		try {
			PropertyDescriptor prop = new PropertyDescriptor(keyArg, this.getClass());
			Method propGetter = prop.getReadMethod();
			value = propGetter.invoke(this, (Object[]) null);
			if(value instanceof Calendar){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				stValue = sdf.format(((Calendar)value).getTime());

			}else{
				if(value != null){
					stValue = value.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return stValue;
	}
}