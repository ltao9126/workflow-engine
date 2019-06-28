package com.greatech.workflow.api.node.data;

import com.greatech.workflow.dto.comm.ExecuteState;

/**
 * Created by Administrator on 2017/7/7.
 * 节点执行结果 监听返回对象
 */
public class NotifyNodeExecuteData {

    //流程数据对象
    private long logDate;
    //流程节点执行结果
    private Object executeResult;
    //流程名称
    private String flowName;
    //节点key
    private String nodeKey;
    //节点执行状态
    private ExecuteState state;

    public NotifyNodeExecuteData(long logDate, Object executeResult, String flowName, String nodeKey, ExecuteState state) {
        this.logDate = logDate;
        this.executeResult = executeResult;
        this.flowName = flowName;
        this.nodeKey = nodeKey;
        this.state = state;
    }

    public long getLogDate() {
        return logDate;
    }

    public void setLogDate(long logDate) {
        this.logDate = logDate;
    }

    public Object getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(Object executeResult) {
        this.executeResult = executeResult;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public ExecuteState getState() {
        return state;
    }

    public void setState(ExecuteState state) {
        this.state = state;
        this.toString();
    }
}
