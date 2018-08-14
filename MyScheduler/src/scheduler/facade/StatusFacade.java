package scheduler.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import scheduler.bean.StatusBean;

public class StatusFacade extends CSVAbstractFacade<StatusBean>{

	@Override
	public List<StatusBean> findAll(){
		return this.findAll(StatusBean.class);
	}

	/*
	@Override
	public List<StatusBean> findAll() {
		List<StatusBean> statusBeanList = this.findAll(NameConstant.TABLE_NAME_M_STATUS, NameConstant.TEST_USER_CODE, NameConstant.TEST_PASSWORD,StatusBean.class);
		//return this.findAll(StatusBean.class);
		return statusBeanList;
	}
	*/


	@Override
	public void createNewDatabase(){
		this.createNewDatabase(StatusBean.class);
	}


	public StatusBean one(String code){
		Map<String,String> primaryKeys = new HashMap<String,String>();
		primaryKeys.put("STATUS_CODE", code);
		return this.one(StatusBean.class, primaryKeys);
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
		status.setStatusCode(projectCode);
		return status;
	}

}
