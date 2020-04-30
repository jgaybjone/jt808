package com.avenger.jt808.controller;

import com.avenger.jt808.base.tbody.ShootAtOnceMsg;
import com.avenger.jt808.domain.EncryptionForm;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.server.TermConnManager;
import lombok.Data;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

/**
 * Created by jg.wang on 2020/4/30.
 * Description:
 */
@Controller
public class MessageController {

    @MessageMapping("shoot")
    public Mono<?> send(Shoot shoot) {
        Header header = new Header(shoot.getSimNo(), false, EncryptionForm.NOTHING);
        Message message = new Message();
        message.setHeader(header);
        message.setMsgBody(shoot.getBody());
        TermConnManager.sendMessage(message);
        return Mono.empty();
    }

    @Data
    private static class Shoot {
        String simNo;
        ShootAtOnceMsg body;
    }
}
