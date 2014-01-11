package com.pojos;

import java.util.Collection;

/**
 *
 * @author RobicToNieMaKomu
 */
public class POJOWithMembersCollection {

    private Collection<SimplePOJO> membersCollection;
    private Collection<POJOWithMembers> fancyCollection;

    public POJOWithMembersCollection(Collection<SimplePOJO> membersCollection, Collection<POJOWithMembers> fancyCollection) {
        this.membersCollection = membersCollection;
        this.fancyCollection = fancyCollection;
    }
    
    public Collection<SimplePOJO> getMembersCollection() {
        return membersCollection;
    }

    public Collection<POJOWithMembers> getFancyCollection() {
        return fancyCollection;
    }
}
