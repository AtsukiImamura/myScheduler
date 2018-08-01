package scheduler.facade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import scheduler.bean.ProjectBean;
import scheduler.bean.TaskBean;


/**
 * 案件ファサード
 * @author ohmoon
 *
 */
public class ProjectBeanFacade extends AbstractFacade<ProjectBean>{

	protected final static String FILENAME = "aaa.csv";

	@Override
	public List<ProjectBean> findAll(){

		/*
		 * TODO テスト中
		return this.findAll(ProjectBean.class);
		*/
		List<ProjectBean> res = new ArrayList<ProjectBean>();


		for(int k=0;k<3;k++){
			ProjectBean projectBean = new ProjectBean();
			projectBean.setProjectCode("P-00"+k);
			projectBean.setProjectName("my project");
			projectBean.setStatus(0);

			List<TaskBean> taskBeanList = new ArrayList<TaskBean>();

			int[] start = {2,6,3,3,2,2};
			int[] finish = {4,10,3,10,4,4};
			for(int i=0;i<4;i++){
				Calendar startAt = Calendar.getInstance();
				startAt.add(Calendar.DAY_OF_MONTH, start[i]);
				Calendar finishAt = Calendar.getInstance();
				finishAt.add(Calendar.DAY_OF_MONTH, finish[i]);
				TaskBean task = new TaskBean();
				task.setStartAt(startAt);
				task.setFinishAt(finishAt);
				task.setProjectCode("P-00"+k);
				task.setTaskCode("t-"+k+"-00"+i);
				task.setProjectDetail("detail.....");
				task.setProjectName("project");
				taskBeanList.add(task);
			}
			projectBean.setTaskBeanList(taskBeanList);

			res.add(projectBean);
		}

		return res;

	}




}
