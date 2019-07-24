package com.greatech.workflow.dto;

import java.util.Arrays;
import java.util.List;

/**
 * 配置判断业务对象支持的操作符
 */
public enum OperateType {
    GREATER_THEN("大于", 1),
    LESS_THEN("小于", 2),
    GREATER_EQUEL("大于等于", 3),
    LESS_EQUEL("小于等于", 4),
    EQUEL("等于", 5),
    CONTAIN("包含", 6),
    IN("在..里面", 7),
    EXPRESSION("表达式", 8);

    private static OperateType[] tables = values();

    private String name;
    private int index;

    OperateType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        OperateType operateType = valueOfIndex(index);
        return operateType == null ? null : operateType.getName();
    }

    public static OperateType valueOfIndex(int index) {
        if (index < 1 || index > 8) return null;
        return tables[index - 1];
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

    /**
     * 获取当前所有枚举对象
     *
     * @return 返回枚举值得集合
     */
    public List<OperateType> getAll() {
        return Arrays.asList(OperateType.values());
    }

}
