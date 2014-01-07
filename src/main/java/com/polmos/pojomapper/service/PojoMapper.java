package com.polmos.pojomapper.service;

import java.io.IOException;
import java.util.List;
import org.w3c.dom.Document;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface PojoMapper {

    void initialize(String pathToXmlMapping) throws IOException;
    
    void initialize(Document xmlMapping) throws IOException;
    
    <T1, T2> T1 mapFromClass(T2 object) throws IOException;
    
    <T1, T2> List<T1> mapFromClass(List<T2> objects) throws IOException;
}
