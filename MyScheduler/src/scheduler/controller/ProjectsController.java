package scheduler.controller;

import java.util.Calendar;
import java.util.List;

import javafx.scene.Group;
import scheduler.bean.ProjectBean;
import scheduler.bean.TaskBean;
import scheduler.common.constant.Constant;
import scheduler.common.utils.Util;
import scheduler.facade.ProjectBeanFacade;
import scheduler.facade.TaskFacade;
import scheduler.view.calendar.CalendarDateCTRLView;
import scheduler.view.calendar.CalendarDateView;
import scheduler.view.projectDisplayer.ProjectsView;

/**
 * 表示部のコントローラ
 * @author ohmoon
 *
 */
public class ProjectsController extends Controller{

	/** 案件のリスト */
	private final List<ProjectBean> projectList;

	/** 案件のビュー */
	private final ProjectsView projectsView;

	/** カレンダーの日付のビュー */
	private final CalendarDateView calendarDateView;

	/** カレンダーをコントロールするビュー */
	private final CalendarDateCTRLView calendarDateCTRLView;

	private static ProjectsController instance;

	public static ProjectsController currentInstance(){
		return instance;
	}

	public List<ProjectBean> getProjectList() {
		return projectList;
	}


	public void addProject(ProjectBean project){
		projectsView.addProject(project);
	}


	public void addProject(String projectCode){
		ProjectBean project = ProjectBeanFacade.getInstance().one(projectCode);
		if(project == null){
			return;
		}
		this.addProject(project);
	}


	public void removeProject(ProjectBean project){
		projectsView.removeProject(project);
		ProjectBeanFacade.getInstance().logicalDelete(project);
		TaskFacade.getInstance().deleteByProjectCode(project.getProjectCode());
	}


	public void removeProject(String projectCode){
		ProjectBean project = ProjectBeanFacade.getInstance().one(projectCode);
		if(project == null){
			return;
		}
		this.removeProject(project);
	}


	public void addTask(TaskBean task){
		this.projectsView.addTask(task);
	}

	public boolean removeTask(TaskBean task){
		return this.projectsView.removeTask(task);
	}



	/**
	 * 表示部で使うべきビューを返す
	 * @return
	 */
	public Group getView(){
		Group view = new Group();

		calendarDateCTRLView.setTranslateX(Constant.APP_PREF_WIDTH*(1-Constant.DEFAULT_RATE_OF_CALENDAR_WIDTH));
		calendarDateCTRLView.setTranslateY(0);

		calendarDateView.setTranslateX(Constant.APP_PREF_WIDTH*(1-Constant.DEFAULT_RATE_OF_CALENDAR_WIDTH));
		calendarDateView.setTranslateY(Constant.CALENDAR_DATE_CTRL_HEIGHT);
		view.getChildren().addAll(calendarDateView,calendarDateCTRLView);


		double calendarHeight = this.calendarDateView.viewHeight.doubleValue();
		this.projectsView.setTranslateY(Constant.CALENDAR_DATE_CTRL_HEIGHT+calendarHeight);

		view.getChildren().add(projectsView);

		return view;
	}


	@Override
	protected void init() {
		// TODO 自動生成されたメソッド・スタブ

	}


	public ProjectsController(){
		projectList = ProjectBeanFacade.getInstance().findAll();
		calendarDateView = new CalendarDateView(Constant.APP_PREF_WIDTH*Constant.DEFAULT_RATE_OF_CALENDAR_WIDTH);

		calendarDateCTRLView = new CalendarDateCTRLView();

		//案件のビューを作成
		projectsView = new ProjectsView(projectList);

		calendarDateCTRLView.dayOffsetProperty().addListener((ov,oldValue,newValue)->{
			System.out.println("source="+Util.getSlashFormatCalendarValue(calendarDateView.getViewStartAt(), true));
			Calendar newViewStartAt = calendarDateCTRLView.getCurrentDate();
			calendarDateView.setViewStartAt(newViewStartAt);
			projectsView.setViewStartAt(newViewStartAt);
			System.out.println("  -> new="+Util.getSlashFormatCalendarValue(calendarDateView.getViewStartAt(), true));
		});

		instance = this;
	}



	public void setWidth(double width){
		this.projectsView.setViewWidth(width);

		double attributeWidth = Util.getAttributePartWidth(width);
		calendarDateCTRLView.setTranslateX(attributeWidth);
		calendarDateView.setTranslateX(attributeWidth);
		calendarDateView.setWidth(width - attributeWidth);
	}
}
