package com.polmos.pojomapper.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author RobicToNieMaKomu
 */
public class HelperUtilsImpl implements HelperUtils {

    private static final Logger logger = LoggerFactory.getLogger(HelperUtilsImpl.class);
    private static final String SCHEMA_NAME = "MappingSchema.xsd";

    @Override
    public Document loadXmlFromPath(String pathToDoc) throws IOException {
        logger.debug("Loading xml from path:" + pathToDoc);
        Document result = null;
        if (pathToDoc != null) {
            try {
                InputStream inputStream = getClass().getResourceAsStream(pathToDoc);
                logger.debug("Loaded file:" + inputStream);
                if (inputStream != null) {
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    dbFactory.setIgnoringComments(true);
                    dbFactory.setIgnoringElementContentWhitespace(true);
                    dbFactory.setValidating(true);
                    dbFactory.setSchema(createXSDSchema());
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    dBuilder.setErrorHandler(new DefaultHandler());
                    result = dBuilder.parse(inputStream);
                    result.normalize();
                } else {
                    throw new IOException("Couldnt load document - incorrect path to the file:" + pathToDoc);
                }
            } catch (SAXException | ParserConfigurationException | FileNotFoundException ex) {
                logger.error("Exception occurred during loading xml document", ex);
                throw new IOException("Couldnt load document");
            }
        } else {
            throw new IOException("Coluldnt load xml document because given path was null");
        }
        return result;
    }

    private Schema createXSDSchema() throws SAXException {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URL schemaURL = getClass().getClassLoader().getResource(SCHEMA_NAME);//
        return sf.newSchema(schemaURL);
    }
}