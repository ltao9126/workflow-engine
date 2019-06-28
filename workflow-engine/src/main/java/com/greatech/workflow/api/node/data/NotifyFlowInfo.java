package com.greatech.workflow.api.node.data;

import com.greatech.workflow.api.node.api.FlowNode;

import java.util.HashMap;

public class NotifyFlowInfo {
    String flowKey;
    HashMap<String, FlowNode> flowNodeHashMap;

    public String getFlowKey() {
        return flowKey;
    }

    public void setFlowKey(String flowKey) {
        this.flowKey = flowKey;
    }

    public HashMap<String, FlowNode> getFlowNodeHashMap() {
        return flowNodeHashMap;
    }

    public void setFlowNodeHashMap(HashMap<String, FlowNode> flowNodeHashMap) {
        this.flowNodeHashMap = flowNodeHashMap;
    }
}
