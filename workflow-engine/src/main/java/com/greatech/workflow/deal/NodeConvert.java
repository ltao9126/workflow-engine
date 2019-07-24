package com.greatech.workflow.deal;

import com.greatech.workflow.api.node.api.FlowNode;
import com.greatech.workflow.api.node.api.FlowResultCheck;
import com.greatech.workflow.dto.ClassConfig;
import com.greatech.workflow.dto.FlowConfigNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.List;

/**
 * 对象转换器
 */
public class NodeConvert implements Serializable {

    private final static Log logger = LogFactory.getLog(NodeConvert.class);

    private static final ObjectTools objectTools = new ObjectTools();

    /**
     * 将节点中的业务配置转化成对象
     *
     * @param flowConfigNode 流程配置信息
     * @return true/false
     */
    public boolean ConvertToBuss(FlowConfigNode flowConfigNode) {
        if (flowConfigNode == null) {
            logger.error("NodeConvert：流程节点对象为null");
            return false;
        }

        try {
            Class<?> clazz = Class.forName(flowConfigNode.getBussObject().getBussClass());
            if (clazz == null) {
                logger.error("NodeConvert：创建" + flowConfigNode.getNodeKey() + "中的业务对象["
                        + flowConfigNode.getBussObject().getBussClass() + "]失败");
                return false;
            }
            Object o = clazz.newInstance();
            if (!(o instanceof FlowNode)) {
                logger.error("NodeConvert：节点：" + flowConfigNode.getNodeKey()
                        + "中的业务节点配置的类未继承自："
                        + FlowNode.class);
                return false;
            }

            for (ClassConfig c :
                    flowConfigNode.getBussObject().getClassConfigList()) {
                boolean b = objectTools.setValueToObject(o, c.getProperty(), c.getValue());
                if (!b) {
                    logger.warn("NodeConvert：节点：" + flowConfigNode.getNodeKey()
                            + ",中的类：" + flowConfigNode.getBussObject().getClass()
                            + "的方法：" + c.getProperty() + "赋值失败" +
                            "，赋值为：" + c.getValue());
                }
            }
            flowConfigNode.getBussObject().setBussFlowNode((FlowNode) o);
            return true;

        } catch (Exception ex) {
            logger.error("NodeConvert：" + ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * 将配置的结果处理类名称,转化为对象
     *
     * @param flowConfigNode 流程配置信息
     * @return true/false
     */
    public boolean convertToResultObject(FlowConfigNode flowConfigNode) {
        if (flowConfigNode == null) {
            logger.error("流程节点对象为null");
            return false;
        }
        if (flowConfigNode.getResultObject() == null) {
            logger.warn("节点：" + flowConfigNode.getNodeKey() + "未配置返回对象相关信息");
            return true;
        }
        //未配置结果检查类 使用默认的结果检查类
        String clazz = flowConfigNode.getResultObject().getResultClass();
        if (null == clazz || "".equals(clazz.trim())) {
            logger.warn("节点：" + flowConfigNode.getNodeKey() + "未配置返回值计算类");
            flowConfigNode.getResultObject().setFlowResultCheck(null);
            return true;
        }
        try {
            //将缓存暂时屏蔽 TODO
            //Object o = mapObjects.get(clazz);
            //if (null == o) {
            Class<?> obj = Class.forName(clazz);
            if (obj == null) {
                logger.error("节点：" + flowConfigNode.getNodeKey()
                        + "在创建返回对象"
                        + flowConfigNode.getResultObject().getResultClass()
                        + "失败");
                return false;
            }
            Object o = obj.newInstance();
            //}
            //condition list 解析json时候已经完成 不需要在赋值
            //for (Condition condition :
            //        flowConfigNode.getResultObject().getResultConditions()) {
            //    for (ClassConfig c :
            //            condition.getClassConfigs()) {
            //        objectTools.setValueToObject(o, c.getProperty(), c.getValue());
            //    }
            //}
            flowConfigNode.getResultObject().setFlowResultCheck((FlowResultCheck) o);
            //mapObjects.put(clazz, o);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }


    private boolean setValueToObject(List<ClassConfig> classConfigs, Object o) {
        return false;
    }
}
