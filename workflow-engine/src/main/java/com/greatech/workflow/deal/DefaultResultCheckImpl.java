//package com.greatech.workflow.deal;
//
//import com.greatech.workflow.api.node.api.FlowResultCheck;
////import com.greatech.workflow.dto.comm.ExpressionUtils;
//import com.greatech.workflow.dto.config.Condition;
//
//import java.io.Serializable;
//
///**
// * Created by ltao on 2017-9-25.
// * <p>
// * 系统默认的业务节点 结果处理类
// * ----------------------------------------------------------------------
// * 默认节点只根据流程中 每个业务活动节点传输的flownodedata（报警信息）来做业务活动表达式
// * 如果需要根据业务节点对象的属性进行表达式计算 需要自定义业务结果处理类
// * ----------------------------------------------------------------------
// * <p>
// * 此类为未配置结果检查类的业务活动配置默认的业务类
// * <p>
// */
//public class DefaultResultCheckImpl implements FlowResultCheck, Serializable {
//    @Override
//    public boolean checkResult(Condition condignProperty, Object flowNodeData) {
//        return (boolean) ExpressionUtils.defaultExecute(condignProperty, flowNodeData);
//    }
//
//    public static DefaultResultCheckImpl getInstance() {
//        return DefaultResultCheckEnum.INSTANCE.getInstance();
//    }
//
//    private DefaultResultCheckImpl() {
//    }
//
//    private enum DefaultResultCheckEnum {
//        INSTANCE;
//        private DefaultResultCheckImpl defaultResultCheck = null;
//
//        DefaultResultCheckEnum() {
//            defaultResultCheck = new DefaultResultCheckImpl();
//        }
//
//        DefaultResultCheckImpl getInstance() {
//            return this.defaultResultCheck;
//        }
//    }
//}
