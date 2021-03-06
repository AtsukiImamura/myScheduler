package scheduler.view.projectDisplayer;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import scheduler.bean.ProjectBean;
import scheduler.bean.StatusBean;
import scheduler.bean.TAttributeBean;
import scheduler.common.constant.Constant;
import scheduler.facade.TAttributeBeanFacade;
import scheduler.view.AbstractView;

/**
 * 案件の属性部を担うビュークラス
 * @author ohmoon
 *
 */
public class ProjectAttributesView extends AbstractView{


	private final Color HOVERED_STROKE_COLOR = Color.rgb(170, 170, 170);

	private final Color CLICKED_STROKE_COLOR = Color.rgb(20, 20, 20);


	/** 個別の属性表示用ビュー */
	private final List<AttributePrimitiveView> primitiveViewList;

	/** 属性リスト */
	private List<TAttributeBean>  attributeList;

	private final Rectangle rect;


	private StatusBean status;

	private boolean clicked = false;

	public void setClicked(boolean clicked){
		this.clicked = clicked;
		if(clicked){
			this.onMouseClicked();
		}else{
			this.onMouseExited();
		}
	}


	public void setStatus(StatusBean status){
		if(status == null){
			return;
		}
		this.status = status;
		primitiveViewList.forEach(view->{
			view.setStatus(status);
		});
	}


	public void redraw(ProjectBean project){
		this.initAttributeList(project);
		init();
	}

	@Override
	protected void init() {

		if(attributeList == null || primitiveViewList == null || viewWidth == null){
			return;
		}

		this.getChildren().clear();
		this.primitiveViewList.clear();

		/** 案件名以外の属性に対して案件名の幅をどれだけ増分させて表示するか */
		double incrementaRate = Constant.ATTRIBUTES_VIEW_INCREMENTAL_RATE_OF_ATTR_NAME;
		/** incrementaRateを考慮して属性全体がviewHeightに収まりきるような属性表示の幅 */
		double defaultPrimitiveViewWidth = this.viewWidth.doubleValue()/(incrementaRate + attributeList.size()-1);

		//TODO コード順に並んでいることが前提
		int index = 0;
		for(TAttributeBean attribute:attributeList){

			AttributePrimitiveView primitiveView;

			//位置・高さ・幅決め
			if(attribute.getAttributeCode().equals(Constant.ATTRIBUTE_CODE_PROJECT_NAME)){
				primitiveView  = new AttributePrimitiveView(attribute,defaultPrimitiveViewWidth*incrementaRate, this.viewHeight.doubleValue());
				primitiveView.setTranslateX(0);
			}else{
				primitiveView  = new AttributePrimitiveView(attribute,defaultPrimitiveViewWidth, this.viewHeight.doubleValue());
				primitiveView.setTranslateX(defaultPrimitiveViewWidth*(incrementaRate+index-1));
			}
			primitiveView.setStatus(status);
			this.getChildren().add(primitiveView);
			this.primitiveViewList.add(primitiveView);

			index++;
		}

	}



	public void onMouseEntered(){
		if(clicked){
			return;
		}
		rect.setStroke(HOVERED_STROKE_COLOR);
//		for(AttributePrimitiveView primitiveView: primitiveViewList){
//			primitiveView.onMouseEntered();
//		}
	}


	public void onMouseExited(){
		if(clicked){
			return;
		}
		rect.setStroke(Color.TRANSPARENT);
//		for(AttributePrimitiveView primitiveView: primitiveViewList){
//			primitiveView.onMouseExited();
//		}
	}


	public void onMouseClicked(){
		rect.setStroke(CLICKED_STROKE_COLOR);
//		for(AttributePrimitiveView primitiveView: primitiveViewList){
//			primitiveView.onClicked();
//		}
		clicked = true;
	}



	/**
	 * コンストラクタ
	 * @param attributes 属性のリスト
	 * @param height 表示高さ
	 */
	public ProjectAttributesView(ProjectBean project,double height){
		primitiveViewList = new ArrayList<AttributePrimitiveView>();
		this.initAttributeList(project);

		this.viewHeight.set(height);
		this.setViewWidth((1-Constant.DEFAULT_RATE_OF_CALENDAR_WIDTH)*Constant.APP_PREF_WIDTH);

		this.getStyleClass().addAll("project_attributes_view");

		init();

		rect = new Rectangle(this.viewWidth.doubleValue(),this.viewHeight.doubleValue());
		rect.setFill(Color.TRANSPARENT);
		rect.setStroke(Color.TRANSPARENT);
		this.getChildren().add(rect);
	}


	private void initAttributeList(ProjectBean project){
		attributeList = TAttributeBeanFacade.getInstance().findByProjectCode(project.getProjectCode());

		TAttributeBean projectNameAttr = new TAttributeBean();
		projectNameAttr.setAttributeCode(Constant.ATTRIBUTE_CODE_PROJECT_NAME);
		projectNameAttr.setValue(project.getProjectName());
		attributeList.add(Constant.ATTRIBUTE_INDEX_PROJECT_NAME, projectNameAttr);
	}






	/**
	 * 高さを変更する。<br>
	 * 各属性のビューも調整される
	 * @param height
	 */
	public void setViewHeight(double height){
		this.setSize(this.viewWidth.doubleValue(), height);
	}


	/**
	 * 幅を調整する。<br>
	 * 各属性のビューも調整される
	 * @param width
	 */
	public void setViewWidth(double width){
		this.setSize(width, this.viewHeight.doubleValue());
	}


	/**
	 * 属性部のサイズを変更する。<br>
	 * 各属性のビューも調整される
	 * @param width
	 * @param height
	 */
	public void setSize(double width,double height){
		this.viewWidth.set(width);
		this.viewHeight.set(height);

		resetPrimitiveViewSize(width,height);
	}



	/**
	 * 各属性のビューの位置・高さ・幅を現在のviewWidth,viewHeightを用いて初期化する
	 * @param width
	 * @param height
	 */
	private void resetPrimitiveViewSize(double width,double height){
		/** 案件名以外の属性に対して案件名の幅をどれだけ増分させて表示するか */
		double incrementaRate = Constant.ATTRIBUTES_VIEW_INCREMENTAL_RATE_OF_ATTR_NAME;
		/** incrementaRateを考慮して属性全体がviewHeightに収まりきるような属性表示の幅 */
		double defaultPrimitiveViewWidth = this.viewWidth.doubleValue()/(incrementaRate + attributeList.size()-1);



		int index = 0;
		for(AttributePrimitiveView primitiveView:primitiveViewList){

			//位置・高さ・幅決め
			if(index == 0){
				primitiveView.setSize(defaultPrimitiveViewWidth*incrementaRate, this.viewHeight.doubleValue());
				primitiveView.setTranslateX(0);
			}else{
				primitiveView.setSize(defaultPrimitiveViewWidth, this.viewHeight.doubleValue());
				primitiveView.setTranslateX(defaultPrimitiveViewWidth*(incrementaRate+index-1));
			}

			//this.getChildren().add(primitiveView);
			//this.primitiveViewList.add(primitiveView);

			index++;
		}
	}


}
