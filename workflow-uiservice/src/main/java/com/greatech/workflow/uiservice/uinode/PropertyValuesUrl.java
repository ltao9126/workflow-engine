package com.greatech.workflow.uiservice.uinode;

import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 */
public class PropertyValuesUrl {
    private String requestType;
    private String url;
    List parameterInfo;
//    private ParameterInfo parameterInfo;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List getParameterInfo() {
        return parameterInfo;
    }

    public void setParameterInfo(List parameterInfo) {
        this.parameterInfo = parameterInfo;
    }
}
