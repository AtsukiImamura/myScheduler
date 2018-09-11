package scheduler.view.projectDisplayer;

import java.util.Calendar;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import scheduler.bean.ProjectBean;
import scheduler.bean.StatusBean;
import scheduler.bean.TAttributeBean;
import scheduler.bean.TaskBean;
import scheduler.common.constant.Constant;
import scheduler.common.utils.Util;
import scheduler.facade.StatusFacade;
import scheduler.facade.TAttributeBeanFacade;
import scheduler.view.AbstractView;
import scheduler.view.calendar.CalenderRow;
import scheduler.view.workDisplayer.ProjectDetailController;

/**
 * 表示部で一つの案件を表示するクラス
 * @author ohmoon
 *
 */
public class ProjectView extends AbstractView{


	/** このビューが持つ案件 */
	private ProjectBean project;

	/** 案件の属性リスト */
	private List<TAttributeBean> attributeList;

	private final TAttributeBeanFacade tAttributeFacade;

	/** 案件のカレンダー */
	private final CalenderRow calenderRow;

	/** 案件の属性ビュー */
	private final ProjectAttributesView projectAttributesView;

	/** アプリの幅のうちカレンダーが占める割合 */
	private double rateOfCalendarWidth;


	public final IntegerProperty selectedIndex;

	public final IntegerProperty hoveredIndex;






	public ProjectBean getProject() {
		return project;
	}


	public void setProject(ProjectBean project) {
		this.project = project;
	}


	public double getRateOfCalendarWidth() {
		return rateOfCalendarWidth;
	}


	/**
	 * 幅のうちカレンダーが占める割合を調整する
	 * @param rateOfCalendarWidth
	 */
	public void setRateOfCalendarWidth(double rateOfCalendarWidth) {
		this.rateOfCalendarWidth = rateOfCalendarWidth;

		//現在の幅で更新すれば全体が更新される
		setViewWidth(this.viewWidth.doubleValue());
	}


	/**
	 * カレンダーの幅を指定して、カレンダーの占める割合を調整する
	 * @param width
	 */
	public void setRateOfCalendarWidthByWidth(double width){
		this.rateOfCalendarWidth = width/this.viewWidth.doubleValue();
		//現在の幅で更新すれば全体が更新される
		setViewWidth(this.viewWidth.doubleValue());
	}



	@Override
	protected void init(){

	}



	/**
	 * コンストラクタ
	 * @param project 案件
	 * @param attributes 案件の属性
	 */
	public ProjectView(ProjectBean project){
		tAttributeFacade = new TAttributeBeanFacade();
		this.project = project;
		this.attributeList = tAttributeFacade.findByProjectCode(project.getProjectCode());
		this.rateOfCalendarWidth = Constant.DEFAULT_RATE_OF_CALENDAR_WIDTH;
		selectedIndex = new SimpleIntegerProperty();
		hoveredIndex = new SimpleIntegerProperty();

		//カレンダー部
		calenderRow = new CalenderRow(project);
		calenderRow.setViewWidth(Constant.APP_PREF_WIDTH*rateOfCalendarWidth);
		calenderRow.setTranslateX(Constant.APP_PREF_WIDTH*(1-rateOfCalendarWidth));

		//表示に要する高さを登録
		double height = Math.max(calenderRow.viewHeight.doubleValue(), Constant.ATTRIBUTE_PRIMITIVE_ROW_DEFAULT_HEIGHT);
		this.viewHeight.set(height);
		this.viewWidth.set(Constant.APP_PREF_WIDTH);


		TAttributeBean projectNameAttr = new TAttributeBean();
		projectNameAttr.setAttributeCode(Constant.ATTRIBUTE_CODE_PROJECT_NAME);
		projectNameAttr.setValue(project.getProjectName());
		attributeList.add(Constant.ATTRIBUTE_INDEX_PROJECT_NAME, projectNameAttr);
		//属性部
		projectAttributesView = new ProjectAttributesView(attributeList,calenderRow.viewHeight.doubleValue());
		projectAttributesView.setSize(Constant.APP_PREF_WIDTH*(1-rateOfCalendarWidth), this.viewHeight.doubleValue());

		StatusFacade statusFacade = new StatusFacade();
		StatusBean status = statusFacade.one(project.getStatus());
		projectAttributesView.setStatus(status);
		projectAttributesView.setOnMouseEntered(event->{
			projectAttributesView.onMouseEntered();
		});
		projectAttributesView.setOnMouseExited(event->{
			projectAttributesView.onMouseExited();
		});
		projectAttributesView.setOnMouseClicked(event->{
			ProjectsView.currentInstance().clearClicked();
			projectAttributesView.onMouseClicked();
			ProjectDetailController projectDetailController = ProjectDetailController.getInstance();
			projectDetailController.setProject(this.project);
			projectDetailController.show();
		});


		//カレンダーの高さが変更されたときは属性部の高さも変更する
		calenderRow.viewHeight.addListener((ov,oldValue,newValue)->{
			projectAttributesView.setViewHeight(newValue.doubleValue());
			this.viewHeight.set(newValue.doubleValue());
			ProjectsView.currentInstance().refreshHeight();
		});

		calenderRow.selectedIndex.addListener((ov,oldValue,newValue)->{
			this.selectedIndex.set(newValue.intValue());
		});

		calenderRow.hoveredIndex.addListener((ov,oldValue,newValue)->{
			this.hoveredIndex.set(newValue.intValue());
		});

		this.getChildren().addAll(
				calenderRow,
				projectAttributesView
				);
	}



	public void setHovered(int index){
		this.calenderRow.setHovered(index, true);
	}

	public void setSelected(int index){
		this.calenderRow.setSelected(index, true);
	}


	public void setViewStartAt(Calendar date){
		this.calenderRow.setViewStartAt(date);
	}


	public void clearClicked(){
		projectAttributesView.setClicked(false);
	}


	/**
	 * カレンダーを一日前にする
	 */
	public void backViewDate(){
		this.calenderRow.backViewDate();
	}



	/**
	 * カレンダーを一日後ろにする
	 */
	public void forwardViewDate(){
		this.calenderRow.forwardViewDate();
	}





	/**
	 * 表示幅を調整する
	 * @param width
	 */
	public void setViewWidth(double width){
		this.viewWidth.set(width);

		double attributeWidth = Util.getAttributePartWidth(width);

		calenderRow.setViewWidth(width - attributeWidth);
		calenderRow.setTranslateX(attributeWidth);
		projectAttributesView.setViewWidth(attributeWidth);
	}


	/**
	 * タスクを一つ追加する。これによってカレンダーの高さが変化した場合は全体の高さも更新される
	 * @param task
	 */
	public void addTask(TaskBean task){
		this.calenderRow.addTask(task);
		//全体高さ更新はlistenerで
	}


	/**
	 * タスクを一つ消去する
	 * @param taskCode 消去するタスクのコード
	 * @return このビューに対象タスクが存在して消去したか
	 */
	public boolean removeTask(String taskCode){
		return this.calenderRow.removeTask(taskCode);
	}




















}
