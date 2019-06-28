package com.greatech.workflow.uiservice.controller;

import com.greatech.workflow.uiservice.service.BusActivityImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@Controller
public class BusActivityController {
    String result;

    @Value("${paths}")
    private String paths;

    /**
     * 业务活动列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "busActivityList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void busActivityList(String page, String size, HttpServletResponse response) throws Exception {
        if (page == "") {
            page = "1";
        }
        if (size == "") {
            size = "10";
        }
        String rb = new BusActivityImpl().busActivityName(Integer.parseInt(page), Integer.parseInt(size), paths);
        PrintWriter out = response.getWriter();
        out.write(rb);
        out.flush();
        out.close();
    }

    /**
     * 查看属性详情
     *
     * @return
     */
    @RequestMapping(value = "busActivityData", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void busActivityData(String bussCaption, HttpServletResponse response) throws Exception {
        String rb = new BusActivityImpl().busActivityList(bussCaption, paths);
        PrintWriter out = response.getWriter();
        out.write(rb);
        out.flush();
        out.close();
    }

    /**
     * 保存业务活动配置
     */
    @RequestMapping(value = "busActivitySave", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void busActivitySave(HttpServletResponse response, HttpServletRequest request) {
        try {
            result = new BusActivityImpl().busActivitySave(response, request, paths);
            PrintWriter out = response.getWriter();
            out.write(result);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 业务活动修改
     */
    @RequestMapping(value = "busActivityUpdata", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void busActivityUpdata(HttpServletResponse response, HttpServletRequest request) throws Exception {
        try {
            result = new BusActivityImpl().busActivityUpdata(response, request, paths);
            PrintWriter out = response.getWriter();
            out.write(result);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 业务活动删除
     */
    @RequestMapping(value = "busActivityDelete", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public void busActivityDelete(String bussCaption, HttpServletResponse response) {
        try {
            result = new BusActivityImpl().busActivityDelete(bussCaption, response, paths);
            PrintWriter out = response.getWriter();
            out.write(result);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
