package com.greatech.workflow.dto.commom;

/**
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
        subFlowClass
    }

    /**
     * 流程 flowNodeDataContainer 属性枚举
     */
    public enum ResultPropEnum {
        defaultNodeKey, resultClass, resultConditions, waitTime, startSubFlow, needStartSubFlow;
    }

    /**
     * 流程 bussObject 属性枚举
     */
    public enum BussObjectPropEnum {
        classConfigList, bussClass
    }

    /**
     * 流程 classConfigList 属性枚举
     */
    public enum ClassConfigListPropEnum {
        property, value, propertyType
    }


    /**
     * 流程 resultFlowNodeBranchConditions 属性枚举
     */
    public enum ResultConditionsPropEnum {
        classConfigs, nodeKey, startSubFlow, needStartSubFlow, order, conditionExpression
    }

    /**
     * 流程 classConfigs 属性枚举
     */
    public enum ClassConfigsPropEnum {
        property, operaterType, value
    }

    public enum FlowNodeType {
        EMAIL(100001, "EMAIL", "send email"),
        MESSAGE(100002, "MESSAGE", "send mobile phone message");
        private int index;
        private String type;
        private String note;

        FlowNodeType(int index, String type, String note) {
            this.index = index;
            this.type = type;
            this.note = note;
        }

        public static FlowNodeType findByType(String type) {
            for (FlowNodeType flowNodeType : values()) {
                if (flowNodeType.type.equals(type))
                    return flowNodeType;
            }
            return null;
        }

        public static FlowNodeType findByIndex(int index) {
            for (FlowNodeType flowNodeType : values()) {
                if (flowNodeType.index == index)
                    return flowNodeType;
            }
            return null;
        }

    }

}
