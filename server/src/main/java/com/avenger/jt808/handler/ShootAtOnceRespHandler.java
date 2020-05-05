package com.avenger.jt808.handler;

import com.avenger.jt808.base.pbody.ShootAtOnceRespMsg;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Created by jg.wang on 2020/5/2.
 * Description: 摄像头立即拍摄命令应答处理
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Component
@Slf4j
@RequiredArgsConstructor
public class ShootAtOnceRespHandler implements MessageHandler {

    @NonNull
    private final ReactiveRedisTemplate reactiveRedisTemplate;

    @Override
    public short getId() {
        return 0x0805;
    }

    @Override
    public Publisher<Message> process(Message message) {
        return Mono.<Message>empty()
                .doOnSuccess(v -> Optional.of(message)
                        .filter(m -> m.getMsgBody() instanceof ShootAtOnceRespMsg)
                        .ifPresent(m -> {
                            final Header h = m.getHeader();
                            final ShootAtOnceRespMsg b = (ShootAtOnceRespMsg) m.getMsgBody();
                            final short respSerialNo = b.getRespSerialNo();
                            reactiveRedisTemplate
                                    .delete(h.getSimNo() + "::" + respSerialNo)
                                    .subscribe();
                        }));
    }
}
