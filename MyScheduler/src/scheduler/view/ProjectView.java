package scheduler.view;

import java.util.List;

import scheduler.bean.ProjectBean;
import scheduler.bean.TAttributeBean;
import scheduler.common.constant.Constant;
import scheduler.view.calendar.CalenderRow;

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

	/** 案件のカレンダー */
	private final CalenderRow calenderRow;

	/** 案件の属性ビュー */
	private final ProjectAttributesView projectAttributesView;

	/** アプリの幅のうちカレンダーが占める割合 */
	private double rateOfCalendarWidth;



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
	public ProjectView(ProjectBean project,List<TAttributeBean> attributes){
		this.project = project;
		this.attributeList = attributes;
		this.rateOfCalendarWidth = Constant.DEFAULT_RATE_OF_CALENDAR_WIDTH;

		//カレンダー部
		calenderRow = new CalenderRow(project);
		calenderRow.setViewWidth(Constant.APP_PREF_WIDTH*rateOfCalendarWidth);
		calenderRow.setTranslateX(Constant.APP_PREF_WIDTH*(1-rateOfCalendarWidth));

		//表示に要する高さを登録
		this.viewHeight.set(calenderRow.viewHeight.doubleValue());
		this.viewWidth.set(Constant.APP_PREF_WIDTH);

		//属性部
		projectAttributesView = new ProjectAttributesView(attributes);
		projectAttributesView.setViewWidth(Constant.APP_PREF_WIDTH*(1-rateOfCalendarWidth));


		this.getChildren().addAll(
				projectAttributesView,
				calenderRow
				);
	}



	/**
	 * 表示幅を調整する
	 * @param width
	 */
	public void setViewWidth(double width){
		this.viewWidth.set(width);

		calenderRow.setViewWidth(width*rateOfCalendarWidth);
		calenderRow.setTranslateX(width*(1-rateOfCalendarWidth));

		projectAttributesView.setViewWidth(width*(1-rateOfCalendarWidth));
	}




















}
