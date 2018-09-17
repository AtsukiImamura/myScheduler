package scheduler.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scheduler.bean.TaskBean;
import scheduler.common.constant.NameConstant;

public class TaskFacade   extends CSVAbstractFacade<TaskBean>{


	private static TaskFacade instance;

	static{
		instance = new TaskFacade();
	}

	public static TaskFacade getInstance(){
		return instance;
	}


	@Override
	public List<TaskBean> findAll(){
		return this.findAll(TaskBean.class);
	}



	@Override
	public void createNewDatabase(){
		this.createNewDatabase(TaskBean.class);
	}


	/**
	 * 案件番号に基づいて紐づく属性のリストを取得する
	 * @param projectCode
	 * @return
	 */
	public List<TaskBean> findByProjectCode(String projectCode){
		Map<String,String> keys = new HashMap<String,String>();
		keys.put("PROJECT_CODE", projectCode);
		return this.find(TaskBean.class, keys);
	}

	/**
	 * 次の案件番号を採番する
	 * @return
	 */
	public String createNewTaskCode(String projectCode){
		Map<String,String> keys = new HashMap<String,String>();
		keys.put("PROJECT_CODE", projectCode);
		List<TaskBean> taskList = this.find(TaskBean.class,keys);
		int max = 0;
		for(TaskBean task: taskList){
			String taskCode = task.getTaskCode();
			String number = taskCode.split("_")[1];
			int num = Integer.parseInt(number);
			if(num > max){
				max = num;
			}
		}
		int newNum = max + 1;
		String newNumber = String.format("%04d", newNum);
		return NameConstant.TASK_CODE_PREFIX+newNumber;
	}


	/**
	 * プロジェクトに紐づくタスクを全て（論理）削除する
	 * @param projectCode
	 */
	public void deleteByProjectCode(String projectCode){
		List<TaskBean> taskList = this.findByProjectCode(projectCode);
		for(TaskBean bean: taskList){
			this.logicalDelete(bean);
		}
	}

}
