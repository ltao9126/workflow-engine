package com.greatech.workflow.dto.comm;

/**
 * Created by bigbeard on 2017/6/27.
 * 配置业务对象的值类型
 */
public enum PropertyValueType {
    INPUT("文本输入", 1), RADIO("单选", 2), RADIO_TEXT("选择输入", 3), CHECKBOX("多选", 4), CTRL_CHECKBOX("唯一控制", 5), AJAX_CHECKBOX("请求多选", 6), NUM("数字", 7), TEXTAREA("文本", 8), SELECT_INPUT("选择输入", 9), TREE("树", 10), AJAX_TREE("请求树", 11), AJAX_PAGE("请求分页", 12);
    private String name;
    private int index;

    private PropertyValueType(String name, int index) {
        this.name = name;
        this.index = index;

    }

    public static String getName(int index) {
        for (PropertyValueType c : PropertyValueType.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
