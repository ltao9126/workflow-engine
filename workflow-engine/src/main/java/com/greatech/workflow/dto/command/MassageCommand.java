package com.greatech.workflow.dto.command;

import com.greatech.workflow.api.node.api.Command;
import com.greatech.workflow.api.node.api.ConfigParameter;

public class MassageCommand implements Command {
    @Override
    public boolean executeCommand(ConfigParameter configParameter) {
        return false;
    }
}
