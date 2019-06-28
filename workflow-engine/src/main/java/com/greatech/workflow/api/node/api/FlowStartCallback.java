package com.greatech.workflow.api.node.api;

import com.greatech.workflow.api.node.data.NotifyFlowInfo;

/**
 * 流程启动回调通知
 */
public interface FlowStartCallback {
    void notifyFlowStart(NotifyFlowInfo flowInfo);
}
