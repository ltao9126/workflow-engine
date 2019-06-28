package com.greatech.workflow.uiservice.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 流程业务接口
 */
public interface WorkFlowDeal {
    public String save(HttpServletResponse response, HttpServletRequest request,String path) throws Exception;
    public String flowName(String path)throws Exception;
    public String flowData(String flowKey,String path)throws Exception;
    public String updateFlow(HttpServletResponse response, HttpServletRequest request,String path)throws Exception;
    public String deleteFlow(String flowKey, HttpServletResponse response,String path) throws Exception;
}
