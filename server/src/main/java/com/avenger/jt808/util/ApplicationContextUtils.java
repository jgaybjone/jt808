package com.avenger.jt808.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Component
@Lazy(value = false)
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext CTX;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        CTX = applicationContext;
        final RedisTemplate redisTemplate = (RedisTemplate) applicationContext.getBean("redisTemplate");
        SerialNoUtils.setSupplier(() -> redisTemplate.opsForValue().increment("header_serial", 1L).shortValue());
    }

    public static void publish(ApplicationEvent event) {
        CTX.publishEvent(event);
    }

    public static <T> T getBean(Class<T> tClass) {
        return CTX.getBean(tClass);
    }

}
