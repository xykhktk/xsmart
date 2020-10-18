package org.xsmart.system.util;

import java.io.*;

public class StreamUtil {

    public static String getString(InputStream is){

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }catch (IOException e){
            // todo log
        }
        return stringBuilder.toString();
    }

}
