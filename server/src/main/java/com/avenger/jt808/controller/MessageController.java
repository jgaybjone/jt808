package com.avenger.jt808.controller;

import com.avenger.jt808.base.tbody.ShootAtOnceMsg;
import com.avenger.jt808.controller.vo.BaseMsgVO;
import com.avenger.jt808.domain.EncryptionForm;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.server.TermConnManager;
import io.netty.channel.ChannelFuture;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Created by jg.wang on 2020/4/30.
 * Description:
 */
@RestController
@Slf4j
public class MessageController {

    @PostMapping("shoot")
    public Mono<?> send(@RequestBody Shoot shoot) {
        return Mono.create(monoSink -> {
            Header header = new Header(shoot.getSimNo(), false, EncryptionForm.NOTHING);
            Message message = new Message();
            message.setHeader(header);
            message.setMsgBody(shoot.getBody());
            final ChannelFuture channelFuture = TermConnManager.sendMessage(message);
            if (channelFuture != null && channelFuture.isSuccess()) {
                monoSink.success(BaseMsgVO.builder()
                        .message("发送成功")
                        .build());
            } else {
                monoSink.success(BaseMsgVO.builder()
                        .error("发送失败")
                        .detail("设备不在线")
                        .build());
            }
        });

    }

    @Data
    private static class Shoot {
        String simNo;
        ShootAtOnceMsg body;
    }
}
