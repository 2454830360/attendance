package com.system.attendance.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.attendance.model.Attendance;
import com.system.attendance.model.AttendanceErr;
import com.system.attendance.model.Test;
import org.springframework.cglib.beans.BeanCopier;

import java.util.List;

/**
 * 简单数组、集合、泛型与json的序列化和反序列化
 */
public class JsonUtil {

    public static void main(String[] args){
        extensiveTypeBetweenJsonJava();

    }

    /**
     * 将集合列表序列化为json
     * @param tList
     * @return
     */
    public static String listToJson(List tList){
        Gson gson = new Gson();
        String json = gson.toJson(tList);
        return json;
    }

    /**
     * json反序列化为集合
     * @param json
     * @return
     */
    public static List jsonToList(String json){
        Gson gson = new Gson();
        List tlist = gson.fromJson(json, new TypeToken<List>(){}.getType());
        return tlist;
    }

    /**
     * 数组与json序列化和反序列化
     * @param src
     * @return
     */
    public static String arrayToJson(String[] src){
        Gson gson = new Gson();
        String json = gson.toJson(src);

        String jsons = "['a','b','c']";
        gson.fromJson(jsons,String[].class);//json反序列化为集合
        System.out.println(jsons);
        return json;
    }

    public static void extensiveTypeBetweenJsonJava(){
        Gson gson = new Gson();
        /*java中泛型序列化为json*/
        Test<Integer> test = new Test<Integer>();
        test.setId(1);
        test.setName("liuzhen");
        test.setAge(22);
        String json = gson.toJson(test);
        System.out.println("json : " + json);

        /*把json反序列化为json中的集合*/
        Test<Integer> fromJson = gson.fromJson(json, new TypeToken<Test<Integer>>(){}.getType());
        System.out.println("java : " + fromJson.toString());
    }

}
