package scheduler.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scheduler.bean.MAttributeBean;

public class MAttributeBeanFacade extends CSVAbstractFacade<MAttributeBean>{

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


	public MAttributeBean findByAttributeCode(String attributeCode){
		Map<String,String> keys = new HashMap<String,String>();
		keys.put("ATTR_CODE", attributeCode);
		return this.one(MAttributeBean.class, keys);
	}

}
