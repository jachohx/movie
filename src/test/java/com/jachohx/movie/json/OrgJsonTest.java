package com.jachohx.movie.json;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 使用json-lib构造和解析Json数据
 * 
 * @author Alexia
 * @date 2013/5/23
 * 
 */
public class OrgJsonTest {
	
    /**
     * 构造Json数据
     * 
     * @return
     * @throws JSONException
     */
    public static String BuildJson() throws JSONException {

        // JSON格式数据解析对象
        JSONObject jo = new JSONObject();

        // 下面构造两个map、一个list和一个Employee对象
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("name", "Alexia");
        map1.put("sex", "female");
        map1.put("age", "23");

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("name", "Edward");
        map2.put("sex", "male");
        map2.put("age", "24");

        List<Map> list = new ArrayList<Map>();
        list.add(map1);
        list.add(map2);

        Employee employee = new Employee();
        employee.setName("wjl");
        employee.setSex("female");
        employee.setAge(24);

        // 将Map转换为JSONArray数据
        JSONArray ja = new JSONArray();
        ja.put(map1);

        System.out.println("JSONArray对象数据格式：");
        System.out.println(ja.toString());

        // 将Javabean转换为Json数据（需要Map中转）
        JSONObject jo1 = JsonHelper.toJSON(employee);

        System.out.println("\n仅含Employee对象的Json数据格式：");
        System.out.println(jo1.toString());

        // 构造Json数据，包括一个map和一个含Employee对象的Json数据
        jo.put("map", ja);
        jo.put("employee", jo1.toString());
        System.out.println("\n最终构造的JSON数据格式：");
        System.out.println(jo.toString());

        return jo.toString();

    }

    /**
     * 解析Json数据
     * 
     * @param jsonString
     *            Json数据字符串
     * @throws JSONException
     * @throws ParseException
     */
    public static void ParseJson(String jsonString) throws JSONException,
            ParseException {

        JSONObject jo = new JSONObject(jsonString);
        JSONArray ja = jo.getJSONArray("map");

        System.out.println("\n将Json数据解析为Map：");
        System.out.println("name: " + ja.getJSONObject(0).getString("name")
                + " sex: " + ja.getJSONObject(0).getString("sex") + " age: "
                + ja.getJSONObject(0).getInt("age"));

        String jsonStr = jo.getString("employee");
        Employee emp = new Employee();
        JsonHelper.toJavaBean(emp, jsonStr);

        System.out.println("\n将Json数据解析为Employee对象：");
        System.out.println("name: " + emp.getName() + " sex: " + emp.getSex()
                + " age: " + emp.getAge());

    }

    /**
     * @param args
     * @throws JSONException
     * @throws ParseException
     */
    public static void main(String[] args) throws JSONException, ParseException {
        // TODO Auto-generated method stub

        ParseJson(BuildJson());
    }

}