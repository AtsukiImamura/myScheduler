package scheduler.facade;

import java.util.List;

import scheduler.bean.StoneBean;

public class StoneFacade extends AbstractFacade<StoneBean>{


	protected final String FILENAME  = "aaa.csv";

	@Override
	public List<StoneBean> findAll() {
		return this.findAll(StoneBean.class);
	}

}
