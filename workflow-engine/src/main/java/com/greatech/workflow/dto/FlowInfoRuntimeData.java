//package com.greatech.workflow.dto;
//
//import com.greatech.workflow.api.node.data.BaseMessage;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * Created by ltao on 2017-9-27.
// * <p>
// * 流程对象运行时数据对象
// * <p>
// * 用于保存流程执行时 产生以及所需要的数据信息
// * <p>
// * 用于帮助节点表达式解析和执行的数据源
// * <p>
// * 用于帮助流程直接结束 数据的回收处理
// */
//public class FlowInfoRuntimeData {
//
//    //当前流程尚未结束的流程分支
//    private AtomicInteger count = new AtomicInteger(0);
//    //业务节点流转的报警信息
//    private BaseMessage alarmData;
//    //流程节点map集合 key为业务活的的nodekey
//    private Map<String, FlowConfigNode> flowConfigNode;
//    //存储flowinfo实例的32位hashcode值
//    private int flowInfoHashCode;
//
//    public FlowInfoRuntimeData() {
//    }
//
//    public FlowInfoRuntimeData(BaseMessage alarmData, FlowConfigNode flowConfigNode, int flowInfoHashCode) {
//        this.count.incrementAndGet();
//        this.alarmData = alarmData;
//        setFlowConfigNode(flowConfigNode);
//        this.flowInfoHashCode = flowInfoHashCode;
//    }
//
//    public int getFlowInfoHashCode() {
//        return flowInfoHashCode;
//    }
//
//    public void setFlowInfoHashCode(int flowInfoHashCode) {
//        this.flowInfoHashCode = flowInfoHashCode;
//    }
//
//    public Map<String, FlowConfigNode> getFlowConfigNode() {
//        return this.flowConfigNode;
//    }
//
//    //添加执行过的流程节点
//    public void setFlowConfigNode(FlowConfigNode flowConfigNode) {
//        if (null == this.flowConfigNode) {
//            this.flowConfigNode = new HashMap<>();
//        }
//        this.flowConfigNode.put(flowConfigNode.nodeKey, flowConfigNode);
//    }
//
//
//    public int incrementCount() {
//        return this.count.incrementAndGet();
//    }
//
//    public int addCount(int count) {
//        return this.count.addAndGet(count);
//    }
//
//    public int decrementCount() {
//        return this.count.decrementAndGet();
//    }
//
//    //流程是否已经结束  依赖于count属性
//    public boolean isFinish() {
//        return this.count.get() == 0;
//    }
//
//    public BaseMessage getAlarmData() {
//        return alarmData;
//    }
//
//    public void setAlarmData(BaseMessage alarmData) {
//        this.alarmData = alarmData;
//    }
//}
