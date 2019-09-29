package com.tang.base.util;

import java.util.HashMap;
import java.util.Map;

/**
 * < ><br>
 *
 * @author tang.jian<br>
 * @CreateDate 2018/11/12 <br>
 */
public class Json {
    public static Map<String, Object> success(Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 200);
        map.put("msg", "OK");
        map.put("data", data);
        return map;
    }

    public static Map<String, Object> fail(Object data) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 499);
        map.put("msg", "Expectation Out");
        map.put("data", data);
        return map;
    }

    public static Map<String, Object> fail() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 500);
        map.put("msg", "Internal Server Error");
        return map;
    }
}
