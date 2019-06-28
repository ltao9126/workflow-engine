package com.greatech.workflow.api.node.api;

import java.io.Serializable;

/**
 * Created by bigbeard on 2017/5/19.
 * 流程对象
 * ----------说明------------
 * 流程业务节点对象接口
 * <p>
 * 1、所有的流程业务节点实现该接口
 * 2、所有的流程业务节点属性名称与xml配置文件中属性名称保持一致
 * <p>
 * -------------------------
 */
public interface FlowNode extends Serializable {

    /**
     * 初始化流程节点
     */
    boolean initNode();

    /**
     * 执行业务对象的方法
     */
    void execute();

    /**
     * 获得当前节点已经注册了的 回调监听
     *
     * @return 回调监听对象
     */
    NotifyExecuteResult getExecuteOverNotify();

    /**
     * 执行完毕之后的回调通知流程驱动器
     *
     * @param flowNodeCallBack 通知流程驱动器的回调对象
     */
    void setExecuteOverNotify(NotifyExecuteResult flowNodeCallBack);

    /**
     * 获取当前节点的流程执行结果对象
     */
    Object getFlowNodeData();

    /**
     * 流程节点之间传递参数
     *
     * @param flowNodeData 传递的业务参数对象
     */
    void setFlowNodeData(Object flowNodeData);
}
