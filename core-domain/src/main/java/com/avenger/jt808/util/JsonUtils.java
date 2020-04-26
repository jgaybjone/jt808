package com.avenger.jt808.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;

//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Classname JsonUtils
 * Description json and bean converted each other
 * Date 2019/5/10 12:00 PM
 * Created by Wang jun gang
 */
@Slf4j
@UtilityClass
public class JsonUtils {

    @Getter
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);


//                Jackson2ObjectMapperBuilder
//                .json()
////                .indentOutput(true) //格式化输出json
//                .failOnEmptyBeans(false) //拆开循环依赖
//                .serializerByType(Long.TYPE, ToStringSerializer.instance)
//                .serializerByType(Long.class, ToStringSerializer.instance)
//                .featuresToEnable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//                .serializationInclusion(JsonInclude.Include.NON_NULL) //null字段json忽略
//                .failOnUnknownProperties(false) //未知字段忽略
//                .build();
    }

    public static String objToJsonStr(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Bean to json string error", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T jsonStrToObj(String json, Class<T> type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            log.error("Json string to bean error", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T jsonStrToObj(String json, JavaType type) {
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            log.error("Json string to bean error", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> Collection<T> jsonStrToObj(String json, Class<? extends Collection> collectionClass,
                                                 Class<T> elementClass) {
        final CollectionType type = objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            log.error("Json string to bean error", e);
            throw new RuntimeException(e);
        }
    }
}
