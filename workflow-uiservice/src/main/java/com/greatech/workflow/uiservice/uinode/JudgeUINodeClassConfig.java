package com.greatech.workflow.uiservice.uinode;


import java.util.List;

/**
 * Created by ltao on 2017/6/27.
 * UI配置中判断节点对象
 */
public class JudgeUINodeClassConfig extends UINodeClassConfig {

    /**
     * 结果判断类
     */
    String resultCheckClass;

    /**
     * 所支持的操作符
     */
//    List<OperateType> operaterTypes;

    public String getResultCheckClass() {
        return resultCheckClass;
    }

    public void setResultCheckClass(String resultCheckClass) {
        this.resultCheckClass = resultCheckClass;
    }

    /*public List<OperateType> getOperaterTypes() {
        return operaterTypes;
    }

    public void setOperaterTypes(List<OperateType> operaterTypes) {
        this.operaterTypes = operaterTypes;
    }*/

}
