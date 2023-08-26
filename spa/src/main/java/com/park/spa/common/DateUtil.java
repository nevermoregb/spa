package com.park.spa.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {

    public static final  int YEAR       = 1;
    public static final  int MONTH      = 2;
    public static final  int DATE       = 3;
    public static final  int MonTHFIRST = 4;
    public static final  int MONTHEND   = 5;

    /**
     * yyyyMMddHHmmssSSS 포맷으로 현재 날짜시간을 리턴한다.
     *
     * 예)
     * getTranscationTimestamp()
     *
     * @return
     */
    public static String getTranscationTimestamp() {
        return getCurrentDate("yyyyMMddHHmmssSSS");
    }

    /**
     * 전달 받은 파라미터 포맷으로 현재 날짜시간을 리턴한다.
     *
     * 예)
     * getCurrentDate("yyyyMMddHHmmssSSS");
     *
     * @param pattern
     * @return
     */
    public static String getCurrentDate(String pattern) {
        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        return formatter.format(today);
    }

    /**
     * yyyyMMdd 포맷으로 현재 날짜를 리턴한다.
     *
     * 예)
     * getCurrentDate("yyyyMMdd")
     * > 20190605
     *
     * @return
     */
    public static String getCurrentDate() {
        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyyMMdd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        return formatter.format(today);
    }

    /**
     * yyyyMMddHHmmss 포맷으로 현재 날짜시간을 리턴한다.
     *
     * 예)
     * getCurrentDateTime()
     *
     * @return
     */
    public static String getCurrentDateTime() {
        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        return formatter.format(today);
    }

    /**
     * HHmmss 포맷으로 현재 시간을 리턴한다.
     *
     * 예)
     * getCurrentTime()
     * > 125859
     *
     * @return
     */
    public static String getCurrentTime() {
        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "HHmmss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        return formatter.format(today);
    }

    /**
     * 지정한 년월의 지정한 주의 월요일 날짜와 함수를 실행하는 현재 시간을 리턴한다.
     *
     * 예)
     * getWeekToDay("201901", 4, "yyyy-MM-DD hh:mm:ss")
     * > 2019-01-21 03:22:35
     *
     * @param yyyymm
     * @param week
     * @param pattern
     * @return
     */
    public static String getWeekToDay(String yyyymm, int week, String pattern) {

        Calendar cal = Calendar.getInstance(Locale.FRANCE);

        int new_yy = Integer.parseInt(yyyymm.substring(0,4));
        int new_mm = Integer.parseInt(yyyymm.substring(4,6));
        int new_dd = 1;

        cal.set(new_yy,new_mm-1,new_dd);

        // 임시 코드
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            week = week - 1;
        }

        cal.add(Calendar.DATE, (week-1)*7+(cal.getFirstDayOfWeek()-cal.get(Calendar.DAY_OF_WEEK)));

        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.KOREA);

        return formatter.format(cal.getTime());
    }

    /**
     * 기준일로부터 field에 지정한 년 or 월 or 날짜에 계산값을 더한 날짜를 'yyyymmdd'포멧으로 리턴한다.
     *
     * 예)
     * getOpDate(5, 3, "20190618")
     * > 20190621
     * getOpDate(5, -5, "20190618")
     * > 20190613
     *
     * @param field (YEAR=1, MONTH=2, DATE=5)
     * @param amount (계산값)
     * @param date (계산 기준일)
     * @return
     */
    public static String getOpDate(int field, int amount, String date) {

         GregorianCalendar calDate = getGregorianCalendar(date);

         if (field == Calendar.YEAR) {
             calDate.add(GregorianCalendar.YEAR, amount);
         } else if (field == Calendar.MONTH) {
             calDate.add(GregorianCalendar.MONTH, amount);
         } else {
             calDate.add(GregorianCalendar.DATE, amount);
         }

         return getYyyymmdd(calDate);
    }


    /**
     * 기준일에 계산값을 더한 결과일의 요일을 Calendar상수값으로 리턴한다.
     *
     * 예)
     * getWeek("20190618", -2)
     * > 1
     * getWeek("20190618", 2)
     * > 5
     *
     * @param yyyymmdd (계산 기준일)
     * @param addDay (계산값)
     * @return 1 : Calendar.SUNDAY
     *         2 : Calendar.MONDAY
     *         3 : Calendar.TUESDAY
     *         4 : Calendar.WEDNESDAY
     *         5 : Calendar.THURSDAY
     *         6 : Calendar.FRIDAY
     *         7 : Calendar.SATURDAY
     */
    public static int getWeek(String yyyymmdd, int addDay){
      Calendar cal = Calendar.getInstance(Locale.KOREA);
      int new_yy = Integer.parseInt(yyyymmdd.substring(0,4));
      int new_mm = Integer.parseInt(yyyymmdd.substring(4,6));
      int new_dd = Integer.parseInt(yyyymmdd.substring(6,8));

      cal.set(new_yy,new_mm-1,new_dd);
      cal.add(Calendar.DATE, addDay);

      int week = cal.get(Calendar.DAY_OF_WEEK);
      return week;
    }


    /**
     * 지정한 년월의 마지막 날자를 구해서 리턴한다.
     * 예)
     * getLastDayOfMon(2019, 5)
     * > 31
     *
     * @param year
     * @param month
     * @return
     */
    public static int getLastDayOfMon(int year, int month) {

        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, 1);

        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);

    }

    /**
     * 지정한 년월의 마지막 날자를 구해서 리턴한다.
     *
     * 예)
     * getLastDayOfMon("201902")
     * > 28
     *
     * @param yyyymm
     * @return
     */
    public static int getLastDayOfMon(String yyyymm) {

        Calendar cal = Calendar.getInstance();
        int yyyy = Integer.parseInt(yyyymm.substring(0, 4));
        int mm = Integer.parseInt(yyyymm.substring(4)) - 1;

        cal.set(yyyy, mm, 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 파라미터로 전달한 년월일이 유효여부를 체크해서 리턴한다.
     *
     * 예)
     * isCorrect("20190230")
     * > false
     *
     * @param yyyymmdd
     * @return
     */
    public static boolean isCorrect(String yyyymmdd) {

        boolean flag  =  false;
        if(yyyymmdd.length() < 8 ) return false;
        try {
             int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
             int mm   = Integer.parseInt(yyyymmdd.substring(4, 6));
             int dd   = Integer.parseInt(yyyymmdd.substring(6));
             flag     = DateUtil.isCorrect( yyyy,  mm,  dd);
        } catch(IndexOutOfBoundsException e) {
            return false;
        } catch(NumberFormatException e) {
            return false;
        } catch(Exception ex) {
            return false;
        }

        return flag;
    }

    /**
     * 파라미터로 전달한 년월일이 유효여부를 체크해서 리턴한다.
     *
     * 예)
     * isCorrect(2020, 2, 29)
     * > true
     *
     * @param yyyy
     * @param mm
     * @param dd
     * @return
     */
    public static boolean isCorrect(int  yyyy, int mm, int dd) {
        if(yyyy < 0 || mm < 0 || dd < 0) return false;
        if(mm > 12 || dd > 31) return false;

        String year     = "" + yyyy;
        String month    = "00" + mm;
        String year_str = year + month.substring(month.length() - 2);
        int endday      = DateUtil.getLastDayOfMon(year_str);

        if(dd > endday) return false;

        return true;
    }


    /**
     * 지정한 패턴에 맞춰 현재날짜를 리턴한다.
     *
     * 예)
     * getThisDay("yyyymmddhhmm")
     * > 201906181659
     *
     * @param type "yyyymmdd"
     *             "yyyymmddhh"
     *             "yyyymmddhhmm"
     *             "yyyymmddhhmmss"
     *             "yyyymmddhhmmssms"
     *
     * @return
     */
    public static String getThisDay(String type) {
        Date date = new Date();
        SimpleDateFormat sdf = null;

        try{
            if("yyyymmdd".equals(type.toLowerCase())) {
                sdf = new SimpleDateFormat("yyyyMMdd");
                return sdf.format(date);
            }
            if("yyyymmddhh".equals(type.toLowerCase())) {
                sdf = new SimpleDateFormat("yyyyMMddHH");
                return sdf.format(date);
            }
            if("yyyymmddhhmm".equals(type.toLowerCase())) {
                sdf = new SimpleDateFormat("yyyyMMddHHmm");
                return sdf.format(date);
            }
            if("yyyymmddhhmmss".equals(type.toLowerCase())) {
                sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                return sdf.format(date);
            }
            if("yyyymmddhhmmssms".equals(type.toLowerCase())) {
                sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                return sdf.format(date);
            } else {
                sdf = new SimpleDateFormat(type);
                return sdf.format(date);
            }
        } catch(NullPointerException e) {
            return "ERROR";
        } catch(Exception e) {
            return "ERROR";
        }
    }

    /**
     * 지정한 날짜를 "yyyy년 mm월 dd일"패턴으로 바꿔 리턴한다.
     *
     * 예)
     * changeDateFormat("20190618")
     * > 2019년 06월 18일
     *
     * @param yyyymmdd
     * @return
     */
    public static String changeDateFormat(String yyyymmdd) {
        String rtnDate=null;

        String yyyy = yyyymmdd.substring(0, 4);
        String mm   = yyyymmdd.substring(4,6);
        String dd   = yyyymmdd.substring(6,8);
        rtnDate=yyyy+"년 "+mm + "월 "+dd + "일";

        return rtnDate;
    }

    /**
     * 지정한 날짜를 지정한 문자로 구분지어 리턴한다.
     *
     * 예)
     * changeDateFormat("20190618", "-");
     * > 2019-06-18
     *
     * @param yyyymmdd
     * @param format
     * @return
     */
    public static String changeDateFormat(String yyyymmdd, String format) {
        String rtnDate=null;

        String yyyy = yyyymmdd.substring(0, 4);
        String mm   = yyyymmdd.substring(4,6);
        String dd   = yyyymmdd.substring(6,8);
        rtnDate= yyyy + format + mm + format + dd;

        return rtnDate;
    }


    /**
     * 시작일로부터 종료일까지의 차이를 구해 리턴한다.
     *
     * 예)
     * getDifferDays("20190618", "20190613")
     * > -5
     * getDifferDays("20190618", "20190620")
     * > 2
     *
     * @param startDate 시작일
     * @param endDate   종료일
     * @return
     */
    public static long getDifferDays(String startDate, String endDate) {

        GregorianCalendar StartDate = getGregorianCalendar(startDate);
        GregorianCalendar EndDate = getGregorianCalendar(endDate);
        long difer = (EndDate.getTime().getTime() - StartDate.getTime().getTime()) / 86400000;
        return difer;
    }


    /**
     * 현재 날짜의 요일을 Calendar상수값으로 리턴한다.
     *
     * 예)
     * getDayOfWeek()
     * > 3
     *
     * @return 1 : Calendar.SUNDAY
     *         2 : Calendar.MONDAY
     *         3 : Calendar.TUESDAY
     *         4 : Calendar.WEDNESDAY
     *         5 : Calendar.THURSDAY
     *         6 : Calendar.FRIDAY
     *         7 : Calendar.SATURDAY
     */
    public static int getDayOfWeek(){
        Calendar rightNow = Calendar.getInstance();
        int day_of_week = rightNow.get(Calendar.DAY_OF_WEEK);
        return day_of_week;
    }


    /**
     * 현재날짜가 년의 몇번째 주인지 리턴한다.
     *
     * 예)
     * getWeekOfYear()
     * > 25
     *
     * @return
     */
    public static int getWeekOfYear(){
        Locale LOCALE_COUNTRY = Locale.KOREA;
        Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
        int week_of_year = rightNow.get(Calendar.WEEK_OF_YEAR);
        return week_of_year;
    }

    /**
     * 현재날짜가 달의 몇번째 주인지 리턴한다.
     *
     * 예)
     * getWeekOfMonth()
     * > 4
     *
     * @return
     */
    public static int getWeekOfMonth(){
        Locale LOCALE_COUNTRY = Locale.KOREA;
        Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
        int week_of_month = rightNow.get(Calendar.WEEK_OF_MONTH);
        return week_of_month;
    }

    /**
     * 지정한 날짜를 Calendar객체로 변경해서 리턴한다.
     *
     * 예)
     * getCalendarInstance("20190618")
     * > java.util.GregorianCalendar[time=?,areFieldsSet=false,areAllFieldsSet=true,lenient=true,
     *   zone=sun.util.calendar.ZoneInfo[id="Asia/Seoul",offset=32400000,dstSavings=0,useDaylight=false,transitions=14,lastRule=null],
     *   firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2019,MONTH=6,WEEK_OF_YEAR=25,WEEK_OF_MONTH=4,
     *   DAY_OF_MONTH=18,DAY_OF_YEAR=169,DAY_OF_WEEK=3,DAY_OF_WEEK_IN_MONTH=3,
     *   AM_PM=1,HOUR=5,HOUR_OF_DAY=17,MINUTE=26,SECOND=41,MILLISECOND=335,ZONE_OFFSET=32400000,DST_OFFSET=0]
     *
     * @param p_date
     * @return
     */
    public static Calendar getCalendarInstance(String p_date){

        Locale LOCALE_COUNTRY = Locale.KOREA;
        Calendar retCal = Calendar.getInstance(LOCALE_COUNTRY);

        if(p_date != null && p_date.length() == 8){
            int year  = Integer.parseInt(p_date.substring(0,4));
            int month = Integer.parseInt(p_date.substring(4,6));
            int date  = Integer.parseInt(p_date.substring(6));

            retCal.set(year, month, date);
        }

        return retCal;
    }

    /**
     * 현재날자에서 주어진 숫자만큼 날짜를 더해서 'yyyyMMdd'포멧으로 리턴한다.
     *
     * 예)
     * getMinusDate(1)
     * > 20190619
     *
     * @param
     * @return
     */
    public static String getMinusDate(int day) {
         Calendar cal = new GregorianCalendar();
         cal.add(Calendar.DATE, day);
         Date newDt=cal.getTime();
         SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
         String now = format.format(newDt);
         return now;
    }

    /**
     * 현재날짜에 지정한 년, 월, 일자만큼 더한 결과를 지정한 포멧으로 리턴한다.
     *
     * 예)
     * getAddDate(2, 0, 0, "yyyy/MM/dd")
     * > 2021/06/18
     *
     * @param year
     * @param month
     * @param day
     * @param pattern
     * @return
     */
    public static String getAddDate(int year, int month, int day, String pattern) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.YEAR, year); // 1년을 더한다.
        cal.add(Calendar.MONTH, month); // 한달을 더한다.
        cal.add(Calendar.DATE, day);
        Date newDt=cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String now = format.format(newDt);
        return now;
    }

    /**
     * Calendar객체의 년월일을 'yyyyMMdd'포멧으로 리턴한다.
     *
     * 예)
     * cal.set(2018, 11, 30)
     * getYyyymmdd(cal)
     * > 20181230
     *
     * @param cal
     * @return
     */
    public static String getYyyymmdd(Calendar cal) {
         Locale currentLocale = new Locale("KOREAN", "KOREA");
         String pattern = "yyyyMMdd";
         SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
         return formatter.format(cal.getTime());
    }

    /**
     * 지정한 날짜를 GregorianCalendar객체로 변경해서 리턴한다.
     *
     * 예)
     * getGregorianCalendar("20190618")
     * > java.util.GregorianCalendar[time=?,areFieldsSet=false,areAllFieldsSet=false,lenient=true,
     *   zone=sun.util.calendar.ZoneInfo[id="Asia/Seoul",offset=32400000,dstSavings=0,useDaylight=false,transitions=14,lastRule=null],
     *   firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=?,YEAR=2019,MONTH=5,WEEK_OF_YEAR=?,WEEK_OF_MONTH=?,
     *   DAY_OF_MONTH=18,DAY_OF_YEAR=?,DAY_OF_WEEK=?,DAY_OF_WEEK_IN_MONTH=?,AM_PM=0,HOUR=0,HOUR_OF_DAY=0,MINUTE=0,SECOND=0,MILLISECOND=?,
     *   ZONE_OFFSET=?,DST_OFFSET=?]
     * @param yyyymmdd
     * @return
     */
    public static GregorianCalendar getGregorianCalendar(String yyyymmdd) {

         int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
         int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
         int dd = Integer.parseInt(yyyymmdd.substring(6));

         GregorianCalendar calendar = new GregorianCalendar(yyyy, mm - 1, dd, 0, 0, 0);

         return calendar;
    }

    /**
     * 현재 년도를 'yyyy'포멧으로 리턴한다.
     *
     * 예)
     * getYear()
     * > 2019
     *
     * @return
     */
    public static String getYear() {

        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);

        return Integer.toString(year);
    }

    /**
     * 현재 달을 'mm'포멧으로 리턴한다.
     *
     * 예)
     * getMonth()
     * > 06
     *
     * @return
     */
    public static String getMonth() {

        Calendar currentDate = Calendar.getInstance();
        int month = currentDate.get(Calendar.MONTH) + 1;
        String mm = month < 10 ? "0" + Integer.toString(month) : Integer.toString(month);

        return mm;
    }

    /**
     * 현재 날자를 'dd'포멧으로 리턴한다.
     *
     * 예)
     * getDay()
     * > 18
     *
     * @return
     */
    public static String getDay() {

        Calendar currentDate = Calendar.getInstance();
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        String dd = day < 10 ? "0" + Integer.toString(day) : Integer.toString(day);

        return dd;
    }

    /**
     * 입력한 '년,월,일'이 유효한 날인지 체크해서 결과를 리턴한다.
     * false = 유효하지 않음. true = 유효.
     *
     * 예)
     * isValid("2019", "02", "30")
     * > false
     * isValid("2020", "02", "29")
     * > true
     *
     * @param yyyy
     * @param mm
     * @param dd
     * @return
     */
    public static boolean isValidDate(String yyyy, String mm, String dd) {


        String checkDate = yyyy + mm + dd;
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setLenient(false);

        try {
            df.parse(checkDate);
            return true;

        } catch(ParseException e) {
            return false;
        } catch(Exception e) {
            return false;
        }
    }
}
