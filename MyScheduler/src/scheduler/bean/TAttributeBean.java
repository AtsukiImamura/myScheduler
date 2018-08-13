package scheduler.bean;

import java.util.ArrayList;
import java.util.List;

import scheduler.common.constant.NameConstant;

/**
 * 属性のトランザクション用ビーン
 * @author ohmoon
 *
 */
public class TAttributeBean  extends DatabaseRelated{

	public String getTableName(){
		return NameConstant.TABLE_NAME_T_ATTRIBUTES;
	}


	public List<String> getPrimaryKeyList(){
		 List<String> primaryKeys = new ArrayList<String>();
		 primaryKeys.add("PROJECT_CODE");
		 primaryKeys.add("ATTRIBUTE_CODE");
		 return primaryKeys;
	}

	/*
	 * 	案件番号	|	PROJECT_CODE	|	String
		属性番号	|	ATTRIBUTE_CODE	|	String
		値			|	VALUE			|	String
		選択肢番号	|	SELECTION_CODE	|	String
		表示順		|	DISP_ORDER		|	int
		作成日		|	CREATED_AT		|	Date
		作成者		|	CREATED_BY		|	String
		変更日		|	CHANGED_AT		|	Date
		変更者		|	CHANGED_BY		|	String
	 */

	/** 案件番号 */
	private String projectCode;

	/** 属性番号 */
	private String attributeCode;

	/** 値 */
	private String value;

	/** 選択肢番号 */
	private String selectionCode;

	/** 表示順 */
	private int dispOrder;

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getAttributeCode() {
		return attributeCode;
	}

	public void setAttributeCode(String attributeCode) {
		this.attributeCode = attributeCode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSelectionCode() {
		return selectionCode;
	}

	public void setSelectionCode(String selectionCode) {
		this.selectionCode = selectionCode;
	}

	public int getDispOrder() {
		return dispOrder;
	}

	public void setDispOrder(int dispOrder) {
		this.dispOrder = dispOrder;
	}

}
