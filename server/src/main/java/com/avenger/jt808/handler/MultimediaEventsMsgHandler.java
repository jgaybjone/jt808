package com.avenger.jt808.handler;

import com.avenger.jt808.base.pbody.MultimediaEventsMsg;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.domain.entity.MultimediaEvent;
import com.avenger.jt808.service.MultimediaEventService;
import com.avenger.jt808.util.CommonMessageUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Created by jg.wang on 2020/4/30.
 * Description: 多媒体消息事件处理
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MultimediaEventsMsgHandler implements MessageHandler {

    @NonNull
    private final MultimediaEventService multimediaEventService;

    @Override
    public short getId() {
        return 0x0800;
    }

    @Override
    public Publisher<Message> process(Message m) {
        return Mono.justOrEmpty(m)
                .filter(message -> message.getMsgBody() instanceof MultimediaEventsMsg)
                .map(message -> {
                    final Header h = message.getHeader();
                    final MultimediaEventsMsg b = (MultimediaEventsMsg) message.getMsgBody();

                    multimediaEventService.saveEntity(MultimediaEvent
                            .builder()
                            .multimediaId(b.getId())
                            .type(b.getType())
                            .encodingFormat(b.getFormat())
                            .simNo(h.getSimNo())
                            .eventType(b.getEventItem())
                            .channel(((int) b.getChannelId()))
                            .build());
                    return CommonMessageUtils.success(h);
                });
    }
}
