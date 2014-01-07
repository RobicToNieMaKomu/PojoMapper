package com.polmos.pojomapper.service;

import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

/**
 *
 * @author RobicToNieMaKomu
 */
public class HelperUtilsTest {

    private static HelperUtils helperUtils;
    
    @BeforeClass
    public static void setup() {
        helperUtils = new HelperUtilsImpl();
    }
    
    @Test(expected = IOException.class)
    public void testLoadNonExistingXml() throws IOException {
        helperUtils.loadXmlFromPath("X/Y/Z/doc");
    }
    
    @Test(expected = IOException.class)
    public void testLoadXmlForNullPath() throws IOException {
        helperUtils.loadXmlFromPath(null);
    }
    
    @Test
    public void testLoadExistingXml() throws IOException {
        Document xmlDoc = helperUtils.loadXmlFromPath("test.xml");
    }
}
