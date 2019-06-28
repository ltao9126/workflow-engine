package com.greatech.workflow.dto.config;

import com.greatech.workflow.api.node.api.FlowResultCheck;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bigbeard on 2017/5/20.
 * 结果节点
 */
public class ResultObject implements Serializable {


    /**
     * 是否启用子流程
     */
    private boolean needStartSubFlow = false;
    /**
     * 等待时间
     */
    private int waitTime;
    /**
     * 返回对象的类名称
     */
    private String resultClass;
    /**
     * 动态创建的判断流程执行结果的对象
     */
    private FlowResultCheck flowResultCheck;
    /**
     * 默认跳转目标节点
     */
    private String defaultNodeKey;
    /**
     * 判断结果条件
     */
    private List<Condition> resultConditions;
    /**
     * 子流程名称
     */
    private String startSubFlow = "";
    /**
     * 真实返回对象
     */
    private Object resultObject;

    public boolean isNeedStartSubFlow() {
        return needStartSubFlow;
    }

    public void setNeedStartSubFlow(boolean needStartSubFlow) {
        this.needStartSubFlow = needStartSubFlow;
    }

    public String getStartSubFlow() {
        return startSubFlow;
    }

    public void setStartSubFlow(String startSubFlow) {
        this.startSubFlow = startSubFlow;
    }

    public Object getResultObject() {
        return resultObject;
    }

    public void setResultObject(Object resultObject) {
        this.resultObject = resultObject;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public String getResultClass() {
        return resultClass;
    }

    public void setResultClass(String resultClass) {
        this.resultClass = resultClass;
    }

    public String getDefaultNodeKey() {
        return defaultNodeKey;
    }

    public void setDefaultNodeKey(String defaultNodeKey) {
        this.defaultNodeKey = defaultNodeKey;
    }

    public List<Condition> getResultConditions() {
        return resultConditions;
    }

    public void setResultConditions(List<Condition> resultConditions) {
        this.resultConditions = resultConditions;
    }

    public FlowResultCheck getFlowResultCheck() {
        return flowResultCheck;
    }

    public void setFlowResultCheck(FlowResultCheck flowResultCheck) {
        this.flowResultCheck = flowResultCheck;
    }
}
