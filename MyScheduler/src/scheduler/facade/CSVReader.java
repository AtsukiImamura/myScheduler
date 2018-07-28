package scheduler.facade;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {


	private String filePath;
	private BufferedReader br;
	private int rowCount = 0;

	public CSVReader(String filePath){
		this.filePath = filePath;
	}

	/**
	 * 列名を取得
	 * @return
	 */
	public String[] getAttrs(){
		String line = "";
		try{
			br = new BufferedReader(new FileReader(new File(filePath)));
			line = br.readLine();
			br.close();
		}catch(IOException ioe){
			line = "";
		}
		return line.split(",");
	}


	/**
	 * キーに合致するものを返す
	 * @param key
	 * @return
	 */
	public String[][] one(String... key){
		String line = "";
		String[] tmp;
		ArrayList<String[]> list = new ArrayList<String[]>();
		try{
			br = new BufferedReader(new FileReader(new File(filePath)));
			while((line = br.readLine()) != null){
				tmp = line.split(",");
				if(this.validateKeys(tmp, key))list.add(tmp);
			}
			br.close();
		}catch(IOException ioe){
			line = "";
		}
		return (String[][]) list.toArray();
	}

	private boolean validateKeys(String[] row,String[] key){
		if(key.length > row.length)return false;
		for(int i=0;i<key.length;i++){
			if(key[i].equals("") || row[i].equals(key[i])){
				continue;
			}else{
				return false;
			}
		}
		return true;
	}

	/**
	 * マスタから次のレコードを取得
	 * @return
	 */
	public String[] next(){
		this.rowCount ++;
		String line = "";
		try{
			br = new BufferedReader(new FileReader(new File(filePath)));
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
	public String[][] all(){
		String line = "";
		ArrayList<String[]> list = new ArrayList<String[]>();
		try{
			br = new BufferedReader(new FileReader(new File(filePath)));
			while((line = br.readLine()) != null){
				list.add(line.split(","));
			}
			br.close();
		}catch(IOException ioe){
			line = "";
		}
		return (String[][]) list.toArray();
	}
}
