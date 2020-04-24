package com.avenger.jt808.server;

import com.avenger.jt808.domain.Message;
import org.reactivestreams.Publisher;

/**
 * Created by jg.wang on 2020/4/22.
 * Description:
 */
public interface MessageHandler {

    int getId();

    Publisher<Message> process(Message message);
}
