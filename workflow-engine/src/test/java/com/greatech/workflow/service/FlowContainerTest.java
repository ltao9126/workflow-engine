package com.greatech.workflow.service;

import org.junit.Test;

/**
 * Created by bigbeard on 2017/7/17.
 */

public class FlowContainerTest {
    @Test
    public void setData() throws Exception {
        //FlowDriver.getFlowDriver().init();
        FlowContainer.getIntance().setData("asdf", FlowDriver.getFlowDriver(), true);
    }

}