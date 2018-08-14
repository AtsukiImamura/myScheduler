package scheduler.facade;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scheduler.bean.DatabaseRelated;


/**
 * csvでデータを管理するにあたって、クラスによらない一般的な機能を集めた抽象クラス。<br>
 * BeanクラスはDatabaseRelatedクラスを継承すればcsvによるデータの管理ができる。<br>
 *
 * DatabaseRelatedクラスを継承するbeanの属性名をどのようにつけても問題はないが、統一性のあるカラム名を保証するためにjavaの命名規則に従った
 * 名称を付けることが望ましい。csvの中ではカラム名は、例えばhogePiyoという属性は HOGE_PIYO とされる。
 * csvのひな型は自動で生成できるので、csvはcreateNewDatabase()によって自動で生成すべきである。<br>
 * Calndarクラスは yyyy-MM-dd hh:mm:ss 形式、その他のクラスはtoString() の値で保存される。
 * よって、Stringでない型のフィールドはかならずStringを引数とするsetterを必要とする。保存形式に合わせたsetterを実装すること。
 * @author ohmoon
 *
 * @param <T>
 */
public abstract class CSVAbstractFacade<T extends DatabaseRelated> {


	/**
	 * 各継承クラスでfindAll(Class<T> c)を呼び出すことで実装する
	 * @return
	 */
	abstract public List<T> findAll();


	/**
	 * 各継承クラスでcreateNewDatabase(Class<T> c)を呼び出すことで実装する
	 */
	abstract public void createNewDatabase();




	private CSVReader createCSVReaderByClass(Class<T> c){
		String tableName = "";
		try {
			tableName = c.newInstance().getTableName();
		} catch (Exception e3) {
			e3.printStackTrace();
			return null;
		}
		File file = new File("");
		String filePath = file.getAbsoluteFile().getParentFile().getAbsolutePath()+"/"+tableName+".csv";
		CSVReader reader = new CSVReader(filePath);

		return reader;
	}

	/**
	 * 継承先のクラスでこのメソッドを呼び出す
	 * @param c
	 * @return
	 */
	protected  List<T> findAll(Class<T> c){
		CSVReader reader = createCSVReaderByClass(c);
		return this.toBean(c, reader.all());
	}


	/**
	 * キーに一致するレコードを全て返す
	 * @param c
	 * @param keys
	 * @return
	 */
	protected List<T> find(Class<T> c,Map<String,String> keys){
		CSVReader reader = createCSVReaderByClass(c);
		return this.toBean(c, reader.findAll(keys));
	}


	/**
	 * 主キーに基づいて一つレコードを取得する
	 * @param c
	 * @param primaryKeys
	 * @return
	 */
	protected T one(Class<T> c,Map<String,String> primaryKeys){
		CSVReader reader = createCSVReaderByClass(c);
		return this.toBean(c, reader.one(primaryKeys));
	}



	public void insert(T bean) {
		bean.setChangedAt(Calendar.getInstance());
		Map<String,String> record = this.getDataMap(bean);
		List<String> primaryKeys = bean.getPrimaryKeyList();

		String filePath = bean.getTableName()+".csv";
		CSVReader reader = new CSVReader(filePath);

		reader.insert(primaryKeys, record);

	}


	public void update(T bean){
		bean.setChangedAt(Calendar.getInstance());
		Map<String,String> record = this.getDataMap(bean);
		List<String> primaryKeys = bean.getPrimaryKeyList();

		String filePath = bean.getTableName()+".csv";
		CSVReader reader = new CSVReader(filePath);

		reader.update(primaryKeys, record);
	}


	private T toBean(Class<T> c,String[] data){
		if(data == null){
			return null;
		}
		List<String[]> dataList = new ArrayList<String[]>();
		dataList.add(data);
		List<T> beanList = this.toBean(c, dataList);
		if(beanList == null || beanList.size() == 0){
			return null;
		}
		return beanList.get(0);
	}



	private List<T> toBean(Class<T> c,List<String[]> data){

		if(data == null ){
			return null;
		}


		List<T> result = new ArrayList<T>();
		if(data.isEmpty()){
			return result;
		}

		CSVReader reader = createCSVReaderByClass(c);
		String[] columnNames = reader.getAttrs();

		//返されたデータのレコードごとに処理
		for(String[] record : data){

			T bean = null;
			try {
				/*インスタンス生成*/
				bean = c.newInstance();
			} catch (Exception e1) {
				e1.printStackTrace();
				return null;
			}

			try{
			//列ごとに処理
			for(int index = 0;index < Math.min(record.length, columnNames.length);index++){
				//データ
				String  columnValue = record[index];
				String key = columnNames[index];

				//setメソッドをつくる
				String[] keyArgs = key.split("_");
				String nameOfSetMethod = "set";
				for(String arg : keyArgs){
					nameOfSetMethod += arg.substring(0, 1).toUpperCase()+arg.substring(1).toLowerCase();
				}

				try {
					//属性を追加 : どの属性にもStringでsetできるメソッドを持つようにしている
					Method m = c.getMethod(nameOfSetMethod,String.class);
					m.invoke(bean, columnValue);
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}

			}catch(NullPointerException e){
				e.printStackTrace();
			}
			result.add(bean);
		}
		return result;
	}



	private Object[] addArrays(Object[] array1,Object[] array2){
		Object[] result = new Object[array1.length + array2.length];
		int count = 0;
		while(count < array1.length){
			result[count] = array1[count];
			count++;
		}
		while(count < result.length){
			result[count] = array2[count-array1.length];
			count++;
		}
		return result;
	}

	/**
	 * insert,updateで送信するためのbeanのデータマップを作成する
	 * @param bean
	 * @return bean情報を詰めたマップ (key: データベースのカラム名 value: 送信する値 )
	 */
	private Map<String,String> getDataMap(T bean){
		Field[] superClassFields = bean.getClass().getSuperclass().getDeclaredFields();
		Field[] beanFields = bean.getClass().getDeclaredFields();

		Field[] fields = new Field[superClassFields.length + beanFields.length];
		int count = 0;
		while(count < superClassFields.length){
			fields[count] = superClassFields[count];
			count++;
		}
		while(count < fields.length){
			fields[count] = beanFields[count-superClassFields.length];
			count++;
		}

		Map<String,String> dataMap = new HashMap<String,String>();
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
				String stValue = "null";
				if(value instanceof Calendar){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					stValue = sdf.format(((Calendar)value).getTime());

				}else{
					if(value != null){
						stValue = value.toString();
					}
				}

				dataMap.put(key, stValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataMap;
	}


	protected void createNewDatabase(Class<T> c){

		String filePath;
		try {
			T bean = c.newInstance();
			filePath = c.newInstance().getTableName()+".csv";
			CSVReader reader = new CSVReader(filePath);
			reader.createNewDatabase(this.getDataMap(bean));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private String[] toArray(List<String> dataList){
		if(dataList == null){
			return null;
		}

		String[] data = new String[dataList.size()];
		for(int index = 0;index<dataList.size();index++){
			data[index] = dataList.get(index);
		}
		return data;
	}

}
