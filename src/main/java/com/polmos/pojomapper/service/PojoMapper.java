package com.polmos.pojomapper.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.w3c.dom.Document;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface PojoMapper {

    void initialize(String pathToXmlMapping) throws IOException;

    void initialize(Document xmlMapping) throws IOException;

    <T1, T2> List<T1> oneToMany(T2 object) throws IOException;

    <T1, T2> T1 oneToOne(T2 object) throws IOException;

    <T1, T2> T1 manyToOne(List<T1> objects) throws IOException;
}
