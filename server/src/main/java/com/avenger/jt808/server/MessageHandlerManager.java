package com.avenger.jt808.server;

import com.avenger.jt808.domain.Message;
import com.avenger.jt808.handler.MessageHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by jg.wang on 2020/4/22.
 * Description:
 */
@Component
@RequiredArgsConstructor
public class MessageHandlerManager {

    private Collection<MessageHandler> handlerMap = Collections.emptyList();

    @NonNull
    private final ApplicationContext applicationContext;


    @PostConstruct
    public void init() {
        handlerMap = applicationContext.getBeansOfType(MessageHandler.class)
                .values();
    }

    public Publisher<Message> process(Message message) {
        return Flux.fromStream(handlerMap.stream().filter(messageHandler -> messageHandler.getId() == message.getHeader().getId()))
                .flatMap(messageHandler -> messageHandler.process(message))
                .subscribeOn(Schedulers.parallel())
                .onErrorResume(Flux::error);
    }


}
