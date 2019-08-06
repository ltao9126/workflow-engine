package com.greatech.workflow.dto.flowInfo;

import com.greatech.workflow.api.node.api.FlowNode;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Flow ҵ�����ݶ���
 */
@Data
public class FlowBusinessDataContainer {

    /**
     * Ĭ����һ�ڵ�key (������branch��֧������������ʱ�� ִ�иýڵ�)
     */
    String defaultFlowNodeKey;
    /**
     * ��ŵ�ǰ���������Ϣ
     */
    List<FlowNodeBranchCondition> nextNodeBranch;

    /**
     * ��ǰ���c�ȴ��r�g
     */
    private int waitTime;
}
