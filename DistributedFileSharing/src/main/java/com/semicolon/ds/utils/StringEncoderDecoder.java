package com.semicolon.ds.utils;

import com.semicolon.ds.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.logging.Logger;

public class StringEncoderDecoder {

    private static final Logger LOG = Logger.getLogger(StringEncoderDecoder.class.getName());

    public static String encode(String str){
        try {
            String output = URLEncoder.encode(str, Constants.ENCODE_CLASS);
            return output;
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return str;
        }
    }

    public static String decode(String str){
        try {
            String output = URLDecoder.decode(str, Constants.ENCODE_CLASS);
            return output;
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return str;
        }
    }
}
