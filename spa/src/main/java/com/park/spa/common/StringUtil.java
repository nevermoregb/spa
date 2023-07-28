package com.park.spa.common;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Clob;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

    final static char[] digits = {

            '0', '1', '2', '3', '4', '5', '6', '7',

            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',

            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',

            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',

            'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',

            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',

            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',

            'U', 'V', 'W', 'X', 'Y', 'Z', '@', '$' // '.', '-'

    };

    /**
     * 빈값(null or "")인지 확인한다.
     *
     * @param str 문자열
     * @return 널도 공백도 아니면 'false', 그렇지 않으면 'true'
     */
    public static boolean isEmpty(String str) {
        if( str != null && !"".equals(str.trim()) ) {
            return false;
        }

        return true;
    }

    /**
     * 빈값(null or "")이 아닌지 확인한다.
     *
     * @param str 문자열
     * @return 널이거나 공백이면 'true', 그렇지 않으면 'false'
     */
    public static boolean isNotEmpty(String str) {
        if( str != null && !"".equals(str.trim()) ) {
            return true;
        }

        return false;
    }

    /**
     * Object가 null 일경우 기본문자열을 리턴한다.
     *
     * @param obj
     * @param def 기본 문자열
     * @return
     */
    public static String convNullObj(Object obj, String def) {
        if(obj == null) {
            return def;
        }

        return obj.toString();
    }


    /**
     * 전달된 문자열을 src_enc 방식에서 dest_enc 방식으로 변환한다.
     *
     * @param String str 변환시킬 문자열
     * @param String src_enc 원래 문자의 encoding방식
     * @param String des_enc 변환시킬 encoding방식.
     * @return String desc_enc 방식으로 변환된 문자열
     * @throws UnsupportedEncodingException : Encoding이 지원되지 않는 문자열 변환시
     */
    public static String toConvert(String str, String src_enc, String dest_enc) throws java.io.UnsupportedEncodingException {
        if(str == null) {
            return "";
        } else {
            return new String(str.getBytes(src_enc), dest_enc);
        }
    }

    /**
     * null String을 "" String으로 바꿔준다.
     *
     * @param String str Null 문자열
     * @return String "' 문자열
     */
    public static String nullConv(String str) {
        if(str == null) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * str이 null이면 str1을 null이 아니면 str을 리턴한다.
     *
     * @param str
     * @param str1
     * @return
     */
    public static String nullConv(String str, String str1) {
        if(str == null) {
            return str1;
        } else {
            return str;
        }
    }

    /**
     * object o 값이 null이면 ""값을 null이 아니면 o값(string변환)을 리턴한다.
     *
     * @param o
     * @return
     */
    public static String nvl(Object o) {
        return nvl(o, "");
    }

    public static String nvl(Object o, String str1) {
        if ( null == o )
            return str1;
        else
            return String.valueOf(o);
    }
    public static String nvl(String str, String str1) {
        if (str == null||str.length()==0)
            return str1;
        else
            return str;
    }

    public static String delOneChar(String str) {
        if (str == null || str.length() == 1)
            return "";
        return str;
    }

    /**
     * 전달된 문자열의 길이를 넘겨온 길이에 맞게 잘라서 넘겨준다. 잘린 String에 Attach String을 덧붙여서 리턴한다.
     *
     * @param src
     * @param str_length
     * @param att_str
     * @return
     */
    static public String strCut(String src, int str_length, String att_str) {
        int ret_str_length = 0;

        String ret_str = "";

        // 현재 환경의 Character length를 구한다.
        //String tempMulLanChar = new String("가");
        String tempMulLanChar = "가";
        int lanCharLength = tempMulLanChar.length();
        // Character가 중간에 잘리지 않게 하기위해 넣은 변수
        int multiLanCharIndex = 0;

        for (int i = 0; i < src.length(); i++) {
            ret_str += src.charAt(i);

            if (src.charAt(i) > '~') {
                ret_str_length = ret_str_length + 2 / lanCharLength;
                multiLanCharIndex++;
            } else {
                ret_str_length = ret_str_length + 1;
            }

            if (ret_str_length > str_length
                    && (multiLanCharIndex % lanCharLength) == 0) {
                ret_str += StringUtil.nullConv(att_str);
                break;
            }
        }

        return ret_str;
    }

    public static String webStrCut(String src, int str_length, String att_str) {
        if (src == null || src.length() == 0) {
            return "";
        }
        if (src.length() <= str_length) {
            return src;
        } else {
            return src.substring(0, str_length) + att_str;
        }
    }

    /**
     * byte[]를 substring 한다.
     * @param s_byte 원본 byte[]
     * @param s_idx  start index
     * @param e_idx  end index
     * @return String substring 후 String으로 리턴
     */
    public static String byteStrCut(byte[] s_byte, int s_idx, int e_idx) {
        byte r_byte[] = new byte[e_idx - s_idx];
        int y = 0;
        for(int z = s_idx; z < e_idx; z++)
            r_byte[y++] = s_byte[z];

        return new String(r_byte);
    }

    /**
     * 주어진 길이(len)에서 문자열(s)의 길이를 제외한 우측을 특정 character(padc)로 채워준다.
     *
     * @param s
     * @param len
     * @param padc
     * @return
     */
    public static String rpad(String s, int len, char padc) {
        int rlen = s.getBytes().length;
        if (rlen >= len) {
            return s;
        }
        StringBuffer sb = new StringBuffer(s);
        for (int i = rlen; i < len; i++)
            sb.append(padc);
        return sb.toString();
    }

    public static String rpad(int srcint, int length, char ch) {
        return rpad(String.valueOf(srcint), length, ch);
    }

    /**
     * 주어진 길이(len)에서 문자열(s)의 길이를 제외한 좌측을 특정 character(padc)로 채워준다.
     *
     * @param s
     * @param len
     * @param padc
     * @return
     */
    public static String lpad(String s, int len, char padc) {
        int rlen = s.getBytes().length;
        if (rlen >= len) {
            return s;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = rlen; i < len; i++)
            sb.append(padc);
        sb.append(s);
        return sb.toString();
    }

    public static String lpad(int srcint, int length, char ch) {
        return lpad(String.valueOf(srcint), length, ch);
    }

    public static String[] split(String str) {
        return split(str, null);
    }

    public static String[] split(String str, String symbol) {
        StringTokenizer st;
        if (symbol != null && symbol.length() > 0) {
            st = new StringTokenizer(str, symbol);
        } else {
            st = new StringTokenizer(str);
        }

        String[] astr = new String[st.countTokens()];
        for (int i = 0; st.hasMoreTokens(); astr[i] = st.nextToken(), i++)
            ;
        return astr;
    }

    /**
     * 주민등록번호, 사업자 번호 형태로 리턴하기
     *
     * @param sidbizno
     * @return
     */
    public static String formatSidBiz(String sidbizno){
        if(sidbizno==null ){
            return sidbizno;
        }else if (sidbizno.length()==13){
            return sidbizno.substring(0,6) + "-" + sidbizno.substring(6,13);
        }else if (sidbizno.length()==10){
            return sidbizno.substring(0, 3)+"-"	+sidbizno.substring(3, 5)+"-"+sidbizno.substring(5);
        }else {
            return sidbizno;
        }
    }

    /**
     * 년월일 사이에 '.'를 첨가한다.
     *
     * @param str
     * @return
     */
    public static String date(String str) {

        String temp = null;

        if(str == null) {
            return str;
        }

        int len = str.length();
        if (len != 8)
            return str;

        if ("0".equals(str.trim()) || "".equals(str.trim()))
            return "";

        temp = str.substring(0,4) + "." + str.substring(4,6) + "." + str.substring(6,8);

        return  temp;
    }

    /**
     * 년월 사이에 '.'를 첨가한다.
     *
     * @param str
     * @return
     */
    public static String dateYM(String str) {

        String temp = null;

        if(str == null) {
            return str;
        }

        int len = str.length();
        if (len != 6)
            return str;

        if ( "000000".equals(str) || "0".equals(str.trim()) || "".equals(str.trim()))
            return "";

        temp = str.substring(0,4) + "." + str.substring(4,6);

        return  temp;
    }

    /**
     * 입력된 문자열에서 왼쪽의 0 을 입력받은 갯수만큼만 남기고 제거, 전화번호 지역번호, 국번 검사에 사용 함<br>
     * 지역번호 0 하나만 남김, cnt=1, 국번 앞의 0을 모두 삭제 cnt=0
     * @param str String
     * @param cnt int : 0을 남길 갯수
     * @return String객체
     **/
    public static String removePadZero(String str, int cnt) {
        int zerocnt = 0;
        for(int i=0;i<str.length()-1;i++){
            if("0".equals(str.substring(i,i+1))){
                zerocnt++;
            } else {
                break;
            }
        }
        if(zerocnt > cnt){
            return str.substring(zerocnt-cnt);
        } else {
            return str;
        }
    }

    /**
     * 입력된 String 을 원하는 길이만큼 채울 문자로 채워준다<br>
     * default 로 왼쪽에 blank 로 채움
     * @param str String 원본 데이터
     * @param len int    최종 데이터 길이
     * @return String객체
     */
    public static String getPadString(String str, int len) {
        return getPadString(str, len, " ", 0);
    }

    /**
     * 입력된 String 을 원하는 길이만큼 채울 문자로 채워준다<br>
     * default 로 왼쪽에 채움
     * @param str String 원본 데이터
     * @param len int    최종 데이터 길이
     * @param padstr String 채울 문자열
     * @return String객체
     */
    public static String getPadString(String str, int len, String padstr) {
        return getPadString(str, len, padstr, 0);
    }

    /**
     * 입력된 String 을 원하는 길이만큼 채울 문자로 채워준다<br>
     * 입력된 lr 에 따라 왼쪽 또는 오른쪽에 채워준다 default 왼쪽
     * @param str String 원본 데이터
     * @param len int    최종 데이터 길이
     * @param padstr String 채울 문자열, 하나의 문자로 하는것을 권장
     * @param lr int 왼쪽에 채울지 오른쪽에 채울지 지정, 0:left(default), 1:right
     * @return String객체
     */
    public static String getPadString(String str, int len, String padstr, int lr) {

        String rtnstr = "";
        if ( str.length() >= len)  {
            return str;
        } else {

            if(lr == 1){
                rtnstr = str + padstr;
            } else {
                rtnstr = padstr + str;
            }
            if(rtnstr.length() < len){
                return getPadString(rtnstr, len, padstr, lr);
            } else {
                return rtnstr;
            }
        }
    }

    /**
     * 2byte 코드 또는 10보다 작은 수의 String 등의 경우 1Byte 인 경우에 앞자리에 0 을 붙여 2Byte 를 만들어 줌
     * @param str String Data
     * @return 2byte String
     */
    public static String getPadZero(String str) {
        return getPadString(str, 2, "0");
    }

    /**
     * 2byte 코드 또는 10보다 작은 수의 String 등의 경우 1Byte 인 경우에 앞자리에 0 을 붙여 2Byte 를 만들어 줌
     * @param num int Data
     * @return 2byte String
     */
    public static String getPadZero(int num) {
        return getPadString(Integer.toString(num), 2, "0");
    }

    /**
     * 년월일 사이에 '-'를 첨가한다.
     *
     * @param str
     * @return
     */
    public static String dateDash(String str) {

        String temp = null;

        if(str == null) {
            return str;
        }

        int len = str.length();
        if (len == 4){
            temp = str.substring(0,3) + "-" + str.substring(3,5) ;
        }else if (len == 6){
            temp = str.substring(0,4) + "-" + str.substring(4,6) ;
        }else if (len == 8){
            temp = str.substring(0,4) + "-" + str.substring(4,6) + "-" + str.substring(6,8);
        }else {
            temp = str;
        }
        if ("0000-00-00".equals(temp) || "0000-00".equals(temp) || "00-00".equals(temp))
            temp = "";

        return temp;
    }

    /**
     * 년월일 한글로 표시한다.
     *
     * @param str
     * @return
     */
    public static String dateHan(String str) {
        String temp = null;
        int len = str.length();

        if (len != 8)
            return str;
        if ( "00000000".equals(str) || "0".equals(str.trim()) || "".equals(str.trim()))
            return "";
        temp = str.substring(0,4) + "년 " + Integer.parseInt(str.substring(4,6)) + "월 " + Integer.parseInt (str.substring(6,8)) + "일";

        return  temp;
    }

     /**
      * 년월 한글로 표시한다
      *
      * @param str
      * @return
      */
     public static String dateHanYM(String str) {

         String temp = null;

         if(str == null) {
             return str;
         }

         int len = str.length();

         if (len != 6)
            return str;
         if ("000000".equals(str)|| "0".equals(str.trim()))
            return "";
         temp = str.substring(0,4) + "년 " + Integer.parseInt (str.substring(4,6)) + "월";

         return  temp;
    }

     /**
      * 시분초 사이에 ':'를 첨가한다.
      *
      * @param str
      * @return
      */
     public static String time(String str) {

         String temp=null;

         if (str==null)
             return "";
         int len = str.length();

         if (len != 6)
            return str;

         temp = str.substring(0,2) + ":" + str.substring(2,4) + ":" + str.substring(4,6);
         return  temp;
     }

     /**
      * 시분 사이에 ':'를 첨가한다.
      *
      * @param str
      * @return
      */
     public static String time2(String str) {

         String temp=null;
         int len = str.length();

         if (len < 4)
            return str;

         temp = str.substring(0,2) + ":" + str.substring(2,4);
         return  temp;
     }

    /**
     * 시분 한글로 표시한다
     *
     * @param str
     * @return
     */
    public static String timeHanHM(String str) {

        String temp=null;
        int len = str.length();

        if (len > 6)
            return str;

        temp = Integer.parseInt (str.substring(0,2)) + "시 " + Integer.parseInt (str.substring(2,4)) + "분";
        return  temp;
    }

    /**
     * 주민등록번호 또는 사업자번호에 '-'를 첨가한다.
     *
     * @param str
     * @return
     */
    public static String regnNo(String str) {

        String temp = null;
        str = str.trim();
        int len = str.length();

        if ((len != 13) && (len !=10))
            return str;

        // 사업자번호
        if(len == 10)
            temp = str.substring(0,3) + "-"
                 + str.substring(3,5) + "-"
                 + str.substring(5,10);

        // 주민등록번호
        if(len == 13)
            temp = str.substring(0,6) + "-"
                 + str.substring(6,13);

        return temp;
    }

    /**
     * 숫자에 컴마표기
     *
     * @param nVal
     * @return
     */
    public static String getMoneyFormat(int nVal) {
        String displayForm = new DecimalFormat("#,###,###,###").format(nVal);
        return displayForm;
    }

    public static String getMoneyFormat(long nVal) {
        String displayForm = new DecimalFormat("#,###,###,###").format(nVal);
        return displayForm;
    }

    public static String getMoneyFormat(double nVal) {
        String displayForm = new DecimalFormat("#,###,###,###").format(nVal);
        return displayForm;
    }

    public static String getMoneyFormat(String strVal) {
        if(strVal==null || "".equals(strVal.trim()) || strVal.trim().replaceAll(",","").equalsIgnoreCase("null")) return "0";

        double nVal = Double.parseDouble(strVal);
        String displayForm = new DecimalFormat("#,###,###,###").format(nVal);
        return displayForm;
    }

    public static String getMoneyUnFormat(String strVal) {
        if(strVal==null || "".equals(strVal.trim()) || strVal.trim().equalsIgnoreCase("null")) return "0";

        String displayForm = replace(strVal, ",", "");
        displayForm = replace(displayForm, ".", "");
        return displayForm;
    }

    public static  String getDollarFormat(double dVal) {
        String displayForm = new DecimalFormat("#,###,###,##0.00").format(dVal);
        return displayForm;
    }

    public static  String getDollarFormat2(String strVal) {
        String displayForm = new DecimalFormat("#,###,###,##0.000").format(strVal);
        return displayForm;
    }

    /**
     *  전체 String값(value)중 파라미터(unit)에해당하는 단위만큼 뒤에서 substring하고
     *  나머지는  세자리마다 ','를찍는다.
     *  ( sign 은 포함하지 않는다. )
     *  -------------------------------------------------------------------
     *  ex>
     *     makeValueAsCurrency("0",3)일 경우                 0.000을 리턴.
     *     makeValueAsCurrency("1",3)일 경우                 0.001을 리턴.
     *     makeValueAsCurrency("1000",3)일 경우              1.000을 리턴.
     *     makeValueAsCurrency("1110000",3)일 경우       1,110.000을 리턴.
     *     makeValueAsCurrency("1110000.1",3)일 경우 1,110,000.100을 리턴.
     *  --------------------------------------------------------------------
     *
     *  @param  n String  형식을 바꾸고자 하는 문자열
     *  @param  unit int  소수점 이하로 표시하고자 하는 문자열 개수
     *
     *  @return 문자열 원하는 길이만큼을 소수점 이하로 처리하고,
     *                  남은 문자열은 세자리 마다 ","를 찍은 문자열
     */
    public static String makeValueAsCurrency(String n, int unit) {

        String o = "";
        String p = "";

        n = (N2S(n,"")).trim();
        n = replace(n,",","");

        if(n.indexOf(".")!=-1) {
            for(int i=0; i<unit; i++)
                n = "0" + n + "0";
            o = n.substring(0, n.indexOf("."));
            p = n.substring(n.indexOf(".")+1, n.indexOf(".")+1+unit);
        } else {
            for(int i=0; i<unit; i++)
                n = "0" + n;
            o = n.substring(0,n.length()-unit);
            p = n.substring(n.length()-unit,n.length());
        }

        o = getCurrency(o);

        if(unit>0)  {
            return o+"."+p;
        } else {
            return o;
        }
    }

    /**
     *  숫자형문자를 돈형식으로 표기하여 가져온다. ( sign 은 포함하지 않는다. )
     *
     *  @param  n String  금액으로 표현하고자하는 문자열
     *  @return String객체  표현처리된 문자열
     */
    public static String getCurrency(String n) {

        String o = "";
        String p = "";

        n = (N2S(n,"")).trim();
        o = replace(o," ","");
        o = replace(o,",","");
        o = replace(o,"+","");

        if ( n.indexOf(".") != -1 ) {
            o = n.substring(0, n.indexOf("."));
            p = n.substring(n.indexOf(".")+1, n.length());
        } else {
            o = n;
        }

        int zeroNum = 0;
        boolean zeroChk = false;
        for ( int i=0; i<o.length(); i++ )  {
            if ( !"0".equals(o.substring(i,i+1)) )  {
                zeroNum = i;
                zeroChk = true;
                break;
            }
        }

        if(zeroNum!=0) {
            o = o.substring(zeroNum,o.length());
        } else if(zeroChk == false) {
            o = "0";
        }

        int tLen = o.length();
        String tMoney = "";
        for(int i=0;i<tLen;i++){
            if (i!=0 && ( i % 3 == tLen % 3) ) {
                tMoney += ",";
            }
            if(i < tLen ) {
                tMoney += o.charAt(i);
            }
        }

        if ( p.length()>0 ) {
            tMoney += "."+p;
        }
        //if ( nFlag == false ) tMoney = "-"+tMoney;

        return tMoney;
    }

    /**
     * 주어진 길이만큼 '0'을 채운다.
     *
     * @param nVal
     * @param length
     * @return
     */
    public static String fillZero(double nVal, int length) {
        String tmp = "";
        String displayForm = "";
        for(int i=0; i<length; i++) {
            tmp += "0";
        }

        if(tmp != null) {
            displayForm = new DecimalFormat(tmp).format(nVal);
        }

        return displayForm;
    }

    /**
     * 주어진 길이만큼 space를 오른쪽에 채운다.
     *
     * @param strVal
     * @param nLength
     * @return
     */
    public static String fillSpace(String strVal, int nLength) {
        return getPadString(strVal, nLength, " ", 1);
    }

    /**
     * 문자열 내에서 원하는 문자열 replace 하기
     *
     * @param oldString
     * @param from
     * @param to
     * @return
     */
    public static String replace( String oldString, String from, String to ){
        String newString = oldString;
        int offset = 0;
        while((offset = newString.indexOf( from, offset ))>-1 ) {
            StringBuffer temp = new StringBuffer( newString.substring( 0, offset ) );
            temp.append( to );
            temp.append( newString.substring( offset+from.length() ) );
            newString = temp.toString();
            offset++;
        }
        return newString;
    }

    /**
     * 해당 문자열 중 <code>oldString</code>을 <code>newString</code>로 변환
     * @param str 문자열
     * @param oldString 옛날 문자
     * @param newString 새 문자
     * @return 바뀐 문자
     */
    public static String replaceAll(String str, String oldString, String newString) {
        if (str != null) {
            for (int i = 0; (i = str.indexOf(oldString, i)) >= 0; i += newString.length()) {
                str = str.substring(0, i) + newString + str.substring(i + oldString.length());
            }
            return str;
        } else {
            return "";
        }
    }

    /**
     * String이 null이거나 "" 이면 true 를 리턴한다.
     *
     * @param str
     * @return
     */
    public static  boolean N2B(String str ) {
        return "".equals(N2S( str, "" ))? true : false;
    }

    /**
     * String이 null이거나 "" 이면 "" 를 리턴한다.
     *
     * @param str
     * @return
     */
    public static  String N2S(String str ) {
        return N2S( str, "" );
    }

    /**
     * String이 null이거나 "" 이면 outstr 을 리턴한다.
     *
     * @param str
     * @param outStr
     * @return
     */
    public static  String N2S(String str, String outStr ) {
        String result = "";
        try {
            if(str == null || "".equals(str.trim())) result = outStr;
            else result = str;
        } catch(NullPointerException e) {
            logger.error("N2S err");
        } catch(Exception e) {
            logger.error("N2S err");
        }
        return result;
    }

    /**
     * String이 null이면 0로 아니면 Integer로 변환하여 값 리턴
     *
     * @param str
     * @return
     * @throws NumberFormatException
     */
    public static int N2I(String str) throws NumberFormatException {
        if(str == null || "".equals(str.trim()))
            return 0;
        else
            return Integer.parseInt(str.trim());
    }

    /**
     * String이 null이면 0로 아니면 Double로 변환하여 값 리턴
     *
     * @param str
     * @return
     * @throws NumberFormatException
     */
    public static double N2D(String str) throws NumberFormatException {
        if (str == null) {
            return 0.0;
        } else {
            if ( "".equals(str.trim()) ) {
                return 0.0;
            } else {
                return Double.parseDouble(str.trim());
            }
        }
    }

    /**
     * String이 null이면 0로 아니면 long으로 변환하여 값 리턴
     *
     * @param str
     * @return
     * @throws NumberFormatException
     */
    public static long N2L(String str) throws NumberFormatException {
        if (str == null) {
            return 0;
        } else {
            if ("".equals(str.trim()) ) {
                return 0;
            } else {
                return Long.parseLong(str.trim());
            }
        }
    }

    /**
    * 한글깔끔히 짜르기.<br>
    * 한글인코딩 처리하시고 한글,영문 모두 1byte로 계산해서 처리하시면 됩니다.<br>
    * &lt;%@ page contentType="text/html;charset=euc-kr" %&gt;
    *
    * @param s
    * @param iLen
    * @return
    */
    public static String strClip(String s, int iLen) {

        try {
            byte[] bRtn = s.getBytes();

            int iCut = iLen;
            int iMaxCnt = s.length();

            for(int i=0; i < bRtn.length; i++) {
                if(i > iLen-1) break;

                if(bRtn[i] < 0) {
                    iCut--;
                    i++;
                }
            }
            if(iCut > iMaxCnt) return s;

            return s.substring(0,iCut);

        }catch(IndexOutOfBoundsException e) {
            return s;
        }catch(Exception e) {
            return s;
        }
    }

    /**
     * 하나의 긴 String을 주어진 integer array의 순서대로 tokenize
     *
     * @param delim
     * @param str
     * @return
     */
    /*
    public static String [] parsing(int [] delim , String str) {

        int i, offset=0;

        // 한글문제로 인하여 byte로 변환한 후 처리
        byte [] input = str.getBytes();
        String [] output = new String [delim.length];
        String temp;

        for (i=0; i<delim.length; i++) {
            temp = new String(input, offset, delim[i]);
            output[i] = temp;
            offset += delim[i];
        }
        return output;
    }
    */
    /**
     * KSC5601 형식의 문자인지 검증한다.
     *
     * @param str
     * @return
     */
    public static boolean isKSC5601(String str) {
        boolean rtn = false;
        try {
            rtn = str.equals( new String(str.getBytes("KSC5601"),"KSC5601")) ? true : false;
        } catch(UnsupportedEncodingException ue)  {
            rtn = false;
        }
        return rtn;
    }

    /**
     * 전각기호로 변환후 maxlen만큼 전각space를 체운뒤 maxlen만큼 잘라낸다.
     *
     * @param str 입력문자
     * @param maxlen maxlength 만큰 체울 byte길이
     * @return String 전각 변환문자.
     */
    public static String convert2ByteChar( String str, int maxlen ) {

        String UNICODE_HALF_FULL_ASCII[][] = {
            {" ","　"},{"!","！"},{"\"","”"},{"#","＃"},{"$","＄"},{"%","％"},{"&","＆"},{"'","’"},{"(","（"},{")","）"},{"*","＊"},{"+","＋"},{",","，"},{"-","－"},{".","．"},{"/","／"},
            {"0","０"},{"1","１"}, {"2","２"},{"3","３"},{"4","４"},{"5","５"},{"6","６"},{"7","７"},{"8","８"},{"9","９"},{":","："},{";","；"},{"<","＜"},{"=","＝"},{">","＞"},{"?","？"},
            {"@","＠"},{"A","Ａ"}, {"B","Ｂ"},{"C","Ｃ"},{"D","Ｄ"},{"E","Ｅ"},{"F","Ｆ"},{"G","Ｇ"},{"H","Ｈ"},{"I","Ｉ"},{"J","Ｊ"},{"K","Ｋ"},{"L","Ｌ"},{"M","Ｍ"},{"N","Ｎ"},{"O","Ｏ"},
            {"P","Ｐ"},{"Q","Ｑ"}, {"R","Ｒ"},{"S","Ｓ"},{"T","Ｔ"},{"U","Ｕ"},{"V","Ｖ"},{"W","Ｗ"},{"X","Ｘ"},{"Y","Ｙ"},{"Z","Ｚ"},{"[","［"},{"\\","￥"},{"]","］"},{"^","＾"},{"_","＿"},
            {"`","‘"},{"a","ａ"}, {"b","ｂ"},{"c","ｃ"},{"d","ｄ"},{"e","ｅ"},{"f","ｆ"},{"g","ｇ"},{"h","ｈ"},{"i","ｉ"},{"j","ｊ"},{"k","ｋ"},{"l","ｌ"},{"m","ｍ"},{"n","ｎ"},{"o","ｏ"},
            {"p","ｐ"},{"q","ｑ"}, {"r","ｒ"},{"s","ｓ"},{"t","ｔ"},{"u","ｕ"},{"v","ｖ"},{"w","ｗ"},{"x","ｘ"},{"y","ｙ"},{"z","ｚ"},{"{","｛"},{"|","｜"},{"}","｝"},{"~","～"}
            };

        int len = str.length();
        int index=0;
        int cnt=1;
        String returnValue = "";

        while (index < len) {
            if (str.charAt(index) < 256) {
                for(int i=0;i<UNICODE_HALF_FULL_ASCII.length;i++){
                    if( str.substring(cnt-1,cnt).equals(UNICODE_HALF_FULL_ASCII[i][0]) )
                        returnValue += UNICODE_HALF_FULL_ASCII[i][1];
                }
            } else  { // 2바이트 문자라면...
                returnValue += str.substring(cnt-1,cnt);
            }
            index++;
            cnt++;
        }

        int curLen = returnValue.getBytes().length;
        for(int i=curLen;maxlen>0 && i<maxlen; i++) {
            returnValue += "　";
            i++;
        }

        //  입력한 길이만큼 잘라낸다.
        returnValue = new String(returnValue.getBytes(), 0, maxlen);

        return returnValue;
    }

    /**
     * 특수기호 변환<br>
     * <pre>[&] ==> &#38 / [(] ==> &#40 / [)] ==> &#41 / [<] ==> &lt / [>] ==> &gt;</pre>
     * @param String str HTML 기호값
     * @return String 변환값
     */
    public static String xssCheck(String str) {
        String temp  = "";
        if(str!= null){
            temp = str.replaceAll("[&]","&#38;");
            temp = temp.replaceAll("[(]","&#40;");
            temp = temp.replaceAll("[)]","&#41;");
            temp = temp.replaceAll("[<]","&lt;");
            temp = temp.replaceAll("[>]","&gt;");
        }
        return temp;
    }

    public static String xssCheckDecoder(String str) {
        String temp  = "";
        if(str!= null){
            temp = str.replaceAll("&#38;","&");
            temp = temp.replaceAll("&#40;","(");
            temp = temp.replaceAll("&#41;",")");
            temp = temp.replaceAll("&lt;","<");
            temp = temp.replaceAll("&gt;",">");
        }
        return temp;
    }

      /**
     *	random number 를 발생시켜 [a-z]+숫자 로 이루어진 문자열을 반환한다.
     *
     *	@return	String	Random 숫자로 구성된 문자열.
     */
    /*
    public static String getRandomPassword(int length) {
        char[] alpha = {
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n',
            'o','p','q','r','s','t','u','v','w','x','y','z',
        };

        Random random = new Random();

        double rn = Math.random();
        int ri = Math.abs(random.nextInt());
        int key = ri % 26;

        String s = String.valueOf(rn);

        int pos = s.indexOf(".");

        if(pos > 0) {
            s = s.substring(pos+1);
        }

        s = String.valueOf(alpha[key]) + s;
        s = s.substring(0,length);

        return s;
    }

    public static String getTransactionChar() {
        return getRandomPassword(8);
    }
    */
    public static String getRandomUUid() {
        return UUID.randomUUID().toString();
    }
    public static String getShortRandomUUid(int shift) {
        UUID uuid = UUID.randomUUID();
        return toUnsignedString(uuid.getMostSignificantBits(), shift) + toUnsignedString(uuid.getLeastSignificantBits(), shift);
    }
    public static String toUnsignedString(long i, int shift) {
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << shift;
        long mask = radix - 1;
        long number = i;
        do {
            buf[--charPos] = digits[(int) (number & mask)];
            number >>>= shift;
        } while (number != 0);
        return new String(buf, charPos, (64 - charPos));
    }

    /**
     * 상기된 문장부호 제거
     * @param String str 입력문자
     * @return String 변환값
     */
    public static String deletePunctuation(String str) {

        if(str == null) {
            return "";
        }

        str = str.replaceAll("'", "");
        str = str.replaceAll("\"", "");
        str = str.replaceAll("\\?", "");

        return str;
    }

    /**
     * extension변수에 등록된 확장자인지를 체크하여 있으면 true 를 반환
     *
     * @param str
     * @return
     */
    public static boolean extensionCheck(String str){

        String[] extension = {"jpg","gif","png","bmp","xls","pdf","ppt", "pptx","doc","excel", "hwp","txt", "xlsx","docx"};
        boolean	bool = false;
        int lastDot = str.lastIndexOf('.');
        if(lastDot == -1){
            return false;
        }
        String extensionNm	=str.substring(str.lastIndexOf('.')+1);
        for(int i=0 ; i < extension.length ; i++){
            if(extensionNm.equalsIgnoreCase(extension[i])){
                bool = true;
            }
        }

        return bool;
    }

    /**
     *  filter_word변수에 등록된 특수문자를 체크하여 있으면 true 를 반환
     *
     * @param str
     * @return
     */
    public static boolean getStrFilterCheck(String str){

        //String[] filter_word = {"\\?","\\/","\\>","\\<","\\,","\\;","\\:","\\[","\\]","\\{","\\}","\\~","\\!","\\@","\\#","\\$","\\%","\\^","\\&","\\*","\\(","\\)","\\=","\\+","\\|","\\\\","\\`","\\\\"};
        String[] filter_word = {"?","/",">","<",",",";",":","[","]","{","}","~","!","@","#","$","%","^","&","*","=","+","|","\\","`","%00","%zz"};
        boolean	bool = false;

        for(int i=0 ; i < filter_word.length ; i++){
            if(str.indexOf(filter_word[i]) > -1){
                bool = true;
            }
        }

        return bool;
    }

    /**
     * filter_word변수에 등록된 Mime Type을 체크하여 있으면 true 를 반환
     *
     * @param str
     * @return
     */
    public static boolean getMimeTypeCheck(String str){
        String[] filter_word = {"application/octet-stream","application/x-msdownload","application/x-sh"};
        boolean	bool = false;

        for(int i=0 ; i < filter_word.length ; i++){
            if(str.equalsIgnoreCase(filter_word[i])){
                bool = true;
            }
        }
        return bool;
    }

    /**
     * UTF-8 로 인코딩 한다.
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodingStr(String str) throws UnsupportedEncodingException{
        String encodeStr = "";
        encodeStr = URLEncoder.encode(str, "UTF-8");
        return encodeStr;
    }

    /**
     * 특수문자를 HTML 특수기호로 변환
     *
     * & --->> &amp;
     * < --->> &lt;
     * > --->> &gt;
     * ' --->> &#039;
     * " --->> &quot;
     * | --->> &brvbar;
     *   --->> &nbsp;
     *
     * @param   String  변환할 특수문자
     * @return  String  변환된 특수문자
     */
    public static String encSpecialCharacters(String str) {
        str = replace(str, "&" , "&amp;"   );
        str = replace(str, "<" , "&lt;"    );
        str = replace(str, ">" , "&gt;"    );
        str = replace(str, "'" , "&#039;"     );
        str = replace(str, "\"", "&quot;"  );
        str = replace(str, "|" , "&brvbar;");
        str = replace(str, " " , "&nbsp;"  );
        str = replace(str, "\r", ""        );
        str = replace(str, "\n", "<br />"        );
        return str;
    }

    /**
     * HTML 특수기호를 특수문자로 변환
     *
     * &amp;    --->> &
     * &lt;     --->> <
     * &gt;     --->> >
     * &#039;   --->> '
     * &quot;   --->> "
     * &brvbar; --->> |
     * &nbsp;   --->>
     *
     * @param   String  변환할 특수문자
     * @return  String  변환된 특수문자
     */
    public static String decSpecialCharacters(String str) {
        str = replace(str, "&amp;"   , "&" );
        str = replace(str, "&lt;"    , "<" );
        str = replace(str, "&gt;"    , ">" );
        str = replace(str, "&#96;"  , "`" );
        str = replace(str, "&#039;"     , "'" );
        str = replace(str, "&quot;"  , "\"");
        str = replace(str, "&brvbar;", "|" );
        str = replace(str, "&nbsp;"  , " " );
        return str;
    }

    public static String removeHtmlTag(String str) {
        String regex = "\\<.*?\\>";

        return str.replaceAll(regex, "");
    }

    /**
     * str에 포함된 태그를 삭제한다.
     *
     * @param str
     * @return
     */
    public static String removeCharacters(String str) {
        str = replace(str, "&" , "");
        str = replace(str, "<" , "");
        str = replace(str, ">" , "");
        str = replace(str, "|" , "");
        return str;
    }

    /**
     * 모든 태그 형식 제거
     * '<'로 시작, '>'로 끝나는 형식
     *
     * @MethodName: removeAllTag
     * @Desc:
     * @param str
     * @return
     */
    public static String removeAllTag(String str) {
        return str.replaceAll("<[^>]*>", "");
    }

    /**
     * 배열안의 값들을 구분자로 합쳐서 하나의 문자열로 만든다.
     *
     * @param delim
     * @param str
     * @return
     */
    public static String implode(String delim, String[] str) {
        String tmp = "";
        if (str == null)
            return tmp;
        for (int i = 0; i < str.length; i++) {
            if (i == 0)
                tmp = tmp + str[i];
            else
                tmp = tmp + delim + str[i];
        }
        return tmp;

    }

    /**
     * 이미지 확장자인지를 체크하여 있으면 true 를 반환
     *
     * @param str
     * @return
     */
    public static boolean imgExtensionCheck(String str){

        String[] extension = {"jpg","gif","png","bmp"};
        boolean		bool	=	false;
        int lastDot = str.lastIndexOf('.');
        if(lastDot == -1){
            return false;
        }
        String extensionNm	=str.substring(str.lastIndexOf('.')+1);
        for(int i=0 ; i < extension.length ; i++){
            if(extensionNm.equalsIgnoreCase(extension[i])){
                bool = true;
            }
        }
        return bool;
    }

    /**
     * 절상, 절하, 반올림 처리
     * @param strMode  - 수식
     * @param nCalcVal - 처리할 값(소수점 이하 데이터 포함)
     * @param nDigit   - 연산 기준 자릿수(오라클의 ROUND함수 자릿수 기준)
     *                   -2:십단위, -1:원단위, 0:소수점 1자리
     *                   1:소수점 2자리, 2:소수점 3자리, 3:소수점 4자리, 4:소수점 5자리 처리
     * @return String nCalcVal
     */
    public static String calcMath(String strMode, String sCalcVal, String sDigit) {
        long nCalcVal = 0;
        int nDigit = Integer.parseInt(sDigit);
        if("ROUND".equals(strMode)) {        //반올림
            if(nDigit < 0) {
                nDigit = -(nDigit);
                nCalcVal = (long)Math.round(toDouble(sCalcVal,0) / Math.pow(10, nDigit));
            } else {
                nCalcVal = (long)Math.round(toDouble(sCalcVal,0) * Math.pow(10, nDigit));
            }
        } else if("CEIL".equals(strMode)) {  //절상
            if(nDigit < 0) {
                nDigit = -(nDigit);
                nCalcVal = (long)Math.ceil(toDouble(sCalcVal,0) / Math.pow(10, nDigit));
            } else {
                nCalcVal = (long)Math.ceil(toDouble(sCalcVal,0) * Math.pow(10, nDigit));
            }
        } else if("FLOOR".equals(strMode)) { //절하
            if(nDigit < 0) {
                nDigit = -(nDigit);
                nCalcVal = (long)Math.floor(toDouble(sCalcVal,0) / Math.pow(10, nDigit));
            } else {
                nCalcVal = (long)Math.floor(toDouble(sCalcVal,0) * Math.pow(10, nDigit));
            }
        } else {                        //그대로(무조건 소수점 첫째 자리에서 반올림)
            nCalcVal = (long)Math.round(toDouble(sCalcVal,0));
        }
        return String.valueOf(nCalcVal);
    }

    /**
     * str이 null이면 defaultValue 리턴하고, null이 아니면 str를 double로 형변환하여 리턴한다.
     *
     * @param str
     * @param defaultValue
     * @return
     */
    public static double toDouble(String str, double defaultValue) {
        if(str == null) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(str);
        } catch(NumberFormatException nfe) {
            return defaultValue;
        } catch(Exception nfe) {
            return defaultValue;
        }
    }

    /**
     * Map data를 Map으로 리셋
     *
     * @param sMap(Source)
     * @param tMap(target)
     * @return
     */
    public static Map<String, Object> mapToMap(Map<String, Object> sMap, Map<String, Object> tMap ){

        if(null == sMap) {
            return tMap;
        }

        tMap.putAll(sMap);

        /*
        String key = "";
        // json to map
        Iterator<String> it = sMap.keySet().iterator();
        while (it.hasNext()) {
            key = String.valueOf(it.next());
            tMap.put(key, StringUtil.nvl(sMap.get(key)));
        }
        */

        return tMap;
    }

    /**
     * text의 길이가 length보다 길경우 length만큼 잘라주고 ...을 붙여준다.
     *
     * @param text
     * @param length
     * @return
     */
    public static String replaceLongText(String text, Integer length){

        String returnText = text.replaceAll("&lt;p&gt;", "").replaceAll("&lt;/p&gt;", "");
        //byte[] byteText = text.getBytes();
        //int oLength = byteText.length;
        int oLength = returnText.length();

        if(oLength > length){
            //returnText = StringUtil.byteStrCut(byteText, 0, length) + "...";
            returnText = returnText.substring(0, length) + "...";
        }

        return returnText;
    }

    /**
     * 이메일 마스킹 (ex - abcd***@naver.com)
     *
     * @param email
     * @return
     * @throws Exception
     */
    public static String getEmailMasking(String email)throws Exception{

        String[] strArr = email.split("@");
        int idLength = strArr[0].length();

        if(idLength < 4) {
            return email;
        }else{
            String frontId = strArr[0].substring(0, idLength-3);
            return frontId + "***@" +  strArr[1];
        }
    }

    /**
     * 폰번호 마스킹 (ex - 010-2222-3***)
     *
     * @param ph
     * @return
     * @throws Exception
     */
    public static String getPhNumberMasking(String ph)throws Exception{

        if (ph == null || "".equals(ph)) {
            return "";
        } else {
            return ph.substring(0, ph.length()-3) + "***";
        }
    }

    /**
     * 주민번호 마스킹 (ex - 881111-1******)
     *
     * @param ju
     * @return
     * @throws Exception
     */
    public static String getJuminMasking(String ju)throws Exception{
        if (ju == null || "".equals(ju)) {
            return "";
        } else if(ju.length() == 13){
            String birth = ju.substring(0, 6);
            String gender = ju.substring(6, 7);

            return birth + "-" +  gender + "******";
        } else {
            return ju;
        }
    }

    /**
     * 태그를 변환처리한다.
     *
     * @param str
     * @return
     * @throws Exception
     */
    public static String tagConverter(String str) throws Exception{
        if(!"".equals(str)){
            str = str.replaceAll("&amp;", "&");
            str = str.replaceAll("&apos;", "'");
            str = str.replaceAll("&quot;", "\"");
            str = str.replaceAll("&lt;", "<");
            str = str.replaceAll("&gt;", ">");
        }

        return str;
    }

       /**
        * clob 데이터를 string으로 변환한다.
        *
        * @param clob
        * @return
        * @throws Exception
        */
       public static String clobToString(Clob clob) throws Exception{

        if(clob == null) {
            return "";
        }

        StringBuffer strOut = new StringBuffer();
        String str = "";
         BufferedReader br = new BufferedReader(clob.getCharacterStream());

        while((str= br.readLine()) != null){
            strOut.append(str);
        }

        return strOut.toString();
    }

       /**
        * 네이밍 룰에 따라 마스킹 처리한다.
        *
        * @param orignStr
        * @return
        */
       public static String strMasking(String orignStr) {

           String[] maskStr = {
                  "REGNO","NAME","RESINO", "CUSTNM", "SELLEMPNM", "DEPOOWNNM", "ACNTNO", "RESINO1", "RESINO2",
                  "regNo", "name", "resiNo", "custNm", "sellEmpNm", "depoOwnNo", "acntNo",
                  "resiNo1", "resiNo2"
           };

           String newStr = null;
           try {
               for(String mask : maskStr) {
                   if(orignStr.contains(mask)) {
                       String[] tempStr = orignStr.split(mask+"=");
                       String maskChar = "";

                       int len = 0;
                       if(tempStr[1].indexOf(",") >= 0) {
                           len = tempStr[1].indexOf(",");
                       } else if(tempStr[1].indexOf("}") >= 0) {
                           len = tempStr[1].indexOf("}");
                       }

                       int half = len/2; //1
                       int cnt = (int)Math.ceil(len/2.0); //2
                       String keyStr = tempStr[1].substring(0, cnt); //2
                       if(half != cnt) {
                           keyStr = tempStr[1].substring(0, cnt); //2
                       }
                       String remainStr = tempStr[1].substring(len); //3 이후
                       for(int i = 0 ; i < half ; i++) {	//1
                           // TODO jini 현업요청 확정 후 마스킹 처리 수정 예정
                           //maskChar += "★";
                       }

                       newStr = tempStr[0] + mask + "=" + keyStr + maskChar + remainStr;
                       orignStr = newStr;
                   }
               }
           } catch(IndexOutOfBoundsException e) {
               logger.error("strMasking err");
           } catch(Exception e) {
               logger.error("strMasking err");
           }
           return orignStr;
       }

       /**
        * xx-xx-xxxxxx-xx 포멧의 운전면허증 번호를 입력하면 세번째 블록의 뒷3자리를 마스킹 처리해서 리턴한다.
        *
        * 예)
        * maskingDrivingLicense("경기-xx-xxxxxx-xx")
        * > 경기-xx-xxx***-xx
        *
        * @param  arg
        * @return
        */
       public static String maskingDrivingLicense (String arg) {

           String replaceString = arg;

           Matcher matcher = Pattern.compile("^(\\S{2})-?(\\d{2})-?(\\d{6})-?(\\d{2})$").matcher(arg);

           if ( matcher.matches() ) {
               replaceString = "";

               boolean isDash = false;
               if ( arg.indexOf("-") > 1 ) {
                   isDash = true;
               }

               for ( int i = 1; i <= matcher.groupCount(); i++ ) {
                   String replaceTarget = matcher.group(3);
                   if ( i == 3) {
                       char[] ch = replaceTarget.toCharArray();
                       Arrays.fill(ch, 3, 6, '*');

                       replaceString = replaceString + String.valueOf(ch);
                   } else {
                       replaceString = replaceString + matcher.group(i);
                   }

                   if ( isDash && i < matcher.groupCount() ) {
                       replaceString = replaceString + "-";
                   }
               }
           }

           return replaceString;
       }

       public static String getNoticeTypeStr(String str) {
        switch (str){
            case "100":
                return "공지사항";
            case "110":
                return "입찰공고";
            case "120":
                return "보도자료";
            case "130":
                return "이벤트";
            default:
                return "공지사항";
        }
    }

       public static String getTermsTypeStr(String str) {
        switch (str){
            case "100":
                return "개인(신용)정보 관련 동의서";
            case "200":
                return "개인(신용)정보 관리";
            case "300":
                return "온라인 이용약관 및 규정";
            case "400":
                return "금융약관";
            default:
                return "";
        }
    }

       public static String reverseEscapeHtml(String text){

           text = text.replace("%20", " ");
           text = text.replace("&amp;", "&");
           text = text.replace("&lt;", "<");
           text = text.replace("&gt;", ">");
           text = text.replace("&quot;", "\"");
           text = text.replace("&nbsp;", " ");

           return text;
       }

    public static String formatDate(String dateStr, String delimiter) {
        if(null == dateStr || dateStr.length() != 8){
            return "";
        }

        String ret = dateStr.substring(0, 4) + delimiter + dateStr.substring(4, 6) + delimiter + dateStr.substring(6, 8);
        return ret;
    }

    public static String bisNumber(String number){
        String formatNum = "";
        if(number.length() == 10){
            formatNum = number.substring(0,3)+"-"+number.substring(3,5)+"-"+number.substring(5,10);
        }else{
            return formatNum;
        }

        return formatNum;
    }

    public static String getPath(String str){
        if(null == str){
            return null;
        }

        String result = str.replace("/jeus/mcpapp/file", "");

        int index = result.lastIndexOf("/");

        if(index > 0){
            result = result.substring(0, index);
        }

        return result;
    }

    /**
     * UTF-8 로 디코딩 한다.
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decodingStr(String str) throws UnsupportedEncodingException{
        String decodeStr = "";
        decodeStr = URLDecoder.decode(str, "UTF-8");
        return decodeStr;
    }

    /**
     *  숫자 체크
     * @param s
     * @return
     */
    public static boolean isNumeric(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    /**
     * 연락처 하이픈 추가
     * @param src
     * @return
     */
    public static String convertTelNo(String src) {

        String mobTelNo = src;

        if (mobTelNo != null) {
            // 일단 기존 - 전부 제거
            mobTelNo = mobTelNo.replaceAll(Pattern.quote("-"), "");

            if (mobTelNo.length() == 11) {
                // 010-1234-1234
                mobTelNo = mobTelNo.substring(0, 3) + "-" + mobTelNo.substring(3, 7) + "-" + mobTelNo.substring(7);

            } else if (mobTelNo.length() == 8) {
                // 1588-1234
                mobTelNo = mobTelNo.substring(0, 4) + "-" + mobTelNo.substring(4);
            } else if (mobTelNo.length() == 10 && mobTelNo.startsWith("1")) {
                // 10-2222-3333
                mobTelNo = "0" + mobTelNo;		// 0 추가
                mobTelNo = mobTelNo.substring(0, 3) + "-" + mobTelNo.substring(3, 7) + "-" + mobTelNo.substring(7);
            } else {
                if (mobTelNo.startsWith("02")) { // 서울은 02-123-1234
                    if(mobTelNo.length() == 9) {
                        mobTelNo = mobTelNo.substring(0, 2) + "-" + mobTelNo.substring(2, 5) + "-" + mobTelNo.substring(5);
                    } else {
                        mobTelNo = mobTelNo.substring(0, 2) + "-" + mobTelNo.substring(2, 6) + "-" + mobTelNo.substring(6);
                    }
                } else { // 그외는 012-123-1345
                    mobTelNo = mobTelNo.substring(0, 3) + "-" + mobTelNo.substring(3, 6) + "-" + mobTelNo.substring(6);
                }
            }

        }

        return mobTelNo;
    }

    /**
     * 주민등록번호에서 생년월일 추출
     * @param JuminNO
     * @return
     */
    public static String getBirthFromJumin(String JuminNO) {
        if(JuminNO == null) return "";
        if(JuminNO.length() == 0) return "";
        if(JuminNO.length() < 13) return "";

        JuminNO = JuminNO.replaceAll("-","");

        String retVal = "";

        // 1.생년월일 계산
        int gubun = Integer.valueOf(JuminNO.substring(6, 7));
        String ssn1 = JuminNO.substring(0, 6);

        switch(gubun){
            case 1:
            case 2:
            case 5:
            case 6:
                JuminNO = "19" + ssn1;
                break;
            case 3:
            case 4:
            case 7:
            case 8:
                JuminNO = "20" + ssn1;
                break;
            default:
                JuminNO = "-1";
                break;
        }

        if(JuminNO.equals("-1")) return "";

        retVal = JuminNO;

        return retVal;

    }

    /**
     * 생년월일에 점 추가
     *
     * 예)
     * 19990120 -> 1999.01.20
     *
     * @param birthDay
     * @return
     */
    public static String putDotToBirth(String birthDay) {
        if(birthDay == null) return "";
        if(birthDay.length() == 0) return "";
        if(birthDay.length() < 8) return "";

        String retVal = "";
        String dot = ".";

        String year = birthDay.substring(0, 4);
        String month = birthDay.substring(4, 6);
        String day = birthDay.substring(6, 8);

        retVal = year + dot + month + dot + day;

        return retVal;
    }

    /**
     * 생년월일에 점 추가
     *
     * 예)
     * 990120 -> 99.01.20
     *
     * @param birthDay
     * @return
     */
    public static String putDotToBirth2(String birthDay) {
        if(birthDay == null) return "";
        String retVal = "";

        if(birthDay.length() == 6) {
            String dot = ".";

            String year = birthDay.substring(0, 2);
            String month = birthDay.substring(2, 4);
            String day = birthDay.substring(4, 6);

            retVal = year + dot + month + dot + day;

        } else {
            retVal = "";
        }

        return retVal;

    }


    /**
     * 이자납입정일코드 텍스트 변환
     *
     * @param intsPymtFxdtCd
     * @return str
     */
    public static String pymtConversion(String intsPymtFxdtCd) {
        switch (intsPymtFxdtCd){
            case "MLA405":
                return "5";
            case "MLA410":
                return "10";
            case "MLA415":
                return "15";
            case "MLA420":
                return "20";
            case "MLA425":
                return "25";
            default:
                return "올바르지 않은 코드 입니다.";
        }
    }


    /**
     * 청구서발송방법구분코드 한글전환
     *
     * @param bilSendMthDvcd, psmlSendCnplCd
     * @return str
     */
    public static String bilSendConversion(String bilSendMthDvcd, String psmlSendCnplCd) {

        String str = "";

        if(bilSendMthDvcd.equals("MLG401") && psmlSendCnplCd.equals("CA091")) {
            str = "우편(자택)";
        }else if(bilSendMthDvcd.equals("MLG401") && psmlSendCnplCd.equals("CA092")){
            str = "우편(직장)";
        }else {
            str = "올바르지 않은 코드 입니다.";
        }

        return str;
    }

    /**
     * 결제방법(청구수납방법코드) 코드 변환
     *
     * @param dmndRcvMtcd (DMND_RCV_MTCD 청구수납필드)
     * @return
     */
    public static String payBillMtdConversion(String dmndRcvMtcd) {
        switch(dmndRcvMtcd) {
            case "ML5101":
                return "자동이체";
            case "ML5102":
                return "무통장";
            case "ML5103":
                return "가상계좌";
            default :
                return "올바르지 않은 코드 입니다.";
        }
    }

    /**
     * 보험관리주체코드 한글전환
     *
     * @param bilSendMthDvcd, psmlSendCnplCd
     * @return str
     */
     public static String insuMngMnbdCd(String insuMngMnbdCd) {
        switch (insuMngMnbdCd){
           case "LF670010":
               return "당사";
           case "LF670020":
               return "고객";
           case "LF670030":
               return "질권설정";
           case "LF670040":
               return "면제";
           case "LF670050":
               return "당사(개별)";
           default:
               return "";
       }
    }

     /**
      * 계약주행거리 코드 변환
      * @param  mileageDvcd
      * @return str
      */
     public static String getChgMileageDvcd(String mileageDvcd) {
         switch (mileageDvcd){
             case "LFK910":
                 return "15000";
             case "LFK920":
                 return "20000";
             case "LFK925":
                 return "25000";
             case "LFK930":
                 return "30000";
             default:
                 return "";
         }
    }

     /**
      * 프로모션코드 변환
      * *확인 프로모션 코드 아직 정의 안됨
      * @param  promotionCd
      * @return str
      */
     public static String getChgPromitioncd(String promotionCd) {
         if(StringUtil.isNotEmpty(promotionCd)) {
             //프로모션 코드 정의되서 나오면 바꿔야함
             switch(promotionCd) {
             case "MLT701" :	//AMAZING SWITCH 안내서
                 return "11";
             case "MLT702" :	//AMAZING SWITCH PLUS 프로그램 안내서
                 return "12";
             case "MLT705" :	//LEXUS VLAUE+리스 반납형 프로그램 안내서
                 return "13";
             case "MLT704" :	//LEXUS VLAUE+리스 재구매형 프로그램 안내서
                 return "14";
             case "MLT803" :	//Premium Reward (리워드)보험 프로그램 안내서
                 return "15";
             case "MLT802" :	//신차교환 보험 프로그램 안내서 (3년)
                 return "16";
             case "MLT801" :	//신차교환 보험 프로그램 안내서 (1년)
                 return "17";
             case "MLT706" :	//LEXUS AUTO CARE LEASE 프로그램 안내서
                 return "18";
             case "MLT707" :	//TOYOTA AUTO CARE LEASE 프로그램 안내서
                 return "19";
             case "MLT709" :	//TOYOTA AUTO CARE LEASE(재구매) 프로그램 안내서
                 return "20";
             case "MLT710" :	//TOYOTA AUTO CARE LEASE(반납형) 프로그램 안내서
                 return "21";
             default :
                 return "";
             }
         }else {
             return "";
         }
     }




     /**
      * 은행코드 '0'자리 체크후 앞자리 0을 제외하고 은행코드를 반환 해주는 함수
      *
      * 예)
      * 032 -> 32  ,  005 -> 5 , 020 -> 20 return
      * @param code
      * @return str
      */
     public static String getChgRcvFicoCd(String str) {

        if (str.charAt(0) == '0' && str.length() > 1) {
            int i = 1;
            while (i < str.length() && str.charAt(i) == '0') {
                i++;
            }
            return str.substring(i);
        }

        return str;
    }

     /**
      *  매개변수: 300,000,000 => 반환값: 금삼억원정
      *
      * @param amt
      * @return str
      */
     public static String NumberToKor(String amt){

         String amt_msg = "";
         String[] arrayNum = {"", "일","이","삼","사","오","육","칠","팔","구"};
         String[] arrayUnit = {"","십","백","천","만","십만","백만","천만","억","십억","백업","천억","조","십조","백조","천조","경","십경","백경","천경","해","십해","백해","천해"};

         if(amt.length() > 0){
             int len = amt.length();

             String[] arrayStr = new String[len];
             String hanStr = "";
             String tmpUnit = "";
             for(int i = 0; i < len; i++){
                 arrayStr[i] = amt.substring(i, i+1);
             }
             int code = len;
             for(int i = 0; i < len; i++){
                 code--;
                 tmpUnit = "";
                 if(arrayNum[Integer.parseInt(arrayStr[i])] != ""){
                     tmpUnit = arrayUnit[code];
                     if(code > 4){
                         if((Math.floor(code/4) == Math.floor((code-1)/4)
                         && arrayNum[Integer.parseInt(arrayStr[i+1])] != "") ||
                         (Math.floor(code/4) == Math.floor((code-2)/4)
                         && arrayNum[Integer.parseInt(arrayStr[i+2])] != "")) {
                             tmpUnit = arrayUnit[code].substring(0,1);
                         }
                     }
                 }
                 hanStr += arrayNum[Integer.parseInt(arrayStr[i])]+tmpUnit;
             }
             amt_msg = hanStr;
         }else{
             amt_msg = amt;
         }

         return amt_msg;
     }

    //차량용도코드 - 보험조건 대상구분 화면노출값으로 변경
    public static String decodeCarUse(String vhclUsgCd) {
        if(StringUtil.isNotEmpty(vhclUsgCd)) {
            switch(vhclUsgCd) {
                case "AM6101" :
                    return "개인용";
                case "AM6102" :
                    return "업무용";
                default :
                    return "";
            }
        } else {
            return "";
        }
    }

    //계약번호 포맷 변경
    public static String formatContractNum(String contractNum) {
        if(contractNum != null && contractNum != "") {
            if(contractNum.length() <= 14) {
                contractNum = contractNum.substring(0, 4) + "-" + contractNum.substring(4, 10) + "-" + contractNum.substring(10, 11) + "-" + contractNum.substring(11, 14);
            }
        }else {
            return "";
        }

        return contractNum;
    }

    /**
     * 브랜드코드 상호로 변경
     */
    public static String getBarndName(String cd) {
        if(cd != null && cd != "") {
            switch(cd) {
                case "LF7602" :
                    return "TOYOTA";
                case "LF7601" :
                    return "LEXUS";
                default :
                    return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 랜덤 숫자 10자리 생성
     */
    public static String RandomNumberGenerator() {
        // 10자리 숫자를 생성하기 위해 DecimalFormat을 사용합니다.
        DecimalFormat decimalFormat = new DecimalFormat("0000000000");

        // 10자리의 무작위 숫자를 생성합니다.
       String randomNumber = decimalFormat.format(Math.random() * 10000000000L);

       return randomNumber;
    }

    /**
     * 오토케어리스 데이터 포맷팅
     * //"1/기본 점검/4\n2/엔진오일/3\n4/(에어컨)캐빈에어필터/2\n5/(엔진)에어클리너/2\n6/와이퍼러버 LH/2\n7/와이퍼러버 RH/2\n8/엔진 플러싱/1\n9/에어케어/1\n10/키배터리/1\n11/픽업서비스/3";
     */
    public static List<Map<String, String>> autocareleaseFormat(String str) {
        List<Map<String, String>> result = new ArrayList<>();

        String strSplit[] = str.split("\n");							//[\n]으로 나눔

        for(int i = 0; i < strSplit.length; i++){
            Map<String, String> autoMap = new HashMap<>();
            autoMap.put(strSplit[i].split("/")[1], strSplit[i].split("/")[2]);			//항목 map에 put
            result.add(autoMap);
        }

        return result;
    }
}
