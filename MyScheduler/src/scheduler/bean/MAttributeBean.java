package scheduler.bean;

import java.util.ArrayList;
import java.util.List;

import scheduler.common.constant.NameConstant;

public class MAttributeBean extends DatabaseRelated{

	public String getTableName(){
		return NameConstant.TABLE_NAME_M_ATTRIBUTES;
	}


	public List<String> getPrimaryKeyList(){
		 List<String> primaryKeys = new ArrayList<String>();
		 primaryKeys.add("ATTR_CODE");
		 return primaryKeys;
	}

	/**
	 * 主キー
	 * 属性コード
	 */
	private String attrCode;

	/**属性名*/
	private String attrName;

	/**説明*/
	private String detail;

	/**カスタマイズタイプ*/
	private int costamaizeType;



	public String getAttrCode() {
		return attrCode;
	}

	public void setAttrCode(String attrCode) {
		this.attrCode = attrCode;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getCostamaizeType() {
		return costamaizeType;
	}

	public void setCostamaizeType(int costamaizeType) {
		this.costamaizeType = costamaizeType;
	}

}
