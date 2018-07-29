package scheduler.facade;

import java.util.ArrayList;
import java.util.List;

import scheduler.bean.MAttributeBean;
import scheduler.bean.TAttributeBean;

public class MAttributeBeanFacade extends AbstractFacade<MAttributeBean> {


	protected final static String FILENAME = "aaa.csv";

	@Override
	public List<MAttributeBean> findAll() {
		this.findAll(MAttributeBean.class);
		return null;
	}


}
