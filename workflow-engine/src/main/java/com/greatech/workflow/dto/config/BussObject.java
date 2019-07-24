package com.greatech.workflow.dto.config;

import com.greatech.workflow.api.node.api.FlowNode;
import com.greatech.workflow.dto.ClassConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 业务节点
 */
public class BussObject implements Serializable {

    /**
     * 配置节点的业务类名称
     */
    String bussClass = "";
    /**
     * 存放当前类的配置信息
     */
    List<ClassConfig> classConfigList;

    /**
     * 流程节点中的业务系统的对象
     */
    private FlowNode bussFlowNode;

    public BussObject() {
        classConfigList = new ArrayList<ClassConfig>();
    }

    public String getBussClass() {
        return bussClass;
    }

    public void setBussClass(String bussClass) {
        this.bussClass = bussClass;
    }

    public List<ClassConfig> getClassConfigList() {
        return classConfigList;
    }

    public void setClassConfigList(List<ClassConfig> classConfigList) {
        this.classConfigList = classConfigList;
    }

    public FlowNode getBussFlowNode() {
        return bussFlowNode;
    }

    public void setBussFlowNode(FlowNode bussFlowNode) {
        this.bussFlowNode = bussFlowNode;
    }

}
