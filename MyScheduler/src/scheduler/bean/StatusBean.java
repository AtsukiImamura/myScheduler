package scheduler.bean;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import scheduler.common.constant.NameConstant;

public class StatusBean  extends DatabaseRelated{

	public String getTableName(){
		return NameConstant.TABLE_NAME_M_STATUS;
	}

	public List<String> getPrimaryKeyList(){
		 List<String> primaryKeys = new ArrayList<String>();
		 primaryKeys.add("STATUS_CODE");
		 return primaryKeys;
	}


	/**
	 * 主キー
	 * ステータスカラーコード*/
	private String statusCode;

	/**色*/
	private Color color;

	/**タイトル*/
	private String tytle;

	/**説明*/
	private String detail;


	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
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
