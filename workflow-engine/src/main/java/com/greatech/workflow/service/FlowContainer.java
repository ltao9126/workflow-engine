package com.greatech.workflow.service;

import com.greatech.workflow.dto.FlowInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 流程管理容器,单例
 * <p>
 * Created by Administrator on 2017/7/17.
 */
public class FlowContainer {

    FlowInfoCatch flowInfoCatch = null;
    private Map<String, FlowInfoCatch> map = new HashMap<>();
    //流程节点数据
    private Object object;
    //引擎对象
    private FlowDriver driver;
    //是否需要检查条件
    private boolean needCheckCondition;

    private FlowContainer() {
        flowInfoCatch = new FlowInfoCatch();
    }

    /**
     * 获取流程管理容器对象,单例
     *
     * @return
     */
    public static FlowContainer getIntance() {
        return Singleton.INSTANCE.getInstance();
    }

    /**
     * 设置缓存流程对象大小
     * <p/>
     * 当缓存中的流程使用完成之后，系统将自动初始化@param initFlowsCount个流程实例
     *
     * @param initFlowsCount 需要初始化的最多流程数量
     */
    public void InitContainer(int initFlowsCount) {
        flowInfoCatch.flowCount = initFlowsCount;
    }

    /**
     * 向流程容器中放入数据,以便启动流程
     *
     * @param object             传入流程的相关数据
     * @param driver             流程驱动
     * @param needCheckCondition 是否需要检查流程启动条件
     */
    public void setData(Object object, FlowDriver driver, boolean needCheckCondition) {
        this.object = object;
        this.driver = driver;
        this.needCheckCondition = needCheckCondition;
        driver.getAllFlow().forEach((flowKey, flowInfo) -> {
            FlowInfo finfo = getFlowInfo(flowKey, flowInfo);
            driver.startFlow(finfo, object, needCheckCondition);
        });
    }

    /**
     * 更新容器中缓存 并将最新的流程全部启动启动
     */
    public synchronized void refreshCatch() {
        driver.getAllFlow().forEach((flowKey, flowInfo) -> {
            FlowInfoCatch flowInfoCatch = new FlowInfoCatch();
            map.clear();
            map.put(flowKey, flowInfoCatch);
            FlowInfo finfo = flowInfoCatch.getFlowInfo(flowInfo);
            driver.startFlow(finfo, object, needCheckCondition);
        });
    }

    private synchronized FlowInfo getFlowInfo(String flowKey
            , FlowInfo flowInfoTemplate) {
        if (map.containsKey(flowKey)) {
            return map.get(flowKey).getFlowInfo(flowInfoTemplate);
        } else {
            FlowInfoCatch flowInfoCatch = new FlowInfoCatch();
            map.put(flowKey, flowInfoCatch);
            return flowInfoCatch.getFlowInfo(flowInfoTemplate);
        }
    }

    private enum Singleton {
        INSTANCE;

        private FlowContainer singleton;

        Singleton() {
            singleton = new FlowContainer();
        }

        public FlowContainer getInstance() {
            return singleton;
        }
    }

    private class FlowInfoCatch {

        public int flowCount = 200;

        volatile ArrayList<FlowInfo> lst = new ArrayList<>();

        public synchronized FlowInfo getFlowInfo(FlowInfo flowInfotemplate) {
            FlowInfo flowInfo = null;
            if (lst.size() > 0) {
                flowInfo = lst.get(0);
                lst.remove(0);
            } else {
                for (int i = 0; i < flowCount; i++) {
                    lst.add(flowInfotemplate.deepClone());
                }
                flowInfo = flowInfotemplate.deepClone();
            }
            return flowInfo;
        }
    }
}
