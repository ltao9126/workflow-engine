package com.greatech.workflow.uiservice.service;

import com.greatech.workflow.uiservice.uinode.ParameterInfo;
import com.greatech.workflow.uiservice.uinode.PropertyValuesUrl;
import com.greatech.workflow.uiservice.uinode.UINodeClassConfig;
import com.greatech.workflow.uiservice.uinode.UINodeConfigInfo;
import com.greatech.workflow.uiservice.util.FileUtils;
import com.greatech.workflow.uiservice.util.ValueUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UINodeDealImp {
    private final static Log logger = LogFactory.getLog(UINodeDealImp.class);
    /*Map<String, String> pathS = FileUtils.getPath();
    String path = (String) pathS.get("path1");*/

    /**
     * 解析xml
     *
     * @throws MalformedURLException
     * @throws DocumentException
     */
    public List<UINodeConfigInfo> loadFlow(String path) throws MalformedURLException, DocumentException {
        logger.debug(path);
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(new File(path));
        Element root = doc.getRootElement();
        List info = root.elements("UIConfigInfo");
        List<UINodeConfigInfo> list = new ArrayList<UINodeConfigInfo>();
        UINodeConfigInfo uc = new UINodeConfigInfo();
        for (Object e : info) {
            UINodeConfigInfo o = new UINodeConfigInfo();
            Element element = (Element) e;
            o.setBussCaption(element.attribute("bussCaption").getValue());
            o.setBussClass(element.attribute("bussClass").getValue());
            o.setShowIcon(element.attribute("showIcon").getValue());
            o.setIdentify(element.attribute("identify").getValue());
            //节点properties
            Element eleProperty = element.element("properties");
            List<UINodeClassConfig> property = property(eleProperty);
            o.setPropertyInfos(property);
            list.add(o);
        }
        return list;
    }

    /**
     * 节点properties
     *
     * @param element
     * @return
     */
    public List<UINodeClassConfig> property(Element element) {
        if (element == null) {
            return null;
        }
        ArrayList<UINodeClassConfig> lists = new ArrayList<>();
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element con = (Element) iterator.next();
            UINodeClassConfig ui = new UINodeClassConfig();
            //获取propertyValues节点
            Element eleProperty = con.element("propertyValues");
            Iterator it = eleProperty.elementIterator();
            List<Object> list = new ArrayList<Object>();//存放value
            List<PropertyValuesUrl> li = new ArrayList<PropertyValuesUrl>();//存放url
            while (it.hasNext()) {
                Element ele = (Element) it.next();
                if (ele.getName().equals("propertyValuesUrl")) {
                    PropertyValuesUrl pvu = new PropertyValuesUrl();
                    pvu.setRequestType(ele.attribute("requestType").getValue());
                    pvu.setUrl(ele.attribute("url").getValue());
                    Iterator e = ele.elementIterator(); //遍历参数节点
                    while (e.hasNext()) {
                        List<ParameterInfo> l = new ArrayList<ParameterInfo>(); //存放参数
                        Element es = (Element) e.next();
                        ParameterInfo parameterInfo = new ParameterInfo();
                        parameterInfo.setParameterKey(es.attribute("key").getValue());
                        parameterInfo.setParameterValue(es.attribute("values").getValue());
                        l.add(parameterInfo);
                        pvu.setParameterInfo(l);
                    }
                    li.add(pvu);
                } else if (ele.getName().equals("value")) {
                    String value = ele.getStringValue();
                    if (con.attribute("propertyCaption").getValue().equals("是否等待")) {
                        ValueUtil vu = new ValueUtil();
                        vu.setName(value);
                        if (value.equals("一直等待")) {
                            vu.setValue("-1");
                        } else if (value.equals("不等待")) {
                            vu.setValue("0");
                        } else if (value.equals("请手动输入等待时间")) {
                            vu.setValue("1");
                        } else {
                            vu.setValue("");
                        }
                        list.add(vu);
                    } else {
                        list.add(value);
                    }
                }
            }
            ui.setPropertyValuesUrl(li);
            ui.setPropertyValues(list); //propertyValues节点的value值，用list存数据
            ui.setPropertyCaption(con.attribute("propertyCaption").getValue());  //propertyCaption属性值
            ui.setProperty(con.attribute("property").getValue());    //property属性值
            ui.setPropertyValueType(con.attribute("propertyValueType").getValue());     //propertyValueType属性值
            ui.setDefaultValue(con.attribute("defaultValue").getValue());    //defaultValue属性值
            lists.add(ui);
        }
        return lists;
    }
}

