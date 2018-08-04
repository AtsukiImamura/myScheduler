package scheduler.common.constant;

import javafx.scene.paint.Color;

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
	public static final double PROJECT_CALENDAR_ROW_HEIGHT = 40;

	/** ポップアップ : 全体の高さ */
	public static final double TASK_POPUP_DETAIL_VBOX_PREF_HEIGHT = 180;

	/** ポップアップ : 全体の幅 */
	public static final double TASK_POPUP_DETAIL_VBOX_PREF_WIDTH = 350;

	/** ポップアップ : 詳細ラベルの高さ */
	public static final double TASK_POPUP_DETAIL_LABEL_PREF_HEIGHT = 120;

	/** ポップアップ : 詳細ラベルの幅 */
	public static final double TASK_POPUP_DETAIL_LABEL_PREF_WIDTH = 340;


	public static final Color CALENDAR_HILIGHT_COLOR = Color.rgb(255, 245, 220);


	public static final Color CALENDAR_NOMAL_COLOR = Color.rgb(250, 250, 250);


	public static final double DEFAULT_RATE_OF_CALENDAR_WIDTH = 0.7;

	public static final double CALENDAR_ROW_VIEW_WIDTH = 850;

	public static final double CALENDAR_ROW_VIEW_HEIGHT = 33;


	public static final double ATTRIBUTE_PRIMITIVE_ROW_DEFAULT_WIDTH = 120;

	public static final double ATTRIBUTE_PRIMITIVE_ROW_DEFAULT_HEIGHT = 40;

	//incremental
	public static final double ATTRIBUTES_VIEW_INCREMENTAL_RATE_OF_ATTR_NAME = 1.4;

	public static final Color ATTRIBUTES_VIEW_DEFAULT_STATUS_COLOR = Color.WHITE;


	public static final String ATTRIBUTE_CODE_PROJECT_NAME = "1";


	public static final Color CALENDAR_DATE_VIEW_SUNDAY = Color.rgb(255, 195, 175);

	public static final Color CALENDAR_DATE_VIEW_SATURDAY = Color.rgb(200, 240, 255);

	public static final Color CALENDAR_DATE_VIEW_NOMAL = Color.rgb(250,250, 250);

	public static final Color CALENDAR_DATE_VIEW_SUNDAY_HOVERED = Color.rgb(255, 200, 185);

	public static final Color CALENDAR_DATE_VIEW_SATURDAY_HOVERED = Color.rgb(210, 245, 255);

	public static final Color CALENDAR_DATE_VIEW_NOMAL_HOVERED = Color.rgb(255,255, 255);

	public static final Color CALENDAR_DATE_VIEW_SUNDAY_CLICKED = Color.rgb(255, 120, 105);

	public static final Color CALENDAR_DATE_VIEW_SATURDAY_CLICKED = Color.rgb(90, 180, 255);

	public static final Color CALENDAR_DATE_VIEW_NOMAL_CLICKED = Color.rgb(210,210, 210);



	public static final double POPUP_TRANSLATE_X = 30;

	public static final double POPUP_TRANSLATE_Y = 30;


	public static final double CALENDAR_DATE_CTRL_HEIGHT = 40;
}
