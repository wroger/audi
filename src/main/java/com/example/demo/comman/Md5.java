package com.example.demo.comman;

import java.io.UnsupportedEncodingException;

import org.springframework.util.DigestUtils;

public class Md5 {

    public static String encryptToMD5(String str) {
        String md5 = "  ";
        try {
            md5 = DigestUtils.md5DigestAsHex(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return md5;
    }
}