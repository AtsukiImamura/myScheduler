package scheduler.view;

import java.util.List;

import scheduler.bean.ProjectBean;
import scheduler.bean.TAttributeBean;
import scheduler.common.constant.Constant;
import scheduler.common.utils.Util;

/**
 * 表示部で一つの案件を表示するクラス
 * @author ohmoon
 *
 */
public class ProjectView extends AbstractView{



	private ProjectBean project;

	private List<TAttributeBean> attributeList;



	@Override
	protected void init(){

	}





	public ProjectView(ProjectBean project,List<TAttributeBean> attributes){
		this.project = project;
		this.attributeList = attributes;

		//案件の表示に必要な行数
		int numOfNecessaryColumns = Util.getNumOfNecessaryColumns(project);
		//表示に要する高さを登録
		this.viewHeight.set(Constant.PROJECT_CALENDAR_ROW_HEIGHT*numOfNecessaryColumns);

	}

}
