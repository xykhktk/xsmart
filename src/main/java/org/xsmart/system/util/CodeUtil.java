package org.xsmart.system.util;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CodeUtil {

    public static String UrlEncode(String source){
        String result = "";
        try {
            result = URLEncoder.encode(source,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String UrlDecode(String source){
        String result = "";
        try {
            result = URLDecoder.decode(source,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
