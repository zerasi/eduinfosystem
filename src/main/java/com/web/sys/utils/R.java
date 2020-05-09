package com.web.sys.utils;

import java.util.HashMap;
import java.util.Map;

public class R {
    public static String OK = "0";
    public static String ERR = "-1";

    public static Map ret(String status, String msg, Object data){
        Map ret = new HashMap();
        assert status != null;
        ret.put("result",status);
        ret.put("msg",msg==null?"":msg);
        ret.put("data",data==null?"":data);
        return ret;
    }
    public static Map ret(String status,String msg){
        return ret(status,msg,null);
    }
    public static Map ret(String status){
        return ret(status,null,null);
    }
    public static Map ret(String status,Object data){
        return ret(status,null,data);
    }
}
