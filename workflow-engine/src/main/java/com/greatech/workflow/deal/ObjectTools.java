package com.greatech.workflow.deal;

import com.greatech.workflow.dto.ClassConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bigbeard on 2017/5/25.
 * 对象工具类
 * 1 获取对象属性名称,属性类型和属性值
 * 2 给对象属性赋值
 * 3 获取对象的属性值
 */
public class ObjectTools implements Serializable {

    private final static Log logger = LogFactory.getLog(ObjectTools.class);

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param o
     * @return
     */
    public Object getFieldValueByName(String fieldName, Object o) {
        try {
            String getter = fieldName;
            if (!fieldName.startsWith("get")) {
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                getter = "get" + firstLetter + fieldName.substring(1);
            }
            return o.getClass().getMethod(getter).invoke(o);
        } catch (Exception e) {
            logger.error("ObjectTools:" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取属性名数组
     *
     * @param o
     * @return
     */
    public String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     *
     * @param o
     * @return
     */
    public List<Map<String, Object>> getFiledsInfo(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> infoMap = null;
        for (Field field : fields) {
            infoMap = new HashMap<>();
            infoMap.put("type", field.getType().toString());
            infoMap.put("name", field.getName());
            infoMap.put("value", getFieldValueByName(field.getName(), o.getClass()));
            list.add(infoMap);
        }
        return list;
    }

    /**
     * 获取对象属性名称，属性值和属性类型
     *
     * @param o
     * @return
     */
    public Map<String, ClassConfig> getObjectFiledsInfo(Object o) {
        Map<String, ClassConfig> map = new HashMap<>();
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            ClassConfig classConfig = new ClassConfig();
            classConfig.setProperty(fields[i].getName());
            classConfig.setPropertyType(fields[i].getType().toString());
            Object fieldValueByName = getFieldValueByName(fields[i].getName(), o.getClass());
            classConfig.setValue(fieldValueByName);
            map.put(classConfig.getProperty(), classConfig);
        }
        return map;
    }


    /**
     * 获取对象的所有属性值，返回一个对象数组
     *
     * @param o
     * @return
     */
    public Object[] getFiledValues(Object o) {
        String[] fieldNames = this.getFiledName(o);
        Object[] value = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            value[i] = this.getFieldValueByName(fieldNames[i], o);
        }
        return value;
    }


    /**
     * 给对象属性赋值
     *
     * @param o            被赋值的对象
     * @param propertyName 属性名称
     * @param value        值
     * @return
     */
    public boolean setValueToObject(Object o, String propertyName, Object value) {

        Class<?> aClass = o.getClass();
        if (!propertyName.startsWith("set") && !"".equals(propertyName)) {
            String firstLetter = propertyName.substring(0, 1).toUpperCase();
            propertyName = "set" + firstLetter + propertyName.substring(1);
        }
        try {
            Method[] methods = aClass.getMethods();
            for (Method m : methods) {
                if (m.getName().equals(propertyName)) {
                    m.invoke(o, value);
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("ObjectTools:" + e.getMessage(), e);
            e.printStackTrace();
            return false;
        }
    }
}
