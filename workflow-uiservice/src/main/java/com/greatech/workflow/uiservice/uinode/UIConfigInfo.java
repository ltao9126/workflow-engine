package com.greatech.workflow.uiservice.uinode;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by ltao on 2017/6/27.
 */
public class UIConfigInfo<T, U>   {
    /**
     * 对象显示名称
     */
    String bussCaption;

    /**
     * 对象类名称
     */
    String bussClass;

    /**
     * 显示图标
     */
    String showIcon;
    /**
     *
     */
    String identify;
    /**
     * 对象能够配置的所有属性
     */
    List<T> propertyInfos;

    /**
     * 结果属性集合
     */
    List<U> conditionsProperties;

    public String getBussCaption() {
        return bussCaption;
    }

    public void setBussCaption(String bussCaption) {
        this.bussCaption = bussCaption;
    }

    public String getBussClass() {
        return bussClass;
    }

    public void setBussClass(String bussClass) {
        this.bussClass = bussClass;
    }

    public String getShowIcon() {
        return showIcon;
    }

    public void setShowIcon(String showIcon) {
        this.showIcon = showIcon;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public List<T> getPropertyInfos() {
        return propertyInfos;
    }

    public void setPropertyInfos(List<T> propertyInfos) {
        this.propertyInfos = propertyInfos;
    }

    public List<U> getConditionsProperties() {
        return conditionsProperties;
    }

    public void setConditionsProperties(List<U> conditionsProperties) {
        this.conditionsProperties = conditionsProperties;
    }
}
