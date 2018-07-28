package scheduler.facade;

import java.util.List;

import scheduler.bean.StoneColorBean;

public class StoneColorFacade extends AbstractFacade<StoneColorBean>{


	protected final String FILENAME  = "aaa.csv";

	@Override
	public List<StoneColorBean> findAll() {
		return this.findAll(StoneColorBean.class);
	}

}
