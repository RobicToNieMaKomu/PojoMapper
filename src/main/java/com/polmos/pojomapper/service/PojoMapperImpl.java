package com.polmos.pojomapper.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

/**
 *
 * @author RobicToNieMaKomu
 */
public class PojoMapperImpl implements PojoMapper {

    private static final Logger logger = LoggerFactory.getLogger(PojoMapperImpl.class);
    private final MappingValidator mappingValidator;
    private final HelperUtils helperUtils;
    private Document xmlMapping;
    private boolean isInitialized;

    public PojoMapperImpl() {
        logger.debug("PojoMapperImpl creating...");
        this.mappingValidator = new MappingValidatorImpl();
        this.helperUtils = new HelperUtilsImpl();
        this.isInitialized = false;
    }

    @Override
    public void initialize(Document xmlMapping) throws IOException {
        if (this.isInitialized == false) {
            logger.info("Initializing PojoMapper");
            mappingValidator.validateMapping(xmlMapping);
            this.xmlMapping = xmlMapping;
            this.isInitialized = true;
            logger.info("PojoMapper has been initialized successfully");
        } else {
            throw new IOException("PojoMapper is already initialized!");
        }
    }

    @Override
    public void initialize(String pathToMapping) throws IOException {
        if (this.isInitialized == false) {
            Document doc = helperUtils.loadXmlFromPath(pathToMapping);
            initialize(doc);
        } else {
            throw new IOException("PojoMapper is already initialized!");
        }
    }

    @Override
    public <T1, T2> List<T1> oneToMany(T2 object) throws IOException {
        if (isInitialized == true) {
        } else {
            throw new IOException("mapFrom method invoked, before mapper initialization");
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T1, T2> T1 oneToOne(T2 object) throws IOException {
        if (isInitialized == true) {
        } else {
            throw new IOException("mapFrom method invoked, before mapper initialization");
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T1, T2> T1 manyToOne(List<T1> objects) throws IOException {
        if (isInitialized == true) {
        } else {
            throw new IOException("mapFrom method invoked, before mapper initialization");
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
