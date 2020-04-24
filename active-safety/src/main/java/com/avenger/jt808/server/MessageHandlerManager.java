package com.avenger.jt808.server;

import com.avenger.jt808.domain.Message;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Collection;

/**
 * Created by jg.wang on 2020/4/22.
 * Description:
 */
@RequiredArgsConstructor
public class MessageHandlerManager {

    @NonNull
    private final Collection<MessageHandler> handlerMap;

    public Publisher<Message> process(Message message) {
        return Flux.fromStream(handlerMap.stream().filter(messageHandler -> messageHandler.getId() == message.getHeader().getId()))
                .flatMap(messageHandler -> messageHandler.process(message))
                .subscribeOn(Schedulers.parallel())
                .onErrorResume(Flux::error);
    }


}
