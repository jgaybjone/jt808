package com.avenger.jt808.handler;

import com.avenger.jt808.base.pbody.QueryMediaRespMsg;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.service.MessageRecordService;
import com.avenger.jt808.util.ApplicationContextUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Created by jg.wang on 2020/5/8.
 * Description:
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class QueryMediaRespMsgHandler implements MessageHandler {

    @Override
    public short getId() {
        return 0x0802;
    }

    @Override
    public Publisher<Message> process(Message message) {
        return Mono.justOrEmpty(message)
                .filter(m -> m.getMsgBody() instanceof QueryMediaRespMsg)
                .doOnSuccess(m -> {
                    ApplicationContextUtils.publish(new MessageRecordService.MessageRecordEvent(m));
                });
    }
}
