package com.greatech.workflow.uiservice.util;

/**
 * Created by Administrator on 2017/7/25.
 * 流程列表工具类
 */
public class FlowUtil {
    private String flowKey;
    private String flowName;
    private int isenable;
    private String createTime;
    private String jsonString;
    private String flowData;
    private String svgData;

    public String getFlowKey() {
        return flowKey;
    }

    public void setFlowKey(String flowKey) {
        this.flowKey = flowKey;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public int getIsenable() {
        return isenable;
    }

    public void setIsenable(int isenable) {
        this.isenable = isenable;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getFlowData() {
        return flowData;
    }

    public void setFlowData(String flowData) {
        this.flowData = flowData;
    }

    public String getSvgData() {
        return svgData;
    }

    public void setSvgData(String svgData) {
        this.svgData = svgData;
    }
}
