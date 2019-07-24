package com.greatech.workflow.dto;

import com.greatech.workflow.api.node.api.FlowNode;
import com.greatech.workflow.api.node.api.FlowStartCheck;
import com.greatech.workflow.deal.NodeConvert;
import com.greatech.workflow.dto.config.ConditionClassConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程对象,包括一个完整的流程节点
 */
public class FlowInfo implements Serializable {


    private final static Log logger = LogFactory.getLog(FlowInfo.class);

    /**
     * 流程关键字
     */
    private String flowKey;

    /**
     * 流程启动条件检测接口，当此接口为空或者检测条件为空或者检测条件无内容讲跳过检测
     */
    private FlowStartCheck flowStartCheck;

    /**
     * 流程启动条件，检测条件为空或者检测条件无内容讲跳过检测
     */
    private List<ConditionClassConfig> startConditions;


    private Map<String, FlowConfigNode> flowTable;

    //内置根据流程信息转换为实体对象工具
    private static final NodeConvert nodeConvert = new NodeConvert();

    public FlowInfo() {
        flowTable = new HashMap<>();
    }

    public FlowStartCheck getFlowStartCheck() {
        return flowStartCheck;
    }

    public void setFlowStartCheck(FlowStartCheck flowStartCheck) {
        this.flowStartCheck = flowStartCheck;
    }

    public List<ConditionClassConfig> getStartConditions() {
        return startConditions;
    }

    public void setStartConditions(List<ConditionClassConfig> startConditions) {
        this.startConditions = startConditions;
    }

    public String getFlowKey() {
        return flowKey;
    }

    public void setFlowKey(String flowKey) {
        this.flowKey = flowKey;
    }

    /**
     * 添加节点
     *
     * @param flowConfigNode
     */
    public void add(FlowConfigNode flowConfigNode) {
        flowTable.put(flowConfigNode.getNodeKey(), flowConfigNode);
    }

    /**
     * 添加节点
     *
     * @param flowConfigNodes
     */
    public void addAll(List<FlowConfigNode> flowConfigNodes) {
        flowConfigNodes.forEach(flowConfigNode -> {
            flowTable.put(flowConfigNode.getNodeKey(), flowConfigNode);
        });
    }

    /**
     * 读流程节点
     *
     * @param nodeKey
     * @return
     */
    public FlowConfigNode getNode(String nodeKey) {
        return flowTable.get(nodeKey);
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
    public FlowConfigNode getFirstNode() {
        for (FlowConfigNode f : this.flowTable.values()) {
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
        for (FlowConfigNode f : flowTable.values()) {
            //转化bussObject对象
            if (nodeConvert.ConvertToBuss(f)) {
                if (!f.getBussObject().getBussFlowNode().initNode()) {
                    logger.warn("FlowInfo:流程:" + this.flowKey + ",初始化节点:" + f.getNodeKey() + "失败");
                }
            } else {
                logger.error("FlowInfo:反射业务流程对象出错 ");
            }
            //转化ResultClass对象
            if (!nodeConvert.convertToResultObject(f)) {
                logger.error("FlowInfo:反射结果检查流程对象出错 ");
            }
        }
    }

    /**
     * 深克隆 当前流程对象
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T deepClone() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oo = new ObjectOutputStream(bos);) {
            oo.writeObject(this);//从流里读出来
            try (ByteArrayInputStream bi = new ByteArrayInputStream(bos.toByteArray());
                 ObjectInputStream oi = new ObjectInputStream(bi)) {
                return (T) oi.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回所有的流程节点
     *
     * @return
     */
    public HashMap<String, FlowNode> getAllBussClassObject() {
        HashMap<String, FlowNode> retValues = new HashMap<>();

        flowTable.forEach((key, value) -> {
            retValues.put(key, value.getBussObject().getBussFlowNode());
        });
        return retValues;
    }
}
