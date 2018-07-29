package scheduler.view;

import java.util.ArrayList;
import java.util.List;

import scheduler.bean.TAttributeBean;
import scheduler.common.constant.Constant;

/**
 * 案件の属性部を担うビュークラス
 * @author ohmoon
 *
 */
public class ProjectAttributesView extends AbstractView{


	/** 個別の属性表示用ビュー */
	private final List<AttributePrimitiveView> primitiveViewList;

	/** 属性リスト */
	private final List<TAttributeBean>  attributeList;

	@Override
	protected void init() {

		if(attributeList == null || primitiveViewList == null ){
			return;
		}

		/** 案件名以外の属性に対して案件名の幅をどれだけ増分させて表示するか */
		double incrementaRate = Constant.ATTRIBUTES_VIEW_INCREMENTAL_RATE_OF_ATTR_NAME;
		/** incrementaRateを考慮して属性全体がviewHeightに収まりきるような属性表示の幅 */
		double defaultPrimitiveViewWidth = this.viewWidth.doubleValue()/(incrementaRate + attributeList.size()-1);

		//TODO コード順に並んでいることが前提
		int index = 0;
		for(TAttributeBean attribute:attributeList){

			AttributePrimitiveView primitiveView;

			//位置・高さ・幅決め
			if(attribute.getAttributeCode() == Constant.ATTRIBUTE_CODE_PROJECT_NAME){
				primitiveView  = new AttributePrimitiveView(attribute,defaultPrimitiveViewWidth*incrementaRate, this.viewHeight.doubleValue());
				primitiveView.setTranslateX(0);
			}else{
				primitiveView  = new AttributePrimitiveView(attribute,defaultPrimitiveViewWidth, this.viewHeight.doubleValue());
				primitiveView.setTranslateX(defaultPrimitiveViewWidth*(incrementaRate+index-1));
			}

			this.getChildren().add(primitiveView);
			this.primitiveViewList.add(primitiveView);

			index++;
		}

	}



	/**
	 * コンストラクタ
	 * @param attributes 属性のリスト
	 * @param height 表示高さ
	 */
	public ProjectAttributesView(List<TAttributeBean> attributes,double height){
		primitiveViewList = new ArrayList<AttributePrimitiveView>();
		attributeList = attributes;

		this.viewHeight.set(height);
		this.setViewWidth((1-Constant.DEFAULT_RATE_OF_CALENDAR_WIDTH)*Constant.APP_PREF_WIDTH);

		init();
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

			this.getChildren().add(primitiveView);
			this.primitiveViewList.add(primitiveView);

			index++;
		}
	}


}
