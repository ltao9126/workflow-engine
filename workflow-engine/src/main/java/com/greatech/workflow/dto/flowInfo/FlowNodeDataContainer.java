package com.greatech.workflow.dto.flowInfo;

import com.greatech.workflow.api.node.api.FlowResultCheck;
import lombok.Data;

import java.util.Map;

/**
 * 结果节点
 */
@Data
public class FlowNodeDataContainer {

    /**
     * 等待时间
     */
    private int waitTime;

    /**
     * 动态创建的判断流程执行结果的对象
     */
    private FlowResultCheck flowResultCheck;
    /**
     * 默认跳转目标节点
     */
    private String defaultNodeKey;
    /**
     * 子流程名称
     */
    private String startSubFlow;
    /**
     * 流程节点上下文
     */
    private Map<String, Object> flowNodeContext;
}
