package com.greatech.workflow.dto.config;

import com.greatech.workflow.api.node.api.NotifyExecuteResult;

import java.io.Serializable;

//import com.greatech.workflow.api.node.api.SubFlowCallbackRegister;

/**
 * <p>
 * 子流程相关信息
 */
public class SubFlowObject implements Serializable {
    private String subFlowClass;

    private NotifyExecuteResult flowNodeCallBack;

    public String getSubFlowClass() {
        return subFlowClass;
    }

    public void setSubFlowClass(String subFlowClass) {
        this.subFlowClass = subFlowClass;
    }

}


