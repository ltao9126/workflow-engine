package com.greatech.workflow.dto.flowInfo;

import com.greatech.workflow.dto.commom.FlowEnum;
import lombok.Data;


/**
 * 流程节点配置信息
 */
@Data
public class FlowNode {


    /**
     * 是否开始节点
     */
    private boolean startNode = false;

    /**
     * 是否结束节点
     */
    private boolean endNode = false;

    /**
     * 当前节点编号
     */
    private String flowNodeKey;

    /**
     * 节点类型
     */
    private FlowEnum.FlowNodeType flowNodeType;

    /**
     * 流程节点数据容器
     */
    private FlowNodeDataContainer flowNodeDataContainer;
    
    /**
     * 流程下一节点启动条件
     */
    private FlowBusinessDataContainer branchCondition;
}
