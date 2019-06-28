package com.greatech.workflow.dto;

import java.io.Serializable;

/**
 * Created by bigbeard on 2017/5/20.
 * 属性配置信息
 */
public class ClassConfig implements Serializable {
    /**
     * 类对应的属性名称
     */
    String property;
    /**
     * 属性值
     */
    Object value;

    /**
     * 属性类型
     */
    String propertyType;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
}
