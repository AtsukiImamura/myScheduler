package scheduler.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scheduler.bean.AttributeSelectionBean;

public class AttributeSelectionBeanFacade extends CSVAbstractFacade<AttributeSelectionBean>{

	@Override
	public List<AttributeSelectionBean> findAll(){
		return this.findAll(AttributeSelectionBean.class);
	}


	@Override
	public void createNewDatabase(){
		this.createNewDatabase(AttributeSelectionBean.class);
	}


	public List<AttributeSelectionBean> findByAttributeCode(String attributeCode){
		Map<String,String> keys = new HashMap<String,String>();
		keys.put("ATTRIBUTE_CODE", attributeCode);
		return this.find(AttributeSelectionBean.class,keys);
	}


	public AttributeSelectionBean one(String attributeCode,String selectionCode){
		Map<String,String> primaryKeys = new HashMap<String,String>();
		primaryKeys.put("ATTRIBUTE_CODE", attributeCode);
		primaryKeys.put("SELECTION_CODE", selectionCode);
		return this.one(AttributeSelectionBean.class,primaryKeys);
	}
}
