package scheduler.facade;

import java.util.List;

import scheduler.bean.StoneBean;

public class StoneFacade extends CSVAbstractFacade<StoneBean>{

	@Override
	public List<StoneBean> findAll(){
		return this.findAll(StoneBean.class);
	}



	@Override
	public void createNewDatabase(){
		this.createNewDatabase(StoneBean.class);
	}

	/*
	@Override
	public List<StoneBean> findAll() {
		//return this.findAll(StoneBean.class);
		List<StoneBean> attributeBeanList = this.findAll(NameConstant.TABLE_NAME_M_STONE, NameConstant.TEST_USER_CODE, NameConstant.TEST_PASSWORD,StoneBean.class);
		return attributeBeanList;
	}
	*/

}
