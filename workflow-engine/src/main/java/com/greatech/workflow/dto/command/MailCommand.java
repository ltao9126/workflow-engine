package com.greatech.workflow.dto.command;

import com.greatech.workflow.api.node.api.Command;
import com.greatech.workflow.api.node.api.ConfigParameter;

import java.util.List;

/**
 * 发送邮件命令
 * 
 * TODO 邮箱和端口注入
 */
public class MailCommand implements Command {
    
    private List<String> email;
    
    
    
    @Override
    public boolean executeCommand(ConfigParameter configParameter) {
        return false;
    }
}
