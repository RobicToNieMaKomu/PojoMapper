package com.polmos.pojomapper.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author RobicToNieMaKomu
 */
public class HelperUtilsImpl implements HelperUtils {

    private static final Logger logger = LoggerFactory.getLogger(HelperUtilsImpl.class);

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
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    result = dBuilder.parse(inputStream);
                } else {
                    throw new IOException("Couldnt load document - incorrect path to the file");
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
}
