package com.greatech.workflow.dto.commom;

import com.googlecode.aviator.AviatorEvaluator;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * !!!!!!!!!!!!!!!!!!!!
 * 使用aviator 解析条件表达式
 * 条件表达式
 * <p>
 * 算数运算符
 * <p>
 * 支持常见的算数运算符 支持二元运算符 一元运算符
 * <p>
 * 逻辑运算符
 * <p>
 * 支持的逻辑运算符包括,一元否定运算符!,以及逻辑与的&&,逻辑或的||。逻辑运算符的操作数只能为Boolean。
 * &&和||都执行短路规则
 * <p>
 * 关系运算符
 * <p>
 * 关系运算符包括<, <=, >, >=以及==和!= 。
 * 关系运算符可以作用于Number之间、String之间、Pattern之间、Boolean之间、变量之间以及其他类型与nil之间的关系比较,
 * 不同类型除了nil之外不能相互比较。
 * <p>
 * 位运算符
 * <p>
 * Aviator 支持所有的 Java 位运算符,包括&, |, ^, ~, >>, <<, >>>。
 * <p>
 * 匹配运算符
 * <p>
 * 匹配运算符=~用于String和Pattern的匹配,它的左操作数必须为String,右操作数必须为Pattern。
 * 匹配成功后,Pattern的分组将存于变量$num,num为分组索引。
 * <p>
 * 三元运算符
 * <p>
 * Aviator 没有提供if else语句,但是提供了三元运算符?:,形式为bool ? exp1: exp2。
 * 其中bool必须为Boolean类型的表达式, 而exp1和exp2可以为任何合法的 Aviator 表达式,并且不要求exp1和exp2返回的结果类型一致。
 * <p>
 * 1、接收由前台直接传输的数据
 * 2、条件表达式返回结果为 true/false 业务活动根条件表达式结果判断是否启动业务节点
 * 3、格式为一般表达式格式 需要动态替换的值为  业务活动简单类名.属性名 多级对象同理
 * <p>
 * !!!!!!!!!!!!!!!!!!!
 */

/**
 * Created by ltao on 2017-9-26.
 * <p>
 * 表达式解析工具类
 * <p>
 * 封装AviatorEvaluator 工具类 进行表达式的解析
 */
public class ExpressionUtils {

    public static boolean checkBoolean(String expression) {
        return (Boolean) execute(expression, (Map<String, Object>) null);
    }

    public static boolean checkBoolean(String expression, Map<String, Object> data) {
        Object obj = execute(expression, data);
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        } else {
            return false;
        }
    }

    public static Object execute(String expression, Map<String, Object> data) {
        if (StringUtils.isEmpty(expression)) {
            return false;
        }
        return AviatorEvaluator.execute(expression, data);
    }
}
