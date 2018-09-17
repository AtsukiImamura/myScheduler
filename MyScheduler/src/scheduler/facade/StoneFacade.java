package scheduler.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scheduler.bean.StoneBean;
import scheduler.common.constant.NameConstant;

public class StoneFacade extends CSVAbstractFacade<StoneBean>{



	private static StoneFacade instance;

	static{
		instance = new StoneFacade();
	}

	public static StoneFacade getInstance(){
		return instance;
	}


	@Override
	public List<StoneBean> findAll(){
		return this.findAll(StoneBean.class);
	}



	@Override
	public void createNewDatabase(){
		this.createNewDatabase(StoneBean.class);
	}


	public String createNewCode(){
		List<StoneBean> stoneList = this.findAll();
		int max = 0;
		for(StoneBean stone: stoneList){
			String stonetCode = stone.getCode();
			String number = stonetCode.split("_")[1];
			number = number.replaceAll("^0*","");
			int num = Integer.parseInt(number);
			if(num > max){
				max = num;
			}
		}
		int newNum = max + 1;
		String newNumber = String.format("%03d", newNum);
		return NameConstant.STONE_CODE_PREFIX+newNumber;
	}



	public StoneBean one(String stoneCode){
		Map<String,String> primaryKeys = new HashMap<String,String>();
		primaryKeys.put("CODE", stoneCode);
		return this.one(StoneBean.class,primaryKeys);
	}

	/*
	@Override
	public List<StoneBean> findAll() {
		//return this.findAll(StoneBean.class);
		List<StoneBean> attributeBeanList = this.findAll(NameConstant.TABLE_NAME_M_STONE, NameConstant.TEST_USER_CODE, NameConstant.TEST_PASSWORD,StoneBean.class);
		return attributeBeanList;
	}
	*/

}
