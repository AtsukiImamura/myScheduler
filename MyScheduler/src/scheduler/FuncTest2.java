package scheduler;

import scheduler.bean.MAttributeBean;
import scheduler.common.constant.Constant;

public class FuncTest2 {



	public static void main(String[] args) throws Exception{
		MAttributeBean bean = new MAttributeBean();
		bean.setCostamaizeType(Constant.ATTRIBUTE_TYPE.FREE);
		System.out.println(Constant.ATTRIBUTE_TYPE.FREE.toString());
		System.out.println(Constant.ATTRIBUTE_TYPE.findByCode("FREE").CODE);
	}
}
