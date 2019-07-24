package com.greatech.workflow.dto.config;

import com.greatech.workflow.dto.ClassConfig;

/**
 */
public class ConditionClassConfig extends ClassConfig {
    /**
     * 当前判断的操作符
     */
    String operaterType;

    public String getOperaterType() {
        return operaterType;
    }

    public void setOperaterType(String operaterType) {
        this.operaterType = operaterType;
    }


}
