package com.polmos.pojomapper.service;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
    private static final String MAP_FROM_ATTR = "classFrom";
    private static final String MAP_TO_ATTR = "classTo";

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
            logger.error("Validation against XSD schema failed" , ex);
            throw new IOException(ex);
        }
    }

    @Override
    public void checkDuplicateMappings(Document xmlMapping) throws IOException {
        logger.debug("Checking if there are duplicates in mapping");
        if (xmlMapping != null) {
            Map<String, String> mappingsMap = new HashMap<>();
            Element root = xmlMapping.getDocumentElement();
            NodeList mappings = root.getChildNodes();
            if (mappings != null) {
                for (int i = 0; i < mappings.getLength(); i++) {
                    Node mapping = mappings.item(i);
                    NamedNodeMap attributes = mapping.getAttributes();
                    String nodeValue = attributes.getNamedItem(MAP_FROM_ATTR).getNodeValue();
                    String nodeValue1 = attributes.getNamedItem(MAP_TO_ATTR).getNodeValue();
                }
            }
        }
    }
}
