package com.polmos.pojomapper.service;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface PojoAdapter {

    <T1, T2> T1 oneToOne(Class<T1> outputClass, T2 object) throws IOException;

    <T1, T2> List<T1> oneToMany(Class<T1> outputClass, T2 object) throws IOException;

    <T1, T2> T1 manyToOne(Class<T1> outputClass, List<T2> objects) throws IOException;
}
