package scheduler.common.constant;

import javafx.scene.paint.Color;
import scheduler.view.calendar.CalendarDay;

/**
 * 固定値保存用クラス
 * @author ohmoon
 *
 */
public class Constant {

	/** アプリ全体の初期高さ */
	public static final double APP_PREF_HEIGHT = 900;

	/** アプリ全体の初期幅 */
	public static final double APP_PREF_WIDTH = 1600;

	/** 表示部: カレンダー1行当たりの高さ */
	public static final double PROJECT_CALENDAR_ROW_HEIGHT = 72;

	/** ポップアップ : 全体の高さ */
	public static final double TASK_POPUP_DETAIL_VBOX_PREF_HEIGHT = 180;

	/** ポップアップ : 全体の幅 */
	public static final double TASK_POPUP_DETAIL_VBOX_PREF_WIDTH = 350;

	/** ポップアップ : 詳細ラベルの高さ */
	public static final double TASK_POPUP_DETAIL_LABEL_PREF_HEIGHT = 120;

	/** ポップアップ : 詳細ラベルの幅 */
	public static final double TASK_POPUP_DETAIL_LABEL_PREF_WIDTH = 340;


	/** カレンダー: マウスホバー時の色 */
	public static final Color CALENDAR_HILIGHT_COLOR = Color.rgb(255, 245, 220);

	/** カレンダー: デフォルトの色 */
	public static final Color CALENDAR_NOMAL_COLOR = Color.rgb(250, 250, 250);

	/** アプリ全体幅に対するカレンダー部分の幅 */
	public static final double DEFAULT_RATE_OF_CALENDAR_WIDTH = 0.7;

	/** カレンダーのデフォルト幅(?) */
	public static final double CALENDAR_ROW_VIEW_WIDTH = 850;

	public static final double CALENDAR_ROW_VIEW_HEIGHT = 33;

	/** 属性部の最大幅 */
	public static final double MAX_ATTRIBUTE_WIDTH = 500;

	/** 属性部の最小幅 */
	public static final double MIN_ATTRIBUTE_WIDTH = 300;

	/** 属性部: 属性一つに対するデフォルトの幅 */
	public static final double ATTRIBUTE_PRIMITIVE_ROW_DEFAULT_WIDTH = 120;

	/** 属性部: デフォルトの高さ */
	public static final double ATTRIBUTE_PRIMITIVE_ROW_DEFAULT_HEIGHT = CalendarDay.DEFAULT_HEIGHT;

	public static final double PROJECTS_SPACE = 5;

	/** 案件名称以外の属性の表示幅に対する案件名称の表示幅の割合 */
	public static final double ATTRIBUTES_VIEW_INCREMENTAL_RATE_OF_ATTR_NAME = 1.4;

	/** ステータスカラーが登録されていなかった時のデフォルト表示カラー */
	public static final Color ATTRIBUTES_VIEW_DEFAULT_STATUS_COLOR = Color.WHITE;

	/** 属性の中で案件名称を表すインデックス */
	public static final String ATTRIBUTE_CODE_PROJECT_NAME = "ATTR_PRJNAME";

	/** 個別案件表示クラスに渡す属性リストのうち、案件名の属性を格納する位置 */
	public static final int ATTRIBUTE_INDEX_PROJECT_NAME = 0;

	/** 日曜日の色 */
	public static final Color CALENDAR_DATE_VIEW_SUNDAY = Color.rgb(255, 195, 175);

	/** 土曜日の色 */
	public static final Color CALENDAR_DATE_VIEW_SATURDAY = Color.rgb(200, 240, 255);

	/** 平日の色 */
	public static final Color CALENDAR_DATE_VIEW_NOMAL = Color.rgb(250,250, 250);

	/** 日曜日: ホバー */
	public static final Color CALENDAR_DATE_VIEW_SUNDAY_HOVERED = Color.rgb(255, 200, 185);

	/** 土曜日: ホバー */
	public static final Color CALENDAR_DATE_VIEW_SATURDAY_HOVERED = Color.rgb(210, 245, 255);

	/** 平日: ホバー */
	public static final Color CALENDAR_DATE_VIEW_NOMAL_HOVERED = Color.rgb(255,255, 255);

	/** 日曜日: クリック */
	public static final Color CALENDAR_DATE_VIEW_SUNDAY_CLICKED = Color.rgb(255, 120, 105);

	/** 土曜日: クリック */
	public static final Color CALENDAR_DATE_VIEW_SATURDAY_CLICKED = Color.rgb(90, 180, 255);

	/** 平日: クリック */
	public static final Color CALENDAR_DATE_VIEW_NOMAL_CLICKED = Color.rgb(210,210, 210);


	/** 各タスクのホバー時に表示するポップアップのタスクに対するx座標位置 */
	public static final double POPUP_TRANSLATE_X = 30;

	/** 各タスクのホバー時に表示するポップアップのタスクに対するy座標位置 */
	public static final double POPUP_TRANSLATE_Y = 30;

	/** カレンダー操作部の高さ */
	public static final double CALENDAR_DATE_CTRL_HEIGHT = 40;

	/** 属性カスタマイズタイプ: 自由入力 */
	public static final int ATTRIBUTE_COSTAMIZE_TYPE_FREE = 10;

	/** 属性カスタマイズタイプ: 選択 */
	public static final int ATTRIBUTE_COSTAMIZE_TYPE_SELECT = 20;

	/** 選択コード長さ */
	public static final int LENGTH_OF_SELECTION_CODE = 5;

	/** DBの値がnullのものの代入文字列 */
	public static final String DB_VALUE_NULL = "null";

	public static final String POSTFIX_CALEDAR_MONTH = "月";

	public static final double MONTH_NAME_LABEL_HEIGHT = 28;


	/** カレンダー日付表示部: 月名表示部分の背景色のベースレベル（灰色の薄さ） */
	public static final int MONTH_NAME_LABEL_BACK_BASE = 195;

	/** カレンダー日付表示部: 月名表示部分の背景色の月による変化 */
	public static final int VAR_OF_BACK_BASE = 25;













}
