package com.wys.dubbo.consumer.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Description:
 */
public class Base64Util {

    public static String decode(String text) {
        try {
            return new String(Base64.getDecoder().decode(text), StandardCharsets.UTF_8);
       } catch (Exception e) {
            return text;
       }
   }

    public static String encode(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
   }
}
