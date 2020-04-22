package com.avenger.jt808.base;

import org.reactivestreams.Publisher;

/**
 * Created by jg.wang on 2020/4/22.
 * Description:
 */
public interface MessageHandler {

    int getId();

    Publisher<Message> process(Message message);
}
