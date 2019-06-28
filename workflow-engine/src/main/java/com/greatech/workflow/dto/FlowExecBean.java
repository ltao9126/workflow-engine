package com.greatech.workflow.dto;

/**
 * Created by Administrator on 2017/7/5.
 * 执行任务Bean
 */
public class FlowExecBean {
    private FlowConfigNode flowConfigNode;
    private FlowInfo flowInfo;
    private Object flowNodeData;

    public FlowExecBean(FlowConfigNode flowConfigNode, FlowInfo flowInfo, Object flowNodeData) {
        this.flowConfigNode = flowConfigNode;
        this.flowInfo = flowInfo;
        this.flowNodeData = flowNodeData;
    }

    public FlowConfigNode getFlowConfigNode() {
        return flowConfigNode;
    }

    public FlowInfo getFlowInfo() {
        return flowInfo;
    }

    public Object getFlowNodeData() {
        return flowNodeData;
    }
}
