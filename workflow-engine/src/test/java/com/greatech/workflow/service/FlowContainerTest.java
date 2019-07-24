package com.greatech.workflow.service;

import org.junit.Test;

public class FlowContainerTest {
    @Test
    public void setData() throws Exception {
        //FlowDriver.getFlowDriver().init();
        FlowContainer.getIntance().setData("asdf", FlowDriver.getFlowDriver(), true);
    }

}