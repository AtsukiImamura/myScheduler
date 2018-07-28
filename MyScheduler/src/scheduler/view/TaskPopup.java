package scheduler.view;

import java.util.Calendar;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import scheduler.bean.TaskBean;
import scheduler.common.constant.Constant;
import scheduler.common.constant.NameConstant;


/**
 * カレンダーでタスクにマウスが入ったときに表示するポップアップ
 * @author ohmoon
 *
 */
public class TaskPopup extends AbstractView{


	/** このポップアップが表示するタスク */
	private TaskBean task;




	@Override
	protected void init() {
		if(this.task==null){
			return;
		}

		//ポップアップの内容表示用VBox
		VBox detailVBox = new VBox();
		detailVBox.getStyleClass().add(NameConstant.TASK_POPUP_DETAIL_VBOX_CSS);

		//タスク名
		Label projectNameLabel = new Label(task.getTaskCode());
		projectNameLabel.setPrefWidth(Constant.TASK_POPUP_DETAIL_LABEL_PREF_WIDTH);
		projectNameLabel.getStyleClass().add(NameConstant.TASK_POPUP_PROJECT_NAME_LABEL_CSS);


		//タスク詳細
		Label projectDetailLabel = new Label(task.getProjectDetail());
		projectDetailLabel.setPrefSize(Constant.TASK_POPUP_DETAIL_LABEL_PREF_WIDTH, Constant.TASK_POPUP_DETAIL_LABEL_PREF_HEIGHT);
		projectDetailLabel.getStyleClass().add(NameConstant.TASK_POPUP_PROJECT_DETAIL_LABEL_CSS);

		//期間
		HBox periodHBox = new HBox();
		Label startAtLabel = new Label();
		startAtLabel.getStyleClass().add(NameConstant.TASK_POPUP_START_AT_LABEL_CSS);

		Calendar startAt = task.getStartAt();
		if(startAt!=null){
			startAtLabel.setText(startAt.get(Calendar.YEAR)+"/"+startAt.get(Calendar.MONTH)+"/"+startAt.get(Calendar.DATE));
		}
		Label dateLabel = new Label(" - ");
		Label finishAtLabel = new Label();
		finishAtLabel.getStyleClass().add(NameConstant.TASK_POPUP_FINISH_AT_LABEL_CSS);
		Calendar finishAt = task.getFinishAt();
		if(finishAt != null){
			finishAtLabel.setText(finishAt.get(Calendar.YEAR)+"/"+finishAt.get(Calendar.MONTH)+"/"+finishAt.get(Calendar.DATE));
		}
		periodHBox.getChildren().addAll(startAtLabel,dateLabel,finishAtLabel);

		//作成者・作成日
		HBox hboxForCreate = new HBox();
		//TODO 作成者コードではなく名前にする
		Label createdByLabel = new Label(" 作成者 : "+task.getCreatedBy());

		Calendar createdAt = task.getCreatedAt();
		Label createdAtLabel = new Label(" 作成日 : ");
		if(createdAt!=null){
			createdAtLabel.setText(" 作成日 : "+createdAt.get(Calendar.YEAR)+"/"+createdAt.get(Calendar.MONTH)+"/"+createdAt.get(Calendar.DATE));
		}
		hboxForCreate.getChildren().addAll(
				createdByLabel,
				createdAtLabel
				);


		//変更者・変更日
		HBox hboxForChange = new HBox();
		//TODO 作成者コードではなく名前にする
		Label changeddByLabel = new Label(" 変更者 : "+task.getChangedBy());
		Label changedAtLabel = new Label(" 変更日 : ");
		Calendar changedAt = task.getChangedAt();
		if(changedAt != null){
			changedAtLabel.setText(" 変更日 : "+changedAt.get(Calendar.YEAR)+"/"+changedAt.get(Calendar.MONTH)+"/"+changedAt.get(Calendar.DATE));
		}
		hboxForChange.getChildren().addAll(
				changeddByLabel,
				changedAtLabel
				);


		detailVBox.getChildren().addAll(
				projectNameLabel,
				projectDetailLabel,
				periodHBox,
				hboxForCreate,
				hboxForChange
				);
		detailVBox.setPrefSize(Constant.TASK_POPUP_DETAIL_VBOX_PREF_WIDTH, Constant.TASK_POPUP_DETAIL_VBOX_PREF_HEIGHT);


		this.getChildren().add(detailVBox);
	}




	public TaskPopup(TaskBean task){
		this.task = task;
		init();
	}
}
