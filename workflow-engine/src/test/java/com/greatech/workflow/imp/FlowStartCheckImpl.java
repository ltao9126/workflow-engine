package com.greatech.workflow.imp;

import com.greatech.workflow.api.node.api.FlowStartCheck;
import com.greatech.workflow.dto.config.ConditionClassConfig;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 * <p>
 * 流程启动检查类
 */
public class FlowStartCheckImpl implements FlowStartCheck {
    @Override
    public boolean checkCanStart(List<ConditionClassConfig> conditionClassConfigs) {

        return true;
    }
}
