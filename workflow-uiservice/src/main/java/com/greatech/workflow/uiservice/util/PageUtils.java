package com.greatech.workflow.uiservice.util;

/**
 * Created by Administrator on 2017/7/24.
 * 分页工具类
 */
public class PageUtils {
    //当前页
    private int curPage;
    //总页数
    private int pageCount;
    //总行数
    private int rowsCount;
    //每页显示条数
    private int size;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public PageUtils(int rows,int size){
        this.setRowsCount(rows);
        this.setSize(size);
        if(this.rowsCount % this.size == 0){
            this.pageCount=this.rowsCount / this.size;
        }
        else if(rows<this.size){
            this.pageCount=1;
        }
        else{
            this.pageCount=this.rowsCount / this.size +1;
        }
    }
}
