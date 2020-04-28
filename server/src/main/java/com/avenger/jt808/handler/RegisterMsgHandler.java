package com.avenger.jt808.handler;

import com.avenger.jt808.base.pbody.RegisterMsg;
import com.avenger.jt808.base.tbody.RegisterRespMsg;
import com.avenger.jt808.domain.EncryptionForm;
import com.avenger.jt808.domain.Header;
import com.avenger.jt808.domain.Message;
import com.avenger.jt808.domain.entity.Terminal;
import com.avenger.jt808.enums.RegisterResult;
import com.avenger.jt808.service.TerminalService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Created by jg.wang on 2020/4/27.
 * Description:
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RegisterMsgHandler implements MessageHandler {

    @NonNull
    private final TerminalService terminalService;

    @Override
    public short getId() {
        return 0x0100;
    }

    @Override
    public Publisher<Message> process(Message message) {
        return Mono.just(message)
                .filter(m -> m.getMsgBody() instanceof RegisterMsg)
                .map(m -> {
                    final Header h = m.getHeader();
                    final RegisterMsg b = (RegisterMsg) m.getMsgBody();
                    final Terminal terminal = Terminal
                            .builder()
                            .termId(b.getTerminalId())
                            .manufacturerCode(b.getManufactureId())
                            .color((short) b.getPlateColor())
                            .province(b.getProvinceId())
                            .model(b.getTerminalModel())
                            .vehicleIdent(b.getPlateNo())
                            .city(b.getCityId())
                            .simNo(h.getSimNo())
                            .build();
                    final boolean result = terminalService.register(terminal);
                    if (result) {
                        log.info("注册成功");
                    } else {
                        log.info("已存在终端，注册失败");
                    }
                    final RegisterRespMsg body = new RegisterRespMsg();
                    body.setAuthCode(terminal.getAuthCode());
                    body.setResult(RegisterResult.OK);
                    body.setRespSerial(h.getSerialNo());
                    final Header header = new Header(h.getSimNo(), false, EncryptionForm.NOTHING);
                    final Message resp = new Message();
                    resp.setHeader(header);
                    resp.setMsgBody(body);
                    return resp;
                });
    }
}
