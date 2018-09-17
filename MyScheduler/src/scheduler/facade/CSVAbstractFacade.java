package scheduler.facade;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
		String filePath = "";
		try{
			filePath = this.createFilePath(c);
		} catch (Exception e3) {
			e3.printStackTrace();
			return null;
		}
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
		System.out.println("findAll "+c.getName());
		return this.toBean(c, reader.all()).stream().filter(bean->!bean.isDeleted()).collect(Collectors.toList());
	}


	/**
	 * キーに一致するレコードを全て返す
	 * @param c
	 * @param keys
	 * @return
	 */
	protected List<T> find(Class<T> c,Map<String,String> keys){
		CSVReader reader = createCSVReaderByClass(c);
		return this.toBean(c, reader.findAll(keys)).stream().filter(bean->!bean.isDeleted()).collect(Collectors.toList());
	}


	/**
	 * 主キーに基づいて一つレコードを取得する
	 * @param c
	 * @param primaryKeys
	 * @return
	 */
	protected T one(Class<T> c,Map<String,String> primaryKeys){
		CSVReader reader = createCSVReaderByClass(c);
		T bean = this.toBean(c, reader.one(primaryKeys));
		if(bean == null || bean.isDeleted()){
			return null;
		}
		return bean;
	}



	public void insert(T bean) {
		bean.setChangedAt(Calendar.getInstance());
		Map<String,String> record = bean.toDataMap();
		List<String> primaryKeys = bean.getPrimaryKeyList();

		String filePath =  this.createFilePath(bean);
		CSVReader reader = new CSVReader(filePath);

		reader.insert(primaryKeys, record);
	}


	public void save(T bean){
		bean.setChangedAt(Calendar.getInstance());
		Map<String,String> record = bean.toDataMap();
		List<String> primaryKeys = bean.getPrimaryKeyList();

		String filePath = this.createFilePath(bean);
		CSVReader reader = new CSVReader(filePath);

		reader.save(primaryKeys, record);
	}


	public void update(T bean){
		bean.setChangedAt(Calendar.getInstance());
		Map<String,String> record = bean.toDataMap();
		List<String> primaryKeys = bean.getPrimaryKeyList();

		String filePath = this.createFilePath(bean);
		CSVReader reader = new CSVReader(filePath);

		reader.update(primaryKeys, record);
	}


	public void logicalDelete(T bean){
		bean.setDeleted(true);
		this.save(bean);
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
			System.out.println("     "+bean.toString());
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


	protected void createNewDatabase(Class<T> c){

		String filePath;
		try {
			T bean = c.newInstance();
			filePath = this.createFilePath(c);
			CSVReader reader = new CSVReader(filePath);
			reader.createNewDatabase(bean.toDataMap());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	private String createFilePath(Class<T> c) throws InstantiationException, IllegalAccessException{
		T bean = c.newInstance();
		return createFilePath(bean);
	}

	private String createFilePath(T bean) {
		File file = new File("");
		String filePath = file.getAbsoluteFile().getParentFile().getAbsolutePath()+"/"+bean.getTableName()+".csv";

		return filePath;
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
