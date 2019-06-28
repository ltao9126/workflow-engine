package com.greatech.workflow.imp;

import com.greatech.workflow.api.node.api.FlowNode;
import com.greatech.workflow.api.node.api.FlowResultCheck;
import com.greatech.workflow.api.node.api.NotifyExecuteResult;
import com.greatech.workflow.api.node.api.NotifyNodeExecuteState;
//import com.greatech.workflow.dto.comm.ExpressionUtils;
import com.greatech.workflow.dto.config.Condition;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017/6/30.
 */
public class MessageFlowNodeTest implements FlowNode, FlowResultCheck {
    NotifyExecuteResult notifyExecuteResult;
    NotifyNodeExecuteState notifyNodeExecuteState;
    Object flowNodeData;

    private int res;
    /**
     * 电话号码
     */
    private String phone;

    /**
     * 短信内容
     */
    private String content;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean initNode() {
        return true;
    }

    @Override
    public void execute() {
        //System.out.println("===========================");
        System.out.println("发送短信到：" + getPhone() + " 短信内容为： " + getContent());
        //System.out.println("===========================");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        res = new Random().nextInt(100);
        if (null != notifyExecuteResult)
            notifyExecuteResult.notifyResult(this.flowNodeData);
    }

    @Override
    public void setExecuteOverNotify(NotifyExecuteResult flowNodeCallBack) {
        this.notifyExecuteResult = flowNodeCallBack;
    }

    @Override
    public NotifyExecuteResult getExecuteOverNotify() {
        return this.notifyExecuteResult;
    }

    @Override
    public Object getFlowNodeData() {
        return this.flowNodeData;
    }

    @Override
    public void setFlowNodeData(Object flowNodeData) {
        this.flowNodeData = flowNodeData;
    }

    @Override
    public boolean checkResult(Condition condition, Object flowNodeData) {
        Map<String, Object> map = new HashMap<>();
        map.put(this.getClass().getSimpleName(), this);
        return true;
       // return (boolean) ExpressionUtils.execute(condition, flowNodeData, map);
    }
}
