package com.example.recallbackend.utils;

import com.example.recallbackend.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author tzih
 * @date 2022.09.20
 */
public class Md5Util {

    public static String getMD5Str(String strValue) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return Base64.encodeBase64String(md5.digest((strValue + Constant.SALT).getBytes()));
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
