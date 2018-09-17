package scheduler.view.workDisplayer;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import scheduler.bean.AttributeSelectionBean;
import scheduler.facade.AttributeSelectionBeanFacade;

public class AttributeSelectionRow extends HBox{


	public final double DEFAULT_HEIGHT = 36;

	public final double DEFAULT_WIDTH = 300;

	public final double DEFAULT_TITLE_WIDTH = 105;

	private AttributeSelectionBean selection;

	private final Label titleLabel;

	private final TextField inputField;

	private final Button editButton;

	private final Button registButton;

	private final Button deleteButton;

	private boolean editing = false;

	public boolean isEditing(){
		return this.editing;
	}


	public AttributeSelectionBean getSelectionBean(){
		return this.selection;
	}


	public void setAttrbute(AttributeSelectionBean selection){
		this.selection = selection;
		titleLabel.setText(selection.getDispName());
	}



	public AttributeSelectionRow(String attrCode){
		this();

		this.selection = new AttributeSelectionBean();
		String newSelectionCode = AttributeSelectionBeanFacade.getInstance().createNewCode(attrCode);
		selection.setSelectionCode(newSelectionCode);
		selection.setAttributeCode(attrCode);

		this.getChildren().addAll(inputField,registButton);

		this.editing = true;
	}


	private AttributeSelectionRow(){
		deleteButton = new Button("削除");
		deleteButton.getStyleClass().addAll("attr_selection_delete_button","attr_selection_button_base");
		editButton = new Button("編集");
		editButton.getStyleClass().addAll("attr_selection_edit_button","attr_selection_button_base");
		registButton = new Button("登録");
		registButton.getStyleClass().addAll("attr_selection_regist_button","attr_selection_button_base");
		inputField = new TextField();
		inputField.getStyleClass().addAll("attr_detail_selection_input");

		titleLabel = new Label();
		titleLabel.getStyleClass().addAll("attr_detail_selection_label");
		titleLabel.setPrefSize(DEFAULT_TITLE_WIDTH, DEFAULT_HEIGHT);

		this.getStyleClass().add("attr_selection_row");

		editButton.setOnMouseClicked(event->{
			if(!AttributeDetailController.canEditAny()){
				return;
			}
			this.getChildren().clear();
			inputField.setText(this.selection.getDispName());
			this.getChildren().addAll(inputField,registButton);
			this.editing = true;
		});

		registButton.setOnMouseClicked(event->{
			String dispName;
			if((dispName = inputField.getText()) == null || dispName.equals("")){
				return;
			}
			this.getChildren().clear();
			this.selection.setDispName(dispName);
			titleLabel.setText(dispName);
			this.getChildren().addAll(titleLabel,editButton,deleteButton);
			AttributeSelectionBeanFacade.getInstance().save(selection);
			this.editing = false;
		});

		deleteButton.setOnMouseClicked(event->{
			AttributeSelectionBeanFacade.getInstance().logicalDelete(selection);
			AttributeDetailController.getInstance().redraw();
		});

	}

	public AttributeSelectionRow(AttributeSelectionBean selection){
		this();

		this.selection = selection;
		titleLabel.setText(selection.getDispName());

		this.getChildren().addAll(titleLabel,editButton,deleteButton);
	}



}
