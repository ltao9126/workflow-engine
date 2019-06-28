package com.greatech.workflow;

import com.alibaba.fastjson.JSONObject;
//import com.greatech.workflow.api.node.data.BaseMessage;
import com.greatech.workflow.comm.FileUtils;
import com.greatech.workflow.dto.FlowInfo;
import com.greatech.workflow.service.FlowDriver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2017/6/29.
 */
public class Test2 {

    private final static Log logger = LogFactory.getLog(FlowDriver.class);
    String path = "D:/eclipse-jee-neon-3-win32-x86_64/workspace/workflowEngine/workflow-engine/src/test/resources/jsonFile.txt";

    @Test
    public void test() throws IOException {
        FlowDriver flowDriver = FlowDriver.getFlowDriver();
        flowDriver.initFlowByJson(FileUtils.readFileByLine(path));
        flowDriver.addListener((obj) -> {

        });
//        BaseMessage baseMessage = new BaseMessage();
//        baseMessage.setAlarmSysId("设备101");
//        baseMessage.setAlarmPointNo("报警点位101");
//        baseMessage.setAlarmLevel(1);
//        baseMessage.setAlarmType(1);
//        baseMessage.setAlarmDesc("报警描述");
//        flowDriver.startFlow("预案5", baseMessage, true);
        try {
            Thread.sleep(20000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() {
        FlowInfo flowInfo = new FlowInfo();
        System.out.println(JSONObject.toJSONString(flowInfo));
    }

    @Test
    public void test2() {
//        Map<String, TestBean> map = new HashMap<>();
//        TestBean testBean = new TestBean(1, 2, 3);
//        TestBean testBean2 = new TestBean(1, 2, 3);
//
//        System.out.println(testBean.hashCode());
//
//        testBean.setJ(3);
//        System.out.println(testBean.hashCode());
//
//
//        System.out.println(testBean2.hashCode());
//
//
//        map.put(TestBean.class.getSimpleName(), testBean);
//
//        TestBean testBean1 = map.get(TestBean.class.getSimpleName());
//        testBean1.setJ(4);
//
//        TestBean testBean3 = map.get(TestBean.class.getSimpleName());
//        System.out.println(testBean3.getJ());
        //map.put("testBean2", testBean2);
        //Boolean aBoolean = (Boolean) AviatorEvaluator.execute("TestBean.i == 1", map);
        //System.out.println(aBoolean);
    }

    @Test
    public void test3() {
        Map<String, Object> map = new WeakHashMap<>();
        List<String> strings = new ArrayList<>();
        strings.add("b");
        strings.add("a");
        strings.add("c");
        map.put("str", strings);
        //AviatorEvaluator.execute("map(str,println)", map);
        System.out.println(strings);
        System.out.println(map.get("str").hashCode());
        strings.add("d");
        System.out.println(strings.hashCode());
        System.out.println(map.get("str").hashCode());
    }

    @Test
    public void test4() {
        //AtomicInteger atomicInteger = new AtomicInteger(0);
        //ExecutorService executorService = Executors.newFixedThreadPool(10000);
        //ArrayList<Future<Integer>> futures = new ArrayList<>();
        //DefaultResultCheckImpl instance = DefaultResultCheckImpl.getInstance();
        //for (int i = 0; i < 10000; i++) {
        //    Future<Integer> submit = executorService.submit(() -> {
        //        try {
        //            int i1 = new Random().nextInt(10);
        //            Thread.sleep(i1);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //        return instance.test();
        //    });
        //    futures.add(submit);
        //}
        //futures.forEach(future -> {
        //    try {
        //        System.out.println(future.get());
        //    } catch (InterruptedException e) {
        //        e.printStackTrace();
        //    } catch (ExecutionException e) {
        //        e.printStackTrace();
        //    }
        //});
    }

    @Test
    public void test5() {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        atomicInteger.incrementAndGet();
        atomicInteger.incrementAndGet();
        atomicInteger.incrementAndGet();
        System.out.println(atomicInteger.addAndGet(0));

    }

}
