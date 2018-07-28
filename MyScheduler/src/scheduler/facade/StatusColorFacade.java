package scheduler.facade;

import java.util.List;

import scheduler.bean.StatusColorBean;

public class StatusColorFacade extends AbstractFacade<StatusColorBean>{


	protected final static String  FILENAME = "aaa.csv";

	@Override
	public List<StatusColorBean> findAll() {
		return this.findAll(StatusColorBean.class);
	}

}
