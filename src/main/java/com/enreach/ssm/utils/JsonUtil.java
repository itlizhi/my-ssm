package com.enreach.ssm.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JsonUtil {

    //https://www.cnblogs.com/peida/archive/2013/06/08/3120820.html


    public String serialize(Object object) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        //设置JSON时间格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(dateFormat);
        return mapper.writeValueAsString(object);
    }


    public <T> T deserialize(String json, Class<T> type) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, type);
    }

}
