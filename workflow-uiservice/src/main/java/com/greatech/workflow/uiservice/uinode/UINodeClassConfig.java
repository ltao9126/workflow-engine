package com.greatech.workflow.uiservice.uinode;



import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by ltao on 2017/5/20.
 * <p>
 * UI配置所需的业务对象的属性信息
 */
public class UINodeClassConfig {

    /**
     * 当前属性显示名称
     */
    String propertyCaption;

    /**
     * 当前属性类型
     */
    String propertyValueType;
    /**
     * 当前属性值
     */
    String property;
    /**
     * 当前属性对于的值
     */
    List propertyValues;
    /**
     * 默认值
     */
    String defaultValue;
    /**
     * 动态获取数据的url地址
     */
    List propertyValuesUrl;

    public String getPropertyCaption() {
        return propertyCaption;
    }

    public void setPropertyCaption(String propertyCaption) {
        this.propertyCaption = propertyCaption;
    }

    public String getPropertyValueType() {
        return propertyValueType;
    }

    public void setPropertyValueType(String propertyValueType) {
        this.propertyValueType = propertyValueType;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public List getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(List propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public List getPropertyValuesUrl() {
        return propertyValuesUrl;
    }

    public void setPropertyValuesUrl(List propertyValuesUrl) {
        this.propertyValuesUrl = propertyValuesUrl;
    }
}
