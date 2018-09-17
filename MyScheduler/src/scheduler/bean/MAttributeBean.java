package scheduler.bean;

import java.util.ArrayList;
import java.util.List;

import scheduler.common.constant.Constant.ATTRIBUTE_TYPE;
import scheduler.common.constant.NameConstant;


/**
 * （マスタ）属性
 * @author ohmoon
 *
 */
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
	private ATTRIBUTE_TYPE costamaizeType;



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

	public ATTRIBUTE_TYPE getCostamaizeType() {
		return costamaizeType;
	}

	public void setCostamaizeType(ATTRIBUTE_TYPE costamaizeType) {
		this.costamaizeType = costamaizeType;
	}

	public void setCostamaizeType(String costamaizeTypeName) {
		this.costamaizeType = ATTRIBUTE_TYPE.findByCode(costamaizeTypeName);
	}

}
