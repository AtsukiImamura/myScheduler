package scheduler;

import scheduler.facade.MAttributeBeanFacade;

public class DataBaseCreationBatch {



	public static void main(String[] args) throws Exception{

//		ProjectBeanFacade projectBeanFacade = new ProjectBeanFacade();
//		projectBeanFacade.createNewDatabase();

//		TAttributeBeanFacade tAttributeBeanFacade = new TAttributeBeanFacade();
//		tAttributeBeanFacade.createNewDatabase();
//
//		TaskFacade taskFacade = new TaskFacade();
//		taskFacade.createNewDatabase();
////
//		StoneFacade stoneFacade = new StoneFacade();
//		stoneFacade.createNewDatabase();
////
//		StatusFacade statusFacade = new StatusFacade();
//		statusFacade.createNewDatabase();
//
		MAttributeBeanFacade mAttributeBeanFacade = new MAttributeBeanFacade();
		mAttributeBeanFacade.createNewDatabase();
//
//		AttributeSelectionBeanFacade attributeSelectionBeanFacade = new AttributeSelectionBeanFacade();
//		attributeSelectionBeanFacade.createNewDatabase();
	}

}
