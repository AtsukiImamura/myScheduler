package scheduler.facade;

import java.util.List;

import javafx.scene.paint.Color;
import scheduler.bean.StatusBean;

public class StatusFacade extends AbstractFacade<StatusBean>{


	protected final static String  FILENAME = "aaa.csv";

	@Override
	public List<StatusBean> findAll() {
		return this.findAll(StatusBean.class);
	}


	public StatusBean findByStatusCode(String code){
		//TODO 実装
		return new StatusBean();
	}



	/**
	 * 案件番号から、その案件の持つステータスを一つ返す
	 * @param projectCode
	 * @return
	 */
	public StatusBean findByProjectCode(String projectCode){
		//TODO 実装
		StatusBean status = new StatusBean();
		status.setColor(Color.rgb(255, 245, 210));
		status.setStatusColorCode(projectCode);
		return status;
	}

}
