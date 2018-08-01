package scheduler.view.calendar;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import scheduler.view.AbstractView;

/**
 * カレンダー部において、特定の行の特定の日の表示に関わるクラス
 * @author ohmoon
 *
 */
public class CalendarDay extends AbstractView{

	/**既定の幅*/
	public static final double DEFAULT_WIDTH = 25;

	/**既定の高さ*/
	public static final double DEFAULT_HEIGHT =25;

	/**既定のカラー*/
	public static final Color DEFAULT_COLOR = Color.rgb(220, 240, 180);

	/**マウスが重なったときの色*/
	public static final Color DEFAULT_MOUSE_HOVER_COLOR = Color.rgb(250, 180, 150);

	/**選択された時の色*/
	public static final Color DEFAULT_MOUSE_CLICKED_COLOR = Color.rgb(120, 180, 255);

	private Color stoneColor;

	private Color mouseHoveredColor;

	private Color mouseClickedColor;

	private Color currentColor;


	/** 外部から使用する用のメタデータ : 並べる順番を管理するなどに使う<br> （注）内部で使用禁止 */
	private int index;


	/** （日付表示用）曜日の値 */
	private int dayOfWeek;


	private int displayDate;


	private Label displayDateLabel;

	private boolean isHovered;

	public final BooleanProperty hoveredProperty;

	/**選択されているかどうか*/
	private boolean isSelected = false;

	/** 選択されているかどうか（外部への通知用） */
	public final BooleanProperty selectedProperty;


	/**
	 * 外部で使用するインデックスを得る<br>
	 * このインデックスはインスタンス内部で使われることはない
	 * <br>
	 * @return 登録されたインデックス<br>
	 * インデックスが登録されていない場合は-1を返す
	 */
	public int getIndex() {
		try{
			return index;
		}catch(Exception e){
			return -1;
		}
	}


	/**
	 * 外部からこのインスタンスのソート順を知るときなどに登録する。<br>
	 * インスタンス内部に影響を及ぼすことはない
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * （日付表示用）日付をセットする
	 * @param date
	 */
	public void setDisplayDate(int date){
		displayDate = date;
		if(displayDateLabel !=null){
			displayDateLabel.setText(date+"");
		}
	}



	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public boolean isSelected() {
		return isSelected;
	}


	public boolean isHovered(){
		return isHovered;
	}

	/**
	 * マウスが重なった状態にする。<br>
	 * 外部から呼び出されることを前提にしているので、外部への通知用プロパティは更新しない
	 * @param isHovered
	 */
	public void setHovered(boolean isHovered) {
		setHoveredForPrivate(isHovered,false);
	}


	/**
	 * 自身をマウスが重なった状態にする。<br>
	 * @param isHovered 新しいホバー状態
	 * @param notice 外部へ通知するかどうか
	 */
	private void setHoveredForPrivate(boolean isHovered,boolean notice){

		this.isHovered = isHovered;
		if(notice){
			hoveredProperty.set(isHovered);
		}
		if(this.isSelected){
			return;
		}
		if(isHovered){
			this.setCurrentColor(this.mouseHoveredColor);
		}else{
			this.setCurrentColor(this.stoneColor);
		}
	}



	/**
	 * 選択状態にする。<br>
	 * 外部から呼び出されることを前提にしているので、外部への通知用プロパティは更新しない
	 * @param isSelected
	 */
	public void setSelected(boolean isSelected) {
		/*
		if(selectedProperty.get() == isSelected){
			return;
		}
		*/
		if(isSelected){
			this.setCurrentColor(this.mouseClickedColor);
		}else{
			this.setCurrentColor(this.stoneColor);
		}
		this.isSelected = isSelected;
	}



	public Color getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(Color currentColor) {
		this.currentColor = currentColor;
		try{
			((Shape) this.getChildren().get(0)).setFill(currentColor);
		}catch(IndexOutOfBoundsException e){

		}
	}

	public Color getStoneColor() {
		return stoneColor;
	}


	public void setStoneColor(Color stoneColor) {
		this.stoneColor = stoneColor;
		if(!isSelected){
			this.setCurrentColor(stoneColor);
		}
	}


	public Color getMouseHoveredColor() {
		return mouseHoveredColor;
	}


	public void setMouseHoveredColor(Color mouseHoveredColor) {
		this.mouseHoveredColor = mouseHoveredColor;
	}


	public Color getMouseClickedColor() {
		return mouseClickedColor;
	}


	public void setMouseClickedColor(Color mouseClickedColor) {
		this.mouseClickedColor = mouseClickedColor;
	}





	CalendarDay(){
		selectedProperty = new SimpleBooleanProperty(false);
		hoveredProperty = new SimpleBooleanProperty(false);
	}




	@Override
	protected void init(){

		this.setStoneColor(DEFAULT_COLOR);
		this.setMouseHoveredColor(DEFAULT_MOUSE_HOVER_COLOR);
		this.setMouseClickedColor(DEFAULT_MOUSE_CLICKED_COLOR);

		//四角形作成・配置
		Rectangle rect = new Rectangle(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		rect.setStroke(this.stoneColor);
		rect.setFill(DEFAULT_COLOR);
		this.getChildren().add(rect);


		//（イベントリスナー）マウスが入ったとき
		this.setOnMouseEntered(event->{
			if(this.isSelected){
				return;
			}
			setHoveredForPrivate(true,true);
		});

		//（イベントリスナー）マウスが出たとき
		this.setOnMouseExited(event->{
			if(this.isSelected){
				return;
			}
			setHoveredForPrivate(false,true);
		});

		//（イベントリスナー）クリックされたとき
		this.setOnMouseClicked(event->{
			//選択フラグを逆に設定
			this.setSelected(!this.isSelected);
			selectedProperty.set(isSelected);
		});


		//日付描画用のラベル
		displayDateLabel = new Label();
		displayDateLabel.setPrefSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		displayDateLabel.setAlignment(Pos.CENTER);
		//重なっても大丈夫なように色は透明にしておく
		displayDateLabel.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(3))));
		this.getChildren().add(displayDateLabel);
	}


	/**
	 * サイズを変更する
	 */
	public void resize(double width,double height){
		Rectangle rect = (Rectangle)this.getChildren().get(0);
		rect.setWidth(width);
		rect.setHeight(height);
	}

}
