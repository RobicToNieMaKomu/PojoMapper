package com.polmos.pojomapper.service;

import com.polmos.pojomapper.pojos.NodeAttributes;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
    private static final String PATH_TO_GETTERS_LEVEL_3 = "/GetterDuplicatesLevel3.xml";
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
        Map<Integer, List<NodeAttributes>> nodesAttributesMap = new HashMap<>();
        if (mappings != null) {
            for (int i = 0; i < mappings.getLength(); i++) {
                Node getter = mappings.item(i);
                mappingValidator.traverseNodes(1, getter.getChildNodes(), nodesAttributesMap);
            }
        }
        assertTrue(nodesAttributesMap.size() == 1);
        List<NodeAttributes> output = nodesAttributesMap.get(1);
        assertTrue(output.contains(new NodeAttributes("s", "s")));
        assertTrue(output.contains(new NodeAttributes("j", "j")));
        assertTrue(output.contains(new NodeAttributes("l", "l")));
        assertTrue(output.contains(new NodeAttributes("d", "d")));
        assertTrue(output.contains(new NodeAttributes("c", "s")));
        assertTrue(output.contains(new NodeAttributes("b", "s")));
    }

    @Test
    public void traverseNodesInGetterDuplicatesTest() throws IOException {
        Document xml = helperUtils.loadXmlFromPath(PATH_TO_GETTER_DUPLICATES);
        Element root = xml.getDocumentElement();
        NodeList mappings = root.getChildNodes();
        Map<Integer, List<NodeAttributes>> nodesAttributesMap = new HashMap<>();
        if (mappings != null) {
            for (int i = 0; i < mappings.getLength(); i++) {
                Node getter = mappings.item(i);
                mappingValidator.traverseNodes(1, getter.getChildNodes(), nodesAttributesMap);
            }
        }
        assertTrue(nodesAttributesMap.size() == 1);
        List<NodeAttributes> output = nodesAttributesMap.get(1);
        assertTrue(output.size() == 2);
        NodeAttributes expected = new NodeAttributes("s", "s");
        assertEquals(output.get(0), expected);
        assertEquals(output.get(1), expected);
    }

    @Test
    public void traverseNodesInGetterDuplicatesLevel3Test() throws IOException {
        Document xml = helperUtils.loadXmlFromPath(PATH_TO_GETTERS_LEVEL_3);
        Element root = xml.getDocumentElement();
        NodeList mappings = root.getChildNodes();
        Map<Integer, List<NodeAttributes>> nodesAttributesMap = new HashMap<>();
        if (mappings != null) {
            for (int i = 0; i < mappings.getLength(); i++) {
                Node getter = mappings.item(i);
                mappingValidator.traverseNodes(1, getter.getChildNodes(), nodesAttributesMap);
            }
        }
        assertEquals(3, nodesAttributesMap.size());
        
        List<NodeAttributes> output1 = nodesAttributesMap.get(1);
        NodeAttributes n1 = new NodeAttributes("1", null);
        NodeAttributes n11 = new NodeAttributes("1", "1");
        assertTrue(output1.contains(n11));
        int count = 0;
        for (NodeAttributes na : output1) {
            if (n1.equals(na)) {
                count++;
            }
        }
        assertEquals(3, count);
        
        List<NodeAttributes> output2 = nodesAttributesMap.get(2);
        NodeAttributes n2 = new NodeAttributes("2", null);
        NodeAttributes n22 = new NodeAttributes("2", "2");
        int countN2 = 0;
        int countN22 = 0;
        for (NodeAttributes na : output2) {
            if (n2.equals(na)) {
                countN2++;
            } else if (n22.equals(na)) {
                countN22++;
            }
        }
        assertEquals(2, countN2);
        assertEquals(2, countN22);
        
        
        List<NodeAttributes> output3 = nodesAttributesMap.get(3);
        NodeAttributes n33 = new NodeAttributes("3", "3");
        int count33 = 0;
        for (NodeAttributes na : output3) {
            if (n33.equals(na)) {
                count33++;
            }
        }
        assertEquals(2, count33);
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
