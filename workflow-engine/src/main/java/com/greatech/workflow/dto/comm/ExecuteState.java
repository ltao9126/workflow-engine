package com.greatech.workflow.dto.comm;

/**
 * Created by bigbeard on 17-7-6.
 * 运行状态枚举类
 */
public enum ExecuteState {
    /**
     * 已执行表示已经触发执行节点，但是不保证执行状态  执行结束 表示该节点已经执行完毕
     */
    prepare("准备", 0), executing("执行中", 1), finish("执行结束", 2), interrupt("中断", 3), exception("异常", 4), executed("已经执", 5);

    private String text;
    private int idnex;

    ExecuteState(String text, int index) {
        this.text = text;
        this.idnex = index;
    }

    /**
     * 根绝显示名称获取枚举
     *
     * @param t
     * @return
     */
    public ExecuteState getByText(String t) {
        for (ExecuteState executeState : values()) {
            if (executeState.getText().equals(t)) {
                return executeState;
            }
        }
        return null;
    }

    /**
     * 根绝显示名称获取枚举
     *
     * @param i
     * @return
     */
    public ExecuteState getByIndex(int i) {
        for (ExecuteState executeState : values()) {
            if (executeState.getIdnex() == i) {
                return executeState;
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIdnex() {
        return idnex;
    }

    public void setIdnex(int idnex) {
        this.idnex = idnex;
    }
}
