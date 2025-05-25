package com.seller66.admin.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jcb on 2020-04-26 15:19
 */
@Slf4j
public class ConvertUtil {

    public static<T,S> T convert(S s, Class<T> tClazz) {
        T t = null;
        try {
            t = (T) tClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        if (s != null && t != null) {
            BeanUtils.copyProperties(s, t);
        }
        return t;
    }


    public static<T,S> PageInfo<T> convertPage(PageInfo<S> sourcePageInfo, Class<T> tClazz) {
        if (sourcePageInfo == null) {
            return null;
        }
        PageInfo<T> targetPageInfo = new PageInfo<>(new ArrayList<>(sourcePageInfo.getSize()), sourcePageInfo.getNavigatePages());
        targetPageInfo.setList(ConvertUtil.convertList(sourcePageInfo.getList(),tClazz));
        return targetPageInfo;
    }


    public static<T,S> List<T> convertList(Collection<S> sourceList, Class<T> tClazz) {
        List<T> targetList = new ArrayList<>(sourceList.size());
        try {
            for (S s : sourceList) {
                T t = (T) tClazz.newInstance();
                BeanUtils.copyProperties(s, t);
                targetList.add(t);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return new ArrayList<>();
        }
        return targetList;
    }

    public static void main(String[] args) {
        String jsonStr = "[\"Product Database - Search over 70 million Amazon products\",\"Keyword Tool - Discover high-value, low-competition keywords\",\"Sales Analysis - Track your product's performance and competitors\",\"Supplier Database - Find reliable suppliers\",\"PPC Management Tools - Optimize your campaigns\",\"Inventory Management - Automatic replenishment reminders and inventory forecasts\"]";       
        // 方法1: 使用JSONArray直接转换
        JSONArray jsonArray = JSONArray.parseArray(jsonStr);
        System.out.println("JSONArray结果: " + jsonArray);
    
        
        // 方法3: 转换为List<String>
        List<String> stringList = JSONObject.parseArray(jsonStr, String.class);
        System.out.println("List结果: " + stringList);
    }
}
