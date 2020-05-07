package com.avenger.jt808.controller;

import com.avenger.jt808.base.tbody.ShootAtOnceMsg;
import com.avenger.jt808.domain.EncryptionForm;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.server.TermConnManager;
import com.avenger.jt808.service.MessageRecordService;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

/**
 * Created by jg.wang on 2020/4/30.
 * Description:
 */
@RestController
@Slf4j
@RequiredArgsConstructor
//@Api(tags = "消息发送")
public class MessageController {

    @NonNull
    private final MessageRecordService messageRecordService;

    @PostMapping(value = "shoot", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    @ApiOperation(value = "立即拍摄")
    public Flux<?> send(@RequestBody Shoot shoot) {
        Header header = new Header(shoot.getSimNo(), false, EncryptionForm.NOTHING);
        Message message = new Message();
        message.setHeader(header);
        message.setMsgBody(shoot.getBody());
        TermConnManager.sendMessage(message);
        return messageRecordService.sendCheck(header.getSimNo(), header.getSerialNo());

    }

    @Data
    private static class Shoot {
        private String simNo;
        private ShootAtOnceMsg body;
    }
}
