package scheduler.bean;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import scheduler.common.constant.Constant.COLOR_TYPE;
import scheduler.common.utils.Util;

public abstract class ColorAttribute extends DatabaseRelated {

	@Override
	public abstract String getTableName();

	@Override
	public List<String> getPrimaryKeyList(){
		 List<String> primaryKeys = new ArrayList<String>();
		 primaryKeys.add("CODE");
		 return primaryKeys;
	}

	/**
	 * 主キー
	 * カラーコード*/
	protected String code;

	/**色*/
	protected Color color;

	/**タイトル*/
	protected String title;

	/**説明*/
	protected String detail;




	public String getCode(){
		return code;
	}

	public void setCode(String code){
		this.code = code;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setColor(String color) {
		this.color = Util.createColorByString(color);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String tytle) {
		this.title = tytle;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}


	@Override
	public abstract ColorAttribute clone();

	public abstract COLOR_TYPE getColorType();



}
