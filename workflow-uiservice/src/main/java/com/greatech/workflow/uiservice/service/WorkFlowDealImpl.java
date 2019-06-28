package com.greatech.workflow.uiservice.service;

import com.alibaba.fastjson.JSONObject;
import com.greatech.workflow.uiservice.api.WorkFlowDeal;
import com.greatech.workflow.uiservice.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/18.
 */
public class WorkFlowDealImpl implements WorkFlowDeal {
    StatusBean sb = new StatusBean();
    FlowBean fb = new FlowBean();

    /*Map pathS = FileUtils.getPath();
    String path = (String) pathS.get("path");*/

    private String flowtxt = "flow.txt";
    private String alltxt = "all.txt";
    List<FlowUtil> list = new ArrayList<FlowUtil>();
    List<Object> oList = new ArrayList<Object>();
    List<String> list1 = new ArrayList<String>();

    /**
     * 保存流程数据
     *
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public String save(HttpServletResponse response, HttpServletRequest request,String path) throws Exception {
        ResultBean rb = new ResultBean();
        try {
            String data = org.apache.commons.io.IOUtils.toString(request.getInputStream(), "UTF-8");
            String flowData = JSONObject.parseObject(data).getString("flowData");  //获取flowData部分数据
            String flowKey = JSONObject.parseObject(flowData).getString("flowKey");  //获取新增流程key
            boolean find = false;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            } else {
                Collection<String> allJson = FileUtils.readFileByLine(path + flowtxt);    //获取txt文档中的流程数据
                if (allJson.size() > 0) {
                    for (String json : allJson) {
                        if (json == null || json.equals("")) {
                        } else {
                            String flowKeys = JSONObject.parseObject(json).getString("flowKey");   //获取TXT文件中流程key，看是否重复
                            if (flowKey.equals(flowKeys)) {
                                find = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (find) {
                sb.setSuccess("NO");
                sb.setErrorMsg("新增失败");
            } else {
                sb.setSuccess("OK");
                sb.setErrorMsg("null");
                FileUtils.writeFile4Line(path + alltxt, JSONObject.parseObject(data).toString());
                FileUtils.writeFile4Line(path + flowtxt, flowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.setSuccess("NO");
            sb.setErrorMsg("新增失败");
        }
        fb.setFlow(null);
        rb.setStatus(sb);
        rb.setData(fb);
        return JSONObject.toJSONString(rb);
    }

    /**
     * 流程名称列表(做分页)
     *
     * @return
     * @throws Exception
     */
    @Override
    public String flowName(String path) throws Exception {
        ResultBean rb = new ResultBean();
        try {
            for (String json : FileUtils.readFileByLine(path + alltxt)) {
                String flowData = JSONObject.parseObject(json).getString("flowData");
                FlowUtil fu = new FlowUtil();
                String flowKey = JSONObject.parseObject(flowData).getString("flowKey");
                String flowName = JSONObject.parseObject(flowData).getString("flowName");
                String isenable = JSONObject.parseObject(flowData).getString("isenable");
                fu.setFlowKey(flowKey);
                fu.setFlowName(flowName);
                fu.setIsenable(Integer.parseInt(isenable));
                fu.setCreateTime(flowKey);
                fu.setJsonString(json);
                list.add(fu);
            }
            sb.setSuccess("OK");
            sb.setErrorMsg("null");
            fb.setFlow(list);
            rb.setStatus(sb);
            rb.setData(fb);
            return JSONObject.toJSONString(rb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(rb);
    }

    /**
     * 流程列表数据
     *
     * @param flowKey
     * @return
     * @throws Exception
     */
    @Override
    public String flowData(String flowKey,String path) throws Exception {
        ResultBean rb = new ResultBean();
        for (String json : FileUtils.readFileByLine(path + alltxt)) {
            String flowData = JSONObject.parseObject(json).getString("flowData");
            String svgData = JSONObject.parseObject(json).getString("svgData");
            String flowKeys = JSONObject.parseObject(flowData).getString("flowKey");//获取流程flowKey
            String flowName = JSONObject.parseObject(flowData).getString("flowName");
            if (flowKeys.equals(flowKey)) {
                FlowUtil fu = new FlowUtil();
                fu.setFlowKey(flowKey);
                fu.setFlowName(flowName);
                fu.setFlowData(flowData);
                fu.setSvgData(svgData);
                list.add(fu);
                sb.setSuccess("OK");
                sb.setErrorMsg("null");
                rb.setStatus(sb);
                rb.setDatas(list);
                return JSONObject.toJSONString(rb);
            }
        }
        return JSONObject.toJSONString(rb);
    }

    /**
     * 修改流程
     *
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public String updateFlow(HttpServletResponse response, HttpServletRequest request,String path) throws Exception {
        ResultBean rb = new ResultBean();
        try {
            String data = org.apache.commons.io.IOUtils.toString(request.getInputStream(), "UTF-8");
            String flows = JSONObject.parseObject(data).getString("flowData");  //获取修改流程的flowData数据
            String flowKey = JSONObject.parseObject(flows).getString("flowKey");
            for (String json : FileUtils.readFileByLine(path + alltxt)) {
                JSONObject flowJson = JSONObject.parseObject(json);
                String flow = flowJson.getString("flowData");   //获取TXT中的flowData数据
                String flowKeys = JSONObject.parseObject(flow).getString("flowKey");   //获取txt中的流程key
                //修改flowKey是否存在
                if (!flowKeys.equals(flowKey)) {
                    oList.add(flowJson);
                    list1.add(flow);
                }
            }
            oList.add(data);
            list1.add(flows);
            this.file(oList, list1,path);
            sb.setSuccess("OK");
            sb.setErrorMsg("null");
        } catch (Exception e) {
            e.printStackTrace();
            sb.setSuccess("NO");
            sb.setErrorMsg("修改失败");
        }
        fb.setFlow(null);
        rb.setStatus(sb);
        rb.setData(fb);
        return JSONObject.toJSONString(rb);
    }

    /**
     * 删除流程
     *
     * @param flowKey
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public String deleteFlow(String flowKey, HttpServletResponse response,String path) throws Exception {
        ResultBean rb = new ResultBean();
        try {
            for (String json : FileUtils.readFileByLine(path + alltxt)) {
                JSONObject flowJson = JSONObject.parseObject(json);
                String flow = flowJson.getString("flowData");
                String flowKeys = JSONObject.parseObject(flow).getString("flowKey");  //获取txt中的flowKey
                if (flowKeys.equals(flowKey)) {
                    flowJson.remove(flowJson);
                } else {
                    oList.add(flowJson);
                    list1.add(flow);
                }
            }
            this.file(oList, list1,path);
            sb.setSuccess("OK");
            sb.setErrorMsg("null");
        } catch (Exception e) {
            e.printStackTrace();
            sb.setSuccess("NO");
            sb.setErrorMsg("删除失败");
        }
        fb.setFlow(null);
        rb.setStatus(sb);
        rb.setData(fb);
        return JSONObject.toJSONString(rb);
    }

    //删除文件(通用方法)
    public void file(List list, List list1,String path) throws IOException {
        File file = new File(path);
        File[] files = file.listFiles();
        for (File fi : files) {
            fi.delete();
        }
        for (Object jsons : list) {
            FileUtils.writeFile4Line(path + alltxt, jsons.toString());
        }
        for (Object jsons : list1) {
            FileUtils.writeFile4Line(path + flowtxt, jsons.toString());
        }
    }

}
