package scheduler.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scheduler.bean.MAttributeBean;
import scheduler.common.constant.NameConstant;

public class MAttributeBeanFacade extends CSVAbstractFacade<MAttributeBean>{

	private static MAttributeBeanFacade instance;

	static{
		instance = new MAttributeBeanFacade();
	}

	public static MAttributeBeanFacade getInstance(){
		return instance;
	}


	@Override
	public List<MAttributeBean> findAll(){
		return this.findAll(MAttributeBean.class);
	}


	@Override
	public void createNewDatabase(){
		this.createNewDatabase(MAttributeBean.class);
	}
	/*
	@Override
	public List<MAttributeBean> findAll() {
		List<MAttributeBean> attributeBeanList = this.findAll(NameConstant.TABLE_NAME_M_ATTRIBUTES, NameConstant.TEST_USER_CODE, NameConstant.TEST_PASSWORD,MAttributeBean.class);
		return attributeBeanList;
	}

*/


	public String createNewCode(){

		int max = 0;
		for(MAttributeBean attr: this.findAll()){
			String attrCode = attr.getAttrCode();
			String number = attrCode.split("_")[1];
			int num = Integer.parseInt(number);
			if(num > max){
				max = num;
			}
		}
		int newNum = max + 1;
		String newNumber = String.format("%03d", newNum);
		return NameConstant.CODE_PREFIX.ATTRIBUTE.PREFIX+newNumber;
	}


	public MAttributeBean one(String attrCode){
		Map<String,String> primaryKeys = new HashMap<String,String>();
		primaryKeys.put("ATTR_CODE", attrCode);
		return this.one(MAttributeBean.class,primaryKeys);
	}


	public MAttributeBean findByAttributeCode(String attributeCode){
		Map<String,String> keys = new HashMap<String,String>();
		keys.put("ATTR_CODE", attributeCode);
		return this.one(MAttributeBean.class, keys);
	}

}
