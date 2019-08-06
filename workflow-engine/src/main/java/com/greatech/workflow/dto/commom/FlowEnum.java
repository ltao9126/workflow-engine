package com.greatech.workflow.dto.commom;

/**
 * ������������ö��
 */
public enum FlowEnum {
    flowKey, flowTable, startCheckClass, startConditions,;

    /**
     * ����node ����ö��
     */
    public enum NodePropEnum {
        nodeKey, startNode, endNode, resultObject, bussObject;
    }

    /**
     * ���� subFlowObject ����ö��
     */
    public enum SubFlowPropEnum {
        subFlowClass
    }

    /**
     * ���� flowNodeDataContainer ����ö��
     */
    public enum ResultPropEnum {
        defaultNodeKey, resultClass, resultConditions, waitTime, startSubFlow, needStartSubFlow;
    }

    /**
     * ���� bussObject ����ö��
     */
    public enum BussObjectPropEnum {
        classConfigList, bussClass
    }

    /**
     * ���� classConfigList ����ö��
     */
    public enum ClassConfigListPropEnum {
        property, value, propertyType
    }


    /**
     * ���� resultFlowNodeBranchConditions ����ö��
     */
    public enum ResultConditionsPropEnum {
        classConfigs, nodeKey, startSubFlow, needStartSubFlow, order, conditionExpression
    }

    /**
     * ���� classConfigs ����ö��
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
