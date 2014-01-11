package com.pojos;

import java.util.Collection;

/**
 *
 * @author RobicToNieMaKomu
 */
public class C {

    private Collection<A> simpleCollection;
    private Collection<OutputEnum> enumCollection;

    public C(Collection<A> collA, Collection<OutputEnum> collB) {
        this.simpleCollection = collA;
        this.enumCollection = collB;
    }
    
    public void setSimpleCollection(Collection<A> simpleCollection) {
        this.simpleCollection = simpleCollection;
    }

    public void setEnumCollection(Collection<OutputEnum> enumCollection) {
        this.enumCollection = enumCollection;
    }
}
