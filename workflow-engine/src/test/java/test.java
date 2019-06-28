import com.alibaba.fastjson.JSONObject;
import com.greatech.workflow.deal.ObjectTools;
import com.greatech.workflow.dto.OperateType;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Administrator on 2017/5/22.
 */
public class test {
    @Test
    public void ttt() {
//        driver d = new driver();
//        d.testa();

        System.out.println(new client() instanceof node);
    }

    @Test
    public void test3() throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println("11111");
        ExecutorService exe = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        Future submit = exe.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    System.out.println("66666666666");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        submit.get(2, TimeUnit.SECONDS);
        System.out.println("555555555555");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("2222222222");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("3333333333");
    }

    @Test
    public void sddd() {
        for (OperateType o :
                OperateType.values()) {
            System.out.println(o.getName());
        }
        //OperateType.values();
        // System.out.println( );
    }

    @Test
    public void setValueToObject() throws Exception {

        Object o = new client();
        String propertyName = "property";
        int value = 1000;
        Class<?> aClass = o.getClass();


        Method[] methods = aClass.getMethods();
        for (Method m : methods) {
            if (m.getName().equals(propertyName)) {
                m.invoke(o, value);
                break;
            }
        }


        System.out.println("OK");
    }

    @Test
    public void testCreate() {
        try {
            Class<?> c = Class.forName("com.greatech.workflow.dto.ClassConfig");
            Object x = c.newInstance();
            ObjectTools objectTools = new ObjectTools();
            objectTools.setValueToObject(x, "property", "1sadf");
            Object property = objectTools.getFieldValueByName("property", x);
            System.out.println(property);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSort() {
        List<dd> lst = new ArrayList<dd>();
        lst.add(new dd(2));
        lst.add(new dd(4));
        lst.add(new dd(3));
        lst.add(new dd(0));
        lst.add(new dd(-1));
        lst.sort(new Comparator<dd>() {
            @Override
            public int compare(dd o1, dd o2) {
                return o2.getI() - o1.getI();
            }
        });
        for (int i = 0; i < lst.size(); i++) {
            int idd = lst.get(i).getI();
            System.out.printf(String.valueOf(idd) + "\r\n");
        }
    }

    @Test
    public void convert() {
        int f = 0xffff;
        byte b0 = (byte) (f >>> 24);
        byte b1 = (byte) (f >>> 16);
        byte b2 = (byte) (f >>> 8);
        byte b3 = (byte) (f);

        int x = ((b0 & 0xFF) << 24) | (b1 & 0xFF) << 16 | (b2 & 0xFF) << 8 | b3 & 0xFF;
        System.out.println(x);
        if (x == f) {
            System.out.println("true");
        }
    }

    @Test
    public void ddddd() {
        List<dddsdf> lst = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dddsdf x = new dddsdf();
            x.setAnInt(i * 100);
            x.setCint(i + 100);
            x.setX(String.valueOf(i));
            lst.add(x);
        }

        String s = JSONObject.toJSONString(lst);
        System.out.println(s);
        // Object parse = JSONObject.parse(s);
        List<dddsdf> arrayList = (List<dddsdf>) JSONObject.parseArray(s, dddsdf.class);
        System.out.println(arrayList);
    }

    interface callback {
        void callbackexecute(node node);
    }

    interface node {

        void setCallback(callback c);

        void exec();
    }

    public static class dddsdf {
        int anInt;
        int cint;
        String x;

        public int getAnInt() {
            return anInt;
        }

        public void setAnInt(int anInt) {
            this.anInt = anInt;
        }

        public int getCint() {
            return cint;
        }

        public void setCint(int cint) {
            this.cint = cint;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }
    }

    class client implements node {

        public int key = 0;

        public int nextkey = 1;

        private callback cc;

        public void setCallback(callback ccc) {
            cc = ccc;
        }

        public void exec() {
            System.out.println("cao le " + key);
            cc.callbackexecute(this);
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getNextkey() {
            return nextkey;
        }

        public void setNextkey(int nextkey) {
            this.nextkey = nextkey;
        }

        public callback getCc() {
            return cc;
        }

        public void setCc(callback cc) {
            this.cc = cc;
        }
    }

    class driver implements callback {
        node nodea = new client();

        int i = 0;
        List<node> ls;

        public void testa() {
            ls = new ArrayList<node>();
            client f = new client();
            f.key = 0;
            f.nextkey = -1;
            f.setCallback(this);
            ls.add(f);
            for (int i = 1; i < 10; i++) {
                client n = new client();
                n.key = i;
                n.nextkey = i - 1;
                n.setCallback(this);
                ls.add(n);
            }
            nodea = ls.get(ls.size() - 1);
            nodea.exec();
        }

        public void callbackexecute(node n) {
            System.out.println("wo cao ");
            client ccc = (client) n;
            if (ccc.nextkey == -1) {
                return;
            }
            node dd = ls.get(ccc.nextkey);

            if (dd != null) {
                dd.exec();
            }
        }
    }

    class dd {
        int i;

        public dd(int v) {
            i = v;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }
    }

    @Test
    public void testAdd()
    {



    }

}

