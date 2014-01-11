package com.pojos;

/**
 *
 * @author RobicToNieMaKomu
 */
public class POJOWithMembers {

    private SimplePOJO simplePOJO;
    private Object object;
    private InputEnum en;
    
    public POJOWithMembers(SimplePOJO simplePOJO, Object object, InputEnum en) {
        this.simplePOJO = simplePOJO;
        this.object = object;
        this.en = en;
    }
    
    public SimplePOJO getSimplePOJO() {
        return simplePOJO;
    }

    public Object getObject() {
        return object;
    }
    
    public InputEnum getEn() {
        return en;
    }
}
