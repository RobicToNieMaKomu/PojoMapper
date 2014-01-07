package com.polmos.pojomapper.service;

import java.io.IOException;
import org.w3c.dom.Document;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface HelperUtils {
 
    Document loadXmlFromPath(String path) throws IOException;
}
