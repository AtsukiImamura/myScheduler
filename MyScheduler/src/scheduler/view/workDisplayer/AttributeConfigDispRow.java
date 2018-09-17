package scheduler.view.workDisplayer;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import scheduler.bean.MAttributeBean;

public class AttributeConfigDispRow extends HBox{


	public final double DEFAULT_HEIGHT = 36;

	public final double DEFAULT_WIDTH = 300;

	public final double DEFAULT_TITLE_WIDTH = 105;

	private MAttributeBean attribute;

	private final Label titleLabel;

	private final Label detailLabel;

	private final String BORDER_INSETS_NOMAL = "-fx-border-insets: 2;";

	private final String BORDER_INSETS_CLICKED = "-fx-border-insets: 0;";

	private final String BACKGROUND_STYLE_NOMAL = "-fx-background-color: #fff4ec;";

	private final String BACKGROUND_STYLE_CLICKED = "-fx-background-color: #faf5c0;";

	private final String BORDER_STYLE_NOMAL = "-fx-border-color: #ffffff; -fx-border-width: 2;";

	private final String BORDER_STYLE_HOVERED = "-fx-border-color: #c0c0c0;";

	private final String BORDER_STYLE_CLICKED = "-fx-border-color: #404040;";



	private boolean clicked = false;

	public boolean isClicked(){
		return this.clicked;
	}

	public void setClicked(boolean clicked){
		this.clicked = clicked;
		if(clicked){
			this.setStyle(BACKGROUND_STYLE_CLICKED+BORDER_STYLE_CLICKED);
		}else{
			this.setStyle(BACKGROUND_STYLE_NOMAL+BORDER_STYLE_NOMAL);
		}
	}


	public MAttributeBean getAttribute(){
		return this.attribute;
	}


	public void setAttrbute(MAttributeBean attr){
		this.attribute = attr;
		titleLabel.setText(attr.getAttrName());
		detailLabel.setText(attr.getDetail());
	}


	public AttributeConfigDispRow(MAttributeBean attr){
		this.attribute = attr;

		titleLabel = new Label(attr.getAttrName());
		titleLabel.setPrefSize(DEFAULT_TITLE_WIDTH, DEFAULT_HEIGHT);

		detailLabel = new Label(attr.getDetail());
		detailLabel.setPrefSize(DEFAULT_WIDTH-DEFAULT_TITLE_WIDTH, DEFAULT_HEIGHT);

		this.setPrefWidth(DEFAULT_WIDTH);

		this.getChildren().addAll(titleLabel,detailLabel);
		this.setStyle(BACKGROUND_STYLE_NOMAL+BORDER_STYLE_NOMAL);
		this.getStyleClass().addAll("attributes_config_disp_row");
	}


	public void onMouseEntered(){
		if(clicked){
			return;
		}
		this.setPrefWidth(DEFAULT_WIDTH-0.5);
		this.setStyle(BACKGROUND_STYLE_NOMAL+BORDER_STYLE_HOVERED);
	}


	public void onMouseExited(){
		if(clicked){
			return;
		}
		this.setPrefWidth(DEFAULT_WIDTH);
		this.setStyle(BORDER_STYLE_NOMAL+BACKGROUND_STYLE_NOMAL);
	}
}
