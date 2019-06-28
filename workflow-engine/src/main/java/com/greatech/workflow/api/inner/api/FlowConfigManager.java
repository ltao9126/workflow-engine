package com.greatech.workflow.api.inner.api;

import com.greatech.workflow.dto.FlowInfo;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/25.
 */
public interface FlowConfigManager {

    /**
     * 加载单个流程
     *
     * @param jsonContent 流程json内容
     * @return 流程对象
     */
    FlowInfo loadFlow(String jsonContent) throws Exception;

    /**
     * 外部 数据源加载流程数据
     *
     * @param flows 外部流程数据源列表
     * @return Map
     */
    Map<String, FlowInfo> loadFlow(Collection<String> flows);
}
