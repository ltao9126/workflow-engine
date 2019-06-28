package com.greatech.workflow.api.node.api;

/**
 * Created by bigbeard on 2017/5/18.
 * <p>
 * 节点回调接口
 */
public interface NotifyExecuteResult {
    /**
     * 业务系统调用的回调通知流程驱动器，已经执行完毕
     * <p>
     * 并默认注销监听
     *
     * @param o 业务系统执行完毕后返回的对象
     */
    default void notifyResult(Object o) {
        notifyResult(o, false);
    }

    /**
     * 业务系统调用的回调通知流程驱动器，已经执行完毕
     *
     * @param o          业务系统执行完毕后返回的对象
     * @param unregister 是否取消注册监听器  true 取消  false 不取消
     */
    void notifyResult(Object o, boolean unregister);


}
