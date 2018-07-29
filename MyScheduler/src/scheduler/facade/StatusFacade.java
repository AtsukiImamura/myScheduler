package scheduler.facade;

import java.util.List;

import scheduler.bean.StatusBean;

public class StatusFacade extends AbstractFacade<StatusBean>{


	protected final static String  FILENAME = "aaa.csv";

	@Override
	public List<StatusBean> findAll() {
		return this.findAll(StatusBean.class);
	}

}
