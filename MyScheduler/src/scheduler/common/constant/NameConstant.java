package scheduler.common.constant;


/**
 * リンク名やcssのクラス名などを保管するクラス
 * @author ohmoon
 *
 */
public class NameConstant {


	public static final String APP_CLASS_NAME = "APP";


	/* ---------------------------------------------------
	 * TaskPopupで使うcssクラス名
	 -----------------------------------------------------*/

	/** ポップアップ : 全体のVBox */
	public static final String TASK_POPUP_DETAIL_VBOX_CSS = "task_popup_detail_vbox";

	/** ポップアップ : 案件名のラベル */
	public static final String TASK_POPUP_PROJECT_NAME_LABEL_CSS = "task_popup_project_name_label";

	/** ポップアップ : 詳細のラベル */
	public static final String TASK_POPUP_PROJECT_DETAIL_LABEL_CSS = "task_popup_project_detail_label";

	/** ポップアップ : 期間表示用のHBox */
	public static final String TASK_POPUP_PERIOD_HBOX_CSS = "task_popup_period_hbox";

	/** ポップアップ : 開始日用ラベル */
	public static final String TASK_POPUP_START_AT_LABEL_CSS = "task_popup_start_at_label";

	/** ポップアップ : 終了日用ラベル */
	public static final String TASK_POPUP_FINISH_AT_LABEL_CSS = "task_popup_finish_at_label";

	/** ポップアップ : 作成関連HBox */
	public static final String TASK_POPUP_HBOX_FOR_CREATE_CSS = "task_popup_hbox_for_create";

	/** ポップアップ : 作成日ラベル */
	public static final String TASK_POPUP_CREATED_AT_LABEL_CSS = "task_popup_created_at_label";

	/** ポップアップ : 作成者ラベル */
	public static final String TASK_POPUP_CREATED_BY_LABEL_CSS = "task_popup_created_by_label";

	/** ポップアップ : 変更関連HBox */
	public static final String TASK_POPUP_HBOX_FOR_CHANGE_CSS = "task_popup_hbox_for_change";

	/** ポップアップ : 変更日ラベル */
	public static final String TASK_POPUP_CHANGED_AT_LABEL_CSS = "task_popup_changed_at_label";

	/** ポップアップ : 変更者ラベル */
	public static final String TASK_POPUP_CHANGED_BY_LABEL_CSS = "task_popup_changed_by_label";





	/* ---------------------------------------------------
	 * AttributePrimitiveViewで使うcssクラス名
	 -----------------------------------------------------*/

	public static final String ATTRIBUTE_PRIMITIVE_VIEW_LABEL_CSS = "attribute_primitive_view_attribute_label";



	/* ---------------------------------------------------
	 * CalendarDateCTRLViewで使う画像URL
	 -----------------------------------------------------*/

	//TODO 画像作成
	//TODO 画像は一か所にまとめたい

	public static final String PIC_URL_FORWARD_WEEK = "forwardWeek.png";

	public static final String PIC_URL_FORWARD_DAY = "forwardDay.png";

	public static final String PIC_URL_BACK_WEEK = "backWeek.png";

	public static final String PIC_URL_BACK_DAY = "backDay.png";




	/* ---------------------------------------------------
	 * CalendarDateCTRLViewで使うcssクラス名
	 -----------------------------------------------------*/

	public static final String CALENDAR_DATE_CTRL_BACK_WEEK_CSS = "back_week_button";

	public static final String CALENDAR_DATE_CTRL_BACK_DAY_CSS = "back_day_button";

	public static final String CALENDAR_DATE_CTRL_FORWARD_WEEK_CSS = "forward_week_button";

	public static final String CALENDAR_DATE_CTRL_FORWARD_DAY_CSS = "forward_day_button";

	public static final String CALENDAR_DATE_CTRL_SELECT_YEAR_CSS = "select_year_box";

	public static final String CALENDAR_DATE_CTRL_SELECT_MONTH_CSS = "select_month_box";

	public static final String CALENDAR_DATE_CTRL_SELECT_DAY_CSS = "select_day_box";



	/* ---------------------------------------------------
	 * データベースのテーブル名
	 -----------------------------------------------------*/

	public static final String TABLE_NAME_T_ATTRIBUTES = "T_ATTRIBUTES";

	public static final String TABLE_NAME_T_PROJECT = "T_PROJECT";

	public static final String TABLE_NAME_T_TASK = "T_TASK";

	public static final String TABLE_NAME_M_USER = "M_USER";

	public static final String TABLE_NAME_M_STATUS = "M_STATUS";

	public static final String TABLE_NAME_M_STONE = "M_STONE";

	public static final String TABLE_NAME_M_ATTRIBUTES = "M_ATTRIBUTES";

	public static final String TABLE_NAME_M_ATTRIBUTE_SELECTION = "M_ATTRIBUTE_SELECTION";


	public static final String DATABASE_URL_GET_DATA = "https://tonkotsu-ohmoon.ssl-lolipop.jp/myscheduler/data_test.php";


	public static final String DATABASE_URL_INSERT_DATA = "https://tonkotsu-ohmoon.ssl-lolipop.jp/myscheduler/insert_data.php";


	public static final String DATABASE_URL_UPDATE_DATA = "https://tonkotsu-ohmoon.ssl-lolipop.jp/myscheduler/insert_data.php";



	/* ---------------------------------------------------
	 * テスト関係
	 -----------------------------------------------------*/

	public static final String TEST_USER_CODE = "test_001";

	public static final String TEST_PASSWORD = "test";




	/* ---------------------------------------------------
	 * データベース
	 -----------------------------------------------------*/

	public static final String PROJECT_CODE_PREFIX = "PJ_";

	public static final String TASK_CODE_PREFIX = "TK_";


	public static final String FXML_PATH_PROJECT_EDITOR = "view/workDisplayer/workDisplayer.fxml";












	/* ---------------------------------------------------
	 * （テンプレ）
	 -----------------------------------------------------*/

}
