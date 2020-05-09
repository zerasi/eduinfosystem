package com.web.sys.utils;

import com.web.sys.bean.SysUser;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class T {

    public static SysUser getUser(HttpServletRequest request){
        return (SysUser)request.getSession().getAttribute("user");
    }
    public static boolean isNullOrWhite(Object str){
        if (str == null || "".equals(str.toString())){
            return true;
        }
        return false;
    }
    public static String getNow(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }

    public static String getRandomString(int len){
        String[] strs = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".split("");
        StringBuilder ret = new StringBuilder();
        Random random = new Random();
        for(int i=0;i<len;i++){
            ret.append(strs[random.nextInt(strs.length)]);
        }
        return ret.toString();
    }
    /**

     * 去掉字符串右边的空格

     * @param str 要处理的字符串

     * @return 处理后的字符串

     */

    public static String rightTrim(String str) {
        if (str == null) {
            return "";
        }
        int length = str.length();
        for (int i = length - 1; i >= 0; i--) {
            if (str.charAt(i) != 0x20) {
                break;
            }
            length--;
        }
        return str.substring(0, length);
    }

    public static void print(Object...objs){
        for(Object obj:objs){
            String t = "";
            if(obj!=null){
                t = obj.toString();
            }
            System.out.print(t+"  ");
        }
        System.out.println();
    }
    public static byte[] File2Byte(String filePath) throws IOException {

        InputStream in = new FileInputStream(filePath);

        byte[] data = toByteArray(in);
        in.close();

        return data;
    }

    public static byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }
    public static int getDateGapCountDays(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }


    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, E.ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find())
            flg = true;
        return flg;
    }

    public static void responseFile(byte[] content, String fileName, HttpServletResponse response){

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + T.getURLEncoderString(fileName));
        try{
            response.getOutputStream().write(content);
        }catch (Exception ex){}
    }

}
