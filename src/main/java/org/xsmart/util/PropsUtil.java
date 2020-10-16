package org.xsmart.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropsUtil {

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
            //todo 记录日志
        }finally {
            if(propFileInputStream != null){
                try {
                    propFileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    //todo 记录日志
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
