package com.avenger.jt808.handler;

import com.avenger.jt808.AttachmentInformationMsg;
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
public class AttachmentInformationHandler implements MessageHandler {
    @Override
    public short getId() {
        return 0x1211;
    }

    @Override
    public Publisher<Message> process(Message message) {
        return Mono.just(message).filter(m -> m.getMsgBody() instanceof AttachmentInformationMsg)
                .map(m -> {
                    final Header h = m.getHeader();
                    final AttachmentInformationMsg b = (AttachmentInformationMsg) m.getMsgBody();
                    // todo 处理消息
                    return CommonMessageUtils.success(h);
                });
    }
}
