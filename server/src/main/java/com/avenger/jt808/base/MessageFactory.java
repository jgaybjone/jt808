package com.avenger.jt808.base;

import com.avenger.jt808.base.pbody.Additional;
import com.avenger.jt808.base.pbody.UnknownAdditional;
import com.avenger.jt808.domain.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
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
    final private static Map<Byte, Class<Additional>> ADDITIONAL_TYPE_MAPPING = new HashMap<>();

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
                        final ReadingMsgClass<Body> readingMsgClass = new ReadingMsgClass<>();
                        readingMsgClass.setReadingMessageType(annotation);
                        readingMsgClass.setBodyClass(bodyClass);
                        return readingMsgClass;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(type -> type.getReadingMessageType().type(), ReadingMsgClass::getBodyClass)));

            ADDITIONAL_TYPE_MAPPING.putAll(Arrays.stream(resources).map(r -> scanPackage + "." + r.getFilename())
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
                    .filter(Additional.class::isAssignableFrom)
                    .map(c -> (Class<Additional>) c)
                    .map(additionalClass -> {
                        final AdditionalAble annotation = additionalClass.getAnnotation(AdditionalAble.class);
                        if (annotation == null) {
                            return null;
                        }
                        final ReadingMsgClass<Additional> readingMsgClass = new ReadingMsgClass<>();
                        readingMsgClass.setAdditionalAble(annotation);
                        readingMsgClass.setBodyClass(additionalClass);
                        return readingMsgClass;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(type -> type.getAdditionalAble().type(), ReadingMsgClass::getBodyClass)));
        } catch (IOException e) {
            log.error("io error", e);
        }
    }


    @Data
    private static class ReadingMsgClass<T> {
        private ReadingMessageType readingMessageType;
        private AdditionalAble additionalAble;
        private Class<T> bodyClass;
    }


    public Message create(ByteBuf byteBuf) throws IllegalAccessException, InstantiationException {
        if (CollectionUtils.isEmpty(BODY_TYPE_MAPPING)) {
            throw new RuntimeException("未找到任何消息接收类");
        }
        final Header header = new Header(byteBuf);
        final Class<Body> bodyClass = BODY_TYPE_MAPPING.get(header.getId());
        final Body body = bodyClass.newInstance();
        final byte[] bytes = new byte[header.getBodySize()];
        byteBuf.readBytes(bytes);
        body.deSerialize(Unpooled.wrappedBuffer(bytes));
        final Message message = new Message();
        message.setHeader(header);
        message.setMsgBody(body);
        return message;
    }

    public List<Additional> parse(ByteBuf byteBuf) {
        List<Additional> adds = new ArrayList<>();
        while (byteBuf.isReadable()) {
            final byte id = byteBuf.readByte();
            final Class<Additional> additionalClass = ADDITIONAL_TYPE_MAPPING.get(id);
            if (additionalClass == null) {
                log.warn("未知的附协议 :{}", id);
                final UnknownAdditional un = new UnknownAdditional();
                un.setId(id);
                final byte len = byteBuf.readByte();
                final byte[] bytes = new byte[len];
                byteBuf.readBytes(bytes);
                un.setRaw(bytes);
                adds.add(un);
            } else {
                try {
                    final Additional additional = additionalClass.newInstance();
                    additional.setId(id);
                    final byte len = byteBuf.readByte();
                    final byte[] bytes = new byte[len];
                    byteBuf.readBytes(bytes);
                    additional.deSerialize(Unpooled.wrappedBuffer(bytes));
                    adds.add(additional);
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("实例化附加协议出错:" + additionalClass.getName(), e);
                }
            }
        }

        return adds;
    }

}
