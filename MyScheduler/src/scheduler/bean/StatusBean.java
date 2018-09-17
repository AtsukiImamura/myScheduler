package scheduler.bean;

import scheduler.common.constant.Constant.COLOR_TYPE;
import scheduler.common.constant.NameConstant;

public class StatusBean  extends ColorAttribute{

	@Override
	public String getTableName(){
		return NameConstant.TABLE_NAME_M_STATUS;
	}

	public StatusBean clone(){
		StatusBean colorAttribute = null;
		try {
			colorAttribute =StatusBean.class.newInstance();
			colorAttribute.setCode(this.code);
			colorAttribute.setTitle(this.title);
			colorAttribute.setColor(this.color);
			colorAttribute.setDetail(this.detail);
			colorAttribute.setDeleted(this.deleted);
			colorAttribute.setChangedAt(this.changedAt);
			colorAttribute.setChangedBy(this.changedBy);
			colorAttribute.setCreatedAt(this.createdAt);
			colorAttribute.setCreatedBy(this.createdBy);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return colorAttribute;
	}

	public COLOR_TYPE getColorType(){
		return COLOR_TYPE.STATUS;
	}
}
