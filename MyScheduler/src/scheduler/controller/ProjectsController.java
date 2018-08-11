package scheduler.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Group;
import scheduler.bean.ProjectBean;
import scheduler.bean.TAttributeBean;
import scheduler.common.constant.Constant;
import scheduler.common.utils.Util;
import scheduler.facade.ProjectBeanFacade;
import scheduler.facade.TAttributeBeanFacade;
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

	/** 案件ファサード */
	private final ProjectBeanFacade projectBeanFacade;

	/** 属性ファサード */
	private final TAttributeBeanFacade tAttributeBeanFacade;

	/** 案件のビュー */
	private final ProjectsView projectsView;

	/** カレンダーの日付のビュー */
	private final CalendarDateView calendarDateView;

	/** カレンダーをコントロールするビュー */
	private final CalendarDateCTRLView calendarDateCTRLView;



	/** 属性のリスト（マップ） <br>
	 * <ul>
	 * <li>キー : 案件番号
	 * <li>値 : 案件番号にひもづく属性のリスト
	 * <ul>
	 */
	private final Map<String,List<TAttributeBean>> attributeLists;


	public List<ProjectBean> getProjectList() {
		return projectList;
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
		projectBeanFacade = new ProjectBeanFacade();
		tAttributeBeanFacade = new TAttributeBeanFacade();
		projectList = projectBeanFacade.findAll();
		attributeLists = new HashMap<String,List<TAttributeBean>>();
		calendarDateView = new CalendarDateView(Constant.APP_PREF_WIDTH*Constant.DEFAULT_RATE_OF_CALENDAR_WIDTH);

		calendarDateCTRLView = new CalendarDateCTRLView();


		//属性リスト初期化
		initAttributeLists(projectList);

		//案件のビューを作成
		projectsView = new ProjectsView(projectList,this.attributeLists);

		calendarDateCTRLView.dayOffsetProperty().addListener((ov,oldValue,newValue)->{
			System.out.println("source="+Util.getSlashFormatCalendarValue(calendarDateView.getViewStartAt(), true));
			Calendar newViewStartAt = calendarDateCTRLView.getCurrentDate();
			calendarDateView.setViewStartAt(newViewStartAt);
			projectsView.setViewStartAt(newViewStartAt);
			System.out.println("  -> new="+Util.getSlashFormatCalendarValue(calendarDateView.getViewStartAt(), true));
		});
	}



	public void setWidth(double width){
		this.projectsView.setViewWidth(width);
	}



	/**
	 * 属性のリスト（マップ）を初期化する<br>
	 * マップは
	 * <ul>
	 * <li>キー : 案件番号
	 * <li>値 : 案件番号にひもづく属性のリスト
	 * <ul>
	 * で作成される
	 *
	 * @param projectList
	 */
	private void initAttributeLists( List<ProjectBean> projectList){
		for(ProjectBean project : projectList){
			String projectCode = project.getProjectCode();
			//案件に紐づく属性のリストを引き当て
			List<TAttributeBean> attributeList = tAttributeBeanFacade.findByProjectCode(projectCode);
			attributeLists.put(projectCode, attributeList);
		}
	}




}
