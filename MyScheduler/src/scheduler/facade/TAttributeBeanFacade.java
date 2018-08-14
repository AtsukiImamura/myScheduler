package scheduler.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scheduler.bean.TAttributeBean;

public class TAttributeBeanFacade  extends CSVAbstractFacade<TAttributeBean>{

	@Override
	public List< TAttributeBean> findAll(){
		return this.findAll( TAttributeBean.class);
	}



	@Override
	public void createNewDatabase(){
		this.createNewDatabase(TAttributeBean.class);
	}
	/*
	@Override
	public List<TAttributeBean> findAll() {
		//this.findAll(TAttributeBean.class);
		//return null;
		List<TAttributeBean> attributeBeanList = this.findAll(NameConstant.TABLE_NAME_T_ATTRIBUTES, NameConstant.TEST_USER_CODE, NameConstant.TEST_PASSWORD,TAttributeBean.class);
		return attributeBeanList;
	}
	*/

	/**
	 * 案件番号に基づいて紐づく属性のリストを取得する
	 * @param projectCode
	 * @return
	 */
	public List<TAttributeBean> findByProjectCode(String projectCode){
		Map<String,String> keys = new HashMap<String,String>();
		keys.put("PROJECT_CODE", projectCode);
		return this.find(TAttributeBean.class, keys);
	}



	public TAttributeBean one(String projectCode,String attributeCode){
		Map<String,String> primaryKeys = new HashMap<String,String>();
		primaryKeys.put("PROJECT_CODE", projectCode);
		primaryKeys.put("ATTRIBUTE_CODE", attributeCode);
		return this.one(TAttributeBean.class, primaryKeys);
	}

}
