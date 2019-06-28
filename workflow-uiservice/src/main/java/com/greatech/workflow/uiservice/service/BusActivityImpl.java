package com.greatech.workflow.uiservice.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.greatech.workflow.uiservice.api.BusActivity;
import com.greatech.workflow.uiservice.uinode.ParameterInfo;
import com.greatech.workflow.uiservice.uinode.PropertyValuesUrl;
import com.greatech.workflow.uiservice.uinode.UINodeClassConfig;
import com.greatech.workflow.uiservice.util.*;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 业务活动实现类
 */
public class BusActivityImpl implements BusActivity {
    StatusBean sb = new StatusBean();
    FlowBean fb = new FlowBean();
    /**
     * 业务活动列表名称
     *
     * @return
     * @throws Exception
     */
    @Override
    public String busActivityName(int page, int size,String path) throws Exception {
        ResultBean rb = new ResultBean();
        try {
            List<String> list = new ArrayList<>();
            String attJson = JSONObject.toJSONString(new UINodeDealImp().loadFlow(path));
            Iterator it = JSONArray.parseArray(attJson).iterator();
            while (it.hasNext()) {
                JSONObject jo = (JSONObject) it.next();
                if (jo.getString("bussCaption") != null) {
                    list.add(jo.getString("bussCaption"));
                }
            }
            //取总页数
            int sizes = list.size() % size == 0 ? list.size() / size : list.size() / size + 1;
            int pages = 0;
            if (page > sizes) {
                pages = sizes;
            } else {
                pages = page;
            }
            PageUtils pagebean = new PageUtils(list.size(), size);
            //设置当前页
            pagebean.setCurPage(pages); //这里page是从页面上获取的一个参数，代表页数
            //获得分页数据在list集合中的索引
            int firstIndex = (pages - 1) * size;
            int toIndex = pages * size;
            if (toIndex > list.size()) {
                toIndex = list.size();
            }
            if (firstIndex > toIndex) {
                firstIndex = 0;
                pagebean.setCurPage(1);
            }
            //截取数据集合，获得分页数据
            List courseList = list.subList(firstIndex, toIndex);
            sb.setSuccess("OK");
            sb.setErrorMsg("null");
            fb.setFlow(courseList);
            fb.setTotal(list.size());
            fb.setPage(page);
            fb.setSize(size);
            rb.setStatus(sb);
            rb.setData(fb);
            return JSONObject.toJSONString(rb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查看详情
     *
     * @return
     * @throws Exception
     */
    @Override
    public String busActivityList(String bussCaption,String path) throws Exception {
        ResultBean rb = new ResultBean();
        try {
            List<ActivityUtil> list = new ArrayList<>();
            //解析控件属性xml文件
            String attJson = JSONObject.toJSONString(new UINodeDealImp().loadFlow(path));
            Iterator it = JSONArray.parseArray(attJson).iterator();
            ActivityUtil au = new ActivityUtil();
            while (it.hasNext()) {
                JSONObject jo = (JSONObject) it.next();
                String bussCap = jo.getString("bussCaption");
                if (bussCap != null && bussCap.equals(bussCaption)) {
                    au.setBussCaption(bussCaption);
                    au.setBussClass(jo.getString("bussClass"));
                    au.setShowIcon(jo.getString("showIcon"));
                    au.setIdentify(jo.getString("identify"));
                    Iterator its = JSONArray.parseArray(jo.getString("propertyInfos")).iterator();
                    List<UINodeClassConfig> lis = new ArrayList<>();//放整个propertyInfo
                    while (its.hasNext()) {
                        JSONObject jos = (JSONObject) its.next();
                        UINodeClassConfig ui = new UINodeClassConfig();
                        ui.setPropertyCaption(jos.getString("propertyCaption"));
                        ui.setProperty(jos.getString("property"));
                        ui.setPropertyValueType(jos.getString("propertyValueType"));
                        ui.setDefaultValue(jos.getString("defaultValue"));
                        List<String> li = new ArrayList<String>();
                        String values = jos.getString("propertyValues");
                        li.add(values);
                        ui.setPropertyValues(li);
                        List<PropertyValuesUrl> ls = new ArrayList<PropertyValuesUrl>();
                        Iterator iter = (Iterator) JSONArray.parseArray(jos.getString("propertyValuesUrl")).iterator();
                        while (iter.hasNext()) {
                            JSONObject ju = (JSONObject) iter.next();
                            PropertyValuesUrl pvu = new PropertyValuesUrl();
                            pvu.setRequestType(ju.getString("requestType"));
                            pvu.setUrl(ju.getString("url"));
                            String j = ju.getString("parameterInfo");
                            if (j != null) {
                                Iterator ite = (Iterator) JSONArray.parseArray(j).iterator();
                                while (ite.hasNext()) {
                                    JSONObject joss = (JSONObject) ite.next();
                                    List<ParameterInfo> li1 = new ArrayList<ParameterInfo>();
                                    ParameterInfo parameterInfo = new ParameterInfo();
                                    parameterInfo.setParameterKey(joss.getString("parameterKey"));
                                    parameterInfo.setParameterValue(joss.getString("parameterValue"));
                                    li1.add(parameterInfo);
                                    pvu.setParameterInfo(li1);
                                }
                            }
                            ls.add(pvu);
                            ui.setPropertyValuesUrl(ls);
                        }
                        lis.add(ui);
                    }
                    au.setPropertyInfos(lis);
                }
            }
            list.add(au);
            sb.setSuccess("OK");
            sb.setErrorMsg("null");
            fb.setFlow(list);
            rb.setStatus(sb);
            rb.setData(fb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.toJSONString(rb);
    }

    /**
     * 业务活动保存
     *
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public String busActivitySave(HttpServletResponse response, HttpServletRequest request,String path) throws Exception {
        ResultBean rb = new ResultBean();
        try {
            //获取配置数据
            String data = org.apache.commons.io.IOUtils.toString(request.getInputStream(), "UTF-8");
            this.xmlInfo(data, response, request,path);
            sb.setSuccess("OK");
            sb.setErrorMsg("null");
        } catch (Exception e) {
            e.printStackTrace();
            sb.setSuccess("NO");
            sb.setErrorMsg("新增失败");
        }
        fb.setFlow(null);
        rb.setStatus(sb);
        rb.setData(fb);
        return JSONObject.toJSONString(rb);
    }

    /**
     * 业务活动修改
     *
     * @return
     * @throws Exception
     */
    @Override
    public String busActivityUpdata(HttpServletResponse response, HttpServletRequest request,String path) throws Exception {
        ResultBean rb = new ResultBean();
        try {
            String data = org.apache.commons.io.IOUtils.toString(request.getInputStream(), "UTF-8");
            String id = JSONObject.parseObject(data).getString("id");
            // 得到Document
            SAXReader reader = new SAXReader();
            Document doc = reader.read(new File(path));
            //获取root元素
            Element root = doc.getRootElement();
            Element rootEle = root.element("UIConfigInfo");
            List info = root.elements("UIConfigInfo");
            for (Object e : info) {
                Element element = (Element) e;
                Attribute bcaption = element.attribute("bussCaption");
                if (bcaption.getValue().equals(id)) {
                    //删除节点
                    rootEle.getParent().remove(element);
//                  rootEle.remove(element);
                    this.writeXml(doc,path);
                    this.xmlInfo(data, response, request,path);
                    sb.setSuccess("OK");
                    sb.setErrorMsg("null");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.setSuccess("NO");
            sb.setErrorMsg("修改失败");
        }
        fb.setFlow(null);
        rb.setStatus(sb);
        rb.setData(fb);
        return JSONObject.toJSONString(rb);
    }


    /**
     * 业务活动删除
     *
     * @param bussCaption
     * @return
     * @throws Exception
     */
    @Override
    public String busActivityDelete(String bussCaption, HttpServletResponse response,String path) throws Exception {
        ResultBean rb = new ResultBean();
        try {
            // 得到Document
            SAXReader reader = new SAXReader();
            Document doc = reader.read(new File(path));
            //获取root元素
            Element root = doc.getRootElement();
            Element priceTmp = root.element("UIConfigInfo");
            List info = root.elements("UIConfigInfo");
            for (Object e : info) {
                Element element = (Element) e;
                Attribute bcaption = element.attribute("bussCaption");
                if (bcaption.getValue().equals(bussCaption)) {
                    //删除节点
                    priceTmp.getParent().remove(element);
                    this.writeXml(doc,path);
                }
            }
            sb.setSuccess("OK");
            sb.setErrorMsg("null");
        } catch (Exception e) {
            e.printStackTrace();
            sb.setSuccess("NO");
            sb.setErrorMsg("删除失败");
        }
        fb.setFlow(null);
        rb.setStatus(sb);
        rb.setData(fb);
        return JSONObject.toJSONString(rb);
    }


    /**
     * 对xml文件进行写 操作
     *
     * @param data
     * @param response
     * @param request
     * @throws DocumentException
     * @throws IOException
     */
    public void xmlInfo(String data, HttpServletResponse response, HttpServletRequest request,String path) throws DocumentException, IOException {
        try {
            // 得到Document
            SAXReader reader = new SAXReader();
            Document doc = reader.read(new File(path));
            //获取配置数据
            JSONObject json = JSONObject.parseObject(data);
            //获取root元素
            Element root = doc.getRootElement();
            Element stuEle = root.addElement("UIConfigInfo"); //添加了UIConfigInfo元素
            //设置属性值,addAttribute设置节点属性，addElement设置下个节点
            stuEle.addAttribute("bussCaption", json.getString("bussCaption"));
            stuEle.addAttribute("bussClass", json.getString("bussClass"));
            stuEle.addAttribute("showIcon", json.getString("showIcon"));
            stuEle.addAttribute("identify", json.getString("identify"));
            String arrays = json.getString("propertyInfos");
            JSONArray arr = JSONArray.parseArray(arrays);
            if (arr.size() > 0) {
                //设置子节点properties
                Element pro = stuEle.addElement("properties");
                for (Object job : arr) {
                    Element propertys = pro.addElement("property"); //设置properties子节点property
                    Element values = propertys.addElement("propertyValues");  //设置子节点propertyValues
                    JSONObject jo = JSONObject.parseObject(String.valueOf(job));
                    //设置property节点属性
                    propertys.addAttribute("propertyCaption", jo.getString("propertyCaption"));
                    propertys.addAttribute("property", jo.getString("property"));
                    propertys.addAttribute("propertyValueType", jo.getString("propertyValueType"));
                    propertys.addAttribute("defaultValue", jo.getString("defaultValue"));
                    String valueData = jo.getString("propertyValues");
                    if (valueData != null) {
                        JSONArray val = JSONArray.parseArray(valueData);
                        if (val.size() > 0) {
                            for (Object valj : val) {
                                //设置子节点value
                                Element evalue = values.addElement("value");
                                evalue.addText(String.valueOf(valj));
                            }
                        } else {
                            Element alue = values.addElement("value");
                            alue.setText("");
                        }
                    }
                    String urlData = jo.getString("propertyValuesUrl");
                    if (urlData != null) {
                        JSONArray ur = JSONArray.parseArray(urlData);
                        if (ur.size() > 0) {
                            for (Object urj : ur) {
                                JSONObject urlJson = JSONObject.parseObject(String.valueOf(urj));
                                //设置子节点propertyValuesUrl
                                Element urls = values.addElement("propertyValuesUrl");
                                JSONArray ja = JSONArray.parseArray(urlJson.getString("parameter"));
                                if (urlJson.getString("requestType") == null && ja.size() == 0) {
                                    urls.addAttribute("requestType", "");
                                    urls.addAttribute("url", "");
                                } else {
                                    urls.addAttribute("requestType", urlJson.getString("requestType"));
                                    urls.addAttribute("url", urlJson.getString("url"));
                                }
                                if (ja.size() > 0) {
                                    for (Object par : ja) {
                                        JSONObject pas = JSONObject.parseObject(String.valueOf(par));
                                        //设置属性节点
                                        Element pars = urls.addElement("parameter");
                                        if (par == null && par.equals("")) {
                                            pars.addAttribute("key", "");
                                            pars.addAttribute("values", "");
                                        } else {
                                            pars.addAttribute("key", pas.getString("pkey"));
                                            pars.addAttribute("values", pas.getString("pvalue"));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.writeXml(doc,path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //写文件
    public void writeXml(Document doc,String path) throws IOException, DocumentException {
        // 设置保存的格式化器  1. \t，使用什么来完成缩进 2. true, 是否要添加换行
        OutputFormat format = new OutputFormat("\t", true);
        format.setTrimText(true); //去掉空白
        // 在创建Writer时，指定格式化器
        XMLWriter wr = new XMLWriter(new FileOutputStream(path), format);
        wr.write(doc);
    }
}
