package com.sh.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTool {

	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	public static Date formatString(String str,String format) throws Exception{
		
		if(StringTool.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
		
	}
	
	public static String getCurrentDateStr() throws Exception{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(date);
	}
   
   /**
    * yyyy-MM-dd HH:mm:ss
    * @return 
    */
   public static String getTime(){
	   
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	     String time=formatter.format(new Date());
	   
	   return time;
	   
   }
   /**
    * yyyy-MM-dd
    * @return
    */
   public static String getDate(){
       
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
       String time=formatter.format(new Date());
       return time;
       
   }

    /**
     * 获取自定义时间
     * @return
     */
    public static String getTime(String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String time=formatter.format(new Date());
        return time;
    }

   /**
    * 返回星期数
    * @Description: TODO
    * @param @return   
    * @return int  
    * @throws
    * @author micoMo
    * @date 2017-5-12
    */
   public static int getWeek(){
	   Calendar c = Calendar.getInstance();
	   // 今天是一周中的第几天
	   int dayOfWeek = c.get(Calendar.DAY_OF_WEEK );
	   return dayOfWeek;
   }
   
   /**
    * 返回本周七天的日期
    * @Description: TODO
    * @param @return   
    * @return String[]  
    * @throws
    * @author micoMo
    * @date 2017-5-12
    */
   public static String[] getWeekDays(){
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	   Calendar c = Calendar.getInstance();
	   // 今天是一周中的第几天
	   int dayOfWeek = c.get(Calendar.DAY_OF_WEEK );
	   if (c.getFirstDayOfWeek() == Calendar.SUNDAY) {
	       c.add(Calendar.DAY_OF_MONTH, 1);
	   }
	   // 计算一周开始的日期
	   c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
	   String[] weekDays = new String[7]; 
	   for (int i=0;i<7;i++) {
	       c.add(Calendar.DAY_OF_MONTH, 1);
	       weekDays[i] = sdf.format(c.getTime());
	   }
	   return weekDays;
   }
   /** 
    * 获取过去第几天的日期 
    * 
    * @param past 
    * @return 
    */  
   public static String getPastDate(int past) {  
       Calendar calendar = Calendar.getInstance();  
       calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);  
       Date today = calendar.getTime();  
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
       String result = format.format(today);  
       return result;  
   }  
   /** 
    * 获取未来第几天的日期 
    * 
    * @param past 
    * @return 
    */  
   public static String getDateAfter(int past) {  
       Calendar calendar = Calendar.getInstance();  
       calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);  
       Date today = calendar.getTime();  
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
       String result = format.format(today);  
       return result;  
   }

   //获取datestr后past天的日期
    public static String getTheDateAfter(String datestr, int past) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d = format.parse(datestr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        String result = format.format(today);
        return result;
    }
    /**
    * 获取相差时间,以分为单位
    * @param starttime
    * @param endtime
    * @return
    * @throws ParseException
    */
   public static int getMinute(String starttime,String endtime) throws ParseException{
	   
	   SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
	   SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
	   long time1=format1.parse(starttime).getTime();
	   long time2=format2.parse(endtime).getTime();
	   int minute=(int) ((time2-time1)/(1000*60));
	   return minute;
   }
   
   /**
    * 获取相差时间,以天为单位
    * @param starttime
    * @param endtime
    * @return
    * @throws ParseException
    */
   public static int getDays(String starttime,String endtime) throws ParseException{
	   
	   SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
       SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	   long time1=format1.parse(starttime).getTime();
	   long time2=format2.parse(endtime).getTime();
	   int days=(int) ((time2-time1)/(1000 * 60 * 60 * 24));
	   return days;
   }
   
   
   /**
    * 获取相差时间,以天为单位
    * @param startdate
    * @param enddate
    * @return
    * @throws ParseException
    */
   public static int getCompareDays(Date startdate,Date enddate) throws ParseException{
	   
	   SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
       SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	   long time1=startdate.getTime();
	   long time2=enddate.getTime();
	   int days=(int) ((time2-time1)/(1000 * 60 * 60 * 24));
	   return days;
   }
   
   /**
    * 时间相加
    * @param starttime
    * @param intervaltime
    * @return
    * @throws ParseException
    */
   public static String addMinute(String starttime,int intervaltime) throws ParseException{
	 
	 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	 int b=intervaltime*60*1000;
	 long result = sdf.parse(starttime).getTime() + (b);
	 Date d1 = new Date(result);
	 return sdf.format(d1).toString();
   }
   
   /**
    * 获取今天是星期几
    * @param dt
    * @return
    */
   public static int getWeekOfDate(Date dt) {
       int []day={0,1,2,3,4,5,6};
       Calendar cal = Calendar.getInstance();
       cal.setTime(dt);

       int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
       if (w < 0)
           w = 0;

       return day[w];
   }
   
   
	/** 
	* 时间加减月数
	* @param startDate 要处理的时间，Null则为当前时间 
	* @param months 加减的月数 
	* @return Date 
	*/  
	public static Date dateAddMonths(Date startDate, int months) {  
        if (startDate == null) {  
            startDate = new Date();  
        }  
        Calendar c = Calendar.getInstance();  
        c.setTime(startDate);  
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + months);  
        return c.getTime();  
    }
	

    /** 
     * 时间比较（如果myDate>compareDate返回1，<返回-1，相等返回0） 
     * @param myDate 时间 
     * @param compareDate 要比较的时间 
     * @return int 
     */  
    public static int dateCompare(Date myDate, Date compareDate) {  
        Calendar myCal = Calendar.getInstance();  
        Calendar compareCal = Calendar.getInstance();  
        myCal.setTime(myDate);  
        compareCal.setTime(compareDate);  
        return myCal.compareTo(compareCal);  
    }
   
   public static void main(String[] args) throws ParseException {
//	     Date date1=new Date();
//       Calendar calendar = Calendar.getInstance();
//       calendar.setTime(date1);
//       calendar.add(calendar.DATE, -1);
//       date1 = calendar.getTime();
//       SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
//       String yesterday = format.format(date1);

       System.out.println("===="+getTheDateAfter("2018-08-26", 1024));
   }
   
   
}
