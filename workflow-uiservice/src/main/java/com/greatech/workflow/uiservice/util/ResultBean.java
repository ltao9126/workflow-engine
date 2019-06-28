package com.greatech.workflow.uiservice.util;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 *状态返回工具类
 */
public class ResultBean {
    public StatusBean status;
    public FlowBean data;
    public List datas;
    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public FlowBean getData() {
        return data;
    }

    public void setData(FlowBean data) {
        this.data = data;
    }

    public List getDatas() {
        return datas;
    }

    public void setDatas(List datas) {
        this.datas = datas;
    }
}
