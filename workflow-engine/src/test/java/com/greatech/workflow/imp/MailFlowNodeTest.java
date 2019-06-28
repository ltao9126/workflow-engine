package com.greatech.workflow.imp;

import com.greatech.workflow.api.node.api.FlowNode;
import com.greatech.workflow.api.node.api.NotifyExecuteResult;
import com.greatech.workflow.api.node.api.NotifyNodeExecuteState;

import java.util.Random;

/**
 * Created by Administrator on 2017/6/30.
 */
public class MailFlowNodeTest implements FlowNode {
    NotifyExecuteResult notifyExecuteResult;
    NotifyNodeExecuteState notifyNodeExecuteState;
    Object flowNodeData;

    /**
     * 邮箱帐号
     */
    private String mailAccout;

    /**
     * 端口
     */
    private String port;
    /**
     * 邮件内容
     */
    private String content;

    private boolean sendState = false;

    public String getMailAccout() {
        return mailAccout;
    }

    public void setMailAccout(String mailAccout) {
        this.mailAccout = mailAccout;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
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
        System.out.println("发送邮件账号：" + getMailAccout() + " 端口： " + getPort() + " 邮件内容：" + getContent());
        //System.out.println("===========================");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendState = new Random().nextBoolean();
        if (null != notifyExecuteResult) {
            notifyExecuteResult.notifyResult(this.flowNodeData,false);
        }
    }

    @Override
    public void setExecuteOverNotify(NotifyExecuteResult flowNodeCallBack) {
        System.out.println(this);
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

}
