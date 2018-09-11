package scheduler.facade;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * CSVリーダー。csvは以下の書式である必要がある。
 *
 * |---------------------------------------------------|
 * |columnName0 , columnName1, columnName2, ....       |
 * |data00 , data01 , data02 , data03 , data04.....    |
 * |data10 , data11 , data12 , data13 , data14.....    |
 * |---------------------------------------------------|
 *
 *
 * @author ohmoon
 *
 */
public class CSVReader {


	private String filePath;
	private final File csvFile;
	private int rowCount = 0;

	private String[] rowAttrs;

	public CSVReader(String filePath){
		this.filePath = filePath;
		csvFile = new File(filePath);
		rowAttrs = this.getAttrs();
		if(!csvFile.exists()){

		}
	}

	/**
	 * 列名を取得
	 * @return
	 */
	public String[] getAttrs(){
		String line = "";
		BufferedReader br;
		try{
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();
			br.close();
		}catch(IOException ioe){
			return null;
		}
		if(line == null){
			return null;
		}
		return line.split(",");
	}


	/**
	 * 主キーをもとにレコードを一つ返す
	 * @param key
	 * @return
	 */
	public String[] one(Map<String,String> primaryKeys){
		 List<String[]> results = this.findAll(primaryKeys);
		 if(results.isEmpty()){
			 return null;
		 }else{
			 return results.get(0);
		 }
	}



	/**
	 * キーに合致するものを返す
	 * @param key
	 * @return
	 */
	public List<String[]> findAll(Map<String,String> keys){
		String line = "";
		String[] tmp;
		BufferedReader br;
		ArrayList<String[]> list = new ArrayList<String[]>();
		try{
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();
			while((line = br.readLine()) != null){
				tmp = line.split(",");
				if(this.validateKeys(tmp, keys)){
					list.add(tmp);
				}
			}
			br.close();
		}catch(IOException ioe){
			line = "";
		}
		return list;
	}


	/**
	 * 指定したキーに合致するものを削除する
	 * @param keys
	 */
	public void delete(Map<String,String> keys){
		String line = "";
		String[] tmp;
		BufferedReader br;
		List<String> records = new ArrayList<String>();
		try{
			br = new BufferedReader(new FileReader(csvFile));
			//1行目は削除しない
			records.add(br.readLine());
			while((line = br.readLine()) != null){
				tmp = line.split(",");
				//主キーに合致しないものだけ再度書き出すリストに加える
				if(!this.validateKeys(tmp, keys)){
					records.add(line);
				}
			}
			br.close();
		}catch(IOException ioe){
			line = "";
		}
		this.writeAll(records);
	}


	/**
	 * レコードを一つ挿入する。
	 * @param primaryKeys 主キーのリスト
	 * @param record レコード
	 * @param force 主キーに重複があった場合にアップデートするか (true=する false=しない)
	 */
	private void insert(List<String> primaryKeys,Map<String,String> record,boolean force){
		//レコードの中身をcsvの格納順に並べ替え
		String[] data = this.prepareData(record);
		if(data == null){
			return;
		}
		//主キー検索用
		Map<String,String> keysMap = this.prepareKeyMap(primaryKeys, data);

		String line = "";
		String[] tmp;
		BufferedReader br = null;
		PrintWriter pw;
		try{
			br = new BufferedReader(new FileReader(csvFile));
			//1行目は飛ばす
			br.readLine();
			while((line = br.readLine()) != null){
				tmp = line.split(",");
				//主キーが重複するなら
				if(this.validateKeys(tmp, keysMap)){
					if(force){
						this.update(primaryKeys, record);
					}
					br.close();
					return;
				}
			}
			br.close();

			pw = new PrintWriter(new BufferedWriter(new FileWriter(this.filePath,true)));
			pw.println(String.join(",", data));
			pw.close();
		}catch(IOException ioe){
			line = "";
			ioe.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
			}
		}
	}


	/**
	 * レコードをひとつ挿入する。主キーで重複があった場合は挿入しない
	 * @param primaryKeys 主キーのリスト
	 * @param record レコード
	 */
	public void insert(List<String> primaryKeys,Map<String,String> record){
		this.insert(primaryKeys, record, false);
	}



	/**
	 * データを保存する。主キーが重複していればアップデートする
	 * @param primaryKeys 主キーのリスト
	 * @param record レコード
	 */
	public void save(List<String> primaryKeys,Map<String,String> record){
		this.insert(primaryKeys, record, true);
	}






	/**
	 * レコードをアップデートする。ファイル内に主キーを同じくするレコードが存在しない場合はアップデートされない
	 * @param primaryKeys 主キー（のカラム名）のリスト
	 * @param record updateするレコード
	 */
	public void update(List<String> primaryKeys,Map<String,String> record){

		//レコードの中身をcsvの格納順に並べ替え
		String[] data = this.prepareData(record);
		if(data == null){
			return;
		}
		//主キー検索用のマップを作成
		Map<String,String> keysMap = this.prepareKeyMap(primaryKeys, data);

		List<String> records = new ArrayList<String>();

		String line = "";
		String[] tmp;
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(csvFile));
			//1行目は飛ばす
			line = br.readLine();
			//nullであれば新たに作成
			if(line == null){
				line = buildHeaderRow(record,",");
			}
			records.add(line);
			while((line = br.readLine()) != null){
				tmp = line.split(",");
				//主キーが重複するなら
				if(!this.validateKeys(tmp, keysMap)){
					records.add(line);
				}else{
					records.add(String.join(",", data));
				}
			}
		}catch(IOException ioe){
			line = "";
		}finally{
			try {
				br.close();
			} catch (IOException e) {
			}
		}

		//書き出し
		this.writeAll(records);
	}


	/**
	 * マスタから次のレコードを取得
	 * @return
	 */
	public String[] next(){
		this.rowCount ++;
		String line = "";
		BufferedReader br;
		try{
			br = new BufferedReader(new FileReader(csvFile));
			int k=0;
			while(k < this.rowCount){
				br.readLine();
				k++;
			}
			line = br.readLine();
			br.close();
		}catch(IOException ioe){
			line = "";
		}
		return line.split(",");
	}



	/**
	 * マスタをすべて取得
	 * @return
	 */
	public List<String[]> all(){
		String line = "";
		ArrayList<String[]> list = new ArrayList<String[]>();
		BufferedReader br;

		int delFlagIndex = this.findArrayIndex("DELETED", this.rowAttrs);
		try{
			br = new BufferedReader(new FileReader(csvFile));
			//先頭行をとばす
			br.readLine();
			while((line = br.readLine()) != null){
				String[] data = line.split(",");
				if(delFlagIndex >= 0 && data[delFlagIndex].equals("true")){
					continue;
				}
				list.add(data);
			}
			br.close();
		}catch(IOException ioe){
			line = "";
		}
		return list;
	}


	public void createNewDatabase(Map<String,String> record){
		String header = this.buildHeaderRow(record, ",");
		PrintWriter pw;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(this.csvFile)));
			pw.println(header);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	/**
	 * レコードの主キーが与えられた主キーと合致するかを返す
	 * @param record レコード
	 * @param keys 主キー・値のマップ
	 * @return
	 */
	private boolean validateKeys(String[] record,Map<String,String> keys){
		if(keys.size() > record.length){
			return false;
		}

		for(Map.Entry<String, String> entry : keys.entrySet()){
			String keyColumnName = entry.getKey();
			String primaryKeyValue = this.findPrimaryKeyValue(keyColumnName, record);
			if(primaryKeyValue == null || !primaryKeyValue.equals(entry.getValue())){
				return false;
			}
		}
		return true;
	}


	/**
	 * 配列の中から、指定されたキーの要素を取り出す
	 * @param keyColumnName	 キーとなるカラムの名称
	 * @param record 要素を探索する対象のレコード
	 * @return
	 */
	private String findPrimaryKeyValue(String keyColumnName,String[] record){
		String[] columns = this.rowAttrs;

		int keyIndex = this.findArrayIndex(keyColumnName, columns);
		if(keyIndex == -1){
			return null;
		}else{
			return record[keyIndex];
		}
	}



	/**
	 * ある値が配列の中にあるかどうかを返す
	 * @param needle
	 * @param haystack
	 * @return needleのhaystackの中のインデックス。存在しない場合は-1
	 */
	private int findArrayIndex(String needle,String[] haystack){
		int count = 0;
		if(needle == null){
			return -1;
		}
		for(String value : haystack){
			if(value == null){
				count ++;
				continue;
			}
			if(value.equals(needle)){
				return count;
			}
			count ++;
		}
		return -1;
	}



	private Map<String,String> prepareKeyMap(List<String> primaryKeys,String[] data){
		Map<String,String> keysMap = new HashMap<String,String>();
		for(String key : primaryKeys){
			keysMap.put(key, this.findPrimaryKeyValue(key, data));
		}

		return keysMap;
	}



	/**
	 * レコードの中身をcsvのカラム順に並び替えて挿入に備える
	 * @param record
	 * @return
	 */
	private String[] prepareData(Map<String,String> record){
		//カラム名
		String[] columns = this.rowAttrs;
		//nullであれば新たに作成する
		if(columns == null){
			columns = this.getColumnsByRecordKeys(record);
		}

		//レコードの中身をcsvの格納順に並べ替え
		String[] data = new String[Math.min(record.size(), columns.length)];
		for(Map.Entry<String, String> entry : record.entrySet()){
			int index = this.findArrayIndex(entry.getKey(), columns);
			if(index == -1){
				continue;
			}
			data[index] = entry.getValue();
		}

		return data;
	}




	/**
	 * レコードからヘッダー情報の配列を作成する
	 * @param record レコード
	 * @return カラム名を要素とする配列
	 */
	private String[] getColumnsByRecordKeys(Map<String,String> record){
		return getColumnsByRecordKeys(record.keySet());
	}


	/**
	 * レコードのキー情報からヘッダー情報の配列を作成する
	 * @param keys
	 * @return
	 */
	private String[] getColumnsByRecordKeys(Set<String> keys){
		String[] columns = new String[keys.size()];
		int count = 0;
		for(String key : keys){
			columns[count] = key;
			count++;
		}
		return columns;
	}


	/**
	 * レコードのキー情報からヘッダー行文字列を作成する
	 * @param record
	 * @param sep
	 * @return
	 */
	private String buildHeaderRow(Set<String> record,String sep){
		String[] columns = getColumnsByRecordKeys(record);
		return String.join(sep,columns);
	}


	/**
	 * レコードから挿入用のヘッダー行文字列を作成する
	 * @param record
	 * @param sep
	 * @return
	 */
	private String buildHeaderRow(Map<String,String> record,String sep){
		return buildHeaderRow(record.keySet(),sep);
	}


	/**
	 * 与えられた行のリストを全て書き出す
	 * @param records
	 */
	public void writeAll(List<String> records){
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(this.filePath,false)));
			for(String record : records){
				pw.println(record);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			pw.close();
		}
	}
}
