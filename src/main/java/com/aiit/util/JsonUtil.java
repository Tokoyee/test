package com.aiit.util;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {
    public static HashMap<String, Object> success(Map<String, Object> data) {
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", HttpServletResponse.SC_OK);     //响应码
        res.put("msg", "请求成功");        //备注消息
        res.put("data", data);     //数据
        return res;
    }
    public static HashMap<String, Object> error(Map<String, Object> data) {
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);     //响应码
        res.put("msg", "用户名不存在或密码错误");        //备注消息
        res.put("data", data);     //数据
        return res;
    }
    public static HashMap<String, Object> class_name_error(Map<String, Object> data) {
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);     //响应码
        res.put("msg", "教室名称出错");        //备注消息
        res.put("data", data);     //数据
        return res;
    }
    public static HashMap<String, Object> token_error(Map<String, Object> data) {
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", HttpServletResponse.SC_HTTP_VERSION_NOT_SUPPORTED);     //响应码
        res.put("msg", "token过期或token不存在");        //备注消息
        res.put("data", data);     //数据
        return res;
    }
    public static HashMap<String, Object> record_error(Map<String, Object> data) {
        HashMap<String, Object> res = new HashMap<>();
        res.put("code", HttpServletResponse.SC_BAD_REQUEST);     //响应码
        res.put("msg", "禁止重复评价!");        //备注消息
        res.put("data", data);     //数据
        return res;
    }
}