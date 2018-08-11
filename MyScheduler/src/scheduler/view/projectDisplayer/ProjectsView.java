package scheduler.view.projectDisplayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import scheduler.bean.ProjectBean;
import scheduler.bean.TAttributeBean;
import scheduler.view.AbstractView;

/**
 * 表示部全体のビュークラス
 * @author ohmoon
 *
 */
public class ProjectsView extends AbstractView{

	/**
	 * 案件のリスト
	 */
	private List<ProjectBean> projectList;


	/**
	 * 案件のビューのリスト
	 */
	private List<ProjectView> projectViewList;




	/**
	 * 案件と属性のリストから案件リストを表示するビューを作成する<br>
	 * カレンダーの表示開始日は今日で初期化される
	 * @param projects
	 * @param attributeLists
	 */
	public ProjectsView(List<ProjectBean> projects,Map<String,List<TAttributeBean>> attributeLists){
		this.projectList = projects;
		projectViewList = new ArrayList<ProjectView>();

		double transY = 0;
		for(ProjectBean project : projectList){
			String projectCode = project.getProjectCode();
			ProjectView projectView = new ProjectView(project,attributeLists.get(projectCode));
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


			projectViewList.add(projectView);

			//ここまでのy軸報告調整の累積
			transY += projectView.viewHeight.doubleValue();
		}

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
