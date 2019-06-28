package com.greatech.workflow.api.node.api;

import com.greatech.workflow.dto.config.ConditionClassConfig;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bigbeard on 17-7-28.
 * <p>
 * 流程启动条件检测
 */
public interface FlowStartCheck extends Serializable {

    /**
     * 检测条件
     *
     * @param conditionClassConfigs 条件参数
     * @return true 条件成立，false条件不成立
     */
    boolean checkCanStart(List<ConditionClassConfig> conditionClassConfigs);
}
