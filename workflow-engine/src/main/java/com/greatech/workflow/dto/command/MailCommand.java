package com.greatech.workflow.dto.command;

import com.greatech.workflow.api.node.api.Command;
import com.greatech.workflow.api.node.api.ConfigParameter;

import java.util.List;

/**
 * �����ʼ�����
 * 
 * TODO ����Ͷ˿�ע��
 */
public class MailCommand implements Command {
    
    private List<String> email;
    
    
    
    @Override
    public boolean executeCommand(ConfigParameter configParameter) {
        return false;
    }
}
