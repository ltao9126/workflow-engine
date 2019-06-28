package com.greatech.workflow.uiservice.util;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */
public class FlowBean {
    public List flow;
    public int total;//总条数
    public int page;
    public int size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List getFlow() {
        return flow;
    }

    public void setFlow(List flow) {
        this.flow = flow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
