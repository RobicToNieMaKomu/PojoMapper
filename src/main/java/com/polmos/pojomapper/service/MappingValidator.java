package com.polmos.pojomapper.service;

import java.io.IOException;
import org.w3c.dom.Document;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface MappingValidator {

    void validateMapping(Document xmlMapping) throws IOException;
    
    void checkDuplicateMappings(Document xmlMapping) throws IOException;
}
