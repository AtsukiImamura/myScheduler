package scheduler.facade;

import java.util.List;

import scheduler.bean.StoneBean;
import scheduler.common.constant.NameConstant;

public class StoneFacade extends AbstractFacade<StoneBean>{


	protected final String FILENAME  = "aaa.csv";

	@Override
	public List<StoneBean> findAll() {
		//return this.findAll(StoneBean.class);
		List<StoneBean> attributeBeanList = this.findAll(NameConstant.TABLE_NAME_M_STONE, NameConstant.TEST_USER_CODE, NameConstant.TEST_PASSWORD,StoneBean.class);
		return attributeBeanList;
	}

}
