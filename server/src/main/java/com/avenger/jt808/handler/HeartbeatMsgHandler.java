package com.avenger.jt808.handler;

import com.avenger.jt808.base.pbody.HeartbeatMsg;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.util.CommonMessageUtils;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Created by jg.wang on 2020/4/28.
 * Description: 心跳维持
 */
@Component
public class HeartbeatMsgHandler implements MessageHandler {
    @Override
    public short getId() {
        return 0x0002;
    }

    @Override
    public Publisher<Message> process(Message m) {
        return Mono.justOrEmpty(Optional.ofNullable(m)
                .filter(message -> message.getMsgBody() instanceof HeartbeatMsg)
                .map(message -> CommonMessageUtils.success(message.getHeader())));
    }
}
