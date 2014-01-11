package com.pojos;

/**
 *
 * @author RobicToNieMaKomu
 */
public class SimplePOJO {

    private String s;
    private int j;
    private long l;
    private double d;
    private char c;
    private byte b;

    public SimplePOJO(String s, int j, long l, double d, char c, byte b) {
        this.s = s;
        this.l = l;
        this.j = j;
        this.d = d;
        this.c = c;
        this.b = b;
    }
    
    public String getS() {
        return s;
    }

    public int getJ() {
        return j;
    }

    public long getL() {
        return l;
    }

    public double getD() {
        return d;
    }

    public char getC() {
        return c;
    }

    public byte getB() {
        return b;
    }
}
