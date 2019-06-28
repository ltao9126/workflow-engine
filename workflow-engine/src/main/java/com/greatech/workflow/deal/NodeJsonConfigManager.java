package com.greatech.workflow.deal;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.greatech.workflow.dto.ClassConfig;
import com.greatech.workflow.dto.FlowConfigNode;
import com.greatech.workflow.dto.FlowInfo;
import com.greatech.workflow.dto.comm.FlowEnum;
import com.greatech.workflow.dto.comm.RefUtils;
import com.greatech.workflow.dto.config.BussObject;
import com.greatech.workflow.dto.config.Condition;
import com.greatech.workflow.dto.config.ConditionClassConfig;
import com.greatech.workflow.dto.config.ResultObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * json格式流程预案
 * <p>
 * 解析json格式的工作流预案
 */
public class NodeJsonConfigManager {

    private final static Log logger = LogFactory.getLog(NodeJsonConfigManager.class);

//    @Value("${config.filepath}")
//    private String path;
//
//    /**
//     * 从存储文件中读取json内容加载流程
//     *
//     * @return
//     * @throws Exception
//     */
//    public Map<String, FlowInfo> loadFlow() throws Exception {
//        Map<String, FlowInfo> map = new HashMap<>();
//        String[] stirs = FileUtils.readFileByLine(path);
//        for (String stir : stirs) {
//            loadFlow(stir,map);
//        }
//        return map;
//    }

    /**
     * 从外部传入json内容加载流程
     *
     * @param flows 流程列表
     * @return
     * @throws Exception
     */
    public Map<String, FlowInfo> loadFlow(Collection<String> flows) {
        Map<String, FlowInfo> map = new HashMap<>();
        if (null == flows) return map;
        for (String stir : flows) {
            loadFlow(stir, map);
        }
        return map;
    }

    private void loadFlow(String content, Map<String, FlowInfo> map) {
        FlowInfo flowInfo = null;
        try {
            flowInfo = loadFlow(content);
        } catch (Exception e) {
            logger.error("NodeJsonConfigManager:--流程初始化异常--" + content, e);
        }
        if (null != flowInfo) {
            map.put(flowInfo.getFlowKey(), flowInfo);
        }
    }

    /**
     * 从json内容加载流程
     *
     * @param content json文本内容
     * @return
     * @throws Exception
     */
    public FlowInfo loadFlow(String content) throws Exception {
        FlowInfo flowInfo = new FlowInfo();
        JSONObject jsonObj = JSONObject.parseObject(content);
        if (null == content || null == jsonObj) {
            return null;
        }
        //解析流程
        for (FlowEnum flowEnum : FlowEnum.values()) {
            String str = jsonObj.getString(flowEnum.name());
            switch (flowEnum) {
                case flowKey:
                    flowInfo.setFlowKey(str == null ? "" : str.trim());
                    break;
                case flowTable:
                    flowInfo.addAll(parseFlowConfigNode(str));
                    break;
                case startCheckClass:
                    if (null != str && !"".endsWith(str))
                        flowInfo.setFlowStartCheck(RefUtils.instanceBean(str));
                    break;
                case startConditions:
                    flowInfo.setStartConditions(parseConditionClassConfig(str));
                    break;
                default:
                    break;
            }
        }
        return flowInfo;
    }

    /**
     * 解析 FlowConfigNode
     *
     * @return
     */
    private List<FlowConfigNode> parseFlowConfigNode(String flowConfigJson) {
        List<FlowConfigNode> flowConfigNodes = new ArrayList<>();
        //获取flowTable列表
        JSONObject flowTables = JSONObject.parseObject(flowConfigJson);
        for (String key : flowTables.keySet()) {
            FlowConfigNode flowConfigNode = new FlowConfigNode();
            flowConfigNode.setNodeKey(key);
            JSONObject flowNode = flowTables.getJSONObject(key);
            //根据属性解析json
            for (FlowEnum.NodePropEnum fe : FlowEnum.NodePropEnum.values()) {
                String str = flowNode.getString(fe.name());
                switch (fe) {
                    case nodeKey:
                        flowConfigNode.setNodeKey(str == null ? "" : str.trim());
                        break;
                    case bussObject:
                        flowConfigNode.setBussObject(parseBussObject(flowNode.getJSONObject(fe.name())));
                        break;
                    case endNode:
                        flowConfigNode.setEndNode(str == null ? false : Boolean.valueOf(str));
                        break;
                    case startNode:
                        flowConfigNode.setStartNode(str == null ? false : Boolean.valueOf(str));
                        break;
                    case resultObject:
                        flowConfigNode.setResultObject(parseResultObject(flowNode.getJSONObject(fe.name())));
                        break;

                    default:
                        break;
                }
            }
            flowConfigNodes.add(flowConfigNode);
        }
        return flowConfigNodes;

    }

    /**
     * 解析ResultObject
     *
     * @param jsonObject
     * @return
     */
    private ResultObject parseResultObject(JSONObject jsonObject) {
        ResultObject resultObject = new ResultObject();
        for (FlowEnum.ResultPropEnum fr : FlowEnum.ResultPropEnum.values()) {
            String str = jsonObject.getString(fr.name());
            switch (fr) {
                case defaultNodeKey:
                    resultObject.setDefaultNodeKey(str);
                    break;
                case waitTime:
                    int wait = -1;
                    try {
                        wait = Integer.valueOf(str);
                    } catch (NumberFormatException e) {
                        logger.error("NodeJsonConfigManager：waitTime 解析出错 使用默认值 " + str, e);
                    }
                    resultObject.setWaitTime(wait);
                    break;
                case resultClass:
                    resultObject.setResultClass(str.trim());
                    break;
                case resultConditions:
                    resultObject.setResultConditions(parseConditions(str));
                    break;
                case startSubFlow:
                    resultObject.setStartSubFlow(str);
                    break;
                case needStartSubFlow:
                    resultObject.setNeedStartSubFlow(Boolean.valueOf(str));
                    break;
                default:
                    break;
            }
        }
        return resultObject;
    }

    private List<Condition> parseConditions(String str) {
        List<Condition> resultConditions = new ArrayList<>();
        JSONArray array = JSONObject.parseArray(str);
        if (null == array) return resultConditions;
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            Condition condition = new Condition();
            for (FlowEnum.ResultConditionsPropEnum fr :
                    FlowEnum.ResultConditionsPropEnum.values()) {
                String temp = jsonObject.getString(fr.name());
                temp = null == temp ? "" : temp.trim();
                switch (fr) {
                    case classConfigs:
                        condition.setClassConfigs(parseConditionClassConfig(temp));
                        break;
                    case needStartSubFlow:
                        condition.setNeedStartSubFlow(Boolean.valueOf(temp));
                        break;
                    case nodeKey:
                        condition.setNodeKey(temp);
                        break;
                    case conditionExpression:
                        condition.setConditionExpression(temp);
                        break;
                    case order:
                        condition.setOrder("".equals(temp) ? 0 : Integer.valueOf(temp));
                        break;
                    case startSubFlow:
                        condition.setStartSubFlow(temp);
                        break;
                    default:
                        break;
                }
            }
            resultConditions.add(condition);
        }

        return resultConditions;
    }

    private List<ConditionClassConfig> parseConditionClassConfig(String str) {
        List<ConditionClassConfig> conditionClassConfigs = new ArrayList<>();
        JSONArray arrays = JSONObject.parseArray(str);
        if (null == arrays) return conditionClassConfigs;
        for (int i = 0; i < arrays.size(); i++) {
            JSONObject jsonObject = arrays.getJSONObject(i);
            ConditionClassConfig conditionClassConfig = new ConditionClassConfig();

            for (FlowEnum.ClassConfigsPropEnum fc :
                    FlowEnum.ClassConfigsPropEnum.values()) {
                String temp = jsonObject.getString(fc.name());
                switch (fc) {
                    case operaterType:
                        conditionClassConfig.setOperaterType(temp);
                        break;
                    case value:
                        conditionClassConfig.setValue(temp);
                        break;
                    case property:
                        conditionClassConfig.setProperty(temp.trim());
                        break;
                    default:
                        break;
                }
            }
            conditionClassConfigs.add(conditionClassConfig);
        }
        return conditionClassConfigs;
    }

    /**
     * 解析 BussObject
     *
     * @param jsonObject
     * @return
     */
    private BussObject parseBussObject(JSONObject jsonObject) {
        BussObject bussObject = new BussObject();
        bussObject.setBussClass(jsonObject.getString(FlowEnum.BussObjectPropEnum.bussClass.name()).trim());
        bussObject.setClassConfigList(parseClassConfig(jsonObject.getString(FlowEnum.BussObjectPropEnum.classConfigList.name())));
        return bussObject;
    }

    /**
     * 解析classconfig属性
     *
     * @param str
     * @return
     */
    private List<ClassConfig> parseClassConfig(String str) {
        List<ClassConfig> classConfigs = new ArrayList<>();
        try {

            JSONArray arrays = JSONObject.parseArray(str);
            if (null == arrays) return classConfigs;
            for (int i = 0; i < arrays.size(); i++) {
                JSONObject jsonObject = arrays.getJSONObject(i);
                ClassConfig classConfig = new ClassConfig();
                classConfig.setProperty(jsonObject.getString(FlowEnum.ClassConfigListPropEnum.property.name()).trim());
                classConfig.setValue(jsonObject.get(FlowEnum.ClassConfigListPropEnum.value.name()));
                classConfig.setPropertyType(jsonObject.getString(FlowEnum.ClassConfigListPropEnum.propertyType.name()));
                classConfigs.add(classConfig);
            }
        } catch (Exception e) {
            logger.error("NodeJsonConfigManager：解析ClassConfig属性出错", e);
        }
        return classConfigs;
    }
}
