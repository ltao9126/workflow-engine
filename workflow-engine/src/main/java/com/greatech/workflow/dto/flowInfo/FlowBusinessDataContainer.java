package com.greatech.workflow.dto.flowInfo;

import com.greatech.workflow.api.node.api.FlowNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Flow 业务数据对象
 */
@Data
public class FlowBusinessDataContainer {

    /**
     * 默认下一节点key (当多有branch分支条件均不满足时候 执行该节点)
     */
    String defaultFlowNodeKey;
    /**
     * 存放当前类的配置信息
     */
    List<FlowNodeBranchCondition> nextNodeBranch;

    /**
     * 當前節點等待時間
     */
    private int waitTime;
}
