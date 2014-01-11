package com.pojos;

/**
 *
 * @author RobicToNieMaKomu
 */
public class A {

    private String s;
    private int j;
    private long l;
    private double d;
    private char c;
    private byte b;
    private Object obj;
    private OutputEnum outputEnum;

    public A(String s, int j, long l, double d, char c, byte b, Object obj, OutputEnum outputEnum) {
        this.s = s;
        this.l = l;
        this.j = j;
        this.d = d;
        this.c = c;
        this.b = b;
        this.obj = obj;
        this.outputEnum = outputEnum;
    }
    
    public void setS(String s) {
        this.s = s;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setL(long l) {
        this.l = l;
    }

    public void setD(double d) {
        this.d = d;
    }

    public void setC(char c) {
        this.c = c;
    }

    public void setB(byte b) {
        this.b = b;
    }
    
    public void setObj(Object obj) {
        this.obj = obj;
    }

    public void setOutputEnum(OutputEnum outputEnum) {
        this.outputEnum = outputEnum;
    }
  
}
