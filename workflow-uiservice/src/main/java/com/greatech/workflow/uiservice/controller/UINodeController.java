package com.greatech.workflow.uiservice.controller;


import com.alibaba.fastjson.JSONObject;
import com.greatech.workflow.uiservice.service.UINodeDealImp;
import com.greatech.workflow.uiservice.service.WorkFlowDealImpl;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
@RequestMapping("/uinode")
public class UINodeController {
    String result;
    @Value("${paths}")
    String paths;

    @Value("${path}")
    String path;

    /**
     * UI界面属性数据
     *
     * @param response
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    @RequestMapping(value = "busActivitiesData", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String busActivitiesData(HttpServletResponse response)
            throws IOException, DocumentException {
        //解决跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        return JSONObject.toJSONString(new UINodeDealImp().loadFlow(paths));
    }

    /**
     * 解析json，保存数据
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "save", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void save(HttpServletResponse response, HttpServletRequest request) throws Exception {
        try {
            result = new WorkFlowDealImpl().save(response, request,path);
            PrintWriter out = response.getWriter();
            out.write(result);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 流程名称列表(做分页)
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "flowName", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void flowName(HttpServletResponse response) throws Exception {
        String rb = new WorkFlowDealImpl().flowName(path);
        PrintWriter out = response.getWriter();
        out.write(rb);
        out.flush();
        out.close();
    }

    /**
     * 流程列表
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "flowData", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void flowData(String flowKey, HttpServletResponse response) throws Exception {
        String rb = new WorkFlowDealImpl().flowData(flowKey,path);
        PrintWriter out = response.getWriter();
        out.write(rb);
        out.flush();
        out.close();
    }

    /**
     * 修改流程
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "updateFlow", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void updateFlow(HttpServletResponse response, HttpServletRequest request) throws Exception {
        result = new WorkFlowDealImpl().updateFlow(response, request,path);
        PrintWriter out = response.getWriter();
        out.write(result);
        out.flush();
        out.close();
    }

    /**
     * 删除流程
     *
     * @param flowKey
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "deleteFlow", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void deleteFlow(String flowKey, HttpServletResponse response) throws Exception {
        result = new WorkFlowDealImpl().deleteFlow(flowKey, response,path);
        PrintWriter out = response.getWriter();
        out.write(result);
        out.flush();
        out.close();
    }

    /**
     * 是否启动流程
     */
    @RequestMapping(value = "ableOrDisable", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void ableOrDisable(HttpServletResponse response, HttpServletRequest request) throws IOException {
//        result = new WorkFlowDealImpl().ableOrDisable(response,request);
        PrintWriter out = response.getWriter();
        out.write(result);
        out.flush();
        out.close();
    }

}
