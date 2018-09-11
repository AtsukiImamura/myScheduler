package scheduler;

import scheduler.facade.ProjectBeanFacade;

public class FuncTest2 {



	public static void main(String[] args) throws Exception{
		ProjectBeanFacade projectBeanFacade = new ProjectBeanFacade();
		projectBeanFacade.createNewDatabase();
//		ProjectBean project = projectBeanFacade.findAll().get(0);
//		System.out.println(project.toString());
	}

}
