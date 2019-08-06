package com.greatech.workflow.dto.flowInfo;

import com.greatech.workflow.api.node.api.FlowStartCondition;
import com.greatech.workflow.dto.config.ConditionClassConfig;
import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程对象,包括一个完整的流程节点
 */
@Data
public class Flow implements Serializable {


    private final static Log logger = LogFactory.getLog(Flow.class);

    /**
     * 流程关键字
     */
    private String flowKey;

    /**
     * 流程启动条件检测接口，当此接口为空或者检测条件为空或者检测条件无内容讲跳过检测
     */
    private FlowStartCondition flowStartCondition;

    /**
     * 流程启动条件表达式
     */
    private String startFlowExpression;

    /**
     * 流程節點集合
     */
    private Map<String, FlowNode> flowTable;

    /**
     * 流程数据上下文
     */
    private Map<String, Object> context;

    public Flow() {
        flowTable = new HashMap<>();
    }

    /**
     * 添加节点
     *
     * @param flowNode
     */
    public void add(FlowNode flowNode) {
        if (flowNode != null)
            flowTable.put(flowNode.getFlowNodeKey(), flowNode);
    }

    /**
     * 添加节点
     *
     * @param flowNodes
     */
    public void addAll(List<FlowNode> flowNodes) {
        if (flowNodes != null)
            flowNodes.forEach(flowNode -> flowTable.put(flowNode.getFlowNodeKey(), flowNode));
    }

    /**
     * 读流程节点
     *
     * @param flowNodeKey
     * @return
     */
    public FlowNode getNode(String flowNodeKey) {
        return flowTable.get(flowNodeKey);
    }

    /**
     * 判断节点是否存在
     *
     * @param nodeKey
     * @return
     */
    public boolean checkNodeKeyExist(String nodeKey) {
        return flowTable.containsKey(nodeKey);
    }

    /**
     * 获取流程开始节点
     *
     * @return null：开始节点不存在
     */
    public FlowNode getFirstNode() {
        for (FlowNode f : this.flowTable.values()) {
            if (f.isStartNode()) {
                return f;
            }
        }
        return null;
    }

    /**
     * 初始化流程对象，同时创建对象
     */
    public void initNode() {
        for (FlowNode f : flowTable.values()) {
            //转化bussObject对象
            if (nodeConvert.ConvertToBuss(f)) {
                if (!f.getBussObject().getBussFlowNode().initNode()) {
                    logger.warn("Flow:流程:" + this.flowKey + ",初始化节点:" + f.getNodeKey() + "失败");
                }
            } else {
                logger.error("Flow:反射业务流程对象出错 ");
            }
            //转化ResultClass对象
            if (!nodeConvert.convertToResultObject(f)) {
                logger.error("Flow:反射结果检查流程对象出错 ");
            }
        }
    }

//    /**
//     * 深克隆 当前流程对象
//     *
//     * @return
//     */
//    @SuppressWarnings("unchecked")
//    public <T> T deepClone() {
//        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
//             ObjectOutputStream oo = new ObjectOutputStream(bos);) {
//            oo.writeObject(this);//从流里读出来
//            try (ByteArrayInputStream bi = new ByteArrayInputStream(bos.toByteArray());
//                 ObjectInputStream oi = new ObjectInputStream(bi)) {
//                return (T) oi.readObject();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
