package com.greatech.workflow.api.node.api;

/**
 * 响应命令最上层接口
 */
public interface Command {

    boolean executeCommand(ConfigParameter configParameter);
}
