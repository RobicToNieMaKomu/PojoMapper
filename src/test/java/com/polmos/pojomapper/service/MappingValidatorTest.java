package com.polmos.pojomapper.service;

import com.polmos.pojomapper.pojos.NodeAttributes;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author RobicToNieMaKomu
 */
public class MappingValidatorTest {

    private static final String PATH_TO_SIMPLE_MAPPING = "/Simple.xml";
    private static final String PATH_TO_MEMBER_INSISDE_MAPPING = "/MemberInside.xml";
    private static final String PATH_TO_MANY_2_ONE_MAPPING = "/Many2one.xml";
    private static final String PATH_TO_COLLECTION_INSIDE_MAPPING = "/CollectionInside.xml";
    private static final String PATH_TO_DUPLICATE_MAPPING = "/Duplicates.xml";
    private static final String PATH_TO_GETTER_DUPLICATES = "/GetterDuplicates.xml";
    private static final String PATH_TO_MANY_TO_ONE_DUPLICATES = "/Many2oneDuplicates.xml";
    private static MappingValidator mappingValidator;
    private static HelperUtils helperUtils;

    @BeforeClass
    public static void setup() {
        mappingValidator = new MappingValidatorImpl();
        helperUtils = new HelperUtilsImpl();
    }

    @Test
    public void traverseNodesInSimpleMappingTest() throws IOException {
        Document xml = helperUtils.loadXmlFromPath(PATH_TO_SIMPLE_MAPPING);
        Element root = xml.getDocumentElement();
        NodeList mappings = root.getChildNodes();
        if (mappings != null) {
            Map<Integer, List<NodeAttributes>> nodesAttributesMap = new HashMap<>();
            for (int i = 0; i < mappings.getLength(); i++) {
                Node getter = mappings.item(i);
                mappingValidator.traverseNodes(1, getter.getChildNodes(), nodesAttributesMap);
            }
        }
        System.out.print("");
    }

    @Test
    public void traverseNodesInDuplicatesMappingTest() throws IOException {
        Document xml = helperUtils.loadXmlFromPath(PATH_TO_DUPLICATE_MAPPING);
    }

    @Test
    public void traverseNodesInGetterDuplicatesTest() throws IOException {
        Document xml = helperUtils.loadXmlFromPath(PATH_TO_GETTER_DUPLICATES);
    }

    @Test
    public void traverseNodesInMany2OneMappingTest() throws IOException {
        Document xml = helperUtils.loadXmlFromPath(PATH_TO_MANY_2_ONE_MAPPING);
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

    @Test(expected = IOException.class)
    public void testCheckXmlMappingContainingMappingDuplicates() throws IOException {
        Document xml = helperUtils.loadXmlFromPath(PATH_TO_DUPLICATE_MAPPING);
        mappingValidator.checkDuplicateMappings(xml);
    }

    @Test(expected = IOException.class)
    public void testCheckXmlMappingContainingDuplicateManyToOneRel() throws IOException {
        Document xml = helperUtils.loadXmlFromPath(PATH_TO_MANY_TO_ONE_DUPLICATES);
        mappingValidator.checkDuplicateMappings(xml);
    }

    @Test(expected = IOException.class)
    public void testCheckMappingContainingGetterDuplicates() throws IOException {
        Document xml = helperUtils.loadXmlFromPath(PATH_TO_GETTER_DUPLICATES);
        mappingValidator.checkDuplicateMappings(xml);
    }
}
