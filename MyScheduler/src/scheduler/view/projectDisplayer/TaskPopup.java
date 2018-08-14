package scheduler.view.projectDisplayer;

import java.util.Calendar;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import scheduler.bean.TaskBean;
import scheduler.common.constant.Constant;
import scheduler.common.constant.NameConstant;
import scheduler.common.utils.Util;
import scheduler.view.AbstractView;


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
		Label projectDetailLabel = new Label(task.getDetail());
		projectDetailLabel.setPrefSize(Constant.TASK_POPUP_DETAIL_LABEL_PREF_WIDTH, Constant.TASK_POPUP_DETAIL_LABEL_PREF_HEIGHT);
		projectDetailLabel.getStyleClass().add(NameConstant.TASK_POPUP_PROJECT_DETAIL_LABEL_CSS);

		//期間
		HBox periodHBox = new HBox();

		//開始日
		Label startAtLabel = new Label();
		startAtLabel.getStyleClass().add(NameConstant.TASK_POPUP_START_AT_LABEL_CSS);
		Calendar startAt = task.getStartAt();
		startAtLabel.setText(Util.getSlashFormatCalendarValue(startAt, true));

		Label dateLabel = new Label(" - ");

		//終了日
		Label finishAtLabel = new Label();
		finishAtLabel.getStyleClass().add(NameConstant.TASK_POPUP_FINISH_AT_LABEL_CSS);
		Calendar finishAt = task.getFinishAt();
		finishAtLabel.setText(Util.getSlashFormatCalendarValue(finishAt, true));

		periodHBox.getChildren().addAll(startAtLabel,dateLabel,finishAtLabel);

		//作成者・作成日
		HBox hboxForCreate = new HBox();
		//TODO 作成者コードではなく名前にする
		Label createdByLabel = new Label(" 作成者 : "+task.getCreatedBy());

		Calendar createdAt = task.getCreatedAt();
		Label createdAtLabel = new Label();
		createdAtLabel.setText(" 作成日 : "+Util.getSlashFormatCalendarValue(createdAt, true));

		hboxForCreate.getChildren().addAll(
				createdByLabel,
				createdAtLabel
				);


		//変更者・変更日
		HBox hboxForChange = new HBox();
		//TODO 作成者コードではなく名前にする
		Label changeddByLabel = new Label(" 変更者 : "+task.getChangedBy());
		Label changedAtLabel = new Label();
		Calendar changedAt = task.getChangedAt();
		changedAtLabel.setText(" 変更日 : "+Util.getSlashFormatCalendarValue(changedAt, true));

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
