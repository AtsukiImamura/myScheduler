package scheduler.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import scheduler.bean.StatusBean;
import scheduler.common.constant.NameConstant;

public class StatusFacade extends CSVAbstractFacade<StatusBean>{


	private static StatusFacade instance;

	static{
		instance = new StatusFacade();
	}

	public static StatusFacade getInstance(){
		return instance;
	}


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
		primaryKeys.put("CODE", code);
		return this.one(StatusBean.class, primaryKeys);
	}

	public String createNewCode(){
		List<StatusBean> statusList = this.findAll();
		int max = 0;
		for(StatusBean status: statusList){
			String statusCode = status.getCode();
			String number = statusCode.split("_")[1];
			number = number.replaceAll("^0*","");
			int num = Integer.parseInt(number);
			if(num > max){
				max = num;
			}
		}
		int newNum = max + 1;
		String newNumber = String.format("%03d", newNum);
		return NameConstant.STATUS_CODE_PREFIX+newNumber;
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
		status.setCode(projectCode);
		return status;
	}

}
