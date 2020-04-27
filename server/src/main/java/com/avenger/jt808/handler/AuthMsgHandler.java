package com.avenger.jt808.handler;

import com.avenger.jt808.base.pbody.AuthMsg;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.domain.entity.Terminal;
import com.avenger.jt808.service.TerminalService;
import com.avenger.jt808.util.CommonMessageUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Created by jg.wang on 2020/4/27.
 * Description:
 */
@Component
@RequiredArgsConstructor
public class AuthMsgHandler implements MessageHandler {

    @NonNull
    private final TerminalService terminalService;

    @Override
    public short getId() {
        return 0x0102;
    }

    @Override
    public Publisher<Message> process(Message message) {
        return Mono.justOrEmpty(Optional.ofNullable(message)
                .filter(m -> m.getMsgBody() instanceof AuthMsg)
                .flatMap(m -> {
                    final Header h = m.getHeader();
                    final AuthMsg b = (AuthMsg) m.getMsgBody();
                    return terminalService.findOne(Example.of(Terminal.builder()
                            .simNo(h.getSimNo())
                            .build())).map(terminal -> {
                        if (terminal.getAuthCode().equals(b.getAuthCode())) {
                            return CommonMessageUtils.success(h);
                        }
                        return null;
                    });
                }));
    }

}
