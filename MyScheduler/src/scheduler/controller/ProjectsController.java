package scheduler.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scheduler.bean.ProjectBean;
import scheduler.bean.TAttributeBean;
import scheduler.facade.AttributeBeanFacade;
import scheduler.facade.ProjectBeanFacade;
import scheduler.view.ProjectView;

/**
 * 表示部のコントローラ
 * @author ohmoon
 *
 */
public class ProjectsController extends Controller{

	/** 案件のリスト */
	private final List<ProjectBean> projectList;

	/** 案件のビューのリスト */
	private final List<ProjectView> projectViewList;

	/** 案件ファサード */
	private final ProjectBeanFacade projectBeanFacade;

	/** 属性ファサード */
	private final AttributeBeanFacade attributeBeanFacade;

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


	public List<ProjectView> getProjectViewList() {
		return projectViewList;
	}




	@Override
	protected void init() {
		// TODO 自動生成されたメソッド・スタブ

	}


	ProjectsController(){
		projectViewList = new ArrayList<ProjectView>();
		projectBeanFacade = new ProjectBeanFacade();
		attributeBeanFacade = new AttributeBeanFacade();
		projectList = projectBeanFacade.findAll();
		attributeLists = new HashMap<String,List<TAttributeBean>>();

		//属性リスト初期化
		initAttributeLists(projectList);

		//案件のビュー初期化
		initProjectViewList(projectList);
	}


	/**
	 * 案件のビューを初期化する。<br>
	 * 各案件ごとに対応するビューを作成し、必要なカレンダー上の行数を計算して高さを確保する。
	 * @param projectList
	 */
	private void initProjectViewList( List<ProjectBean> projectList){
		double transY = 0;
		for(ProjectBean project : projectList){
			String projectCode = project.getProjectCode();
			ProjectView projectView = new ProjectView(project,this.attributeLists.get(projectCode));
			//y軸方向調整
			projectView.setTranslateY(transY);

			//ここまでのy軸報告調整の累積
			transY += projectView.getViewHeight();

			projectViewList.add(projectView);
		}
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
			List<TAttributeBean> attributeList = attributeBeanFacade.findByProjectCode(projectCode);
			attributeLists.put(projectCode, attributeList);
		}
	}




}
