package com.greatech.workflow.deal;

import com.greatech.workflow.dto.ClassConfig;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

/**
 * Created by bigbeard on 2017/5/25.
 */
public class ObjectToolsTest {

    ObjectTools objectTools = new ObjectTools();


    @Test
    public void getFieldValueByName() throws Exception {
        ClassConfig o = new ClassConfig();
        o.setProperty("NameV");
        o.setValue("dddd");
        Object getValue = objectTools.getFieldValueByName("getValue", o);
        System.out.println(getValue);
        Object value = objectTools.getFieldValueByName("Property", o);
        System.out.println(value);
        System.out.println("============");
    }

    @Test
    public void getFiledName() throws Exception {
        ClassConfig o = new ClassConfig();
        o.setProperty("NameV");
        o.setValue("dddd");
        String[] filedName = objectTools.getFiledName(o);
        for (String s : filedName) {
            System.out.println(s);
        }
        System.out.println("============");
    }

    @Test
    public void getFiledsInfo() throws Exception {
        ClassConfig o = new ClassConfig();
        o.setProperty("NameV");
        o.setValue("dddd");
        List filedsInfo = objectTools.getFiledsInfo(o);
        for (Object m :
                filedsInfo) {
            HashMap<String, Object> hp = (HashMap<String, Object>) m;
            System.out.println("type=" + hp.get("type").toString());
            System.out.println("name=" + hp.get("name").toString());
            System.out.println("value=" + hp.get("value").toString());
        }
        System.out.println("============");
    }

    @Test
    public void getFiledValues() throws Exception {
        ClassConfig o = new ClassConfig();
        o.setProperty("NameV");
        o.setValue("dddd");
        Object[] filedValues = objectTools.getFiledValues(o);
        System.out.println(filedValues.length);
        for (Object obj : filedValues
                ) {
            System.out.println(obj.toString());

        }
        System.out.println("============");
    }

    @Test
    public void setValueToObject() throws Exception {
        Object o = new ClassConfig();
        String propertyName = "setProperty";
        Object value = "asdf";
        objectTools.setValueToObject(o.getClass(), propertyName, value);
        Object[] filedValues = objectTools.getFiledValues(o);
        for (Object x :
                filedValues) {
            System.out.println(x);
        }

    }

}