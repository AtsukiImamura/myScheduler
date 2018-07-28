package scheduler.common.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import scheduler.bean.ProjectBean;
import scheduler.bean.TaskBean;

public class Util {

	public static void log(String value){
		Date date = new Date();
		System.out.println("["+date.toString()+"] "+calledFrom()+"  :  "+value);
	}

	public static void log(){
		log("");
	}


    /**
     * このメソッドを呼び出したメソッドの呼び出し元のメソッド名、ファイル名、行数の情報を取得します。
     *
     * @return メソッド名、ファイル名、行数の情報文字列
     */
    public static String calledFrom() {
        StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
        if (steArray.length <= 3) {
            return "";
        }
        StackTraceElement ste = steArray[3];
        StringBuilder sb = new StringBuilder();
        sb.append(ste.getMethodName())        // メソッド名取得
            .append("(")
            .append(ste.getFileName())        // ファイル名取得
            .append(":")
            .append(ste.getLineNumber())    // 行番号取得
            .append(")");
        return sb.toString();
    }


    /**
     * 案件を表示するのに必要な行数を返す
     * @param project 案件
     * @return 案件を表示するのに必要な行数
     */
    public static int getNumOfNecessaryColumns(ProjectBean project){
    	List<TaskBean> taskBeanList = project.getTaskBeanList();

    	int numOfNecessaryColumns = 1;
    	for(TaskBean task : taskBeanList){
    		Calendar startAt = task.getStartAt();
    		Calendar finishAt = task.getFinishAt();
    		Calendar tmpCalendar = (Calendar) startAt.clone();
    		int tmpNumOfNecessaryColumns;
    		while(tmpCalendar.before(finishAt)){
    			tmpNumOfNecessaryColumns = getMaxNecessaryColumns(taskBeanList,tmpCalendar);
    			if(tmpNumOfNecessaryColumns >numOfNecessaryColumns ){
    				numOfNecessaryColumns = tmpNumOfNecessaryColumns;
    			}
    			tmpCalendar.add(Calendar.DAY_OF_MONTH, 1);
    		}

    	}

    	return numOfNecessaryColumns;
    }


    /**
     * タスクを開始日順で並び替える
     * @param taskList
     * @return
     */
    public static List<TaskBean> sortTasksByStartAt(List<TaskBean> taskList){

    	return new ArrayList<TaskBean>();
    }



    /**
     * タスクのリストの中で指定した日付を含むものがいくつあるかを返す
     * @param taskBeanList タスクのリスト
     * @param cal 指定する日付
     * @return タスクのリストの中で指定した日付を含むものの数
     */
    private static int getMaxNecessaryColumns(List<TaskBean> taskBeanList,Calendar cal){
    	int numOfNecessaryColumns = 0;
    	for(TaskBean task : taskBeanList){
    		if(task.isInPeriod(cal)){
    			numOfNecessaryColumns++;
    		}
    	}
    	return numOfNecessaryColumns;
    }















}
