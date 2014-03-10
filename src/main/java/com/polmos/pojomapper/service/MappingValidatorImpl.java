package com.polmos.pojomapper.service;

import com.polmos.pojomapper.pojos.NodeAttributes;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.XMLConstants;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author RobicToNieMaKomu
 */
public class MappingValidatorImpl implements MappingValidator {

    private static final Logger logger = LoggerFactory.getLogger(MappingValidatorImpl.class);
    private static final String SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    private static final String SCHEMA_URL = "http://www.w3.org/2001/XMLSchema";
    private static final String SCHEMA_NAME = "MappingSchema.xsd";
    private static final String CLASS_FROM_ATTR = "classFrom";
    private static final String CLASS_TO_ATTR = "classTo";
    private static final String FROM_ATTR = "from";
    private static final String TO_ATTR = "to";

    @Override
    public void validateMapping(Document xmlMapping) throws IOException {
        try {
            logger.debug("Validating document against XSD schema");
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL schemaURL = getClass().getClassLoader().getResource(SCHEMA_NAME);//
            Schema schema = sf.newSchema(schemaURL);
            Validator validator = schema.newValidator();
            DOMSource source = new DOMSource(xmlMapping);
            validator.validate(source);
        } catch (SAXException | IOException ex) {
            logger.error("Validation against XSD schema failed", ex);
            throw new IOException(ex);
        }
    }

    @Override
    public void checkDuplicateMappings(Document xmlMapping) throws IOException {
        logger.debug("Checking if there are duplicates in mapping");
        if (xmlMapping != null) {
            Element root = xmlMapping.getDocumentElement();
            NodeList mappings = root.getChildNodes();
            if (mappings != null) {
                Map<Integer, NodeAttributes> nodesAttributesMap = new HashMap<>();
                for (int i = 0; i < mappings.getLength(); i++) {
                    Node mapping = mappings.item(i);
                    NamedNodeMap attributes = mapping.getAttributes();
                    Node classFromAttr = attributes.getNamedItem(CLASS_FROM_ATTR);
                    Node classToAttr = attributes.getNamedItem(CLASS_TO_ATTR);
                    if (classFromAttr == null && classToAttr == null) {
                        logger.error("Expected classFrom/classTo attributes but none of them found");
                        throw new IOException("Missing classTo/classFrom attributes for mapping:" + mapping.getTextContent());
                    } else if (classFromAttr == null && classToAttr != null) {
                        nodesAttributesMap.put(i, new NodeAttributes(null, classToAttr.getTextContent()));
                    } else if (classFromAttr != null && classToAttr == null) {
                        nodesAttributesMap.put(i, new NodeAttributes(classFromAttr.getTextContent(), null));
                    } else if (classFromAttr != null && classToAttr != null) {
                        nodesAttributesMap.put(i, new NodeAttributes(classFromAttr.getTextContent(), classToAttr.getTextContent()));
                    }
                    // Find getter duplicates
                    Map<Integer, List<NodeAttributes>> lvlToAttributesMap = new HashMap<>();
                    traverseNodes(0, mapping.getChildNodes(), lvlToAttributesMap);
                    findGetterDuplicates(lvlToAttributesMap);
                }
                // Check if there are two pairs with the same mapFrom and mapTo attributes
                for (Integer i : nodesAttributesMap.keySet()) {
                    NodeAttributes node = nodesAttributesMap.get(i);
                    for (Integer j : nodesAttributesMap.keySet()) {
                        if (i != j && node.equals(nodesAttributesMap.get(j))) {
                            String from = node.getFrom();
                            String to = node.getTo();
                            logger.error("There are duplicated mappings with mapFrom:" + from + " and mapTo:" + to + " attributes");
                            throw new IOException("Duplicated mappings in xml mapping document");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void traverseNodes(int level, NodeList getters, Map<Integer, List<NodeAttributes>> nodeAttributes) {
        if (getters != null) {
            for (int i = 0; i < getters.getLength(); i++) {
                Node node = getters.item(i);
                if (node != null) {
                    NodeList childNodes = node.getChildNodes();
                    if (childNodes != null) {
                        traverseNodes(level + 1, childNodes, nodeAttributes);
                        NamedNodeMap attributes = node.getAttributes();
                        if (attributes != null) {
                            Node fromAttr = attributes.getNamedItem(FROM_ATTR);
                            Node toAttr = attributes.getNamedItem(TO_ATTR);
                            String from = (fromAttr != null) ? fromAttr.getTextContent() : null;
                            String to = (toAttr != null) ? toAttr.getTextContent() : null;
                            NodeAttributes nodeAttribute = new NodeAttributes(from, to);
                            List<NodeAttributes> listOfNodeAttributes = nodeAttributes.get(level);
                            if (listOfNodeAttributes == null) {
                                listOfNodeAttributes = new ArrayList<>();
                            }
                            listOfNodeAttributes.add(nodeAttribute);
                            nodeAttributes.put(level, listOfNodeAttributes);
                        }
                    }
                } else {
                    logger.warn("Null node at level " + level);
                }
            }
        }
    }

    private void findGetterDuplicates(Map<Integer, List<NodeAttributes>> lvlToAttributesMap) throws IOException {
        if (lvlToAttributesMap != null) {
            // Initialize with attributes from the deepest lvl
            List<NodeAttributes> attributesFromDeeperLvl = lvlToAttributesMap.get(lvlToAttributesMap.size() - 1);
            for (int i = lvlToAttributesMap.size() - 1; i == 0; i--) {
                List<NodeAttributes> attributes = lvlToAttributesMap.get(i);
                attributesFromDeeperLvl.retainAll(attributes);
            }
            if (attributesFromDeeperLvl.size() > 1 && hasDuplicate(attributesFromDeeperLvl)) {
                throw new IOException("Chatanooga!");
            }
        }
    }

    private <T> boolean hasDuplicate(Iterable<T> all) {
        Set<T> set = new HashSet<>();
        // Set#add returns false if the set does not change, which
        // indicates that a duplicate element has been added.
        for (T each : all) {
            if (!set.add(each)) {
                return true;
            }
        }
        return false;
    }
}
