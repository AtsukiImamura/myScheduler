package scheduler.facade;

import java.util.List;

import scheduler.bean.MAttributeBean;
import scheduler.common.constant.NameConstant;

public class MAttributeBeanFacade extends AbstractFacade<MAttributeBean> {


	@Override
	public List<MAttributeBean> findAll() {
		List<MAttributeBean> attributeBeanList = this.findAll(NameConstant.TABLE_NAME_M_ATTRIBUTES, NameConstant.TEST_USER_CODE, NameConstant.TEST_PASSWORD,MAttributeBean.class);
		return attributeBeanList;
	}


}
