package com.enreach.ssm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class JsonUtil {

    /**
     * 序列化对象
     *
     * @param object
     * @param formatDate false：时间返回时间戳，true:format yyyy-MM-dd'T'HH:mm:ss.SSSZ
     * @return
     * @throws JsonProcessingException
     */
    public static String serialize(Object object, boolean formatDate) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if (formatDate) {
            //设置JSON时间格式
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            mapper.setDateFormat(dateFormat);
        }

        return mapper.writeValueAsString(object);
    }


    /**
     * 反序列化对象，返回class
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T deserialize(String json, Class<T> type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, type);
    }

    /**
     * 反序列号json集合 返回 List<Class>
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> deserializeArray(String json, Class<T> clazz) throws IOException {

        //configure json是单个对象时也可以解析

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

}
