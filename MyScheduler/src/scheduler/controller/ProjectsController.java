package scheduler.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Group;
import scheduler.bean.ProjectBean;
import scheduler.bean.TAttributeBean;
import scheduler.facade.ProjectBeanFacade;
import scheduler.facade.TAttributeBeanFacade;
import scheduler.view.ProjectsView;

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



	public Group getView(){
		Group view = new Group();


		//TODO 他のコンポーネントを入れる

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

		//属性リスト初期化
		initAttributeLists(projectList);

		projectsView = new ProjectsView(projectList,this.attributeLists);
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
