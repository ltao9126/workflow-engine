package com.greatech.workflow.api.node.api;

import com.greatech.workflow.api.node.data.NotifyNodeExecuteData;

/**
 * Created by bigbeard on 2017/6/27.
 * <p>
 * 对外通知节点执行情况的接口
 */
public interface NotifyNodeExecuteState {
    /**
     * 接点执行通知
     * @param notifyNodeExecuteData 执行过程中的数据
     */
    void notifyState(NotifyNodeExecuteData notifyNodeExecuteData);
}
