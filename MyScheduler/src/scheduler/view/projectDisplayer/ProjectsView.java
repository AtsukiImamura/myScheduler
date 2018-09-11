package scheduler.view.projectDisplayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import scheduler.bean.ProjectBean;
import scheduler.bean.TaskBean;
import scheduler.common.constant.Constant;
import scheduler.facade.TAttributeBeanFacade;
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
	 */
	private List<ProjectView> projectViewList;


	/**
	 *
	 */
	private final TAttributeBeanFacade tAttributeBeanFacade;




	/**
	 * プロジェクトのy方向の位置を調整する。各プロジェクトの高さが変化したときに呼ばれる
	 */
	public void refreshHeight(){
		double transY = 0;
		for(ProjectView projectView : projectViewList){
			projectView.setTranslateY(transY);
			transY += projectView.viewHeight.get()+Constant.PROJECTS_SPACE;
		}
	}



	/**
	 * 案件を追加する
	 * @param project
	 */
	public void addProject(ProjectBean project){
		projectList.add(project);
		double transY = this.viewHeight.doubleValue();

		ProjectView projectView = this.createInitializedProjectView(project,transY);
		projectViewList.add(projectView);
		this.getChildren().add(0, projectView);
	}


	public void addTask(TaskBean task){
		String projectCode = task.getProjectCode();

		List<ProjectView> viewList = projectViewList.stream().filter(view->{
			if(view.getProject().getProjectCode().equals(projectCode)){
				return true;
			}
			return false;
		}).collect(Collectors.toList());
		if(viewList.isEmpty()){
			return;
		}
		viewList.forEach(view->{
			view.addTask(task);
		});
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

		//表示している案件の中でコードに該当するものを得る
		List<ProjectBean> validProjectList = projectList.stream().filter(project->project.getProjectCode().equals(projectCode)).collect(Collectors.toList());
		if(validProjectList.isEmpty()){
			return;
		}

		//ProjectViewを消す
		this.getChildren().removeAll(
				this.getChildren()
				.stream()
				.filter((child)->{
					//ProjectViewのインスタンスでないものは除外
					if(!(child instanceof ProjectView)){
						return false;
					}
					//対象のプロジェクトリストに含まれているかを調べる
					for(ProjectBean proejct: validProjectList){
						//コードが合致していれば
						if(((ProjectView)child).getProject().getProjectCode().equals(proejct.getProjectCode())){
							return true;
						}
					}
					return false;
				}).collect(Collectors.toList())
			);

		double transY = 0;

		int from = this.getChildren().size()-1;
		for(int index = from; index >= 0;index--){
			Node child = this.getChildren().get(index);
			if(!(child instanceof ProjectView)){
				continue;
			}
			((ProjectView)child).setTranslateY(transY);
			transY += ((ProjectView)child).viewHeight.doubleValue() + Constant.PROJECTS_SPACE;
		}

	}



	public void clearClicked(){
		projectViewList.forEach(view->{
			view.clearClicked();
		});
	}


	private ProjectView createInitializedProjectView(ProjectBean project,double transY){
		ProjectView projectView = new ProjectView(project);
		//y軸方向調整
		projectView.setTranslateY(transY);

		projectView.selectedIndex.addListener((ov,oldValue,newValue)->{
			for(ProjectView view : projectViewList){
				if(view.equals(projectView)){
					continue;
				}
				view.setSelected(newValue.intValue());
			}
		});

		projectView.hoveredIndex.addListener((ov,oldValue,newValue)->{
			for(ProjectView view : projectViewList){
				if(view.equals(projectView)){
					continue;
				}
				view.setHovered(newValue.intValue());
			}
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
		tAttributeBeanFacade = new TAttributeBeanFacade();
		this.projectList = projects;
		projectViewList = new ArrayList<ProjectView>();

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
			projectViewList.add(projectView);

			//ここまでのy軸報告調整の累積
			transY += projectView.viewHeight.doubleValue() + Constant.PROJECTS_SPACE;
		}

//ListViewは中身に対して負荷がかかりすぎんようによしなにしてくれるから便利
//		ObservableList<ProjectView> projectObservableList = FXCollections.observableArrayList(projectViewList);
//		ListView<ProjectView> listView = new ListView<ProjectView>(projectObservableList);
//		listView.setPrefWidth(Constant.APP_PREF_WIDTH);

		this.viewHeight.set(transY);

		for(int index = projectViewList.size()-1;index>=0 ; index--){
			this.getChildren().add(this.projectViewList.get(index));
		}
	}



	public void setViewWidth(double width){
		for(ProjectView view : projectViewList){
			view.setViewWidth(width);
		}
	}

	@Override
	protected void init() {
		// TODO 自動生成されたメソッド・スタブ

	}


	public void setViewStartAt(Calendar date){
		for(ProjectView view : projectViewList){
			view.setViewStartAt(date);
		}
	}


	/**
	 * カレンダーを一日前にする
	 */
	public void backViewDate(){
		for(ProjectView view : projectViewList){
			view.backViewDate();
		}
	}



	/**
	 * カレンダーを一日後ろにする
	 */
	public void forwardViewDate(){
		for(ProjectView view : projectViewList){
			view.forwardViewDate();
		}
	}


	/**
	 * タスクを除去する
	 * @param projectCode
	 * @param taskCode
	 * @return タスクの除去に成功したかどうか
	 */
	public boolean removeTask(String projectCode,String taskCode){
		for(ProjectView projectView : this.projectViewList){
			if(projectView.getProject().getProjectCode() != projectCode){
				continue;
			}
			return true && projectView.removeTask(taskCode);
		}
		return false;
	}

}
