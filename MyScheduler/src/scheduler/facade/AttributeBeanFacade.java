package scheduler.facade;

import java.util.ArrayList;
import java.util.List;

import scheduler.bean.AttributeBean;
import scheduler.bean.TAttributeBean;

public class AttributeBeanFacade extends AbstractFacade<AttributeBean> {


	protected final static String FILENAME = "aaa.csv";

	@Override
	public List<AttributeBean> findAll() {
		this.findAll(AttributeBean.class);
		return null;
	}


	/**
	 * 案件番号に基づいて紐づく属性のリストを取得する
	 * @param projectCode
	 * @return
	 */
	public List<TAttributeBean> findByProjectCode(String projectCode){
		//TODO 実装
		return new ArrayList<TAttributeBean>();
	}

}
