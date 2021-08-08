/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author abernal
 */
public class PropertiesPathUtil {

    private static final Logger LOG = Logger.getLogger(PropertiesPathUtil.class);

    /**
     * path base is catalina.home
     *
     * @param list
     * @return 
     */
    public String getPath(List<String> list) {
        String path = System.getProperty("catalina.home", ".")
                + System.getProperty("file.separator");
        for (String string : list) {
            path += string + System.getProperty("file.separator");

        }
        return path;
    }

    public Properties getProperties(String path) {
        Properties p = new Properties();
        FileInputStream fileIn;
        try {
            fileIn = new FileInputStream(path);
            p.load(fileIn);
            return (p);
        } catch (IOException ex) {
            LOG.error("Error, file exception", ex);
        }
        return null;
    }

}
