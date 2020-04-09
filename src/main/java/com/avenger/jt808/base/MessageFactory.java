package com.avenger.jt808.base;

import com.avenger.jt808.annotation.ReadingMessageType;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@SuppressWarnings("unchecked")
@Slf4j
@Component
public class MessageFactory implements ApplicationContextAware {

    final private static Map<Short, Class<Body>> BODY_TYPE_MAPPING = new HashMap<>();

    private final String scanPackage = "com.avenger.jt808.base.pbody";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            final Resource[] resources = applicationContext.getResources(String.format("%s/*.class", scanPackage.replaceAll("\\.", "/")));
            BODY_TYPE_MAPPING.putAll(Arrays.stream(resources)
                    .map(r -> scanPackage + "." + r.getFilename())
                    .map(r -> r.replace(".class", ""))
                    .map(c -> {
                        try {
                            return Thread.currentThread().getContextClassLoader().loadClass(c);
                        } catch (ClassNotFoundException e) {
                            log.error("加载消息类失败", e);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(Body.class::isAssignableFrom)
                    .map(c -> (Class<Body>) c)
                    .map(bodyClass -> {
                        final ReadingMessageType annotation = bodyClass.getAnnotation(ReadingMessageType.class);
                        if (annotation == null) {
                            return null;
                        }
                        final ReadingMsgClass readingMsgClass = new ReadingMsgClass();
                        readingMsgClass.setReadingMessageType(annotation);
                        readingMsgClass.setBodyClass(bodyClass);
                        return readingMsgClass;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(type -> type.getReadingMessageType().type(), ReadingMsgClass::getBodyClass)));
        } catch (IOException e) {
            log.error("io error", e);
        }
    }


    @Data
    private static class ReadingMsgClass {
        private ReadingMessageType readingMessageType;
        private Class<Body> bodyClass;
    }


    public Message create(ByteBuf byteBuf) throws IllegalAccessException, InstantiationException {
        if (CollectionUtils.isEmpty(BODY_TYPE_MAPPING)) {
            throw new RuntimeException("未找到任何消息接收类");
        }
        final Header header = new Header(byteBuf);
        final Class<Body> bodyClass = BODY_TYPE_MAPPING.get(header.getId());
        final Body body = bodyClass.newInstance();
        body.deSerialize(byteBuf);
        final Message message = new Message();
        message.setHeader(header);
        message.setMsgBody(body);
        return message;
    }

}
