package scheduler.facade;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * ファサードの親クラス
 * @author ohmoon
 *
 * @param <T>
 */
public abstract class AbstractFacade<T> {


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
	protected  List<T> findAll(Class<T> c){
		String[][] records = csv.all();
		String[] attrs = csv.getAttrs();
		List<T> result = new ArrayList<T>();
		for(String[] record : records){
			T bean = null;
			try {
				/*インスタンス生成*/
				bean = c.newInstance();

			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
			for(int i=0;i<attrs.length;i++){
				try {
					/**属性を追加*/
					Method m = c.getMethod("get"+attrs[i] ,String.class);
					try {
						m.invoke(bean, record[i]);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
			result.add(bean);
		}
		return result;
	}
}
