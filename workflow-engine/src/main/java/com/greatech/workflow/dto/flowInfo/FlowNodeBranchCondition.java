package com.greatech.workflow.dto.flowInfo;

import lombok.Data;
import org.apache.tools.ant.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 条件配置信息
 */
@Data
public class FlowNodeBranchCondition {
    
    /**
     * 条件优先级，数字越小越优先
     */
    int order;
    
    /**
     * 本条件成立是跳转的目标节点
     */
    String flowNodeKey;


    /**
     * 是否启用子流程
     */
    boolean startSubFlow;

    /**
     * 子流程名称
     */
    String subFlowKey;

    /**
     * 条件 表达式
     */
    String expressions;
}
