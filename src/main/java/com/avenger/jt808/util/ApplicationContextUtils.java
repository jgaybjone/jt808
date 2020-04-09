package com.avenger.jt808.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * Created by jg.wang on 2020/4/9.
 * Description:
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext CTX;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        CTX = applicationContext;
    }

    public static void publish(ApplicationEvent event) {
        CTX.publishEvent(event);
    }

}
