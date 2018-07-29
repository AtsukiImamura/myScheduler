package scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import scheduler.bean.TaskBean;
import scheduler.common.utils.Util;;

public class FuncTest {


	public static void main(String[] args){
		//double[] value = {2,7,8,15,4,13,20,18,17,13,22,4,6};
		//Util.sort(value);


		List<TaskBean> taskList = new ArrayList<TaskBean>();
		for(int i=0;i<10;i++){
			TaskBean task = new TaskBean();
			Calendar startAt  = Calendar.getInstance();
			startAt.add(Calendar.DAY_OF_MONTH, (int)(20*Math.random()));
			task.setStartAt(startAt);
			taskList.add(task);
		}

		Util.sortTasksByStartAt(taskList);
	}

}
