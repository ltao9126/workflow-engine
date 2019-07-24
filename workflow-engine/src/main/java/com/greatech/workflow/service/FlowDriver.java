package com.greatech.workflow.service;

import com.greatech.workflow.api.inner.api.FlowConfigManager;
import com.greatech.workflow.api.node.api.FlowNode;
import com.greatech.workflow.api.node.api.FlowStartCallback;
import com.greatech.workflow.api.node.api.NotifyNodeExecuteState;
import com.greatech.workflow.api.node.data.NotifyFlowInfo;
import com.greatech.workflow.api.node.data.NotifyNodeExecuteData;
import com.greatech.workflow.deal.FlowConfigManagerImp;
import com.greatech.workflow.dto.FlowConfigNode;
import com.greatech.workflow.dto.FlowExecBean;
import com.greatech.workflow.dto.FlowInfo;
import com.greatech.workflow.dto.comm.ExecuteState;
import com.greatech.workflow.dto.config.Condition;
import com.greatech.workflow.dto.config.ResultObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.util.concurrent.*;

//import com.greatech.workflow.api.node.data.BaseMessage;
//import com.greatech.workflow.dto.FlowInfoRuntimeData;


/**
 * 流程驱动器
 * 当前逻辑为：如果为非等待的情况下
 * 一直循环的取满足条件的节点
 * 然后请求线程池的线程资源进行执行
 */
public class FlowDriver {

    private final static Log logger = LogFactory.getLog(FlowDriver.class);
    //允许同时执行的最大执行数
    private static final Semaphore semaphore = new Semaphore(1000);
    //执行节点队列
    private final ArrayBlockingQueue<FlowExecBean> exeFlowsQueue = new ArrayBlockingQueue<>(1000);
    /**
     * 所有的流程信息，key表明流程名称唯一
     */
    private Map<String, FlowInfo> allFlow = new ConcurrentHashMap<>();

    //流程引擎运行时数据存储集合 保存flowinfo hashcode值 和 flowinfo运行时产生的数据对象
    //private Map<Integer, FlowInfoRuntimeData> runtimeDataMap = new ConcurrentHashMap<>();
    //执行业务线程池
    private ExecutorService executor = Executors.newFixedThreadPool(1000);
    //通知类线程池
    private ExecutorService notifyExecutor = Executors.newFixedThreadPool(1000);

    private FlowConfigManager flowConfigManager = new FlowConfigManagerImp();
    //消息通知监听
    private NotifyNodeExecuteState notifyExecStateListener;

    private FlowStartCallback flowStartCallback = null;

    //执行状态
    private volatile boolean RUN = false;

    private FlowDriver() {
    }

    /**
     * 获取单例实例
     *
     * @return FlowDriver
     */
    public static FlowDriver getFlowDriver() {
        return FlowDriverInstance.INSTANCE.getInstance();
    }

    /**
     * remove flow
     *
     * @param key need to remove flow key
     */
    public void removeFlow(String key) {
        synchronized (allFlow) {
            allFlow.remove(key);
        }
    }

    /**
     * 动态追加流程
     *
     * @param flowList 流程json集合
     */
    public void addFlow(Collection<String> flowList) {
        //解析流程
        Map<String, FlowInfo> appendFlow = flowConfigManager.loadFlow(flowList);
        //初始化流程
        appendFlow.values().forEach(FlowInfo::initNode);
        //追加到allflow中
        allFlow.putAll(appendFlow);
    }

    /**
     * 外部flowinfo 流程集合 初始化流程数据
     *
     * @param flowList FlowInfo 流程集合列表
     */
    public void initByFlowInfos(Collection<FlowInfo> flowList) {
        allFlow.clear();
        for (FlowInfo flowInfo : flowList) {
            allFlow.put(flowInfo.getFlowKey(), flowInfo);
        }
        initFlow();
    }

    /**
     * 外部有参 初始化获取流程数据
     *
     * @param flowList 流程预案json 集合
     */
    public void initFlowByJson(Collection<String> flowList) {
        if (null != flowList) {
            allFlow = flowConfigManager.loadFlow(flowList);
        }
        initFlow();
    }

    /**
     * 初始化启动流程
     */
    private void initFlow() {
        allFlow.values().forEach(FlowInfo::initNode);
        //开起执行线程
        if (!RUN) {
            RUN = true;
            new Thread(() -> {
                while (RUN) {
                    FlowExecBean flowExecBean = null;
                    try {
                        flowExecBean = exeFlowsQueue.take();
                        executeFlowNode(flowExecBean.getFlowConfigNode(),
                                flowExecBean.getFlowInfo(), flowExecBean.getFlowNodeData());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        logger.error("flowDirver:执行被中断", e);
                    }
                }
            }).start();
        }
    }

    /**
     * 启动流程,启动流程的时候不会启动新的流程实例.使用缓存中的流程实例.
     *
     * @param flowKey      流程key
     * @param flowNodeData 流程节点上下文 以及节点信息
     * @return 启动结果 true 启动成功  false 启动失败
     */
    public boolean startFlow(String flowKey, Object flowNodeData, boolean needCheckCondition) {
        if (!allFlow.containsKey(flowKey)) {
            logger.error("flowDirver:流程：" + flowKey + "没找到，请确定配置正确");
            return false;
        }
        FlowInfo flowInfo = allFlow.get(flowKey);
        //根据KEY启动流程  以克隆流程的形式进行执行 避免多次启动流程 造成流程执行状态和数据混乱
        return startFlow((FlowInfo) flowInfo.deepClone(), flowNodeData, needCheckCondition);
    }

    /**
     * 启动流程
     *
     * @param flowInfo           流程对象
     * @param flowNodeData       流程上下文数据
     * @param needCheckCondition 是否需要验证流程启动条件
     * @return true/false
     */
    public boolean startFlow(FlowInfo flowInfo, Object flowNodeData, boolean needCheckCondition) {
        if (null == flowInfo) {
            logger.error("flowDirver:流程对象为null在启动方法:startFlow(FlowInfo,Object)中");
            return false;
        }
        //流程启动条件校验
        if (needCheckCondition
                && null != flowInfo.getFlowStartCheck()
                && null != flowInfo.getStartConditions()
                && flowInfo.getStartConditions().size() > 0) {
            if (!flowInfo.getFlowStartCheck()
                    .checkCanStart(flowInfo.getStartConditions())) {
                logger.warn("flowDirver:流程" + flowInfo.getFlowKey() + "不满足启动条件,系统不会启动该流程");
                return false;
            }
        }

        FlowConfigNode firstNode = flowInfo.getFirstNode();
        if (firstNode == null) {
            logger.error("flowDirver:流程：" + flowInfo.getFlowKey() + "找不到开始节点，无法运行");
            return false;
        }

        //启动流程初始化运行时数据域
//        int flowInfoHashCode = flowInfo.hashCode();
//        runtimeDataMap.put(flowInfoHashCode,
//                new FlowInfoRuntimeData((BaseMessage) flowNodeData, firstNode, flowInfoHashCode));

        if (null != flowStartCallback) {
            NotifyFlowInfo notifyFlowInfo = new NotifyFlowInfo();
            notifyFlowInfo.setFlowKey(flowInfo.getFlowKey());

            HashMap<String, FlowNode> allBussClassObject = flowInfo.getAllBussClassObject();

            notifyFlowInfo.setFlowNodeHashMap(allBussClassObject);
            flowStartCallback.notifyFlowStart(notifyFlowInfo);
        }
        //使用统一入口 执行节点
        try {
            exeFlowsQueue.put(new FlowExecBean(firstNode, flowInfo, flowNodeData));
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("flowDirver:等待线程被打断", e);
        }
        //executeFlowNode(firstNode, flowInfo, flowNodeData);
        return true;
    }

    /**
     * 执行流程节点逻辑
     *
     * @param flowConfigNode 流程配置节点
     * @param flowInfo       流程对象
     * @param flowNodeData   流程中数据对象
     */
    private void executeFlowNode(FlowConfigNode flowConfigNode, FlowInfo flowInfo, Object flowNodeData) {

        if (null == flowConfigNode) {
            return;
        }

        ResultObject resultObject = flowConfigNode.getResultObject();
        FlowNode bussFlowNode = flowConfigNode.getBussObject().getBussFlowNode();
        if (null == bussFlowNode) {
            logger.error("flowDirver:未配置业务节点对象");
            return;
        }
        //设置当前节点的flownodedata为上一节点的执行结果返回
        bussFlowNode.setFlowNodeData(flowNodeData);
        //获取 执行操作的权限 控制正在执行的操作数量
        try {
            semaphore.acquire(1);
        } catch (InterruptedException e) {
            logger.error("flowDirver:interruped exception the semaphore acquire waiting", e);
        }
        notifyState(new NotifyNodeExecuteData(System.currentTimeMillis(), null
                , flowConfigNode.getNodeKey(), flowInfo.getFlowKey(), ExecuteState.prepare));

        // 需要执行等待操作
        if (resultObject.getWaitTime() < 0) {
            //注册回调监听
            //如果已经存在为取消注册的监听 那么不需要再次注册
            if (null == bussFlowNode.getExecuteOverNotify()) {
                bussFlowNode.setExecuteOverNotify((obj, unregister) -> {
                    bussFlowNode.setFlowNodeData(obj);
                    //通知流程对象执行完毕
                    notifyState(new NotifyNodeExecuteData(System.currentTimeMillis(), obj, flowConfigNode.getNodeKey()
                            , flowInfo.getFlowKey(), ExecuteState.finish));
                    //执行当前流程下一个节点
                    executeNextNode(getNextNodeCondition(flowConfigNode, obj), flowInfo, obj);
                    if (unregister) {
                        bussFlowNode.setExecuteOverNotify(null);
                    }
                });
            }
            //执行当前节点操作
            execute(bussFlowNode);
        } else if (resultObject.getWaitTime() > 0) {
            //注册回调监听
            //如果已经存在为取消注册的监听 那么不需要再次注册
            if (null == bussFlowNode.getExecuteOverNotify()) {
                bussFlowNode.setExecuteOverNotify((obj, unregister) -> {
                    bussFlowNode.setFlowNodeData(obj);
                    if (unregister) {
                        bussFlowNode.setExecuteOverNotify(null);
                    }
                });
            }
            //事件状态
            ExecuteState tempState = ExecuteState.executed;
            //执行等待操作 超时则放弃等待子线程结果
            Future<?> future = execute(bussFlowNode);
            try {
                future.get(resultObject.getWaitTime(), TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                logger.error("flowDirver:等待执行节点操作异常", e);
                tempState = ExecuteState.exception;
                e.printStackTrace();
            } finally {
                //通知流程对象执行完毕
                notifyState(new NotifyNodeExecuteData(System.currentTimeMillis(), bussFlowNode.getFlowNodeData()
                        , flowConfigNode.getNodeKey(), flowInfo.getFlowKey(), tempState));
                //执行下个节点
                executeNextNode(getNextNodeCondition(flowConfigNode, bussFlowNode.getFlowNodeData())
                        , flowInfo, bussFlowNode.getFlowNodeData());
            }
        } else {
            //执行当前节点操作
            execute(bussFlowNode);
            //通知流程对象执行完毕
            notifyState(new NotifyNodeExecuteData(System.currentTimeMillis(), bussFlowNode.getFlowNodeData()
                    , flowConfigNode.getNodeKey(), flowInfo.getFlowKey(), ExecuteState.executed));
            //执行下个节点
            executeNextNode(getNextNodeCondition(flowConfigNode, bussFlowNode.getFlowNodeData())
                    , flowInfo, bussFlowNode.getFlowNodeData());
        }
        if (flowConfigNode.getEndNode()) {
            logger.debug("flowDirver:" + flowInfo.getFlowKey()
                    + "最后一个节点[" + flowConfigNode.getNodeKey() + ","
                    + flowConfigNode.getBussObject().getBussClass()
                    + "]对象[IsEndNode=true]");
        }
    }

    /**
     * 获取下一个满足条件的condition
     *
     * @param cur_FlowConfigNode 当前流程业务节点
     * @param flowNodeData       流程节点对象
     * @return 下一节点
     */
    private List<Condition> getNextNodeCondition(FlowConfigNode cur_FlowConfigNode, Object flowNodeData) {
        ResultObject resultObject = cur_FlowConfigNode.getResultObject();
        List<Condition> conditions = new ArrayList<>();
        List<Condition> lstConditions = resultObject.getResultConditions();
        //判断是否是最后一个节点 最后一个节点 直接返回空列表
        if (cur_FlowConfigNode.isEndNode()) {
            return conditions;
        }
        if (null != resultObject.getFlowResultCheck()) { //结果判定对象为空表面没有配置结果检测对象,直接走默认节点
            logger.debug("------flowDirver:开始检查流程[" + cur_FlowConfigNode.getNodeKey() + "]节点执行条件------");
            if (lstConditions != null && !lstConditions.isEmpty()) {
                for (Condition cond : lstConditions) {
                    boolean b = resultObject.getFlowResultCheck().checkResult(cond, flowNodeData);
                    if (b) {
                        conditions.add(cond);
                    }
                }
            }
            logger.debug("-------flowDirver:结束检查流程[" + cur_FlowConfigNode.getNodeKey() + "]节点执行条件------");
            return conditions;
        } else {
            logger.debug("------flowDirver:流程节点[" + cur_FlowConfigNode.getNodeKey() + "]使用默认节点条状------");
            Condition condition = new Condition();
            condition.setNodeKey(resultObject.getDefaultNodeKey());
            condition.setNeedStartSubFlow(resultObject.isNeedStartSubFlow());
            condition.setStartSubFlow(resultObject.getStartSubFlow());
            conditions.add(condition);
            return conditions;
        }
    }

    /**
     * 执行当前节点操作
     *
     * @param bussFlowNode 流程节点对象
     * @return 任务执行future
     */
    private Future<?> execute(FlowNode bussFlowNode) {
        return executor.submit(() -> {
            logger.debug("flowDirver:node executing start");
            bussFlowNode.execute();
            semaphore.release(1);
            logger.debug("flowDirver:node executing end");
        });
    }

    /**
     * 启动子流程
     *
     * @param flowKey            流程key
     * @param parentFlowNodeData 父节点流程节点对象
     */
    private void executeSubFlow(String flowKey, Object parentFlowNodeData) {
        //需要执行子流程
        executor.submit(() -> {
            //子流程不再检车启动条件
            startFlow(flowKey, parentFlowNodeData, false);
        });
    }

    /**
     * 执行下一个节点
     *
     * @param conditions   条件列表
     * @param flowInfo     流程对象
     * @param flowNodeData 流程业务节点执行结果对象
     */
    private void executeNextNode(List<Condition> conditions, FlowInfo flowInfo, Object flowNodeData) {
        //int count = 0;
        //根据flowinfo 获取当前运行时数据对象
        //int hashCode = flowInfo.hashCode();
        //FlowInfoRuntimeData runtimeData = runtimeDataMap.get(hashCode);
        for (Condition condition : conditions) {
            //是否执行子流程
            if (condition.isNeedStartSubFlow()) {
                executeSubFlow(condition.getStartSubFlow(), flowNodeData);
            }
            FlowConfigNode node = flowInfo.getNode(condition.getNodeKey());
            if (null != node) {
                //  count++;
                executor.submit(() -> {
                    try {
                        exeFlowsQueue.put(new FlowExecBean(node, flowInfo, flowNodeData));
                        //设置节点数据
                        // runtimeData.setFlowConfigNode(node);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        logger.error("flowDirver:等待线程被打断", e);
                    }
                });
            }
        }
        //重新计数当前流程
//        runtimeData.addCount(count - 1);
//        if (runtimeData.isFinish()) {
//            runtimeDataMap.remove(hashCode);
//        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.RUN = false;
        executor.shutdown();
    }

    /**
     * 注册流程执行状态监听
     *
     * @param listener 监听器
     */
    public void addListener(NotifyNodeExecuteState listener) {
        this.notifyExecStateListener = listener;
    }

    /**
     * 消息通知方法
     *
     * @param data 通知数据对象
     */
    private void notifyState(NotifyNodeExecuteData data) {
        if (null != this.notifyExecStateListener) {
            notifyExecutor.submit(() -> {
                if (null != this.notifyExecStateListener) {
                    this.notifyExecStateListener.notifyState(data);
                }
            });
        }
    }

    public Map<String, FlowInfo> getAllFlow() {
        return allFlow;
    }

    //  public Map<Integer, FlowInfoRuntimeData> getRuntimeDataMap() {
//        return runtimeDataMap;
//    }

    /**
     * 設置流程啟動的回調
     *
     * @param flowStartCallback
     */
    public void setFlowStartCallback(FlowStartCallback flowStartCallback) {
        this.flowStartCallback = flowStartCallback;
    }


    /**
     * 单例模式 内部类枚举类
     */
    private enum FlowDriverInstance {
        INSTANCE;
        FlowDriver flowDriver = null;

        FlowDriverInstance() {
            flowDriver = new FlowDriver();
        }

        private FlowDriver getInstance() {
            return flowDriver;
        }
    }

}
