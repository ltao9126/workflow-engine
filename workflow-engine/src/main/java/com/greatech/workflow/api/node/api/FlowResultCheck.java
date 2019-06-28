package com.greatech.workflow.api.node.api;

import com.greatech.workflow.dto.config.Condition;

/**
 * Created by bigbeard on 17-7-6.
 */
public interface FlowResultCheck {

    /**
     * 结果条件条件判断
     *
     * @param conditonProperty 配置的条件内容
     * @param flowNodeData     流程上下文参数
     * @return true:传入的条件成功,false:条件不成立
     */
    boolean checkResult(Condition conditonProperty, Object flowNodeData);

}
