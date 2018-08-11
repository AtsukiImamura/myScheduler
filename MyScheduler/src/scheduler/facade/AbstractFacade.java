package scheduler.facade;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import scheduler.bean.DatabaseRelated;
import scheduler.common.utils.DatabaseUtil;


/**
 * ファサードの親クラス
 * @author ohmoon
 *
 * @param <T>
 */
public abstract class AbstractFacade<T extends DatabaseRelated> {


	protected CSVReader csv;
	protected final static String FILENAME = "";

	public AbstractFacade(){
		csv = new CSVReader(FILENAME);
	}

	/**
	 * 各継承クラスで実装する用
	 * @return
	 */
	public abstract List<T> findAll();

	/**
	 * 継承先のクラスでこのメソッドを呼び出す
	 * TODO おそらくcsvは使わないのでＤＢから引き当てるように変更する必要がある。
	 * @param c
	 * @return
	 */
	protected  List<T> findAll(String requestType,String userCode,String password,Class<T> c){

		JsonArray data = null;
		try {
			data = DatabaseUtil.findData(requestType, userCode, password);
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		if(data == null){
			return null;
		}

		List<T> result = new ArrayList<T>();

		//返されたデータのレコードごとに処理
		for(JsonElement record : data){
			JsonObject recordObject = record.getAsJsonObject();
			Set<String> recordKeySet = recordObject.getAsJsonObject().keySet();

			T bean = null;
			try {
				/*インスタンス生成*/
				bean = c.newInstance();
			} catch (Exception e1) {
				e1.printStackTrace();
				return null;
			}

			//列ごとに処理
			for(String key : recordKeySet){
				//データ
				JsonElement columnElement = recordObject.get(key);
				if(columnElement.isJsonNull()){
					continue;
				}
				String  columnValue = columnElement.getAsString();

				//setメソッドをつくる
				String[] keyArgs = key.split("_");
				String nameOfSetMethod = "set";
				for(String arg : keyArgs){
					nameOfSetMethod += arg.substring(0, 1).toUpperCase()+arg.substring(1).toLowerCase();
				}

				try {
					//属性を追加
					Method m = c.getMethod(nameOfSetMethod,String.class);
					m.invoke(bean, columnValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			result.add(bean);
		}
		return result;
	}


	protected void insert(String requestType,String userCode,String password,T bean) {
		Field[] fields = bean.getClass().getDeclaredFields();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		for(Field field : fields){
			String keyArg = field.getName();
			String key = "";
			for(char ch : keyArg.toCharArray()){
				if((int)ch >= 65 && (int)ch <=90){
					//大文字だった場合はアンダーバーを前につける
					key += "_"+String.valueOf(ch);
				}else{
					//小文字だった場合は大文字に変換
					int chv = (int)ch;
					char nch = (char)(chv-32);
					key += String.valueOf(nch);
				}
			}

			Object value = "";
			try {
				PropertyDescriptor prop = new PropertyDescriptor(keyArg, bean.getClass());
				Method propGetter = prop.getReadMethod();
				value = propGetter.invoke(bean, (Object[]) null);
				if(value instanceof Date){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					value = sdf.format(((Date)value));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			dataMap.put(key, value);
		}

		DatabaseUtil.insert(requestType, userCode, password,dataMap);
	}




}
