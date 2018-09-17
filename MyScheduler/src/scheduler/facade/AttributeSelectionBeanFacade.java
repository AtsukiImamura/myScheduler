package scheduler.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scheduler.bean.AttributeSelectionBean;
import scheduler.common.constant.NameConstant;

public class AttributeSelectionBeanFacade extends CSVAbstractFacade<AttributeSelectionBean>{


	private static AttributeSelectionBeanFacade instance;

	static{
		instance = new AttributeSelectionBeanFacade();
	}

	public static AttributeSelectionBeanFacade getInstance(){
		return instance;
	}

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



	public AttributeSelectionBean one(String attrCode,String selectionCode){
		Map<String,String> primaryKeys = new HashMap<String,String>();
		primaryKeys.put("ATTRIBUTE_CODE",attrCode);
		primaryKeys.put("SELECTION_CODE",selectionCode);
		return this.one(AttributeSelectionBean.class,primaryKeys);
	}


	/**
	 * 次の案件番号を採番する
	 * @return
	 */
	public String createNewCode(String attrCode){
		List<AttributeSelectionBean> attrSelectionList = this.findByAttributeCode(attrCode);
		int max = 0;
		for(AttributeSelectionBean attrSelection: attrSelectionList){
			String selectionCode = attrSelection.getSelectionCode();
			String number = selectionCode.split("_")[1];
			int num = Integer.parseInt(number);
			if(num > max){
				max = num;
			}
		}
		int newNum = max + 1;
		String newNumber = String.format("%03d", newNum);
		return NameConstant.CODE_PREFIX.ATTR_SELECTION.PREFIX+newNumber;
	}
}
