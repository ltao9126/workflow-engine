package com.greatech.workflow.api.node.api;

import com.greatech.workflow.dto.commom.ExpressionUtils;
import com.greatech.workflow.dto.config.ConditionClassConfig;

import java.util.List;

/**
 * <p>
 * 流程启动条件检测
 */
public interface FlowStartCondition {

    /**
     * 检测条件
     *
     * @param expression 条件表達式  如果條件表達式不成立 那麽流程不啓動
     * @return true 条件成立，false条件不成立
     */
    default boolean checkCanStart(String expression) {
        return ExpressionUtils.checkBoolean(expression);
    }
}
