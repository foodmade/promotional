package com.seller66.admin.utils;

import com.seller66.admin.common.enmu.LangTypeEnum;
import com.seller66.admin.entity.Lang;
import com.alibaba.fastjson.JSONObject;
import java.util.List;

public class LangUtil {
    /**
     * 查找指定type和key的value
     */
    public static String findValueByTypeAndKey(List<Lang> langList, LangTypeEnum type, String key) {
        if (langList == null || type == null || key == null) return null;
        for (Lang lang : langList) {
            if (type.getType().equals(lang.getType())) {
                return getValueByPath(lang.getContext(), key);
            }
        }
        return null;
    }

    /**
     * 设置指定type和key的value，修改Lang对象的context字段
     * @return 是否设置成功
     */
    public static boolean setValueByTypeAndKey(List<Lang> langList, LangTypeEnum type, String key, String value) {
        if (langList == null || type == null || key == null) return false;
        for (Lang lang : langList) {
            if (type.getType().equals(lang.getType())) {
                String context = lang.getContext();
                if (context == null) context = "{}";
                JSONObject jsonObject = JSONObject.parseObject(context);
                setValueByPath(jsonObject, key, value);
                lang.setContext(jsonObject.toJSONString());
                return true;
            }
        }
        return false;
    }

    // 工具方法：通过路径查找
    private static String getValueByPath(String jsonStr, String path) {
        if (jsonStr == null || path == null) return null;
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String[] keys = path.split("\\.");
        Object current = jsonObject;
        for (String key : keys) {
            if (current instanceof JSONObject) {
                current = ((JSONObject) current).get(key);
            } else {
                return null;
            }
        }
        return current == null ? null : current.toString();
    }

    // 工具方法：通过路径设置
    private static void setValueByPath(JSONObject jsonObject, String path, String value) {
        String[] keys = path.split("\\.");
        JSONObject current = jsonObject;
        for (int i = 0; i < keys.length - 1; i++) {
            if (!current.containsKey(keys[i]) || !(current.get(keys[i]) instanceof JSONObject)) {
                current.put(keys[i], new JSONObject());
            }
            current = current.getJSONObject(keys[i]);
        }
        current.put(keys[keys.length - 1], value);
    }
} 