//package com.greatech.workflow.dto.comm;
//
//import com.googlecode.aviator.AviatorEvaluator;
////import com.greatech.workflow.api.node.data.BaseMessage;
//import com.greatech.workflow.dto.config.Condition;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by ltao on 2017-9-26.
// * <p>
// * 表达式解析工具类
// *
// * 封装AviatorEvaluator 工具类 进行表达式的解析
// */
//public class ExpressionUtils {
//    private static final Log logger = LogFactory.getLog(ExpressionUtils.class);
//
//    /**
//     * 表达式解析处理方法
//     *
//     * @param condition 条件对象
//     *                  <p>
//     *                  ------------------------------------------------------------------
//     *                  此对象中key为表达式中需要被替换的字符串相同 如a ==1 则 map中需要有key为a的数据
//     *                  支持自定义对象 表达式 如 a.prop == 1 则需要map中有key为a的数据 且value值为a的实例对象
//     *                  对象中有为prop的属性 以及对应的get和set方法
//     *                  <p>
//     *                  !!!必须must使用业务活动的 nodekey  唯一标识作为业务活动在map<string,Object>中的key 前后台统一!!!!!
//     *                  !!!默认的报警信息对象key为BaseMessage !!!
//     * @param datas     表达式数据对象
//     *                  ------------------------------------------------------------------
//     * @return 表达式执行结果
//     */
//    public static Object execute(Condition condition, Object flowNodeData, Map<String, Object> datas) {
//        //新接口 只兼容新数据 如果为配置该表达式 那么返回false 并记录日志
//        String expression = condition.getConditionExpression();
//        if (null == expression || "".equals(expression)) {
//            logger.info("this is no conditionExpression " + condition.getNodeKey());
//            return false;
//        }
//        //配置报警信息的数据对象
//        datas.put(BaseMessage.class.getSimpleName(), flowNodeData);
//        return AviatorEvaluator.execute(expression, datas);
//    }
//
//    /**
//     * 默认的 仅包含报警消息的表达式解析
//     *
//     * @param condition    条件对象
//     * @param flowNodeData 流程中传递的警情对象
//     * @return 表达式执行结果
//     */
//    public static Object defaultExecute(Condition condition, Object flowNodeData) {
//        //新接口 只兼容新数据 如果为配置该表达式 那么返回false 并记录日志
//        String expression = condition.getConditionExpression();
//        if (null == expression || "".equals(expression)) {
//            logger.info("this is no conditionExpression " + condition.getNodeKey());
//            return false;
//        }
//        Map<String, Object> map = new HashMap<>();
//        map.put(BaseMessage.class.getSimpleName(), flowNodeData);
//        return AviatorEvaluator.execute(expression, map);
//    }
//
//}
