package scheduler.view;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import scheduler.bean.StatusBean;
import scheduler.bean.TAttributeBean;
import scheduler.common.constant.Constant;
import scheduler.common.constant.NameConstant;


/**
 * 属性部で一つの属性の表示を担うクラス
 * @author ohmoon
 *
 */
public class AttributePrimitiveView extends AbstractView{


	/** 表示する属性 */
	private final TAttributeBean attribute;

	/** 案件のステータス */
	private StatusBean status;

	/** 属性表示用ラベル */
	private Label attributeLabel;





	@Override
	protected void init() {
		if(attribute == null){
			return;
		}
		//TODO 体裁を整える（大きさ・配置・css)
		attributeLabel = new Label(attribute.getValue());
		attributeLabel.setPrefSize(
				this.viewWidth == null ? Constant.ATTRIBUTE_PRIMITIVE_ROW_DEFAULT_WIDTH : this.viewWidth.doubleValue(),
				this.viewHeight == null ? Constant.ATTRIBUTE_PRIMITIVE_ROW_DEFAULT_HEIGHT : this.viewHeight.doubleValue()
				);
		attributeLabel.getStyleClass().add(NameConstant.ATTRIBUTE_PRIMITIVE_VIEW_LABEL_CSS);

		this.getChildren().add(attributeLabel);

	}


	/**
	 * サイズを変更する
	 * @param width
	 * @param height
	 */
	public void setSize(double width,double height){
		this.viewWidth.set(width);
		this.viewHeight.set(height);

		attributeLabel.setPrefSize(width, height);

	}


	/**
	 * ステータスを変更する<br>
	 * 同時に背景色がステータスに合わせて変更される
	 * @param status
	 */
	public void setStatus(StatusBean status){
		this.status = status;
		setLabelBackgroundColor(status.getColor());
	}


	/**
	 * 属性ラベルの背景色を変更する
	 * @param color
	 */
	private void setLabelBackgroundColor(Color color){
		List<BackgroundFill> fills =  attributeLabel.getBackground().getFills();
		//fillsのサイズが0の場合は適当に新しいBackgroundFillを作成する
		BackgroundFill sourceBackGroundFill = fills.size() > 0 ? fills.get(0) : new BackgroundFill(Color.WHITE,new CornerRadii(3),new Insets(3)) ;

		attributeLabel.setBackground(
				new Background(
						new BackgroundFill(
								color,
								sourceBackGroundFill.getRadii(),
								sourceBackGroundFill.getInsets()
								)
						)
				);
	}



	/**
	 * コンストラクタ
	 * @param attribute 属性
	 * @param width 表示幅
	 * @param height 表示高さ
	 */
	public AttributePrimitiveView(TAttributeBean attribute,double width,double height){
		this.attribute = attribute;

		init();

		this.viewWidth.set(width);
		this.viewHeight.set(height);
	}

}
