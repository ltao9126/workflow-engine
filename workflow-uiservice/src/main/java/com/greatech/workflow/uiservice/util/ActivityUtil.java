package com.greatech.workflow.uiservice.util;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */
public class ActivityUtil {
    private String bussCaption;
    private String bussClass;
    private String showIcon;
    private String identify;
    private List propertyInfos;

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

    public List getPropertyInfos() {
        return propertyInfos;
    }

    public void setPropertyInfos(List propertyInfos) {
        this.propertyInfos = propertyInfos;
    }
}
