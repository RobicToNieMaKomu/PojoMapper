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
    private PojoAdapter pojoAdapter;
    
    private volatile boolean isInitialized;

    public PojoMapperImpl() {
        logger.debug("PojoMapperImpl creating...");
        this.mappingValidator = new MappingValidatorImpl();
        this.helperUtils = new HelperUtilsImpl();
        this.isInitialized = false;
    }

    @Override
    public synchronized void initialize(Document xmlMapping) throws IOException {
        if (this.isInitialized == false) {
            logger.info("Initializing PojoMapper");
            mappingValidator.validateMapping(xmlMapping);
            mappingValidator.checkDuplicateMappings(xmlMapping);
            this.pojoAdapter = new PojoAdapterImpl(xmlMapping);
            this.isInitialized = true;
            logger.info("PojoMapper has been initialized successfully");
        } else {
            throw new IOException("PojoMapper is already initialized!");
        }
    }

    @Override
    public synchronized void initialize(String pathToMapping) throws IOException {
        if (this.isInitialized == false) {
            Document doc = helperUtils.loadXmlFromPath(pathToMapping);
            initialize(doc);
        } else {
            throw new IOException("PojoMapper is already initialized!");
        }
    }

    @Override
    public <T1, T2> List<T1> oneToMany(Class<T1> outputClass, T2 object) throws IOException {
        List<T1> result = null;
        if (isInitialized == true) {
            result = pojoAdapter.oneToMany(outputClass, object);
        } else {
            throw new IOException("mapFrom method invoked, before mapper initialization");
        }
        return result;
    }

    @Override
    public <T1, T2> T1 oneToOne(Class<T1> outputClass, T2 object) throws IOException {
        T1 result = null;
        if (isInitialized == true) {
            result = pojoAdapter.oneToOne(outputClass, object);
        } else {
            throw new IOException("mapFrom method invoked, before mapper initialization");
        }
        return result;
    }

    @Override
    public <T1, T2> T1 manyToOne(Class<T1> outputClass, List<T2> objects) throws IOException {
        T1 result = null;
        if (isInitialized == true) {
            result = pojoAdapter.manyToOne(outputClass, objects);
        } else {
            throw new IOException("mapFrom method invoked, before mapper initialization");
        }
        return result;
    }
}
