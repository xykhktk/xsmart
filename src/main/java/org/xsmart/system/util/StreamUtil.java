package org.xsmart.system.util;

import org.apache.log4j.Logger;

import java.io.*;

public class StreamUtil {

    private static Logger logger = Logger.getLogger(StreamUtil.class);

    public static String getString(InputStream is){

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }catch (IOException e){
            logger.error("StreamUtil getString error :" + e.getMessage());
        }
        return stringBuilder.toString();
    }

}
