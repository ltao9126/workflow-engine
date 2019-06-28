import com.greatech.workflow.service.FlowDriver;
import com.greatech.workflow.uiservice.util.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by Administrator on 2017/6/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-test.xml")
public class Test2 {

    FlowDriver flowDriver = FlowDriver.getFlowDriver();
    String path = "D:/eclipse-jee-neon-3-win32-x86_64/workspace/workflowEngine/workflow-engine/src/test/resources/jsonFile.txt";

    @Test
    public void test() throws IOException {
//        flowDriver.initFlowByJson(FileUtils.readFileByLine(path));
//        flowDriver.addListener(new NotifyNodeExecuteStateImpl());
//        //for (int i = 1; i < 6; i++) {
//        //    flowDriver.startFlow("预案"+i, null);
//        //}
//        flowDriver.startFlow("预案1", null,true);
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void test3() throws Exception {
        //NodeJsonConfigManager nodeManager = new NodeJsonConfigManager();
        //Map<String, FlowInfo> flows = nodeManager.loadFlow();
        //flows.forEach((key, value) -> {
        //    System.out.println(key + "  " + value);
        //});
    }
}
