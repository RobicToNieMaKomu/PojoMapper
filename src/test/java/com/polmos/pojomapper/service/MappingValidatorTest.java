package com.polmos.pojomapper.service;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

/**
 *
 * @author RobicToNieMaKomu
 */
public class MappingValidatorTest {

    private static final String PATH_TO_SIMPLE_MAPPING = "/Simple.xml";
    private static final String PATH_TO_MEMBER_INSISDE_MAPPING = "/MemberInside.xml";
    private static final String PATH_TO_MANY_2_ONE_MAPPING = "/Many2one.xml";
    private static final String PATH_TO_COLLECTION_INSIDE_MAPPING = "/CollectionInside.xml";
    
    private static MappingValidator mappingValidator;
    private static HelperUtils helperUtils;
    
    @BeforeClass
    public static void setup() {
        mappingValidator = new MappingValidatorImpl();
        helperUtils = new HelperUtilsImpl();
    }
    
    @Test
    public void testCheckNullMapping() throws IOException {
        Document xmlMapping = null;
        mappingValidator.checkDuplicateMappings(xmlMapping);
    }
    
    @Test
    public void testCheckMappingWithoutDuplicates() throws IOException {
        Document simpleMapping = helperUtils.loadXmlFromPath(PATH_TO_SIMPLE_MAPPING);
        Document collInsideMapping = helperUtils.loadXmlFromPath(PATH_TO_COLLECTION_INSIDE_MAPPING);
        Document memberInsideMapping = helperUtils.loadXmlFromPath(PATH_TO_MEMBER_INSISDE_MAPPING);
        Document manyToOneMapping = helperUtils.loadXmlFromPath(PATH_TO_MANY_2_ONE_MAPPING);
        
        mappingValidator.checkDuplicateMappings(simpleMapping);
        mappingValidator.checkDuplicateMappings(collInsideMapping);
        mappingValidator.checkDuplicateMappings(memberInsideMapping);
        mappingValidator.checkDuplicateMappings(manyToOneMapping);
    }
    
    @Test
    public void testCheckXmlMappingContainingMappingDuplicates() {
        
    }
    
    @Test
    public void testCheckMappingContainingGetterDuplicates() {
        
    }
}
