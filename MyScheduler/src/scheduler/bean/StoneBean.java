package scheduler.bean;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import scheduler.common.constant.NameConstant;
import scheduler.common.utils.Util;

public class StoneBean  extends DatabaseRelated{

	public String getTableName(){
		return NameConstant.TABLE_NAME_M_STONE;
	}

	public List<String> getPrimaryKeyList(){
		 List<String> primaryKeys = new ArrayList<String>();
		 primaryKeys.add("PROJECT_CODE");
		 return primaryKeys;
	}

	/**
	 * 主キー
	 * ストーンカラーコード*/
	private String stoneCode;

	/**色*/
	private Color color;

	/**タイトル*/
	private String tytle;

	/**説明*/
	private String detail;





	public String getStoneCode() {
		return stoneCode;
	}

	public void setStoneCode(String stoneCode) {
		this.stoneCode = stoneCode;
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

	public String getTytle() {
		return tytle;
	}

	public void setTytle(String tytle) {
		this.tytle = tytle;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
