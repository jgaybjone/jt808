package com.avenger.jt808.handler;

import com.avenger.jt808.base.pbody.CommonMsg;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.service.MessageRecordService;
import com.avenger.jt808.util.ApplicationContextUtils;
import com.avenger.jt808.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Created by jg.wang on 2020/4/29.
 * Description: 终端通用应答处理
 */
@SuppressWarnings({"rawtypes", "CallingSubscribeInNonBlockingScope"})
@Component
@Slf4j
@AllArgsConstructor
public class CommonMsgHandler implements MessageHandler {

    private final ReactiveRedisTemplate reactiveRedisTemplate;

    @Override
    public short getId() {
        return 0x0001;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Publisher<Message> process(Message m) {
        return Optional.ofNullable(m)
                .filter(message -> message.getMsgBody() instanceof CommonMsg)
                .map(message -> {
                    final Header h = message.getHeader();
                    final CommonMsg b = (CommonMsg) message.getMsgBody();
                    final Mono<Message> mono = reactiveRedisTemplate.opsForValue().get(h.getSimNo() + "::" + b.getRespSerialNo());
                    switch (b.getResult()) {
                        case FAIL:
                            return mono;
                        case BAD_MSG:
                            mono.subscribe(v -> log.error("message send fail! :{}", JsonUtils.objToJsonStr(v)));
                            break;
                        case NOT_SUPPORT:
                            mono.subscribe(v -> log.error("terminal not support message! :{}", JsonUtils.objToJsonStr(v)));
                            break;
                        default:
                            break;
                    }
                    ApplicationContextUtils.publish(new MessageRecordService.RespMessageRecordEvent(m));
                    return Mono.<Message>empty();
                }).orElse(Mono.empty());

    }
}
