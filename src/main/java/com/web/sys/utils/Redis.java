package com.web.sys.utils;

import java.util.HashMap;

public class Redis {
    public static HashMap<String,Object> redis = new HashMap<>();

    public static Object get(String key){
        if(redis.keySet().contains(key)){
            return redis.get(key);
        }
        return null;
    }

    public static void set(String key,Object value){
        remove(key);
        redis.put(key,value);
    }
    public static void remove(String key){
        if(redis.keySet().contains(key)){
            redis.remove(key);
        }
    }
}
