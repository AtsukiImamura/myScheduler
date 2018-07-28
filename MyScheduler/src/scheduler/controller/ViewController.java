package scheduler.controller;

import javafx.scene.Group;

/**
 * ビューのコントロールに関するクラス。自分の担当するビューのNodeのGroupを所持
 * @author ohmoon
 *
 */
public abstract class ViewController {

	/**
	 * 自身が描画するNode
	 */
	protected Group group;

	Group getGroup(){
		return group;
	}

	/**
	 * 初期化
	 */
	abstract protected void init();

	public ViewController(){
		init();
	}
}
