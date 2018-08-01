package scheduler.facade;

import java.util.ArrayList;
import java.util.List;

import scheduler.bean.TAttributeBean;

public class TAttributeBeanFacade  extends AbstractFacade<TAttributeBean> {



	@Override
	public List<TAttributeBean> findAll() {
		this.findAll(TAttributeBean.class);
		return null;
	}

	/**
	 * 案件番号に基づいて紐づく属性のリストを取得する
	 * @param projectCode
	 * @return
	 */
	public List<TAttributeBean> findByProjectCode(String projectCode){

		/*
		//TODO 実装
		return new ArrayList<TAttributeBean>();
		*/

		List<TAttributeBean> attributes = new ArrayList<TAttributeBean>();
		for(int i=0;i<3;i++){
			TAttributeBean attr = new TAttributeBean();
			attr.setAttributeCode("ATTR-"+i);
			attr.setDispOrder(i);
			attr.setProjectCode(projectCode);
			attr.setValue("valuevalue");
			attr.setSelectionCode("");
			attributes.add(attr);
		}

		return attributes;
	}

}
