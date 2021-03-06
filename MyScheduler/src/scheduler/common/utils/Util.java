package scheduler.common.utils;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import scheduler.bean.AttributeSelectionBean;
import scheduler.bean.ProjectBean;
import scheduler.bean.StatusBean;
import scheduler.bean.TaskBean;
import scheduler.common.constant.Constant;
import scheduler.facade.TaskFacade;

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
    	List<TaskBean> taskBeanList = TaskFacade.getInstance().findByProjectCode(project.getProjectCode());

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



    /**
     * タスクを開始日順で並び替える
     * @param taskList
     * @return
     */
    public static List<TaskBean> sortTasksByStartAt(List<TaskBean> taskList){

    	for(int i=0;i<taskList.size();i++){
    		int k=i;
    		TaskBean tmp;

    		while( k+1<taskList.size() && Util.compareCalendarDate((tmp = taskList.get(k)).getStartAt(), taskList.get(k+1).getStartAt()) > 0){
    			taskList.set(k, taskList.get(k+1));
    			taskList.set(k+1, tmp);

    			int m=k;
        		while( m>0 && (tmp = taskList.get(m)).getStartAt().before(taskList.get(m-1).getStartAt())){
        			taskList.set(m, taskList.get(m-1));
        			taskList.set(m-1, tmp);
        			m--;
        		}
    			k++;
    		}
    	}

    	return taskList;
    }



    private static void printStartAtByArray(List<TaskBean> taskList){
    	for(TaskBean task : taskList){
		Calendar startAt = task.getStartAt();
    		System.out.printf("%d - %d - %d\n",startAt.get(Calendar.YEAR),startAt.get(Calendar.MONTH),startAt.get(Calendar.DAY_OF_MONTH));
    	}
    	System.out.println("");

    }


    public static void sort(double[] value){
    	printArrayValue(value);

    	for(int i=0;i<value.length;i++){
    		int k=i;
    		//>= ?
    		double tmp;
    		while( k+1<value.length && value[k] > value[k+1]){
    			tmp = value[k];
    			value[k] = value[k+1];
    			value[k+1] = tmp;

    			int m=k;
        		while( m>0 && value[m] < value[m-1]){
        			tmp = value[m];
        			value[m] = value[m-1];
        			value[m-1] = tmp;
        			m--;
        		}

    			k++;
    		}
    	}


    	printArrayValue(value);
    }


    private static void printArrayValue(double[] value){
    	for(double val : value){
    		System.out.printf("%.0f ",val);
    	}
    	System.out.println("");
    }




	/**
	 * 二つの日付の差の絶対値を返す
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getAbsOffsetOfDate(Calendar date1,Calendar date2){
		//System.out.println("getOffsetOfDate : date1="+getBarFormatCalendarValue(date1,true)+"  date2="+getBarFormatCalendarValue(date2,true));
		int diffOfDays = 0;
		Calendar tmpDate = (Calendar)date1.clone();

		int offset;
		while((offset = compareCalendarDate(tmpDate,date2)) != 0){
			//System.out.print("   ["+diffOfDays+"] tmpDate="+getBarFormatCalendarValue(tmpDate,true)+"  offset="+offset);
			diffOfDays++;
			// A.compareTo(B)はAのほうが大きい時に１が返るので、逆の方向に進めるためにー１をかける
			tmpDate.add(Calendar.DAY_OF_MONTH, offset*(-1));
			//System.out.println("   --> tmpDate="+getBarFormatCalendarValue(tmpDate,true));
		}
		return diffOfDays;
	}



	/**
	 * 二つの日付の差を返す
	 * @param date1
	 * @param date2
	 * @return date1の日付 - date2の日付 （年月の繰上りは考慮される）
	 * @throws Exception
	 */
	public static int getOffsetOfDate(Calendar date1,Calendar date2){
		//System.out.println("getOffsetOfDate : date1="+getBarFormatCalendarValue(date1,true)+"  date2="+getBarFormatCalendarValue(date2,true));
		int diffOfDays = 0;
		Calendar tmpDate = (Calendar)date1.clone();

		int offset;
		while((offset = compareCalendarDate(tmpDate,date2)) != 0){
			diffOfDays += offset;
			// A.compareTo(B)はAのほうが大きい時に１が返るので、逆の方向に進めるためにー１をかける
			tmpDate.add(Calendar.DAY_OF_MONTH, offset*(-1));
		}
		return diffOfDays;
	}



	public static void printCalendarValue(Calendar date){
		System.out.println(date.get(Calendar.YEAR)+"-"+(date.get(Calendar.MONTH)+1)+"-"+date.get(Calendar.DAY_OF_MONTH));
	}


	public static String getBarFormatCalendarValue(Calendar date,boolean addZero){
		return getFormatCalendarValue(date,"-",addZero);

	}


	public static Calendar copyCalendar(Calendar date){
		int 	year = date.get(Calendar.YEAR),
				month = date.get(Calendar.MONTH)+1,
				day = date.get(Calendar.DAY_OF_MONTH);
		Calendar copiedCalendar = Calendar.getInstance();
		copiedCalendar.set(year, month, day);

		return copiedCalendar;
	}


	private static String getFormatCalendarValue(Calendar date,String split,boolean addZero){
		if(date == null){
			return "  "+split+"  "+split+"  ";
		}
		int 	year = date.get(Calendar.YEAR),
				month = date.get(Calendar.MONTH)+1,
				day = date.get(Calendar.DAY_OF_MONTH);
		String 	monthValue = month+"",
				dayValue = day+"";
		if(addZero){
			if(month<10){
				monthValue = "0"+month;
			}
			if(day<10){
				dayValue = "0"+day;
			}
		}
		return year+split+monthValue+split+dayValue;
	}


	public static String getSlashFormatCalendarValue(Calendar date,boolean addZero){
		return getFormatCalendarValue(date,"/",addZero);
	}


	/**
	 * 二つの日付を比較する
	 * @param date1
	 * @param date2
	 * @return 年月日を比較して<ul><li>date1＜date2ならば-1 <li>date1=date2ならば0<li>date1＞date2ならば1<ul>
	 */
	public static int compareCalendarDate(Calendar date1,Calendar date2){

		//System.out.print("compareCalendarDate : date1="+getBarFormatCalendarValue(date1,true)+" date2="+getBarFormatCalendarValue(date2,true));

		int 	year1 = date1.get(Calendar.YEAR),
				year2 = date2.get(Calendar.YEAR),
				month1 = date1.get(Calendar.MONTH),
				month2 = date2.get(Calendar.MONTH),
				day1 = date1.get(Calendar.DAY_OF_MONTH),
				day2 = date2.get(Calendar.DAY_OF_MONTH);

		int res =  year1 < year2 ?
				-1 : year1 > year2 ?
						1 : month1 < month2 ?
								-1 : month1 > month2 ?
										1: day1 < day2 ?
												-1 : day1 > day2 ?
														1 : 0;
		//System.out.println("  --> res="+res);
		return res;
	}



	public static Calendar createCalendarByStringValue(String date) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTime(format.parse(date));

		return calendarDate;
	}


	public static Color createColorByString(String color){
		color = color.substring(0, 8);
		int colorCode = Integer.decode(color);
		int blue = colorCode%(256);
		int green = (colorCode/256)%(256);
		int red = (colorCode/(256*256))%(256);

		return  Color.rgb(red, green, blue);
	}




	/**
	 * [(code)] selection value の形のものからcodeだけを取り出す
	 * @param value
	 * @return
	 */
	public static  String decodeSelectionCodeFromSelection(String selectedValue,int lengthOfCode){
		if(selectedValue == null){
			return "";
		}
		return selectedValue.substring(1,lengthOfCode+1);

	}


	/**
	 * [(code)] selection valueの形の選択表示を作成する
	 * @param selectedValue
	 * @return
	 */
	public static  String createSelection(AttributeSelectionBean selectedValue){
		return createSelection(selectedValue.getSelectionCode(), selectedValue.getDispName());
	}


	public static  String createSelection(String code,String value){
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(code);
		builder.append("] ");
		builder.append(value);
		return builder.toString();
	}


	public static  String createSelection(StatusBean status){
		return createSelection(status.getCode(), status.getTitle());
	}



	/**
	 * 指定したURLのfxmlファイルを読み込んだローダーを取得する
	 * @param url fxmlのURL
	 * @return
	 */
	public static FXMLLoader createProjectFxmlLoader(String url){
		URL location = ClassLoader.getSystemResource(url);
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        fxmlLoader.setRoot( new VBox());

        return fxmlLoader;
	}



	public static Color createHighLightColor(Color color){
		double blue = color.getBlue();
		double red = color.getRed();
		double green = color.getGreen();

		Color highLightColor = Color.rgb(
				Math.min(255, (int)(red*255)+5),
				Math.min(255, (int)(green*255)+5),
				Math.min(255, (int)(blue*255)+5));

		return highLightColor;
	}

	public static Color createClickedColor(Color color){

		int diff = 13;
		double[] colorValues = {color.getRed(),color.getGreen(),color.getBlue()};
		double max = colorValues[0];
		int maxIndex = 0;
		for(int index=0;index<colorValues.length;index++){
			if(colorValues[index] > max){
				maxIndex = index;
				max = colorValues[index];
			}
		}
		colorValues[maxIndex] += diff/255.0;

		Color clickedColor = Color.rgb(
				Math.max(0, (int)(colorValues[0]*255)-diff),
				Math.max(0, (int)(colorValues[1]*255)-diff),
				Math.max(0, (int)(colorValues[2]*255)-diff));

		return clickedColor;
	}


	public static double getAttributePartWidth(double width){
		double attributeWidth = Math.min(width*(1-Constant.DEFAULT_RATE_OF_CALENDAR_WIDTH), Constant.MAX_ATTRIBUTE_WIDTH);
		attributeWidth = Math.max(attributeWidth, Constant.MIN_ATTRIBUTE_WIDTH);

		return attributeWidth;
	}









}
