package com.greatech.workflow.deal;

import com.greatech.workflow.api.inner.api.FlowConfigManager;
import com.greatech.workflow.dto.FlowInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/25.
 */
public class FlowConfigManagerImp implements FlowConfigManager {

    private final static Log logger = LogFactory.getLog(FlowConfigManagerImp.class);

    NodeJsonConfigManager nodeJsonConfigManager = new NodeJsonConfigManager();

    @Override
    public FlowInfo loadFlow(String jsonContent) throws Exception {
        FlowInfo flowInfo = nodeJsonConfigManager.loadFlow(jsonContent);
        return flowInfo;
    }

    @Override
    public Map<String, FlowInfo> loadFlow(Collection<String> flows) {
        Map<String, FlowInfo> resFlows = new HashMap<>();
        try {
            Map<String, FlowInfo> jsonFlows = nodeJsonConfigManager.loadFlow(flows);
            resFlows.putAll(jsonFlows);
        } catch (Exception e) {
            logger.error("FlowConfigManagerImp:--------初始化外部流程数据源失败---------", e);
        }
        return resFlows;
    }
}
