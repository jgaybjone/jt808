package com.avenger.jt808.handler;

import com.avenger.jt808.AlarmAccessoriesMsg;
import com.avenger.jt808.CommonMessageUtils;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.server.MessageHandler;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

/**
 * Created by jg.wang on 2020/4/24.
 * Description:
 */
public class AlarmAccessoriesHandler implements MessageHandler {
    @Override
    public int getId() {
        return 0x1210;
    }

    @Override
    public Publisher<Message> process(Message message) {
        return Mono.just(message)
                .filter(m -> m.getMsgBody() instanceof AlarmAccessoriesMsg)
                .map(m -> ((AlarmAccessoriesMsg) m.getMsgBody()))
                .map(body -> {
                    // todo
                    final Header h = message.getHeader();
                    return CommonMessageUtils.success(h);
                });
    }
}
