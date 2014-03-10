package com.polmos.pojomapper.service;

import java.io.IOException;
import org.w3c.dom.Document;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface PojoMapper extends PojoAdapter {

    void initialize(String pathToXmlMapping) throws IOException;

    void initialize(Document xmlMapping) throws IOException;
}
