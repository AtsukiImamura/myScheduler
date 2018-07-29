package scheduler.bean;

public class StoneBean {

	/**
	 * 主キー
	 * ストーンカラーコード*/
	private String stoneColorCode;

	/**色*/
	private String color;

	/**タイトル*/
	private String tytle;

	/**説明*/
	private String detail;


	public String getStatusColorCode() {
		return stoneColorCode;
	}

	public void setStatusColorCode(String statusColorCode) {
		this.stoneColorCode = statusColorCode;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTytle() {
		return tytle;
	}

	public void setTytle(String tytle) {
		this.tytle = tytle;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
