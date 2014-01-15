package com.polmos.pojomapper.service;

import com.polmos.pojomapper.pojos.NodeAttributes;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface MappingValidator {

    void validateMapping(Document xmlMapping) throws IOException;
    
    void checkDuplicateMappings(Document xmlMapping) throws IOException;
    
    void traverseNodes(int level, NodeList getters, Map<Integer, List<NodeAttributes>> nodeAttributes);
}
