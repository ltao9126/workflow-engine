package com.greatech.workflow.api.node.data;

/**
 * Created by bigbeard on 2017/5/19.
 * 流程传递参数对象,在流程执行过程中对象所携带的内容是会变化的
 */
public interface FlowNodeData {

    FlowNodeData getParentFlowNodeData();

    /**
     * 设置上级节点上下文内容,驱动设定
     *
     * @param flowNodeData
     */
    FlowNodeData setParentFlowNodeData(FlowNodeData flowNodeData);

    Object getResultObjec();

    /**
     * 设置节点执行结果
     *
     * @param object
     * @return
     */
    FlowNodeData setResultObjec(Object object);

    String getNodeKey();

    /**
     * 设置节点ket
     *
     * @param nodeKey
     */
    FlowNodeData setNodeKey(String nodeKey);

    String getFlowKey();

    /**
     * 设置流程key
     *
     * @param flowkey
     */
    FlowNodeData setFlowKey(String flowkey);
}
