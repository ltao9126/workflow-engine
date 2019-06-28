package com.greatech.workflow.uiservice.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ltao on 17-6-28.
 *
 */
public interface BusActivity {
    public String busActivityName(int page,int size,String path)throws Exception;
    public String busActivityList(String bussCaption,String path)throws Exception;
    public String busActivitySave(HttpServletResponse response, HttpServletRequest request,String path) throws Exception;
    public String busActivityUpdata(HttpServletResponse response, HttpServletRequest request,String path)throws Exception;
    public String busActivityDelete(String bussCaption,HttpServletResponse response,String path)throws Exception;
}

