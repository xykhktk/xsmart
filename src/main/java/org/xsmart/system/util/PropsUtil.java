package org.xsmart.system.util;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropsUtil {

    private static Logger logger = Logger.getLogger(PropsUtil.class);

    public static Properties loadPropFile(String fileName){
        Properties properties = null;
        InputStream propFileInputStream = null;

        try{
            propFileInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(propFileInputStream == null){
                throw new FileNotFoundException("load prop file:" + fileName + " ,file not found");
            }
            properties = new Properties();
            properties.load(propFileInputStream);
        } catch (IOException e){
            logger.error("PropsUtil loadPropFile error :" + e.getMessage());
        }finally {
            if(propFileInputStream != null){
                try {
                    propFileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("PropsUtil close inputStream error :" + e.getMessage());
                }
            }
        }

        return properties;
    }

    public static String getString(Properties p,String key){
        return getString(p,key,"");
    }

    public static String getString(Properties p,String key,String defaultValue){
        if(p.containsKey(key)){
            return p.getProperty(key);
        }
        return defaultValue;
    }

}
