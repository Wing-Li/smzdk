package com.lyl.smzdk.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author: lyl
 * Date Created : 2017/12/17.
 */

public class Md5Utils {

    /**
     * MD5加密
     * <br>http://stackoverflow.com/questions/1057041/difference-between-java-and-php5-md5-hash
     * <br>http://code.google.com/p/roboguice/issues/detail?id=89
     *
     * @param string 源字符串
     * @return 加密后的字符串
     */
    public static String md5(String string) {
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
