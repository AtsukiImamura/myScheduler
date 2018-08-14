package scheduler.bean;

import java.util.ArrayList;
import java.util.List;

import scheduler.common.constant.NameConstant;

public class AttributeSelectionBean extends DatabaseRelated{

	public String getTableName(){
		return NameConstant.TABLE_NAME_M_ATTRIBUTE_SELECTION;
	}

	public List<String> getPrimaryKeyList(){
		 List<String> primaryKeys = new ArrayList<String>();
		 primaryKeys.add("ATTRIBUTE_CODE");
		 primaryKeys.add("SELECTION_CODE");
		 return primaryKeys;
	}


	private String attributeCode;

	private String selectionCode;

	private String name;

	private String dispName;

	private String detail;

	private boolean dispFlag;

	private int dispOrder;

	public String getAttributeCode() {
		return attributeCode;
	}

	public void setAttributeCode(String attributeCode) {
		this.attributeCode = attributeCode;
	}

	public String getSelectionCode() {
		return selectionCode;
	}

	public void setSelectionCode(String selectionCode) {
		this.selectionCode = selectionCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDispName() {
		return dispName;
	}

	public void setDispName(String dispName) {
		this.dispName = dispName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public boolean isDispFlag() {
		return dispFlag;
	}

	public void setDispFlag(boolean dispFlag) {
		this.dispFlag = dispFlag;
	}

	public void setDispFlag(String dispFlag) throws Exception{
		this.dispFlag = Boolean.parseBoolean(dispFlag);
	}

	public int getDispOrder() {
		return dispOrder;
	}

	public void setDispOrder(int dispOrder) {
		this.dispOrder = dispOrder;
	}

	public void setDispOrder(String dispOrder) {
		this.dispOrder = Integer.parseInt(dispOrder);
	}

	@Override
	public String toString(){
		return this.dispName;
	}



}
