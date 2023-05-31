package service.lt.common.util.date;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 *
 * @author 芋道源码
 */
public class DateUtils {

    /**
     * 时区 - 默认
     */
    public static final String TIME_ZONE_DEFAULT = "GMT+8";

    /**
     * 秒转换成毫秒
     */
    public static final long SECOND_MILLIS = 1000;

    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static Date addTime(Duration duration) {
        return new Date(System.currentTimeMillis() + duration.toMillis());
    }

    public static boolean isExpired(Date time) {
        return System.currentTimeMillis() > time.getTime();
    }

    public static long diff(Date endTime, Date startTime) {
        return endTime.getTime() - startTime.getTime();
    }

    /**
     * 创建指定时间
     *
     * @param year        年
     * @param mouth       月
     * @param day         日
     * @return 指定时间
     */
    public static Date buildTime(int year, int mouth, int day) {
        return buildTime(year, mouth, day, 0, 0, 0);
    }

    /**
     * 创建指定时间
     *
     * @param year        年
     * @param mouth       月
     * @param day         日
     * @param hour        小时
     * @param minute      分钟
     * @param second      秒
     * @return 指定时间
     */
    public static Date buildTime(int year, int mouth, int day,
                                 int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mouth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0); // 一般情况下，都是 0 毫秒
        return calendar.getTime();
    }

    public static Date max(Date a, Date b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return a.compareTo(b) > 0 ? a : b;
    }

    public static boolean beforeNow(Date date) {
        return date.getTime() < System.currentTimeMillis();
    }

    public static boolean afterNow(Date date) {
        return date.getTime() >= System.currentTimeMillis();
    }

    /**
     * 计算当期时间相差的日期
     *
     * @param field  日历字段.<br/>eg:Calendar.MONTH,Calendar.DAY_OF_MONTH,<br/>Calendar.HOUR_OF_DAY等.
     * @param amount 相差的数值
     * @return 计算后的日志
     */
    public static Date addDate(int field, int amount) {
        return addDate(null, field, amount);
    }

    /**
     * 计算当期时间相差的日期
     *
     * @param date   设置时间
     * @param field  日历字段 例如说，{@link Calendar#DAY_OF_MONTH} 等
     * @param amount 相差的数值
     * @return 计算后的日志
     */
    public static Date addDate(Date date, int field, int amount) {
        if (amount == 0) {
            return date;
        }
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }
        c.add(field, amount);
        return c.getTime();
    }
    //获取两个日期的天数差
    public static int getQuot(String time1, String time2) {
        long quot = 0;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = ft.parse(time1);
            Date date2 = ft.parse(time2);
            quot = date2.getTime() - date1.getTime();
            quot = quot / 1000 / 60 / 60 / 24;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(String.valueOf(quot));
    }

    //判断是否是一整年
    public static boolean isOneYear(Date start, Date end) throws ParseException {
        Calendar startday = Calendar.getInstance();
        Calendar endday = Calendar.getInstance();
        startday.setTime(start);
        endday.setTime(dealDays(end,1));
        if (startday.after(endday)) {
            return false;
        }
        long sl = startday.getTimeInMillis();
        long el = endday.getTimeInMillis();
        long days = ((el - sl) / (1000 * 60 * 60 * 24));
        if (days == 365 || days == 366) {
            if (startday.get(Calendar.MONTH) <= 1) {
                startday.set(Calendar.MONTH, 1);
                int lastDay = startday.getActualMaximum(Calendar.DAY_OF_MONTH);
                return (lastDay == 28 && days == 365) || (lastDay == 29 && days == 366);
            } else {
                endday.set(Calendar.MONTH, 1);
                int lastDay = endday.getActualMaximum(Calendar.DAY_OF_MONTH);
                return (lastDay == 28 && days == 365) || (lastDay == 29 && days == 366);
            }
        } else {
            return false;
        }
    }


    /**
     * 计算日期所在月份的天数
     * @param date 日期
     * @date 2022/11/4 8:47
     * @return int
     */
    public static int getMonthDays(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String str=sdf.format(date);
        int month = ca.get(Calendar.MONTH)+1;//第几个月
        int year = ca.get(Calendar.YEAR);//年份数值
        int days = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            //定义31天的月份。
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            //定义30天的月份。
            case 2:
                days = year % 400 == 0 || year % 4 == 0 && year % 100 != 0 ? 29 : 28;
                //此行命令为计算闰年的公式！！！***
                LocalDate now = LocalDate.of(year, month, 1);
                days = now.isLeapYear() ? 29 : 28;
                break;
            //定义2月的天数
            default:
                days = 0;
                break;

        }
        return days;
    }

    //计算两个日期之间的月份数
    public static int getMonthCount(Date startDate,Date endDate){
        long daysBetween = ChronoUnit.MONTHS.between(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return  (int)daysBetween;

    }
    //计算两个日期之间的月份数(忽略日期,只看年月)
    public static Integer getMonths(String date1,String date2){
        int year1=Integer.parseInt(date1.substring(0,4));
        int year2=Integer.parseInt(date2.substring(0,4));
        int month1=Integer.parseInt(date1.substring(5,7));
        int month2=Integer.parseInt(date2.substring(5,7));

        return  (year2-year1)*12+(month2-month1);
    }




    /**
     * 在日期date上增加amount天 。
     *
     * @param date   处理的日期，非null
     * @param amount 要加的天数(正数)/要减去的天数(负数)
     */
    public static Date dealDays(Date date, int amount) throws ParseException {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + amount);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = simpleDateFormat.format(now.getTime());

        return now.getTime();
    }
    /**
     * 获取n个月后的月末日期
     *
     * @param date   处理的日期，非null
     * @param amount 要加的天数(正数)/要减去的天数(负数)
     */
    public static Date getLaseMonthDay(Date date, int amount) throws ParseException {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int day = ca.get(Calendar.DAY_OF_MONTH);//第几天
        if(day==1){
            amount-=1;
        }
        ca.add(Calendar.MONTH,amount); //获取当前时间的后3个月

        int cc=getMonthDays(ca.getTime());
        int newDay = ca.get(Calendar.DAY_OF_MONTH);//第几天

       return     dealDays(ca.getTime(),cc-newDay);

    }
    public static Date  getAfterMonth(String inputDate,int number) {
        Calendar c = Calendar.getInstance();//获得一个日历的实例
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try{
            date = sdf.parse(inputDate);//初始日期
        }catch(Exception e){

        }
        c.setTime(date);//设置日历时间
        c.add(Calendar.MONTH,number);//在日历的月份上增加多少月
        String strDate = sdf.format(c.getTime());//的到你想要得多少个月后的日期
        date=sdf.parse(strDate,new ParsePosition(0));
        return date;
    }

    //获取下个月1号
    public static Date TimeTools(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);
        int year  = calendar.get(Calendar.YEAR); //获取年
        int month = calendar.get(Calendar.MONTH) + 2; //获取月
        int day = 1;//获取日
        if (month > 12){
            month=1;
            year = year+1;
        }
        String newDate = year +"-"+month +"-"+day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sdf.parse(newDate);
        return  parse;
    }
    /**
     * 获取两个日期相差的月数(相隔)
     */
    public static int getMonthDiff(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);

        // 获取年的差值
        int yearInterval = year2 - year1;
        // 获取月数差值
        int monthInterval = month2 - month1 - 1;
        monthInterval %= 12;
        int monthsDiff = Math.abs(yearInterval * 12 + monthInterval);
        return monthsDiff;
    }
    public static Date setDay(Date date,int day){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE,day);
        return c.getTime();
    }



    /**
     * 计算2个日期之间的工作日数
     * @param d1 日期一
     * @param d2 日期二
     * @return int 天数
     */
    public static int getWorkingDay(java.util.Calendar d1, java.util.Calendar d2) {
        int result = -1;

        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            java.util.Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }

        int charge_start_date = 0;//开始日期的日期偏移量
        int charge_end_date = 0;//结束日期的日期偏移量

        // 日期不在同一个日期内
        int stmp;
        int etmp;

        stmp = 7 - d1.get(Calendar.DAY_OF_WEEK);
        etmp = 7 - d2.get(Calendar.DAY_OF_WEEK);

        if (stmp != 0 && stmp != 6) {// 开始日期为星期六和星期日时偏移量为0
            charge_start_date = stmp - 1;
        }

        if (etmp != 0 && etmp != 6) {// 结束日期为星期六和星期日时偏移量为0
            charge_end_date = etmp - 1;
        }

        result = (getDaysBetween(getNextMonday(d1), getNextMonday(d2)) / 7) * 5 + charge_start_date - charge_end_date;
        return result;
    }

    /**
     * 获取周末日期数量
     * @param d1 日期一
     * @param d2 日期二
     * @return int 天数
     */
    public static int getHolidays(Calendar d1, Calendar d2){
        return getDaysBetween(d1, d2)-getWorkingDay(d1, d2);
    }

    /**
     * 获得日期的下一个星期一的日期
     * @param date 日期
     * @return Calendar
     */
    public static Calendar getNextMonday(Calendar date) {
        Calendar result = null;
        result = date;
        do {
            result = (Calendar) result.clone();
            result.add(Calendar.DATE, 1);
        } while (result.get(Calendar.DAY_OF_WEEK) != 2);
        return result;
    }

    /**
     * 获取俩个日期中间的天数
     * @param d1 日期一
     * @param d2 日期二
     * @author hm
     * @date 2022/10/24 17:31
     * @return int 天数
     */
    public static int getDaysBetween(java.util.Calendar d1, java.util.Calendar d2) {
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            java.util.Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }

        int days = d2.get(java.util.Calendar.DAY_OF_YEAR) - d1.get(java.util.Calendar.DAY_OF_YEAR);

        int y2 = d2.get(java.util.Calendar.YEAR);
        if (d1.get(java.util.Calendar.YEAR) != y2) {
            d1 = (java.util.Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
                d1.add(java.util.Calendar.YEAR, 1);
            } while (d1.get(java.util.Calendar.YEAR) != y2);
        }
        return days;
    }

    /**
     * 获取两个日期之间的所有月份 (年月日默认一号)
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @return List<String>
     */
    public static List<String> getMonthBetweenDate(String startTime, String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime()<=endDate.getTime()){
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.MONTH, 1);

                // 获取增加后的日期
                startDate=calendar.getTime();
            }

            // 循环处理 - 主要处理开始结束时间，用于之后的计算当月的营业时间
            for (int i = 0; i < list.size(); i++) {
                if(startTime.contains(list.get(i)) || endTime.contains(list.get(i))){
                    if(startTime.contains(list.get(i))){
                        list.set(i,list.get(i) + "-" +startTime.split("-")[2]);
                    }

                    if(endTime.contains(list.get(i))){
                        list.set(i, list.get(i) + "-" + endTime.split("-")[2]);
                    }
                }else{
                    list.set(i,list.get(i)+"-01");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 获取当月最后一天
     * @param date 日期
     * @author hm
     * @date 2022/11/7 9:51
     * @return Date
     */
    public static Date getMonthLastDay(Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        //把日期增加一天
        ca.add(Calendar.MONTH, -1);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        return format.parse(last);
    }

    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        String str="2021-01-01";
//        String str2="2021-07-14";
//        Date parse1 = sdf.parse(str);
//        System.out.println(setDay(parse1,15));

//        String startStr = "2022-01-15";
//        String endStr = "2023-02-14";
//        List<String> list = getMonthBetweenDate(startStr, endStr);
//        System.out.println(list);


//        Date monthLastDay = getMonthLastDay("2022-03-15");
//        System.out.println("monthLastDay = " + monthLastDay);


    }
    /**
     * 计算2个日期之间相差的  相差多少年月日
     * 比如：2011-02-02 到  2017-03-02 相差 6年，1个月，0天
     * @param fromDate
     * @param toDate
     * @return
     */
    public static int[] dayComparePrecise(Date fromDate,Date toDate){
        Calendar  from  =  Calendar.getInstance();
        from.setTime(fromDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(toDate);

        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int fromDay = from.get(Calendar.DAY_OF_MONTH);

        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int toDay = to.get(Calendar.DAY_OF_MONTH);
        int year = toYear  -  fromYear;
        int month = toMonth  - fromMonth;
        int day = toDay  - fromDay;
        return new int[]{year,month,day};
    }
    /**
     * 判断当前时间是否为1号
     * @Param date 传null为当前日期
     * @return
     */
    public static Boolean isFirstDay(Date date){
        Calendar cal = Calendar.getInstance();
        if(null != date) {
            cal.setTime(date);
        }
        int data = cal.get(Calendar.DATE);
        return data == 1;
    }

    /**
     * 判断给定日期是否是当月的最后一天
     * @param date
     * @return
     */
    public static boolean isLastDayOfMonth(Date date) {
        //1、创建日历类
        Calendar calendar = Calendar.getInstance();
        //2、设置当前传递的时间，不设就是当前系统日期
        calendar.setTime(date);
        //3、data的日期是N，那么N+1【假设当月是30天，30+1=31，如果当月只有30天，那么最终结果为1，也就是下月的1号】
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        //4、判断是否是当月最后一天【1==1那么就表明当天是当月的最后一天返回true】
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断给定日期是否是闰年
     * @param
     * @return
     */
    public  static Boolean isRunYear(Date date){
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String str=sdf.format(date);
        int year = ca.get(Calendar.YEAR);//年份数值

        if(year%4==0 && year%100!=0) {
            return true;
        }else if(year%400==0) {
            return true;
        }else {
            return false;
        }

    }

}
