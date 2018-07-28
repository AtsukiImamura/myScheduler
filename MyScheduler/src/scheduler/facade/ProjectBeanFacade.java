package scheduler.facade;

import java.util.List;

import scheduler.bean.ProjectBean;


/**
 * 案件ファサード
 * @author ohmoon
 *
 */
public class ProjectBeanFacade extends AbstractFacade<ProjectBean>{

	protected final static String FILENAME = "aaa.csv";

	@Override
	public List<ProjectBean> findAll(){
		return this.findAll(ProjectBean.class);
	}



}
