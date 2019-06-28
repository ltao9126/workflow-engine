package com.greatech.workflow.dto.comm;

/**
 * Created by Administrator on 2017/7/3.
 * 流程属性名称枚举
 */
public enum FlowEnum {
    flowKey, flowTable, startCheckClass, startConditions,;

    /**
     * 流程node 属性枚举
     */
    public enum NodePropEnum {
        nodeKey, startNode, endNode, resultObject, bussObject;
    }

    /**
     * 流程 subFlowObject 属性枚举
     */
    public enum SubFlowPropEnum {
        subFlowClass;
    }

    /**
     * 流程 resultObject 属性枚举
     */
    public enum ResultPropEnum {
        defaultNodeKey, resultClass, resultConditions, waitTime, startSubFlow, needStartSubFlow;
    }

    /**
     * 流程 bussObject 属性枚举
     */
    public enum BussObjectPropEnum {
        classConfigList, bussClass;
    }

    /**
     * 流程 classConfigList 属性枚举
     */
    public enum ClassConfigListPropEnum {
        property, value, propertyType;
    }


    /**
     * 流程 resultConditions 属性枚举
     */
    public enum ResultConditionsPropEnum {
        classConfigs, nodeKey, startSubFlow, needStartSubFlow, order, conditionExpression;
    }

    /**
     * 流程 classConfigs 属性枚举
     */
    public enum ClassConfigsPropEnum {
        property, operaterType, value;
    }

}
