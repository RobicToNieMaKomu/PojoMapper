package com.polmos.pojomapper.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author RobicToNieMaKomu
 */
public class PojoAdapterImpl implements PojoAdapter {

    private static final String CLASS_FROM_ATTR = "classFrom";
    private static final String CLASS_TO_ATTR = "classTo";
    private final Document doc;
    private final MappingValidator mappingValidator;

    public PojoAdapterImpl(Document doc, MappingValidator mappingValidator) throws IOException {
        if (doc == null) {
            throw new IOException("Invalid argument passed");
        }
        this.doc = doc;
        this.mappingValidator = mappingValidator;
    }

    public PojoAdapterImpl(Document doc) throws IOException {
        this(doc, new MappingValidatorImpl());
    }

    @Override
    public <T1, T2> T1 manyToOne(Class<T1> outputClass, List<T2> objects) throws IOException {
        T1 result = null;
        if (objects != null && outputClass != null) {
        } else {
            throw new IOException("Invalid input parameters:" + outputClass + ", " + objects);
        }
        return result;
    }

    @Override
    public <T1, T2> List<T1> oneToMany(Class<T1> outputClass, T2 object) throws IOException {
        List<T1> result = new ArrayList<>();
        if (object != null && outputClass != null) {
        } else {
            throw new IOException("Invalid input parameters:" + outputClass + ", " + object);
        }
        return result;
    }

    @Override
    public <T1, T2> T1 oneToOne(Class<T1> outputClass, T2 object) throws IOException {
        T1 result = null;
        if (object != null && outputClass != null) {
            NodeList childNodes = doc.getChildNodes();
            if (childNodes != null) {
                Node root = childNodes.item(0);
                NodeList mappings = root.getChildNodes();
                for (int i = 0; i < mappings.getLength(); i++) {
                    Node mapping = mappings.item(i);
                    if (mapping != null) {
                        NamedNodeMap attributes = mapping.getAttributes();
                        Node classFromAttr = attributes.getNamedItem(CLASS_FROM_ATTR);
                        Node classToAttr = attributes.getNamedItem(CLASS_TO_ATTR);
                        if (classFromAttr != null) {
                        }
                        if (classToAttr != null) {
                        }
                        mappingValidator.traverseNodes(0, mappings, null);
                    }
                }
            }
        } else {
            throw new IOException("Invalid input parameters:" + outputClass + ", " + object);
        }
        return result;
    }
}
