package com.web.sys.utils;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SMS {
    private static int appid =  1400146931;
    private static String appkey = AES.desEncrypt("Vo1aNh1eTKwo40qH+3fClMm/hXIJ0U634bMZJfbKIfs=",E.AESKEY,E.AESIV);
    private static int studentTemplateId = 234761;//Integer.parseInt(AES.desEncrypt("s0g4PogC6nbe6MBw33X9nA==",E.AESKEY,E.AESIV)); // {1}您好，您有一场考试将于{2} {3} 开始考试，请提前半小时入场准备。
    private static int teeacherTemplateId =234757;// Integer.parseInt(AES.desEncrypt("ewisP+Y639/10yhgoukFSg==",E.AESKEY,E.AESIV));//234757; // {1}您好，您有一场考试将于{2} {3} 开始考试，请提前半小时入场布置考场。
    private static String smsSign = AES.desEncrypt("bPTfRYjZAbSHyUddSbj/eA==",E.AESKEY,E.AESIV); // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`

    public static  Map<String,Object> sendMessage(String username,String date,String time,String[] phoneNumbers,boolean isTeacher){
        Map<String,Object> err = new HashMap<>();
        err.put("result","-1");
        err.put("msg","发送失败");
        int tmpid = studentTemplateId ;
        if(isTeacher){
            tmpid = teeacherTemplateId ;
        }

        try {
            String[] params = {username,date,time};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            SmsMultiSender msender = new SmsMultiSender(appid, appkey);
            SmsMultiSenderResult resultsms =  msender.sendWithParam("86", phoneNumbers,
                    tmpid, params, "", "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            String ret = decode(resultsms.toString());
            return new JSONObject(ret).toMap();
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return err;
    }

    public static String decode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5)
                        && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
                        .charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(
                                unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }

    public  static void main(String[] args){
        Map<String,Object> map = sendMessage("万一","2018-12-26","天津",new String[]{"123"},true);
        for(String key:map.keySet()){
            T.print(key,map.get(key));
        }
    }
}
