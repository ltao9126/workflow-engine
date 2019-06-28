
import com.alibaba.fastjson.JSONObject;
import com.greatech.workflow.dto.FlowInfo;
import com.greatech.workflow.dto.OperateType;
import com.greatech.workflow.uiservice.service.UINodeDealImp;
import com.greatech.workflow.uiservice.uinode.UINodeClassConfig;
import com.greatech.workflow.uiservice.uinode.UINodeConfigInfo;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.util.*;

/**
 * Created by ltao on 17-6-28.
 */
public class test {

    @Test
    public void symbol() {
        Map map = new HashMap();
        map.put("大于", OperateType.GREATER_THEN);
        map.put("小于", OperateType.LESS_THEN);
        map.put("等于", OperateType.EQUEL);
        map.put("包含", OperateType.CONTAIN);
        map.put("在..里面", OperateType.IN);
        System.out.println(map.entrySet());
    }

    /**
     * 从xml对象加载流程
     *
     * @param
     * @return
     */
    @Test
    public void loadFlow() throws MalformedURLException, DocumentException {
        UINodeConfigInfo o = new UINodeConfigInfo();
        SAXReader saxReader = new SAXReader();
        String url = this.getClass().getResource("/UINodeConfig.xml").getFile();
        Document doc = saxReader.read(new File(url));
        Map<String, FlowInfo> map = new HashMap<String, FlowInfo>();
        Element root = doc.getRootElement();
        List info = root.elements("UIConfigInfo");
        for (Object e : info) {
            Element element = (Element) e;
            Attribute bcaption = element.attribute("bussCaption");
            Attribute bussClass = element.attribute("bussClass");
            Attribute showIcon = element.attribute("showIcon");
            o.setBussCaption(bcaption.getValue());
            o.setBussClass(bussClass.getValue());
            o.setShowIcon(showIcon.getValue());
            Element nextNode = element.element("properties");
            ArrayList lists = new ArrayList();
            if (nextNode != null) {
                Iterator iterator = nextNode.elementIterator();
                while (iterator.hasNext()) {
                    Element con = (Element) iterator.next();
                    UINodeClassConfig ui = new UINodeClassConfig();
                    Attribute pcaption = con.attribute("propertyCaption");
                    Attribute property = con.attribute("property");
                    Attribute propertyValueType = con.attribute("propertyValueType");
                    Attribute propertyDes = con.attribute("propertyDes");
                    Attribute propertyGroup = con.attribute("propertyGroup");
                    Attribute defaultValue = con.attribute("defaultValue");
                    ui.setPropertyCaption(pcaption.getValue());
                    ui.setProperty(property.getValue());
                    String type = propertyValueType.getValue();
//                    ui.setPropertyValueType(propertyValueType.getValue());
//                    List<UINodeClassConfig> uis = propertyValues((Element) ui);
                    ui.setDefaultValue(defaultValue.getValue());
                    lists.add(ui);
                }
//                System.out.println(lists);
                o.setPropertyInfos(lists);
            }
            //Gson gson = new Gson();
            String os = JSONObject.toJSONString(o);
            System.out.println(os);
        }
    }

    public List<UINodeClassConfig> propertyValues(Element element) {
        List list = new ArrayList<>();
        Iterator it = element.elementIterator();
        if (it.hasNext()) {
            Element property = (Element) it.next();
            Attribute value = property.attribute("value");
            list.add(value.getValue());
        }
        return list;
    }

    @Test
    public void method4() throws Exception {
        // 得到Document
        SAXReader reader = new SAXReader();
        String path = UINodeDealImp.class.getResource("/").getPath() + "UINodeConfig.xml";
        Document doc = reader.read(new File(path));
        //获取root元素
        Element root = doc.getRootElement();
        Element stuEle = root.addElement("UIConfigInfo"); //添加了UIConfigInfo元素
        //设置属性值,addAttribute设置节点属性，addElement设置下个节点
        stuEle.addAttribute("bussCaption", "test");
        stuEle.addAttribute("bussClass", "wangWu");
        stuEle.addAttribute("showIcon", "textIcon");
        //设置子节点properties
        Element pro = stuEle.addElement("properties");
        Element property = pro.addElement("property");
        //设置property节点属性
        property.addAttribute("propertyCaption", "显示");
        property.addAttribute("property", "Caption");
        property.addAttribute("propertyValueType", "INPUT");
        property.addAttribute("defaultValue", "");
        //设置子节点propertyValues
        Element values = property.addElement("propertyValues");
        //设置子节点propertyValuesUrl
        Element url = values.addElement("propertyValuesUrl");
        //设置propertyValuesUrl节点属性
        url.addAttribute("requestType", "");
        url.addAttribute("url", "");
        //设置value节点
        Element value = values.addElement("value");
        value.setText("显示");
        // 设置保存的格式化器  1. \t，使用什么来完成缩进 2. true, 是否要添加换行
        OutputFormat format = new OutputFormat("\t", true);
        format.setTrimText(true); //去掉空白
        // 在创建Writer时，指定格式化器
        XMLWriter writer = new XMLWriter(new FileOutputStream(path), format);
        writer.write(doc);
    }

    @Test
    public void method6() throws Exception {
        // 得到Document
        SAXReader reader = new SAXReader();
        String path = UINodeDealImp.class.getResource("/").getPath() + "UINodeConfig.xml";
        Document doc = reader.read(new File(path));
        // 查找UIConfigInfo元素，条件是UIConfigInfo子元素的文本内容为test
        Element stuEle = (Element) doc.selectSingleNode("//UIConfigInfo[bussCaption='testsss']");
        //获取父元素，使用父元素删除指定子元素
        stuEle.getParent().remove(stuEle);
        // 设置保存的格式化器  1. \t，使用什么来完成缩进 2. true, 是否要添加换行
        OutputFormat format = new OutputFormat("\t", true);
        format.setTrimText(true); //去掉空白
        // 在创建Writer时，指定格式化器
        XMLWriter writer = new XMLWriter(new FileOutputStream(path), format);
        writer.write(doc);
    }

    @Test
    public void test3() throws DocumentException, IOException {
        // 得到Document
        SAXReader reader = new SAXReader();
        String xmlPath = "E:/dingWorkspace/workflow/workflow/workflow-uiservice/src/main/resources/UINodeConfig.xml";
        Document doc = reader.read(new File(xmlPath));
        //获取root元素
        Element root = doc.getRootElement();
        Element priceTmp = root.element("UIConfigInfo");
        List info = root.elements("UIConfigInfo");
        for (Object e : info) {
            Element element = (Element) e;
            Attribute bcaption = element.attribute("bussCaption");
            if (bcaption.getValue().equals("可燃物")) {


                OutputFormat format = OutputFormat.createPrettyPrint();
                format.setEncoding("UTF-8");
                XMLWriter writer = new XMLWriter(
                        new OutputStreamWriter(new FileOutputStream(xmlPath)), format);
                writer.write(doc);
                writer.close();
            }
        }
    }
}

