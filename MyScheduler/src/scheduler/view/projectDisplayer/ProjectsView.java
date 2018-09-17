package scheduler.view.projectDisplayer;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import scheduler.bean.ProjectBean;
import scheduler.bean.TaskBean;
import scheduler.common.constant.Constant;
import scheduler.view.AbstractView;

/**
 * 表示部全体のビュークラス
 * @author ohmoon
 *
 */
public class ProjectsView extends AbstractView{


	private static ProjectsView instance = null;

	public static ProjectsView currentInstance(){
		return instance;
	}


	/**
	 * 案件のリスト
	 */
	private List<ProjectBean> projectList;


	/**
	 * 案件のビューのリスト
	 * key: 案件コード
	 * value: ProjectView
	 */
	private final Map<String,ProjectView> projectViewList;



	/**
	 * プロジェクトのy方向の位置を調整する。各プロジェクトの高さが変化したときに呼ばれる
	 */
	public void refreshHeight(){
		double transY = 0;

		for (Entry<String, ProjectView> entry : projectViewList.entrySet()) {
			ProjectView projectView = entry.getValue();
			projectView.setTranslateY(transY);
			transY += projectView.viewHeight.get()+Constant.PROJECTS_SPACE;
	    }
	}



	/**
	 * 案件を追加する
	 * @param project
	 */
	public void addProject(ProjectBean project){

		double transY = 0;

		String projectCode = project.getProjectCode();
		if(projectViewList.containsKey(projectCode)){
			projectViewList.get(projectCode).setProject(project);
			return;
		}
		for(ProjectView projectView : projectViewList.values()){
			transY += projectView.viewHeight.doubleValue() +Constant.PROJECTS_SPACE;
		}

		ProjectView projectView = this.createInitializedProjectView(project,transY);
		projectViewList.put(projectCode,projectView);
		this.getChildren().add(0, projectView);
		return;
	}


	public void addTask(TaskBean task){
		String projectCode = task.getProjectCode();

		if(!projectViewList.containsKey(projectCode)){
			return;
		}

		projectViewList.get(projectCode).addTask(task);
	}



	/**
	 * 案件を削除する
	 * @param project
	 */
	public void removeProject(ProjectBean project){
		String projectCode = project.getProjectCode();
		this.removeProject(projectCode);
	}


	public void removeProject(String projectCode){

		this.getChildren().remove(projectViewList.get(projectCode));
		projectViewList.remove(projectCode);

		double transY = 0;

		List<ProjectView> list = projectViewList.values().stream().collect(Collectors.toList());
		int from =list.size()-1;
		for(int index = from; index >= 0;index--){
			ProjectView view = list.get(index);
			view.setTranslateY(transY);
			transY += view.viewHeight.doubleValue() + Constant.PROJECTS_SPACE;
		}

	}



	public void clearClicked(){
		projectViewList.forEach((code,view)->{
			view.clearClicked();
		});
	}


	private ProjectView createInitializedProjectView(ProjectBean project,double transY){
		ProjectView projectView = new ProjectView(project);
		//y軸方向調整
		projectView.setTranslateY(transY);

		projectView.selectedIndex.addListener((ov,oldValue,newValue)->{
			projectViewList.forEach((code,view)->{
				if(view.equals(projectView)){
					return;
				}
				view.setSelected(newValue.intValue());
			});
		});

		projectView.hoveredIndex.addListener((ov,oldValue,newValue)->{
			projectViewList.forEach((code,view)->{
				if(view.equals(projectView)){
					return;
				}
				view.setHovered(newValue.intValue());
			});
		});


		return projectView;
	}


	/**
	 * 案件と属性のリストから案件リストを表示するビューを作成する<br>
	 * カレンダーの表示開始日は今日で初期化される
	 * @param projects
	 * @param attributeLists
	 */
	public ProjectsView(List<ProjectBean> projects){
		this.projectList = projects;
		projectViewList = new HashMap<String,ProjectView>();

		initPrjectViewList();

		//this.getChildren().add(listView);
		instance = this;

	}


	private void initPrjectViewList(){
		projectViewList.clear();
		this.getChildren().clear();

		double transY = 0;
		for(ProjectBean project : projectList){
			ProjectView projectView = this.createInitializedProjectView(project, transY);
			projectViewList.put(project.getProjectCode(),projectView);

			//ここまでのy軸報告調整の累積
			transY += projectView.viewHeight.doubleValue() + Constant.PROJECTS_SPACE;
		}

//ListViewは中身に対して負荷がかかりすぎんようによしなにしてくれるから便利
//		ObservableList<ProjectView> projectObservableList = FXCollections.observableArrayList(projectViewList);
//		ListView<ProjectView> listView = new ListView<ProjectView>(projectObservableList);
//		listView.setPrefWidth(Constant.APP_PREF_WIDTH);

		this.viewHeight.set(transY);

		List<ProjectView> list = projectViewList.values().stream().collect(Collectors.toList());

		for(int index = list.size()-1;index>=0 ; index--){
			this.getChildren().add(list.get(index));
		}
	}



	public void setViewWidth(double width){
		projectViewList.forEach((projectCode,view)->{
			view.setViewWidth(width);
		});
	}

	@Override
	protected void init() {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void setViewStartAt(Calendar date){
		projectViewList.forEach((projectCode,view)->{
			view.setViewStartAt(date);
		});
	}


	/**
	 * カレンダーを一日前にする
	 */
	public void backViewDate(){
		projectViewList.forEach((projectCode,view)->{
			view.backViewDate();
		});
	}



	/**
	 * カレンダーを一日後ろにする
	 */
	public void forwardViewDate(){
		projectViewList.forEach((projectCode,view)->{
			view.forwardViewDate();
		});
	}


	/**
	 * タスクを除去する
	 * @param projectCode
	 * @param taskCode
	 * @return タスクの除去に成功したかどうか
	 */
	public boolean removeTask(String projectCode,String taskCode){

		if(!projectViewList.containsKey(projectCode)){
			return false;
		}

		return true && projectViewList.get(projectCode).removeTask(taskCode);
	}


	public boolean removeTask(TaskBean task){
		return this.removeTask(task.getProjectCode(), task.getTaskCode());
	}

}
