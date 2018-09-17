package scheduler.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scheduler.bean.ProjectBean;
import scheduler.common.constant.NameConstant;


/**
 * 案件ファサード
 * @author ohmoon
 *
 */
public class ProjectBeanFacade extends CSVAbstractFacade<ProjectBean>{

	private static ProjectBeanFacade instance;

	static{
		instance = new ProjectBeanFacade();
	}


	public static ProjectBeanFacade getInstance(){
		return instance;
	}




	@Override
	public List<ProjectBean> findAll(){
		return this.findAll(ProjectBean.class);
	}


	@Override
	public void createNewDatabase(){
		this.createNewDatabase(ProjectBean.class);
	}


	public ProjectBean one(String projectCode){
		Map<String,String> primaryKeys = new HashMap<String,String>();
		primaryKeys.put("PROJECT_CODE", projectCode);
		return this.one(ProjectBean.class,primaryKeys);
	}


	/**
	 * 次の案件番号を採番する
	 * @return
	 */
	public String createNewProjectCode(){
		List<ProjectBean> projects = this.findAll();
		int max = 0;
		for(ProjectBean project: projects){
			String projectCode = project.getProjectCode();
			String number = projectCode.split("_")[1];
			number = number.replaceAll("^0*","");
			int num = Integer.parseInt(number);
			if(num > max){
				max = num;
			}
		}
		int newNum = max + 1;
		String newNumber = "000"+newNum;
		return NameConstant.PROJECT_CODE_PREFIX+newNumber.substring(newNumber.length()-4);
	}


}
