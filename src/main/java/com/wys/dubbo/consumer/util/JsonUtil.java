package com.wys.dubbo.consumer.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JsonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtil() {

   }

    public static ObjectMapper getInstance() {

        return objectMapper;
   }

    /**
     * javaBean,list,array convert to json string
     */
    public static String toString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
       } catch (Exception e) {
            return null;
       }

   }

    /**
     * json string convert to javaBean
     */
    public static <T> T toPojo(String jsonStr, Class<T> clazz) {
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(jsonStr, clazz);
       } catch (IOException e) {
            e.printStackTrace();
       }
        return null;
   }

    /**
     * json string convert to map
     */
    public static <T> Map<String, Object> toMap(String jsonStr)
            throws Exception {
        return objectMapper.readValue(jsonStr, Map.class);
   }

    /**
     * json string convert to map with javaBean
     */
    public static <T> Map<String, T> toMap(String jsonStr, Class<T> clazz)
            throws Exception {
        Map<String, T> map = objectMapper.readValue(jsonStr,
                new TypeReference<Map<String, T>>() {
               });
        Map<String, T> result = new HashMap<>();
        for (Entry<String, T> entry : map.entrySet()) {
            result.put(entry.getKey(), toPojo((Map) entry.getValue(), clazz));
       }
        return result;
   }

    /**
     * json array string convert to list with javaBean
     */
    public static <T> List<T> toList(String jsonArrayStr, Class<T> clazz)
            throws Exception {
        List<T> list = objectMapper.readValue(jsonArrayStr,
                new TypeReference<List<T>>() {
               });
        List<T> result = new ArrayList<T>();
        for (T map : list) {
            result.add(toPojo((Map) map, clazz));
       }
        return result;
   }

    /**
     * map convert to javaBean
     */
    public static <T> T toPojo(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
   }

}