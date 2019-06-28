package com.greatech.workflow;

import java.io.*;

public class TestBean implements Serializable{
    public int i;
    public int j;
    public int k;

    public TestBean(int i, int j, int k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    /**
     * 深克隆 当前流程对象
     *
     * @return
     */
    public TestBean deepClone() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bos);
            oo.writeObject(this);//从流里读出来
            ByteArrayInputStream bi = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(bi);
            return (TestBean) oi.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return null;
    }
}