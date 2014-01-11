package com.pojos;

import java.util.Collection;

/**
 *
 * @author RobicToNieMaKomu
 */
public class POJOWithPrimitiveCollection {

    private Collection<Integer> collection;

    public POJOWithPrimitiveCollection(Collection<Integer> collection) {
        this.collection = collection;
    }
    
    public Collection<Integer> getCollection() {
        return collection;
    }
}
